package com.hl7.india.openemrhapifhirr4.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Model that maps with procedure_order table of OpenEMR database
 */
@Entity
@Table(name = "procedure_order")
public class ProcedureOrder implements Serializable {
    @Id
    @Column(name = "procedure_order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long procedureOrderId;
    @Column(name = "provider_id")
    private Long providerId;
    @ManyToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "pid")
    private PatientData patientData;
    @ManyToOne
    @JoinColumn(name = "encounter_id", referencedColumnName = "encounter")
    private FormEncounter formEncounter;
    @Column(name = "date_collected")
    private Date dateCollected;
    @Column(name = "date_ordered")
    private Date dateOrdered;
    @Column(name = "lab_id")
    private Long labId;

    public ProcedureOrder() {
    }

    public ProcedureOrder(Long procedureOrderId, Long providerId, PatientData patientData,
                          FormEncounter formEncounter, Date dateCollected, Date dateOrdered, Long labId) {
        this.procedureOrderId = procedureOrderId;
        this.providerId = providerId;
        this.patientData = patientData;
        this.formEncounter = formEncounter;
        this.dateCollected = dateCollected;
        this.dateOrdered = dateOrdered;
        this.labId = labId;
    }

    public Long getProcedureOrderId() {
        return procedureOrderId;
    }

    public void setProcedureOrderId(Long procedureOrderId) {
        this.procedureOrderId = procedureOrderId;
    }

    public Long getProviderId() {
        return providerId;
    }

    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }

    public PatientData getPatientData() {
        return patientData;
    }

    public void setPatientData(PatientData patientData) {
        this.patientData = patientData;
    }

    public FormEncounter getFormEncounter() {
        return formEncounter;
    }

    public void setFormEncounter(FormEncounter formEncounter) {
        this.formEncounter = formEncounter;
    }

    public Date getDateCollected() {
        return dateCollected;
    }

    public void setDateCollected(Date dateCollected) {
        this.dateCollected = dateCollected;
    }

    public Date getDateOrdered() {
        return dateOrdered;
    }

    public void setDateOrdered(Date dateOrdered) {
        this.dateOrdered = dateOrdered;
    }

    public Long getLabId() {
        return labId;
    }

    public void setLabId(Long labId) {
        this.labId = labId;
    }

    @Override
    public String toString() {
        return "ProcedureOrder{" +
            "procedureOrderId=" + procedureOrderId +
            ", providerId=" + providerId +
            ", patientData=" + patientData +
            ", encounter=" + formEncounter +
            ", dateCollected=" + dateCollected +
            ", dateOrdered=" + dateOrdered +
            ", labId=" + labId +
            '}';
    }
}
