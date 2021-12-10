package com.hl7.india.openemrhapifhirr4.db.repo;

import com.hl7.india.openemrhapifhirr4.db.model.PatientData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * An interface which contains methods to perform DB operations on patient_data table of OpeneEMR
 */
@Repository
public interface PatientDataRepo extends JpaRepository<PatientData, Long> {

    List<PatientData> findByMrn(String mrn);

    List<PatientData> findBySex(String gender);

    List<PatientData> findByDob(Date dob);

    @Query(value = "SELECT pd " +
        "FROM PatientData pd " +
        "WHERE pd.firstName = :name " +
        "OR pd.lastName = :name")
    List<PatientData> findByName(String name);

    List<PatientData> findByHealthId(String healthId);
}
