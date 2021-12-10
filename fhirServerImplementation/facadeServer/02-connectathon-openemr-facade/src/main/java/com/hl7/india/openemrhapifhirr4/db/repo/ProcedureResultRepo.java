package com.hl7.india.openemrhapifhirr4.db.repo;

import com.hl7.india.openemrhapifhirr4.db.model.ProcedureResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * An interface which contains methods to perform DB operations on procedure_result table of OpeneEMR
 */
@Repository
public interface ProcedureResultRepo extends JpaRepository<ProcedureResult, Long> {

    @Query(value = "SELECT pr " +
        "FROM ProcedureResult pr " +
        "WHERE pr.procedureReport.procedureOrder.patientData.pid = :pid")
    List<ProcedureResult> findByPatient(Long pid);
}
