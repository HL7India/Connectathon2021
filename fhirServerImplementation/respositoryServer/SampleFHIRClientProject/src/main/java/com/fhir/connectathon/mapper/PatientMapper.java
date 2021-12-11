package com.fhir.connectathon.mapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.EnumUtils;
import org.apache.log4j.Logger;
import org.hl7.fhir.r4.model.Address;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.ContactPoint;
import org.hl7.fhir.r4.model.Enumerations.AdministrativeGender;
import org.json.simple.JSONObject;
import org.hl7.fhir.r4.model.Extension;
import org.hl7.fhir.r4.model.Patient;

import com.fhir.connectathon.constants.FHIRConstants;
import com.fhir.connectathon.utility.FHIRUtility;

import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.v26.datatype.CWE;
import ca.uhn.hl7v2.model.v26.datatype.CX;
import ca.uhn.hl7v2.model.v26.datatype.XPN;
import ca.uhn.hl7v2.model.v26.message.ADT_A01;
import ca.uhn.hl7v2.model.v26.segment.PID;

/**
 * This class is used for mapping  ADT_AO1 HL7 V2 message to FHIR Patient. This class maps below fields
 * Identifiers,Name, DOB, Gender,Address, Telecom Details and Religion 
 * */
public class PatientMapper {
	
	static Logger log = Logger.getLogger(PatientMapper.class.getName());

	/**
	 *  This method will parse the HL7 ADT HL7 v2 message to FHIR Patient
	 *  This method takes HAPI message and returns FHIR Patient 
	 * */
	public Patient convertPatientAdtToFHIRPatient(Message hapiMsg) {
		ADT_A01 adtMsg = (ADT_A01) hapiMsg;
		//Featch the PID segment from HAPI message 
		PID pid = adtMsg.getPID();
		Patient patient = mapPatient(pid);
		return patient;
	}

	/**
	 * Map PID segment to FHIR Patient
	 */
	public Patient mapPatient(PID pid) {
		Patient patient = new Patient();
		//map PID-13
		//setPatientIdentifiers(pid, patient);
		//map PID-5
		//setPatientName(pid, patient);
		//map PID-7
		//setPatientDOB(pid, patient);
		//map PID-8
		//setPatientGender(pid, patient);
		//map PID-11
		//setPatientAddress(pid, patient);
		//map PID-13
		//setPatientTelecomDetails(pid, patient);
		//map PID-17 
		//setEthnicityAsExtension(pid, patient);
		return patient;
	}

	/**
	 *  This method maps PID-17 to religion. 
	 *  Religion is mapped as extension. 
	 * */
	private void setEthnicityAsExtension(PID pid, Patient patient){
		CWE cwe = pid.getPid17_Religion();
		
		String text = cwe.getText().getValue();
		String code = cwe.getIdentifier().getValue();
		String system = cwe.getNameOfCodingSystem().getValue();
		
		Extension ethnicityExt = new Extension();
		ethnicityExt.setUrl(FHIRConstants.FHIR_ETHNICITY_URL);
		
		CodeableConcept religionType = new CodeableConcept();
		religionType.setText(text);
		religionType.addCoding().setSystem(system).setCode(code)
				.setDisplay(text);
		 
		ethnicityExt.setValue(religionType);
		patient.addExtension(ethnicityExt);

	}
	
	/**
	 * This method maps PID-8 to patient gender 
	 * */
	private void setPatientGender(PID pid, Patient patient) {
		if (EnumUtils.isValidEnum(AdministrativeGender.class, pid.getPid8_AdministrativeSex().getValue().toUpperCase())) {
			patient.setGender(AdministrativeGender.valueOf(pid.getPid8_AdministrativeSex().getValue().toUpperCase()));
		} else {
			log.error("Invalid Gender identified");
		}
	}

	/**
	 * This method maps Telecome details from PID-13
	 * */
	private void setPatientTelecomDetails(PID pid, Patient patient) {
		List<ContactPoint> telecom = new ArrayList<ContactPoint>();
		int telecoms = pid.getPid13_PhoneNumberHome().length;

		for (int i = 0; i < telecoms; i++) {
			ContactPoint tele = new ContactPoint();
			tele.setUse(org.hl7.fhir.r4.model.ContactPoint.ContactPointUse.valueOf(pid.getPid13_PhoneNumberHome(i)
					.getXtn2_TelecommunicationUseCode().getValue().toString().toUpperCase()));
			tele.setSystem(org.hl7.fhir.r4.model.ContactPoint.ContactPointSystem.valueOf(pid.getPid13_PhoneNumberHome(i)
					.getXtn3_TelecommunicationEquipmentType().getValue().toString().toUpperCase()));
			if ((pid.getPid13_PhoneNumberHome(i).getXtn3_TelecommunicationEquipmentType().getValue())
					.equalsIgnoreCase(FHIRConstants.FHIR_TELECOM_TYPE_EMAIL)) {
				tele.setValue(pid.getPid13_PhoneNumberHome(i).getXtn4_CommunicationAddress().getValue());
			} else {
				tele.setValue(pid.getPid13_PhoneNumberHome(i).getXtn7_LocalNumber().getValue());
			}
			telecom.add(tele);
		}

		patient.setTelecom(telecom);
	}

	/**
	 * Map PID-11 to Patient address, mapping right now - street 
	 * address to line, city, state, postal code
	 * handling multiple address from PID-11 field
	 * */
	private void setPatientAddress(PID pid, Patient patient) {
		List<Address> address = new ArrayList<Address>();
		int tempSize = pid.getPid11_PatientAddress().length;;
		for (int i = 0; i < tempSize; i++) {
			Address addr = new Address();
			addr.addLine(pid.getPid11_PatientAddress(i).getXad1_StreetAddress().getSad1_StreetOrMailingAddress().getValue());
			addr.setCity(pid.getPid11_PatientAddress(i).getXad3_City().getValue());
			addr.setPostalCode(pid.getPid11_PatientAddress(i).getXad5_ZipOrPostalCode().getValue());
			addr.setState(pid.getPid11_PatientAddress(i).getXad4_StateOrProvince().getValue());
			address.add(addr);
		}
		patient.setAddress(address);
	}


	/**
	 * Map PID-7 to Birthdate(yyyy-MM-dd)
	 * */
	private void setPatientDOB(PID pid, Patient patient) {
		Date date = null;
		try {
			date = new SimpleDateFormat(FHIRConstants.FHIR_BIRTHDATE_FORMAT).parse(pid.getPid7_DateTimeOfBirth().getValue());
		} catch (ParseException e) {
			log.error(e.getMessage());
		}
		patient.setBirthDate(date);
	}


	/**
	 * Map PID-5 to Patient name
	 * */
	private void setPatientName(PID pid, Patient patient) {
		XPN[] names = pid.getPid5_PatientName();
		for (XPN name : names) {
			patient.addName().setFamily(name.getXpn1_FamilyName().getFn1_Surname().getValue())
					.addGiven(name.getXpn2_GivenName().getValue());
		}
	}
	
	/**
	 * Map PID-3 to Patient Identifier
	 * */
	private void setPatientIdentifiers(PID pid, Patient patient) {
		CX[] identifiers = pid.getPid3_PatientIdentifierList();
		
		for (CX cx : identifiers) {

			String value = cx.getCx1_IDNumber().getValue();
		 
			CodeableConcept identifierType = new CodeableConcept();
			identifierType.setText(FHIRConstants.FHIR_PATIENT_ID_TEXT);
			identifierType.addCoding()
					.setSystem(FHIRConstants.FHIR_PATIENT_ID_CODE_SYSTEM)
					.setCode(FHIRConstants.FHIR_PATIENT_ID_CODE_CODE)
					.setDisplay(FHIRConstants.FHIR_PATIENT_ID_CODE_DISPLAY);

			patient.addIdentifier().setType(identifierType)
					.setSystem(FHIRConstants.FHIR_PATIENT_ID_TYPE_SYSTEM)
					.setValue(value);
		}
	}
}
