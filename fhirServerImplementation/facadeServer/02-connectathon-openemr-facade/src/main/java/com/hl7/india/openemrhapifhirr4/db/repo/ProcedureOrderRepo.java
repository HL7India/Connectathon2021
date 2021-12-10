package com.hl7.india.openemrhapifhirr4.db.repo;

import com.hl7.india.openemrhapifhirr4.db.model.ProcedureOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * An interface which contains methods to perform DB operations on procedure_order table of OpeneEMR
 */
@Repository
public interface ProcedureOrderRepo extends JpaRepository<ProcedureOrder, Long> {
}
