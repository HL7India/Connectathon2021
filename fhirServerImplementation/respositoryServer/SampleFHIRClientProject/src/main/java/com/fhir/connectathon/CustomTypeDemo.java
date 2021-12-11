package com.fhir.connectathon;

import java.io.IOException;

import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.StringType;

import com.fhir.connectathon.custom.resources.CustomPatient;
import com.fhir.connectathon.utility.FHIRUtility;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;

public class CustomTypeDemo {

	public static void main(String[] args) throws IOException {
		
		IParser  p = FhirContext.forR4().newJsonParser().setPrettyPrint(true);

		CustomPatient patient = new CustomPatient();
		
		patient.addName().setFamily("Swati").addGiven("Kulkarni");
		patient.setCast(new StringType("Maratha"));
		
		String messageString = p.encodeResourceToString(patient);
		
		System.out.println(messageString);
		
		Patient patientResult = FHIRUtility.uploadPatientToFhirServer(patient,false);
		
		String patientResultString = p.encodeResourceToString(patientResult);
		
		System.out.println(patientResultString);
		
		
	}

}
