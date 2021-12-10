package com.hl7.india.openemrhapifhirr4.db.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Model that maps with form_encounter table of OpenEMR database
 */
@Entity
@Table(name = "form_encounter")
public class FormEncounter implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "date")
    private Date date;
    @Column(name = "reason")
    private String reason;
    @Column(name = "facility")
    private String facility;
    @Column(name = "facility_id")
    private Integer facilityId;
    @ManyToOne
    @JoinColumn(name = "pid", referencedColumnName = "pid")
    private PatientData patientData;
    @Column(name = "encounter")
    private Long encounter;
    @Column(name = "onset_date")
    private Date onsetDate;
    @Column(name = "pc_catid")
    private Integer pcCatId;
    @Column(name = "last_level_billed")
    private Integer latLevelBilled;
    @Column(name = "last_level_closed")
    private Integer lastLevelClosed;
    @Column(name = "stmt_count")
    private Integer statementCount;
    @Column(name = "provider_id")
    private Integer providerId;
    @Column(name = "supervisor_id")
    private Integer supervisorId;
    @Column(name = "billing_facility")
    private Integer billingFacility;
    @ManyToOne
    @JoinColumn(name = "class_code", referencedColumnName = "option_id")
    private ListOptions listOptions;


    public FormEncounter() {
    }

    public FormEncounter(Long id, Date date, String reason, String facility, Integer facilityId, PatientData patientData,
                         Long encounter, Date onsetDate, Integer pcCatId, Integer latLevelBilled, Integer lastLevelClosed,
                         Integer statementCount, Integer providerId, Integer supervisorId, Integer billingFacility,
                         ListOptions listOptions) {
        this.id = id;
        this.date = date;
        this.reason = reason;
        this.facility = facility;
        this.facilityId = facilityId;
        this.patientData = patientData;
        this.encounter = encounter;
        this.onsetDate = onsetDate;
        this.pcCatId = pcCatId;
        this.latLevelBilled = latLevelBilled;
        this.lastLevelClosed = lastLevelClosed;
        this.statementCount = statementCount;
        this.providerId = providerId;
        this.supervisorId = supervisorId;
        this.billingFacility = billingFacility;
        this.listOptions = listOptions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getFacility() {
        return facility;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }

    public Integer getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(Integer facilityId) {
        this.facilityId = facilityId;
    }

    public PatientData getPatientData() {
        return patientData;
    }

    public void setPatientData(PatientData patientData) {
        this.patientData = patientData;
    }

    public Long getEncounter() {
        return encounter;
    }

    public void setEncounter(Long encounter) {
        this.encounter = encounter;
    }

    public Date getOnsetDate() {
        return onsetDate;
    }

    public void setOnsetDate(Date onsetDate) {
        this.onsetDate = onsetDate;
    }

    public Integer getPcCatId() {
        return pcCatId;
    }

    public void setPcCatId(Integer pcCatId) {
        this.pcCatId = pcCatId;
    }

    public Integer getLatLevelBilled() {
        return latLevelBilled;
    }

    public void setLatLevelBilled(Integer latLevelBilled) {
        this.latLevelBilled = latLevelBilled;
    }

    public Integer getLastLevelClosed() {
        return lastLevelClosed;
    }

    public void setLastLevelClosed(Integer lastLevelClosed) {
        this.lastLevelClosed = lastLevelClosed;
    }

    public Integer getStatementCount() {
        return statementCount;
    }

    public void setStatementCount(Integer statementCount) {
        this.statementCount = statementCount;
    }

    public Integer getProviderId() {
        return providerId;
    }

    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }

    public Integer getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(Integer supervisorId) {
        this.supervisorId = supervisorId;
    }

    public Integer getBillingFacility() {
        return billingFacility;
    }

    public void setBillingFacility(Integer billingFacility) {
        this.billingFacility = billingFacility;
    }

    public ListOptions getListOptions() {
        return listOptions;
    }

    public void setListOptions(ListOptions listOptions) {
        this.listOptions = listOptions;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "FormEncounter{" +
                "id=" + id +
                ", date=" + date +
                ", reason='" + reason + '\'' +
                ", facility='" + facility + '\'' +
                ", facilityId=" + facilityId +
                ", patientData=" + patientData +
                ", encounter=" + encounter +
                ", onsetDate=" + onsetDate +
                ", pcCatId=" + pcCatId +
                ", latLevelBilled=" + latLevelBilled +
                ", lastLevelClosed=" + lastLevelClosed +
                ", statementCount=" + statementCount +
                ", providerId=" + providerId +
                ", supervisorId=" + supervisorId +
                ", billingFacility=" + billingFacility +
                ", listOptions=" + listOptions +
                '}';
    }
}
