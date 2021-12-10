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
 * Model that maps with procedure_report table of OpenEMR database
 */
@Entity
@Table(name = "procedure_report")
public class ProcedureReport implements Serializable {

    @Id
    @Column(name = "procedure_report_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long procedureReportId;

    @ManyToOne
    @JoinColumn(name = "procedure_order_id", referencedColumnName = "procedure_order_id")
    private ProcedureOrder procedureOrder;

    @Column(name = "procedure_order_seq")
    private Integer procedureSequence;

    @Column(name = "date_report")
    private Date dateReport;

    @Column(name = "report_status")
    private String reportStatus;

    @Column(name = "review_status")
    private String reviewStatus;

    public ProcedureReport() {
    }

    public ProcedureReport(Long procedureReportId, ProcedureOrder procedureOrder, Integer procedureSequence,
                           Date dateReport, String reportStatus, String reviewStatus) {
        this.procedureReportId = procedureReportId;
        this.procedureOrder = procedureOrder;
        this.procedureSequence = procedureSequence;
        this.dateReport = dateReport;
        this.reportStatus = reportStatus;
        this.reviewStatus = reviewStatus;
    }

    public Long getProcedureReportId() {
        return procedureReportId;
    }

    public void setProcedureReportId(Long procedureReportId) {
        this.procedureReportId = procedureReportId;
    }

    public ProcedureOrder getProcedureOrder() {
        return procedureOrder;
    }

    public void setProcedureOrder(ProcedureOrder procedureOrder) {
        this.procedureOrder = procedureOrder;
    }

    public Integer getProcedureSequence() {
        return procedureSequence;
    }

    public void setProcedureSequence(Integer procedureSequence) {
        this.procedureSequence = procedureSequence;
    }

    public Date getDateReport() {
        return dateReport;
    }

    public void setDateReport(Date dateReport) {
        this.dateReport = dateReport;
    }

    public String getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(String reportStatus) {
        this.reportStatus = reportStatus;
    }

    public String getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(String reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    @Override
    public String toString() {
        return "ProcedureReport{" +
            "procedureReportId=" + procedureReportId +
            ", procedureOrder=" + procedureOrder +
            ", procedureSequence=" + procedureSequence +
            ", dateReport=" + dateReport +
            ", reportStatus='" + reportStatus + '\'' +
            ", reviewStatus='" + reviewStatus + '\'' +
            '}';
    }
}
