package com.fhir.connectathon.utility;

import org.apache.log4j.Logger;
import org.hl7.fhir.common.hapi.validation.validator.FhirInstanceValidator;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.DomainResource;
import org.hl7.fhir.r4.model.Observation;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.ServiceRequest;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.fhir.connectathon.config.FhirContextBean;
import com.fhir.connectathon.constants.FHIRConstants;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.client.api.ServerValidationModeEnum;
import ca.uhn.fhir.rest.client.interceptor.BearerTokenAuthInterceptor;
import ca.uhn.fhir.rest.client.interceptor.LoggingInterceptor;
import ca.uhn.fhir.validation.FhirValidator;
import ca.uhn.fhir.validation.IValidatorModule;
import ca.uhn.fhir.validation.SingleValidationMessage;
import ca.uhn.fhir.validation.ValidationResult;

public class FHIRUtility {
	
	static Logger log = Logger.getLogger(FHIRUtility.class.getName());
	
	/**
	 * This method convert FHIR resource to JSONObject
	 * */
	public static JSONObject getResourceJSON(IBaseResource resource) {
		FhirContext ctxR4 = FhirContextBean.getInstance();
		IParser jsonParser = ctxR4.newJsonParser();
		String encoded1 = jsonParser.encodeResourceToString(resource);
		JSONObject resourceJSON = (JSONObject) JSONValue.parse(encoded1);
		return resourceJSON;
	}
	
	/**
	 * This method will return FHIR Context with pre-defined configuration 
	 * */
	public static FhirContext getFhirContext() {
		FhirContext ctxR4 = FhirContextBean.getInstance();
		// added to avoid first call for metadata endpoint
		ctxR4.getRestfulClientFactory().setServerValidationMode(ServerValidationModeEnum.NEVER);
		//added to avoid the time out error default value was 10000
		ctxR4.getRestfulClientFactory().setSocketTimeout(1000000);
		return ctxR4;
	}
	
	/**
	 * This method returns Logging Interceptor.
	 * This will log Request Summary, Request Body and Response Body
	 * */
	static private LoggingInterceptor getLogginInterceptor(){
		LoggingInterceptor loggingInterceptor = new LoggingInterceptor();
		loggingInterceptor.setLogRequestSummary(true);
		loggingInterceptor.setLogRequestBody(true);
		loggingInterceptor.setLogResponseBody(true);
		return loggingInterceptor;
	}
	
	/**
	 * This method returns BearerToken Interceptor
	 * This method takes input as access token 
	 * */
	static private BearerTokenAuthInterceptor getBearerTokenAuthInterceptor(String token){
		BearerTokenAuthInterceptor authInterceptor = new BearerTokenAuthInterceptor(token);
		return authInterceptor;
	}
	
	/**
	 * This method creates and configures FHIR client
	 * This FHIR client is registered with LoggingInterceptor and BearerTokenAuthInterceptor 
	 * */
	private static IGenericClient getRestfulClient(FhirContext ctx, boolean secure){
		String serverBase = "";
		String accessToken = "";
		if(secure) {
			serverBase = FHIRConstants.FHIR_BASE_SECURE_SERVER_URL;
			//get AWS access token
			accessToken = AWSCognitoUtility.getAWSAccessToken();
		}else {
			serverBase = FHIRConstants.FHIR_BASE_SERVER_URL;
		}
		IGenericClient client = ctx.newRestfulGenericClient(serverBase);
		if(secure) {
			client.registerInterceptor(getBearerTokenAuthInterceptor(accessToken));
		}
		client.registerInterceptor(getLogginInterceptor());
		return client;
	}
		

	/**
	 * This method will take input as FHIR Patient object and upload to FHIR server 
	 * */
	public static Patient uploadPatientToFhirServer(Patient patient, boolean secure){
		//get FHIR context
		FhirContext ctxR4 = getFhirContext();
		//get FHIR client
		IGenericClient client = getRestfulClient(ctxR4,secure);
		//get Patient JSONObject
		JSONObject patientJSON = getResourceJSON(patient);
		//create Patient
		MethodOutcome resultPatient  = client.create().resource(patientJSON.toJSONString()).execute();
		//get uploaded Patient
		Patient patientResult = (Patient)resultPatient.getResource();
		return patientResult;
		
	}
	
	/**
	 * This method read patient using patient Id
	 * */
	public static Patient readPatientFromFhirServer(String patientId,boolean secure){
		//get FHIR context
		FhirContext ctxR4 = getFhirContext();
		//get FHIR client
		IGenericClient client = getRestfulClient(ctxR4,secure);
		//read Patient
		Patient patient =  client.read().resource(Patient.class).withId(patientId).execute();
		return patient;
	}
	
	/**
	 * This method will take input as FHIR Observation object and upload to FHIR server
	 * */
	public static Observation uploadObservationToFhirServer(Observation observation, boolean secure){
		//get FHIR context
		FhirContext ctxR4 = getFhirContext();
		//get FHIR client
		IGenericClient client = getRestfulClient(ctxR4,secure);
		// get Observation  JSONObject
		JSONObject observationJSON = getResourceJSON(observation);
		MethodOutcome resultPatient  = client.create().resource(observationJSON.toJSONString()).execute();
		Observation observationResult = (Observation)resultPatient.getResource();
		return observationResult;
	}
	
	/**
	 * This method reads Observation using observationId
	 * */
	public static Observation readObservationFromFhirServer(String observationId, boolean secure){
		FhirContext ctxR4 = getFhirContext();
		IGenericClient client = getRestfulClient(ctxR4,secure);
		Observation observation =  client.read().resource(Observation.class).withId(observationId).execute();
		return observation;
	}
	
	/**
	 * This method will take input as FHIR ServiceRequest object and upload to FHIR server
	 * */
	public static ServiceRequest sendServiceRequestToFhirServer(ServiceRequest serviceRequest, boolean secure){
		FhirContext ctxR4 = getFhirContext();
		IGenericClient client = getRestfulClient(ctxR4,secure);
		JSONObject serviceRequestJSONJSON = getResourceJSON(serviceRequest);
		MethodOutcome resultServiceRequest  = client.create().resource(serviceRequestJSONJSON.toJSONString()).execute();
		ServiceRequest serviceRequestResult = (ServiceRequest)resultServiceRequest.getResource();
		return serviceRequestResult;
	}
	
	/**
	 * This method reads ServiceRequest using serviceRequestId
	 * */
	public static ServiceRequest readServiceRequestFromFhirServer(String serviceRequestId, boolean secure){
		FhirContext ctxR4 = getFhirContext();
		IGenericClient client = getRestfulClient(ctxR4,secure);
		ServiceRequest serviceRequest =  client.read().resource(ServiceRequest.class).withId(serviceRequestId).execute();
		return serviceRequest;
	}
	
	/**
	 * Get the serviceRequestId 
	 * */
	public static String getServiceRequestReferenceId(ServiceRequest serviceRequest, boolean secure) {
		ServiceRequest serviceRequestReturned  = sendServiceRequestToFhirServer(serviceRequest,secure);
		String serviceRequestId = null;
		if(serviceRequestReturned != null & serviceRequestReturned.hasIdElement()) {
			serviceRequestId = serviceRequestReturned.getIdElement().getIdPart();
		}
		return serviceRequestId;
	}
	
	/**
	 * This method validates the resource using FhirInstanceValidator 
	 * */
	public static boolean validateResource(DomainResource res) {
		FhirContext ctx = getFhirContext();
		FhirValidator validator = ctx.newValidator();
		IValidatorModule module = new FhirInstanceValidator(ctx);
		validator.registerValidatorModule(module);
		ValidationResult result = validator.validateWithResult(res);
		if(result.getMessages().size() > 0){	 
			 for (SingleValidationMessage next : result.getMessages()) {
				 log.error(next.getLocationString() + " - " + next.getMessage());
		     }
			 return false;
		}else {
			 return true;
		}
	}
}
