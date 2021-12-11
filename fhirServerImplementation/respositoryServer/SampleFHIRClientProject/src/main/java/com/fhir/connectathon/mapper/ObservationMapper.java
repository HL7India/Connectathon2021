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
import org.json.simple.JSONObject;

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
		
		String patientId = null;
		
		// get the patient id from maps
		patientId = getPatientId(patientIdMaps, patientResult);
		
		try {
			//get order observation i.e. OBR segments from ORU message
			List<ORU_R01_ORDER_OBSERVATION> orderObservations =   patientResult.getORDER_OBSERVATIONAll();
			for (ORU_R01_ORDER_OBSERVATION oru_R01_ORDER_OBSERVATION : orderObservations) {
				 
				//get the list of observations for a observation order 
				List<ORU_R01_OBSERVATION> oru_ro1_observations =  oru_R01_ORDER_OBSERVATION.getOBSERVATIONAll();
				for (ORU_R01_OBSERVATION oru_R01_OBSERVATION : oru_ro1_observations) {
					
					Observation observation = new Observation();
		
					//get the observation 
					OBX obx =  oru_R01_OBSERVATION.getOBX();
					
					//Get the unit from OBX-6
					String unit = obx.getObx6_Units().getIdentifier().getValue();
		
					//map OBX-11
					setObservationStatus(observation, obx);
					
					//mapp OBX-3
					setObservationCode(observation, obx);
					
					//map OBX-5 and OBX-6
					setObservationValue(observation, obx,unit);
					
					//map OBX-7
					setObservationRefrenceRange(observation, obx, unit); 

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

	private String getPatientId(Map<String, String> patientIdMaps, ORU_R01_PATIENT_RESULT patientResult) {
		String patientId = null;
		// get patient's identifier list
		CX[] patientIdentifiers = patientResult.getPATIENT().getPID().getPatientIdentifierList();
		//get patient identifier which is used later to map for subject
		String patientIdentifier = patientIdentifiers[0].getCx1_IDNumber().getValue();
		
		if(patientIdMaps.containsKey(patientIdentifier)){
			patientId = patientIdMaps.get(patientIdentifier);
		}
		return patientId;
	}

	/**
	 * This method sets Patient reference in subject field of Observation 
	 * */
	private void setObservationSubject(String patientId, Observation observation) {
		//TODO :  add code to set subject
	}
	
	/**
	 * This method maps Reference range high and low.
	 *  Assuming that reference rage is separated by '-' and it has both values i.e. low and high
	 * */
	private void setObservationRefrenceRange(Observation observation, OBX obx, String unit) {
		//TODO :  add code to set reference range
	}

	/**
	 * This method sets actual observation value(OBX5) and units(OBX6)
	 * */
	private void setObservationValue(Observation observation, OBX obx, String unit) {
		//TODO :  add code to set observation value i.e actual results with unit
	}

	/**
	 * This maps OBX-3 i.e. Observation code which contains loinc code,Systemt URL and display
	 * */
	private void setObservationCode(Observation observation, OBX obx) {
		 //TODO :  add code to set observation code
	}

	/**
	 * This method sets observation status i.e OBX-11
	 * */
	private void setObservationStatus(Observation observation, OBX obx) {
		 //TODO :  add code to set observation status  
	}

}
