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

/**
 * Model that maps with procedure_result table of OpenEMR database
 */
@Entity
@Table(name = "procedure_result")
public class ProcedureResult implements Serializable {

    @Id
    @Column(name = "procedure_result_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long procedureResultId;

    @ManyToOne
    @JoinColumn(name = "procedure_report_id", referencedColumnName = "procedure_report_id")
    private ProcedureReport procedureReport;

    @Column(name = "result_data_type")
    private String resultDataType;

    @Column(name = "result_code")
    private String resultCode;

    @Column(name = "result_text")
    private String resultText;

    @Column(name = "units")
    private String units;

    @Column(name = "result")
    private String result;

    @Column(name = "\"range\"")
    private String range;

    @Column(name = "result_status")
    private String resultStatus;

    public ProcedureResult() {
    }

    public ProcedureResult(Long procedureResultId, ProcedureReport procedureReport, String resultDataType, String resultCode, String resultText,
                           String units, String result, String range, String resultStatus) {
        this.procedureResultId = procedureResultId;
        this.procedureReport = procedureReport;
        this.resultDataType = resultDataType;
        this.resultCode = resultCode;
        this.resultText = resultText;
        this.units = units;
        this.result = result;
        this.range = range;
        this.resultStatus = resultStatus;
    }

    public Long getProcedureResultId() {
        return procedureResultId;
    }

    public void setProcedureResultId(Long procedureResultId) {
        this.procedureResultId = procedureResultId;
    }

    public ProcedureReport getProcedureReport() {
        return procedureReport;
    }

    public void setProcedureReport(ProcedureReport procedureReport) {
        this.procedureReport = procedureReport;
    }

    public String getResultDataType() {
        return resultDataType;
    }

    public void setResultDataType(String resultDataType) {
        this.resultDataType = resultDataType;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultText() {
        return resultText;
    }

    public void setResultText(String resultText) {
        this.resultText = resultText;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(String resultStatus) {
        this.resultStatus = resultStatus;
    }

    @Override
    public String toString() {
        return "ProcedureResult{" +
            "procedureResultId=" + procedureResultId +
            ", procedureReport=" + procedureReport +
            ", resultDataType='" + resultDataType + '\'' +
            ", resultCode='" + resultCode + '\'' +
            ", resultText='" + resultText + '\'' +
            ", units='" + units + '\'' +
            ", result='" + result + '\'' +
            ", referenceRange='" + range + '\'' +
            ", resultStatus='" + resultStatus + '\'' +
            '}';
    }
}
