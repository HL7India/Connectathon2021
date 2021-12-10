package com.hl7.india.openemrhapifhirr4.fhir.resourceprovider;

import ca.uhn.fhir.rest.annotation.*;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.param.TokenParam;
import ca.uhn.fhir.rest.server.IResourceProvider;
import ca.uhn.fhir.rest.server.exceptions.InvalidRequestException;
import ca.uhn.fhir.rest.server.exceptions.ResourceNotFoundException;
import com.hl7.india.openemrhapifhirr4.db.model.PatientData;
import com.hl7.india.openemrhapifhirr4.db.model.Sequences;
import com.hl7.india.openemrhapifhirr4.db.repo.PatientDataRepo;
import com.hl7.india.openemrhapifhirr4.db.repo.SequenceRepo;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Class which contains all the methods to perform REST operations corresponding to Patient Resource
 */
@Component
public class PatientResourceProvider implements IResourceProvider {

    @Autowired
    private PatientDataRepo patientDataRepo;

    @Autowired
    private SequenceRepo sequenceRepo;

    @Override
    public Class<? extends IBaseResource> getResourceType() {
        return Patient.class;
    }

    /**
     * End point(GET) which takes patientId and gives Patient Resource as response
     * after fetching Patient from patient_data table of OpenEMR
     *
     * @param patientId
     * @return Patient Resource
     */
    @Read
    public Patient getPatient(@IdParam IdType patientId) {

        Long patId = patientId.getIdPartAsLong();
        Patient patient;
        Optional<PatientData> patientData = patientDataRepo.findById(patId);
        if (patientData.isPresent()) {
            PatientData patData = patientData.get();
            patient = convertPatientDataToPatientResource(patData);
        } else {
            throw new ResourceNotFoundException("Patient with ID : " + patId + " Not Found");
        }
        return patient;
    }

    /**
     * End Point (GET) to search all the patients present in OpenEMR
     *
     * @return Bundle of Patient Resources
     */
    @Search
    public List<Patient> getAllPatients() {
        List<PatientData> patientDataList = patientDataRepo.findAll();
        List<Patient> fhirPatientList = new ArrayList<>();
        for (PatientData patientData : patientDataList) {
            Patient patient = convertPatientDataToPatientResource(patientData);
            fhirPatientList.add(patient);
        }
        return fhirPatientList;
    }

    /**
     * End Point(GET) which search for a patient with given name in patient_data table of OPenEMR
     *
     * @param name
     * @return Bundle of Patient Resources
     */
    @Search
    public List<Patient> getPatientByName(@RequiredParam(name = Patient.SP_NAME) String name) {
        List<PatientData> patientDataList = patientDataRepo.findByName(name);
        List<Patient> patientList = new ArrayList<>();
        for (PatientData patientData : patientDataList) {
            patientList.add(convertPatientDataToPatientResource(patientData));
        }
        return patientList;
    }

    /**
     * End Point(POST) which receives Patient Resource as a request and store the extacted data from the resource
     * in patient_data table of openEMR
     *
     * @param patient
     * @return Patient Resource
     */
    @Create
    @Transactional
    public MethodOutcome createPatient(@ResourceParam Patient patient) {
        PatientData patientData = new PatientData();
        //--------get Name from resource and set to patient_data model--------
        patientData.setTitle(patient.getName().get(0).getPrefix().get(0).toString());
        patientData.setFirstName(patient.getName().get(0).getGiven().get(0).toString());
        if (patient.getName().get(0).getGiven().size() > 1) {
            patientData.setMiddleName(patient.getName().get(0).getGiven().get(1).toString());
        } else {
            patientData.setMiddleName("");
        }
        patientData.setLastName(patient.getName().get(0).getFamily());
        //--------get Address from resource and set to patient_data model--------
        patientData.setStreet(patient.getAddress().get(0).getLine().get(0).toString());
        patientData.setCity(patient.getAddress().get(0).getCity());
        patientData.setState(patient.getAddress().get(0).getState());
        patientData.setCountryCode(patient.getAddress().get(0).getCountry());
        patientData.setPostalCode(patient.getAddress().get(0).getPostalCode());
        //--------get DOB from resource and set to patient_data model--------
        patientData.setDob(patient.getBirthDate());
        //--------get Gender from resource and set to patient_data model--------
        patientData.setSex(patient.getGender().getDisplay());
        //--------Iterate over telecom to get phone numbers and email from resource and set to patient_data model--------
        for (ContactPoint contactPoint : patient.getTelecom()) {
            if (contactPoint.getSystem() == ContactPoint.ContactPointSystem.PHONE) {
                if (contactPoint.getUse() == ContactPoint.ContactPointUse.MOBILE) {
                    patientData.setMobilePhone(contactPoint.getValue());
                } else if (contactPoint.getUse() == ContactPoint.ContactPointUse.HOME) {
                    patientData.setHomePhone(contactPoint.getValue());
                } else if (contactPoint.getUse() == ContactPoint.ContactPointUse.WORK) {
                    patientData.setWorkPhone(contactPoint.getValue());
                }
            } else if (contactPoint.getSystem() == ContactPoint.ContactPointSystem.EMAIL) {
                patientData.setEmail(contactPoint.getValue());
            }
        }
        //--------Iterate over identifer to get MRN and Health Id from resource and set to patient_data model--------
        for (Identifier identifier : patient.getIdentifier()) {
            if (identifier.getSystem().equalsIgnoreCase("https://healthid.ndhm.gov.in")) {
                patientData.setHealthId(identifier.getValue());
                if (identifier.getType() != null) {
                    for (Coding coding : identifier.getType().getCoding()) {
                        if (coding.getSystem().equalsIgnoreCase("http://terminology.hl7.org/CodeSystem/v2-0203")) {
                            patientData.setMrn(coding.getCode());
                        }
                    }
                }
            }
        }
        //--------get Marital Status from resource and set to patient_data model--------
        String maritalStatusCode = patient.getMaritalStatus().getCoding().get(0).getCode();
        if (maritalStatusCode.equalsIgnoreCase("U")) {
            patientData.setMaritalStatus("single");
        } else if (maritalStatusCode.equalsIgnoreCase("M")) {
            patientData.setMaritalStatus("married");
        } else if (maritalStatusCode.equalsIgnoreCase("D")) {
            patientData.setMaritalStatus("divorced");
        } else if (maritalStatusCode.equalsIgnoreCase("T")) {
            patientData.setMaritalStatus("domestic partner");
        } else if (maritalStatusCode.equalsIgnoreCase("W")) {
            patientData.setMaritalStatus("widowed");
        } else if (maritalStatusCode.equalsIgnoreCase("L")) {
            patientData.setMaritalStatus("separated");
        }

        //--------get deceased date from resource and set to patient_data model--------
        patientData.setDeceasedDate(patient.getDeceasedDateTimeType().getValue());

        //--------set pid of patient_data--------
        List<Sequences> sequencesList = sequenceRepo.findAll();
        Sequences sequences = sequencesList.get(0);
        Long pid = sequences.getPid();
        patientData.setPid(pid);
        patient.setId(pid.toString());
        patientDataRepo.save(patientData);
        sequences.setPid(pid + 1);
        sequenceRepo.save(sequences);

        MethodOutcome outcome = new MethodOutcome();
        outcome.setResource(patient);
        return outcome;
    }

    /**
     * End Point(GET) which search for a patient with given MRN(PUBPID) in patient_data table of OPenEMR
     *
     * @param identifier
     * @return Bundle of Patient Resources
     */
    @Search
    public List<Patient> getPatientByIdentifier(@RequiredParam(name = Patient.SP_IDENTIFIER) TokenParam identifier) {
        String identifierSystem = identifier.getSystem();
        String identifierValue = identifier.getValue();
        List<Patient> patientList = new ArrayList<>();
        if (identifierSystem.equalsIgnoreCase("http://terminology.hl7.org/CodeSystem/v2-0203")) {
            List<PatientData> patientDataList = patientDataRepo.findByMrn(identifierValue);
            for (PatientData patientData : patientDataList) {
                patientList.add(convertPatientDataToPatientResource(patientData));
            }
        } else if (identifierSystem.equalsIgnoreCase("https://healthid.ndhm.gov.in")) {
            List<PatientData> patientDataList = patientDataRepo.findByHealthId(identifierValue);
            for (PatientData patientData : patientDataList) {
                patientList.add(convertPatientDataToPatientResource(patientData));
            }
        } else {
            throw new InvalidRequestException("Invalid System");
        }
        return patientList;
    }

    /**
     * End Point(GET) which searchs for all the patients of given gender in patient_data table of OpenEMR
     *
     * @param gender
     * @return Bundle of PatientResources
     */
    @Search
    public List<Patient> getPatientByGender(@RequiredParam(name = Patient.SP_GENDER) String gender) {
        List<PatientData> patientDataList = patientDataRepo.findBySex(gender);
        List<Patient> patientList = new ArrayList<>();
        for (PatientData patientData : patientDataList) {
            patientList.add(convertPatientDataToPatientResource(patientData));
        }
        return patientList;
    }

    /**
     * End Point(GET) which searchs for all the patients having given DOB in patient_data table of OpenEMR
     *
     * @param dob
     * @return Bundle of Patient Resources
     * @throws ParseException
     */
    @Search
    public List<Patient> getPatientByBirthDate(@RequiredParam(name = Patient.SP_BIRTHDATE) String dob) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate = sdf.parse(dob);
        List<PatientData> patientDataList = patientDataRepo.findByDob(birthDate);
        List<Patient> patientList = new ArrayList<>();
        for (PatientData patientData : patientDataList) {
            patientList.add(convertPatientDataToPatientResource(patientData));
        }
        return patientList;
    }

    /**
     * Common method to convert Patient Data to Patient Resource
     *
     * @param patientData
     * @return Patient Resource
     */
    private Patient convertPatientDataToPatientResource(PatientData patientData) {
        Patient patient = new Patient();

        //--------Set the ID---------
        patient.setId(patientData.getPid().toString());

        //---------Set Identifier--------
        //Set Type under Identifier 1
        Identifier identifier = new Identifier();
        CodeableConcept codeableConcept = new CodeableConcept();
        List<Coding> codingList = new ArrayList<>();
        Coding coding = new Coding();
        coding.setSystem("http://terminology.hl7.org/CodeSystem/v2-0203")
                .setCode(patientData.getMrn())
                .setDisplay("Medical record number");
        codingList.add(coding);
        codeableConcept.setCoding(codingList);
        identifier.setType(codeableConcept);
        identifier.setSystem("https://healthid.ndhm.gov.in");
        identifier.setValue(patientData.getHealthId());
        patient.addIdentifier(identifier);

        //--------Set Name--------
        List<HumanName> humanNameList = new ArrayList<>();
        HumanName humanName = new HumanName();
        humanName.addGiven(patientData.getFirstName());
        humanName.addGiven(patientData.getMiddleName());
        humanName.setFamily(patientData.getLastName());
        humanName.addPrefix(patientData.getTitle());
        humanName.setText(patientData.getFirstName() + " " + patientData.getLastName());
        humanNameList.add(humanName);
        patient.setName(humanNameList);

        //--------Set Telecom 1 - Phone Mobile--------
        ContactPoint mobileContactPoint = new ContactPoint();
        mobileContactPoint.setSystem(ContactPoint.ContactPointSystem.PHONE);
        mobileContactPoint.setValue(patientData.getMobilePhone());
        mobileContactPoint.setUse(ContactPoint.ContactPointUse.MOBILE);
        patient.addTelecom(mobileContactPoint);

        //--------Set Telecom 2 - Phone Home--------
        ContactPoint homeContactPoint = new ContactPoint();
        homeContactPoint.setSystem(ContactPoint.ContactPointSystem.PHONE);
        homeContactPoint.setValue(patientData.getMobilePhone());
        homeContactPoint.setUse(ContactPoint.ContactPointUse.HOME);
        patient.addTelecom(homeContactPoint);

        //--------Set Telecom 4 - Phone Work--------
        ContactPoint workContactPoint = new ContactPoint();
        workContactPoint.setSystem(ContactPoint.ContactPointSystem.PHONE);
        workContactPoint.setValue(patientData.getMobilePhone());
        workContactPoint.setUse(ContactPoint.ContactPointUse.WORK);
        patient.addTelecom(workContactPoint);

        //--------Set Telecom 4 - Email--------
        ContactPoint emailContactPoint = new ContactPoint();
        emailContactPoint.setSystem(ContactPoint.ContactPointSystem.EMAIL);
        emailContactPoint.setValue(patientData.getEmail());
        patient.addTelecom(emailContactPoint);

        //--------Set Gender--------
        String sex = patientData.getSex();
        Enumerations.AdministrativeGender administrativeGender;
        if (sex.equalsIgnoreCase("male")) {
            administrativeGender = Enumerations.AdministrativeGender.MALE;
        } else if (sex.equalsIgnoreCase("female")) {
            administrativeGender = Enumerations.AdministrativeGender.FEMALE;
        } else {
            administrativeGender = Enumerations.AdministrativeGender.UNKNOWN;
        }
        patient.setGender(administrativeGender);

        //--------Set Birth Date--------
        patient.setBirthDate(patientData.getDob());

        //--------Set Address--------
        Address address = new Address();
        address.addLine(patientData.getStreet());
        address.setCity(patientData.getCity());
        address.setState(patientData.getState());
        address.setPostalCode(patientData.getPostalCode());
        address.setCountry(patientData.getCountryCode());
        patient.addAddress(address);

        //--------Set Deceased Date--------
        if (patientData.getDeceasedDate() != null) {
            patient.setDeceased(new DateTimeType(patientData.getDeceasedDate()));
        }

        //--------Set Marital Status--------
        CodeableConcept maritalCodeableConcept = new CodeableConcept();
        List<Coding> maritalCodingList = new ArrayList<>();
        Coding maritalCoding = new Coding();
        maritalCoding.setSystem("http://terminology.hl7.org/CodeSystem/v3-MaritalStatus");
        if (patientData.getMaritalStatus().equalsIgnoreCase("single")) {
            maritalCoding.setCode("U");
            maritalCoding.setDisplay("unmarried");
        } else if (patientData.getMaritalStatus().equalsIgnoreCase("married")) {
            maritalCoding.setCode("M");
            maritalCoding.setDisplay("Married");
        } else if (patientData.getMaritalStatus().equalsIgnoreCase("divorced")) {
            maritalCoding.setCode("D");
            maritalCoding.setDisplay("Divorced");
        } else if (patientData.getMaritalStatus().equalsIgnoreCase("domestic partner")) {
            maritalCoding.setCode("T");
            maritalCoding.setDisplay("Domestic Partner");
        } else if (patientData.getMaritalStatus().equalsIgnoreCase("widowed")) {
            maritalCoding.setCode("W");
            maritalCoding.setDisplay("Widowed");
        } else if (patientData.getMaritalStatus().equalsIgnoreCase("separated")) {
            maritalCoding.setCode("L");
            maritalCoding.setDisplay("Legally Separated");
        }
        maritalCodingList.add(maritalCoding);
        maritalCodeableConcept.setCoding(maritalCodingList);
        patient.setMaritalStatus(maritalCodeableConcept);

        return patient;
    }
}
