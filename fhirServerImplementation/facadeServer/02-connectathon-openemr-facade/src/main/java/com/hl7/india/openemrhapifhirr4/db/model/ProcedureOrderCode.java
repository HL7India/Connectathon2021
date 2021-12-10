package com.hl7.india.openemrhapifhirr4.db.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Model that maps with procedure_order_code table of OpenEMR database
 */
@Entity
@Table(name = "procedure_order_code")
public class ProcedureOrderCode implements Serializable {
    @Id
    @Column(name = "procedure_order_id")
    private Long procedureOrderId;
    @Column(name = "procedure_order_seq")
    private Integer procedureSequence;
    @Column(name = "procedure_code")
    private String procedureCode;
    @Column(name = "procedure_name")
    private String procedureName;
    @Column(name = "procedure_order_title")
    private String procedureTitle;

    public ProcedureOrderCode() {
    }

    public ProcedureOrderCode(Long procedureOrderId, Integer procedureSequence, String procedureCode,
                              String procedureName, String procedureTitle) {
        this.procedureOrderId = procedureOrderId;
        this.procedureSequence = procedureSequence;
        this.procedureCode = procedureCode;
        this.procedureName = procedureName;
        this.procedureTitle = procedureTitle;
    }

    public Long getProcedureOrderId() {
        return procedureOrderId;
    }

    public void setProcedureOrderId(Long procedureOrderId) {
        this.procedureOrderId = procedureOrderId;
    }

    public Integer getProcedureSequence() {
        return procedureSequence;
    }

    public void setProcedureSequence(Integer procedureSequence) {
        this.procedureSequence = procedureSequence;
    }

    public String getProcedureCode() {
        return procedureCode;
    }

    public void setProcedureCode(String procedureCode) {
        this.procedureCode = procedureCode;
    }

    public String getProcedureName() {
        return procedureName;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }

    public String getProcedureTitle() {
        return procedureTitle;
    }

    public void setProcedureTitle(String procedureTitle) {
        this.procedureTitle = procedureTitle;
    }

    @Override
    public String toString() {
        return "ProcedureOrderCode{" +
                "id=" + procedureOrderId +
                ", procedureSequence=" + procedureSequence +
                ", procedureCode='" + procedureCode + '\'' +
                ", procedureName='" + procedureName + '\'' +
                ", procedureTitle='" + procedureTitle + '\'' +
                '}';
    }
}
