package com.hl7.india.openemrhapifhirr4.db.repo;

import com.hl7.india.openemrhapifhirr4.db.model.FormEncounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * An interface which contains methods to perform DB operations on form_encounter table of OpeneEMR
 */
@Repository
public interface FormEncounterRepo extends JpaRepository<FormEncounter, Long> {
}
