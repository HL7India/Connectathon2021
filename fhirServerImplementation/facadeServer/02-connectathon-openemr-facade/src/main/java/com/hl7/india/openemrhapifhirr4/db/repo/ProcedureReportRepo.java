package com.hl7.india.openemrhapifhirr4.db.repo;

import com.hl7.india.openemrhapifhirr4.db.model.ProcedureReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * An interface which contains methods to perform DB operations on procedure_report table of OpeneEMR
 */
@Repository
public interface ProcedureReportRepo extends JpaRepository<ProcedureReport, Long> {
}
