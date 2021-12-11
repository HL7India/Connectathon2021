package com.fhir.connectathon;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hl7.fhir.r4.model.Observation;
import org.hl7.fhir.r4.model.Patient;
import org.json.simple.JSONObject;

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
		//parseORUMessages(p,false);
		
	    hapi.close(); 
	}

	

	private static void parseADTMessage(Parser p, boolean secure) throws HL7Exception {
		Message hapiMsg;
		ArrayList<String> adtMessages = FileUtility.getMessages(FileUtility.ADT_HL7_MSG_PATH);
		for (String message : adtMessages) {
			hapiMsg = p.parse(message);
			Patient patient = new PatientMapper().convertPatientAdtToFHIRPatient(hapiMsg);
			
			JSONObject patientJSON = FHIRUtility.getResourceJSON(patient);
			
			log.info("Patient JSON "+patientJSON.toJSONString());
			
			
			
			/*String patientIdentifier = patient.getIdentifier().get(0).getValue();
			
			boolean flag = FHIRUtility.validateResource(patient);
			
			if(flag) {
				log.info("FHIR resource is validated");
				
				String patientId = uploadPatientResource(secure, patient);
				
				if(patientId != null) {
					readPatientById(patientId,secure);
					
					//save patient identifier value and id in map
					patientIdMaps.put(patientIdentifier, patientId);
				}
				 
			}else {
				log.error("Validation falied for resource!!! Check logs for meore details. ");
			}*/
		}
	}

	private static void parseORUMessages(Parser p, boolean secure) throws HL7Exception {
		Message hapiMsg;
		ArrayList<String> strList =   FileUtility.getMessages(FileUtility.ORU_HL7_MSG_PATH);
		for (String str : strList) {
			hapiMsg = p.parse(str);
			List<Observation> observations = new ObservationMapper().convertORUToFHIRObservation(hapiMsg,patientIdMaps,secure);
			
			for (Observation observation : observations) {
				
				JSONObject observationJSON = FHIRUtility.getResourceJSON(observation);
				
				log.info("Observation JSON "+observationJSON.toJSONString());
				
				/*boolean flag = FHIRUtility.validateResource(observation);
				if(flag){
					log.info("FHIR resource is correct... ");
					
					String observationId = uploadObservationResource(secure, observation);
					
					if(observationId != null) {
						readObservationById(observationId,secure);
					}
					
				}else {
					log.error("Validation falied for resource!!! Check logs for meore details. ");
				}*/
			}
		}
	}

	

	// this method will validate and upload the patient resource on FHIR Server
	private static String uploadPatientResource(boolean secure, Patient patient) {
		String patientId = null;

		Patient patientResult = FHIRUtility.uploadPatientToFhirServer(patient, secure);

		if (patientResult.hasId()) {
			patientId = patientResult.getIdElement().getIdPart();
		}
		return patientId;
	}


	// read Patient by id 
	private static void readPatientById(String patientId,boolean secure) {
		Patient readPatient = FHIRUtility.readPatientFromFhirServer(patientId,secure);
		
		if (readPatient.hasId()) {
			log.info("Patient read is successfull " + patientId);
		}
	}

	//upload observation resource to fhir sever
	private static String uploadObservationResource(boolean secure, Observation observation) {
		String observationId = null;

		Observation observationResult = FHIRUtility.uploadObservationToFhirServer(observation, secure);

		if (observationResult != null && observationResult.hasId()) {
			observationId = observationResult.getIdElement().getIdPart();
		} else {
			log.info("Observation does not returned id");
		}

		return observationId;
	}


	// read observation by Id 
	private static void readObservationById(String observationId,boolean secure) {
		log.info("Observation read is successfull "+observationId);
		
		Observation observationRead = FHIRUtility.readObservationFromFhirServer(observationId,secure);
		
		if(observationRead.hasId()){
			log.info("Observation read is successfull "+observationId);
		}
	}
	
}
