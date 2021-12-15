package com.fhir.connectathon.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.EnumUtils;
import org.apache.log4j.Logger;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.DecimalType;
import org.hl7.fhir.r4.model.Observation;
import org.hl7.fhir.r4.model.Observation.ObservationReferenceRangeComponent;
import org.hl7.fhir.r4.model.Observation.ObservationStatus;
import org.hl7.fhir.r4.model.Quantity;
import org.hl7.fhir.r4.model.Reference;
import org.hl7.fhir.r4.model.ServiceRequest;
import org.hl7.fhir.r4.model.ServiceRequest.ServiceRequestIntent;
import org.hl7.fhir.r4.model.ServiceRequest.ServiceRequestStatus;
import org.hl7.fhir.r4.model.SimpleQuantity;

import com.fhir.connectathon.constants.FHIRConstants;
import com.fhir.connectathon.utility.FHIRUtility;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.Varies;
import ca.uhn.hl7v2.model.v26.datatype.CWE;
import ca.uhn.hl7v2.model.v26.datatype.CX;
import ca.uhn.hl7v2.model.v26.group.ORU_R01_OBSERVATION;
import ca.uhn.hl7v2.model.v26.group.ORU_R01_ORDER_OBSERVATION;
import ca.uhn.hl7v2.model.v26.group.ORU_R01_PATIENT_RESULT;
import ca.uhn.hl7v2.model.v26.message.ORU_R01;
import ca.uhn.hl7v2.model.v26.segment.OBR;
import ca.uhn.hl7v2.model.v26.segment.OBX;

/**
 * This class is used for mapping  ORU_R01 HL7 V2 message to FHIR Observation resource. This class maps below fields
 * Status, Value, Code, Units, Reference Range, subject, basedOn, 
 * */
public class ObservationMapper  {
	static Logger log = Logger.getLogger(ObservationMapper.class.getName()); 
	
	/**
	 *  This method will parse the HL7 ORU HL7 v2 message to FHIR Observation
	 *  This method takes HAPI message and returns FHIR Observation Resource 
	 * */
	public List<Observation> convertORUToFHIRObservation(Message hapiMsg, Map<String,String> patientIdMaps,boolean secure){
		List<Observation> observations = new ArrayList<Observation>();
		//get the ORU message from hl7 hapi message
		ORU_R01 oruMsg = (ORU_R01) hapiMsg;
		
		//get patient result 
		ORU_R01_PATIENT_RESULT patientResult = oruMsg.getPATIENT_RESULT();
		
		// get patient's identifier list
		CX[] patientIdentifiers = patientResult.getPATIENT().getPID().getPatientIdentifierList();
		//get patient identifier which is used later to map for subject
		String patientIdentifier = patientIdentifiers[0].getCx1_IDNumber().getValue();
		String patientId = null;
		if(patientIdMaps.containsKey(patientIdentifier)){
			patientId = patientIdMaps.get(patientIdentifier);
		}
		
		try {
			//get order observation i.e. OBR segments from ORU message
			List<ORU_R01_ORDER_OBSERVATION> orderObservations =   patientResult.getORDER_OBSERVATIONAll();
			for (ORU_R01_ORDER_OBSERVATION oru_R01_ORDER_OBSERVATION : orderObservations) {
				//get observation order
				OBR  obr =  oru_R01_ORDER_OBSERVATION.getOBR();
				
				// map observation order to FHIR Service Request 
				ServiceRequest serviceRequest =  convertV2ObservationRequestToFHIRServiceRequest(patientId,obr);
				
				//get the ServiceRequest Id which is used later to map basedOn field
				String serviceRequestId = FHIRUtility.getServiceRequestReferenceId(serviceRequest,secure);
				
				//get the list of observations for a observation order 
				List<ORU_R01_OBSERVATION> oru_ro1_observations =  oru_R01_ORDER_OBSERVATION.getOBSERVATIONAll();
				for (ORU_R01_OBSERVATION oru_R01_OBSERVATION : oru_ro1_observations) {
					
					Observation observation = new Observation();
		
					//get the observation 
					OBX obx =  oru_R01_OBSERVATION.getOBX();
		
					//map OBX-11
					setObservationStatus(observation, obx); 
					//mapp OBX-3
					setObservationCode(observation, obx);
					
					//Get the unit from OBX-6
					String unit = obx.getObx6_Units().getIdentifier().getValue();
					//map OBX-5 and OBX-6
					setObservationValue(observation, obx,unit);
					//map OBX-7
					setObservationRefrenceRange(observation, obx, unit); 
					//map ORC
					setObservationRequest(serviceRequestId, observation); 
					// map subject 
					setObservationSubject(patientId, observation);
					observations.add(observation);
				}
			}
		} catch (HL7Exception e) {
			log.error(e.getMessage());
		}
		return observations;
	}

	/**
	 * This method sets Patient reference in subject field of Observation 
	 * */
	private void setObservationSubject(String patientId, Observation observation) {
		if(patientId != null){
			observation.setSubject(new Reference(FHIRConstants.FHIR_PATIENT_REFERENCE+patientId));
		}
	}

	/**
	 * This method sets ServiceRequest reference in basedOn field of Observation 
	 * */
	private void setObservationRequest(String serviceRequestId, Observation observation) {
		List<Reference> basedOn = new ArrayList<Reference>();
		if(serviceRequestId != null) {
			Reference obrReference = new Reference(FHIRConstants.FHIR_SERVICE_REQUEST_REFERENCE + serviceRequestId);
			basedOn.add(obrReference);
			observation.setBasedOn(basedOn);
		}
	}

	/**
	 * This method maps Reference range high and low.
	 *  Assuming that reference rage is separated by '-' and it has both values i.e. low and high
	 * */
	private void setObservationRefrenceRange(Observation observation, OBX obx, String unit) {
		String referenceRangeTxt = obx.getObx7_ReferencesRange().getValue();		
		if(referenceRangeTxt != null && !referenceRangeTxt.isEmpty() && referenceRangeTxt.contains("-")){
			List<ObservationReferenceRangeComponent> theReferenceRange = new ArrayList<ObservationReferenceRangeComponent>();
			 
			SimpleQuantity lowQuantity =  new SimpleQuantity();
			SimpleQuantity highQuantity =  new SimpleQuantity();
			String rangValues[] =	referenceRangeTxt.split("-");
			
			if(!rangValues[0].isEmpty()) {
				lowQuantity.setUnit(unit);
				lowQuantity.setValueElement(new DecimalType(rangValues[0]));
				lowQuantity.setSystem(FHIRConstants.FHIR_CODE_SYSTEM);
			}
			
			if(!rangValues[1].isEmpty()) {	
				highQuantity.setUnit(unit);
				highQuantity.setValueElement(new DecimalType(rangValues[1]));
				highQuantity.setSystem(FHIRConstants.FHIR_CODE_SYSTEM);
			}
			
			ObservationReferenceRangeComponent rangeComponent = new ObservationReferenceRangeComponent();
			rangeComponent.setLow(lowQuantity);
			rangeComponent.setHigh(highQuantity);
			theReferenceRange.add(rangeComponent);			
			observation.setReferenceRange(theReferenceRange);
			
			
		}
	}

	/**
	 * This method sets actual observation value(OBX5) and units(OBX6)
	 * */
	private String setObservationValue(Observation observation, OBX obx, String unit) {
		Varies varies[] =  obx.getObx5_ObservationValue();
		Quantity quantity =  new Quantity();
		quantity.setUnit(unit);
		quantity.setSystem(FHIRConstants.FHIR_CODE_SYSTEM);
		 
		if(varies.length > 0){
			String valueStr = varies[0].getData().toString();
			quantity.setValueElement(new DecimalType(valueStr));
		}
		observation.setValue(quantity);
		return unit;
	}

	/**
	 * This maps OBX-3 i.e. Observation code which contains loinc code,Systemt URL and display
	 * */
	private void setObservationCode(Observation observation, OBX obx) {
		
		String observationLoincCode = obx.getObx3_ObservationIdentifier().getCwe1_Identifier().getValue();
		String observationText = obx.getObx3_ObservationIdentifier().getCwe2_Text().getValue();
		String observationDisplay = obx.getObx3_ObservationIdentifier().getCwe5_AlternateText().getValue();

		CodeableConcept observationCode = new CodeableConcept();
		
		observationCode.setText(observationText);
		
		Coding coding = new Coding();
		coding.setCode(observationLoincCode);
		coding.setSystem(FHIRConstants.LOINC_CODE_SYSTEM_URL);
		coding.setDisplay(observationDisplay);

		observationCode.getCoding().add(coding);				
		observation.setCode(observationCode);
	}

	/**
	 * This method sets observation status i.e OBX-11
	 * */
	private void setObservationStatus(Observation observation, OBX obx) {
		String observationStatus = obx.getObx11_ObservationResultStatus().getValue();
		if(EnumUtils.isValidEnum(ObservationStatus.class, observationStatus.toUpperCase())){
			observation.setStatus(ObservationStatus.valueOf(observationStatus.toUpperCase()));
		}
	}
	
	/**
	 * This method converts OBR segment to FHIR ServiceRequest resource. This maps below fields
	 * Identifier, Intent, Status, Code, Display, Text, Subject and Requester 
	 * */
	public ServiceRequest convertV2ObservationRequestToFHIRServiceRequest(String patientId, OBR obr) {
		//get Identifier
		CWE	cwe  =   obr.getObr4_UniversalServiceIdentifier();
		//get intent 
		String intent = obr.getObr11_SpecimenActionCode().getValue();
		// get status
		String status = obr.getObr25_ResultStatus().getValue();
		//get code
		String code = cwe.getAlternateIdentifier().getValue();
		// get display field
		String display= cwe.getAlternateText().getValue();
		// get text 
		String text = cwe.getText().getValue();
		
		ServiceRequest serviceRequest = new ServiceRequest();
		
		if(EnumUtils.isValidEnum(ServiceRequestStatus.class, status.toUpperCase())){
			serviceRequest.setStatus(ServiceRequestStatus.valueOf(status.toUpperCase()));
		}
		
		if(EnumUtils.isValidEnum(ServiceRequestIntent.class, intent.toUpperCase())){
			serviceRequest.setIntent(ServiceRequestIntent.valueOf(intent.toUpperCase()));
		}
		
		CodeableConcept codeableConcept = new CodeableConcept();
		codeableConcept.addCoding().setCode(code).setSystem(FHIRConstants.FHIR_SERVICE_REQUEST_SYSTEM_URL).setDisplay(display);
		codeableConcept.setText(text);
		serviceRequest.setCode(codeableConcept);
		
		serviceRequest.setSubject(new Reference(FHIRConstants.FHIR_PATIENT_REFERENCE + patientId));
		serviceRequest.setRequester(new Reference(FHIRConstants.FHIR_PATIENT_REFERENCE + patientId));
		return serviceRequest;		
	}
}
