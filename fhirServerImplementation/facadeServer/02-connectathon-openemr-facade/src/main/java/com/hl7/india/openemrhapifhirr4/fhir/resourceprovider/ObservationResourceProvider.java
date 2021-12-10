package com.hl7.india.openemrhapifhirr4.fhir.resourceprovider;

import ca.uhn.fhir.rest.annotation.*;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.param.ReferenceParam;
import ca.uhn.fhir.rest.server.IResourceProvider;
import ca.uhn.fhir.rest.server.exceptions.InvalidRequestException;
import ca.uhn.fhir.rest.server.exceptions.ResourceNotFoundException;
import com.hl7.india.openemrhapifhirr4.db.model.PatientData;
import com.hl7.india.openemrhapifhirr4.db.model.ProcedureOrder;
import com.hl7.india.openemrhapifhirr4.db.model.ProcedureResult;
import com.hl7.india.openemrhapifhirr4.db.repo.PatientDataRepo;
import com.hl7.india.openemrhapifhirr4.db.repo.ProcedureOrderRepo;
import com.hl7.india.openemrhapifhirr4.db.repo.ProcedureReportRepo;
import com.hl7.india.openemrhapifhirr4.db.repo.ProcedureResultRepo;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.Observation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Class which contains all the methods to perform REST operations corresponding to Observation Resource
 */
@Component
public class ObservationResourceProvider implements IResourceProvider {

    @Autowired
    private ProcedureResultRepo procedureResultRepo;

    @Autowired
    private ProcedureReportRepo procedureReportRepo;

    @Autowired
    private PatientDataRepo patientDataRepo;

    @Autowired
    private ProcedureOrderRepo procedureOrderRepo;

    @Override
    public Class<? extends IBaseResource> getResourceType() {
        return Observation.class;
    }

    /**
     * End point(GET) which takes procedureResultId and gives Observation Resource as response
     * after fetching data from patient_data table of OpenEMR
     *
     * @param procedureResultId
     * @return Observation Resource
     */
    @Read
    public Observation getObservation(@IdParam IdType procedureResultId) {
        Observation observation;
        Optional<ProcedureResult> procedureResultOptional = procedureResultRepo.findById(procedureResultId.getIdPartAsLong());
        if (procedureResultOptional.isPresent()) {
            ProcedureResult procedureResult = procedureResultOptional.get();
            observation = convertProcedureResultToObservation(procedureResult);
        } else {
            throw new ResourceNotFoundException("Observation with id : " + procedureResultId + " not found");
        }
        return observation;
    }

    /**
     * End Point (GET) to search all the Observations present in OpenEMR
     *
     * @return Bundle of Observation Resources
     */
    @Search
    public List<Observation> getAllObservations() {
        List<Observation> observationList = new ArrayList<>();
        List<ProcedureResult> procedureResultList = procedureResultRepo.findAll();
        for (ProcedureResult procedureResult : procedureResultList) {
            observationList.add(convertProcedureResultToObservation(procedureResult));
        }
        return observationList;
    }

    /**
     * End Point(GET) which searchs for all the observations of a given patient in
     * procedure_result and procedure_report tables of OpenEMR
     *
     * @param subject
     * @return Bundle of Observation Resources
     */
    @Search
    public List<Observation> getObservationsOfPatient(@RequiredParam(name = Observation.SP_SUBJECT) ReferenceParam subject) {
        List<Observation> observationList = new ArrayList<>();
        if (subject.hasResourceType()) {
            String resourceType = subject.getResourceType();
            if (!resourceType.equals("Patient")) {
                throw new InvalidRequestException("Invalid resource type for parameter 'subject': " + resourceType);
            } else {
                Long id = subject.getIdPartAsLong();
                Optional<PatientData> patientDataOptional = patientDataRepo.findById(id);
                if (patientDataOptional.isPresent()) {
                    List<ProcedureResult> procedureResultList = procedureResultRepo.findByPatient(id);
                    for (ProcedureResult procedureResult : procedureResultList) {
                        observationList.add(convertProcedureResultToObservation(procedureResult));
                    }
                } else {
                    throw new ResourceNotFoundException("Patient with id : " + id + " not found");
                }
            }
        }
        return observationList;
    }

    /**
     * End Point(POST) which receives Observation Resource as a request and store the extacted data from the resource
     * in procedure_report and procedure_result tables of openEMR
     *
     * @param observation
     * @return Observation Resource
     */
    @Create
    @Transactional
    public MethodOutcome createObservation(@ResourceParam Observation observation) {

        //--------Extract Patient Id from Observation Resource--------
        Long pid = Long.parseLong(observation.getSubject().getReference().split("/")[1]);
        //--------Extract ServiceRequest (ProcedureOrder) Id from Observation Resource--------
        Long serviceReqId = Long.parseLong(observation.getBasedOn().get(0).getReference().split("/")[1]);
        Optional<PatientData> patientDataOptional = patientDataRepo.findById(pid);
        Optional<ProcedureOrder> procedureOrderOptional = procedureOrderRepo.findById(serviceReqId);
        //--------Check if Patient and Order are present in DB--------
        if (procedureOrderOptional.isPresent() && procedureOrderOptional.get().getPatientData().getPid() == pid) {
            //--------Code Snippet Begin FileName :create-observation.txt--------

            //--------Code Snippet End FileName :create-observation.txt--------

            // Set Method Outcome as observation resource and return
            MethodOutcome methodOutcome = new MethodOutcome();
            methodOutcome.setResource(observation);
            return methodOutcome;
        } else {
            throw new InvalidRequestException("Invalid Patient Id or Service Request Id");
        }
    }

    /**
     * Common method to convert ProcedureResult to Observation Resource
     *
     * @param procedureResult
     * @return Observation Resource
     */
    private Observation convertProcedureResultToObservation(ProcedureResult procedureResult) {

        Observation observation = new Observation();
        //--------Code Snippet Begin FileName :convert-procedure-result.txt--------
        //--------Code Snippet End FileName :convert-procedure-result.txt--------
        return observation;
    }

    private boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
