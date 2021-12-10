package com.hl7.india.openemrhapifhirr4.db.repo;

import com.hl7.india.openemrhapifhirr4.db.model.Sequences;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * An interface which contains methods to perform DB operations on sequences table of OpeneEMR
 */
@Repository
public interface SequenceRepo extends JpaRepository<Sequences, Integer> {

    Sequences findByEncounterId(Long encounterId);
}
