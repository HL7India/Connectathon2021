package com.hl7.india.openemrhapifhirr4.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Model that maps with sequences table of OpenEMR database
 */
@Entity
@Table(name = "sequences")
public class Sequences implements Serializable {

    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "encounter_id")
    private Long encounterId;
    @Column(name = "pid")
    private Long pid;

    public Sequences() {
    }

    public Sequences(Integer id, Long encounterId, Long pid) {
        this.id = id;
        this.encounterId = encounterId;
        this.pid = pid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getEncounterId() {
        return encounterId;
    }

    public void setEncounterId(Long encounterId) {
        this.encounterId = encounterId;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    @Override
    public String toString() {
        return "Sequence{" +
            "id=" + id +
            ", encounterId=" + encounterId +
            ", pid=" + pid +
            '}';
    }
}
