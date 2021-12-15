package com.fhir.connectathon;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hl7.fhir.r4.model.Observation;
import org.hl7.fhir.r4.model.Patient;

import com.fhir.connectathon.mapper.ObservationMapper;
import com.fhir.connectathon.mapper.PatientMapper;
import com.fhir.connectathon.utility.FHIRUtility;
import com.fhir.connectathon.utility.FileUtility;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.parser.Parser;

 
public class V2ToFHIRConvertorDemo {
	
	static Logger log = Logger.getLogger(V2ToFHIRConvertorDemo.class.getName());  
	 
	// The key for this map is identifier value in ADT message for a patient 
	// The value for this map is id returned after uploading patient to FHIR Server 
	public static Map<String,String> patientIdMaps = new HashMap<String,String>();
	
	public static void main(String[] args) throws HL7Exception, IOException  {
		 
		HapiContext hapi = new DefaultHapiContext();
		
		Parser p = hapi.getGenericParser();
	
		//default security is false
		parseADTMessage(p,false);
	
		//default security is false 
		parseORUMessages(p,false);
		
	    hapi.close(); 
	}

	

	private static void parseADTMessage(Parser p, boolean secure) throws HL7Exception {
		Message hapiMsg;
		ArrayList<String> adtMessages = FileUtility.getMessages(FileUtility.ADT_HL7_MSG_PATH);
		for (String message : adtMessages) {
			hapiMsg = p.parse(message);
			Patient patient = new PatientMapper().convertPatientAdtToFHIRPatient(hapiMsg);

			String patientIdentifier = patient.getIdentifier().get(0).getValue();

			boolean flag = FHIRUtility.validateResource(patient);

			if (flag) {
				log.info("FHIR resource is validated");
				Patient patientResult = FHIRUtility.uploadPatientToFhirServer(patient,secure);

				if (patientResult.hasId()) {
					String patientId = patientResult.getIdElement().getIdPart();

					//save patient identifier value and id in map
					patientIdMaps.put(patientIdentifier, patientId);
					Patient readPatient = FHIRUtility.readPatientFromFhirServer(patientId,secure);
					if (readPatient.hasId()) {
						log.info("Patient read is successfull " + patientId);
					}
				} else {
					log.error("Patient is not having id ...... ");
				}

			} else {
				log.error("Validation falied for resource!!! Check logs for meore details. ");
			}

		}
	}

	
	
	private static void parseORUMessages(Parser p, boolean secure) throws HL7Exception {
		Message hapiMsg;
		ArrayList<String> strList =   FileUtility.getMessages(FileUtility.ORU_HL7_MSG_PATH);
		for (String str : strList) {
			hapiMsg = p.parse(str);
			List<Observation> observations = new ObservationMapper().convertORUToFHIRObservation(hapiMsg,patientIdMaps,secure);
			
			for (Observation observation : observations) {
				boolean flag = FHIRUtility.validateResource(observation);
				
				if(flag){
					log.info("FHIR resource is correct... ");
					Observation observationResult = FHIRUtility.uploadObservationToFhirServer(observation,secure);
					
					if(observationResult!= null && observationResult.hasId()){

						String observationId = observationResult.getIdElement().getIdPart();
						log.info("Observation read is successfull "+observationId);
						Observation observationRead = FHIRUtility.readObservationFromFhirServer(observationId,secure);
						if(observationRead.hasId()){
							log.info("Observation read is successfull "+observationId);
						}
					}else{
						log.info("Observation does not returned id");
					}
				}else{
					log.error("Validation falied for resource!!! Check logs for meore details. ");
				}
			}
		}
	}
	
}
