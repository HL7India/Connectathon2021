package com.hl7.india.openemrhapifhirr4.db.repo;

import com.hl7.india.openemrhapifhirr4.db.model.ProcedureOrderCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * An interface which contains methods to perform DB operations on procedure_order_code table of OpeneEMR
 */
@Repository
public interface ProcedureOrderCodeRepo extends JpaRepository<ProcedureOrderCode, Long> {
}
