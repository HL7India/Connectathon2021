package com.hl7.india.openemrhapifhirr4.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Model that maps with patient_data table of OpenEMR database
 */
@Entity
@Table(name = "patient_data")
public class PatientData implements Serializable {
    @Id
    @Column(name = "pid")
    private Long pid;
    @Column(name = "title")
    private String title;
    @Column(name = "fname")
    private String firstName;
    @Column(name = "lname")
    private String lastName;
    @Column(name = "mname")
    private String middleName;
    @Column(name = "street")
    private String street;
    @Column(name = "postal_code")
    private String postalCode;
    @Column(name = "city")
    private String city;
    @Column(name = "state")
    private String state;
    @Column(name = "country_code")
    private String countryCode;
    @Column(name = "phone_cell")
    private String mobilePhone;
    @Column(name = "phone_home")
    private String homePhone;
    @Column(name = "phone_biz")
    private String workPhone;
    @Column(name = "sex")
    private String sex;
    @Column(name = "email")
    private String email;
    @Column(name = "pubpid")
    private String mrn;
    @Column(name = "DOB")
    private Date dob;
    @Column(name = "genericval1")
    private String healthId;
    @Column(name = "deceased_date")
    private Date deceasedDate;
    @Column(name = "status")
    private String maritalStatus;


    public PatientData() {
    }

    public PatientData(Long pid, String title, String firstName, String lastName, String middleName,
                       String street, String postalCode, String city, String state, String countryCode,
                       String mobilePhone, String homePhone, String workPhone, String sex, String email,
                       String mrn, Date dob, String healthId, Date deceasedDate, String maritalStatus) {
        this.pid = pid;
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.street = street;
        this.postalCode = postalCode;
        this.city = city;
        this.state = state;
        this.countryCode = countryCode;
        this.mobilePhone = mobilePhone;
        this.homePhone = homePhone;
        this.workPhone = workPhone;
        this.sex = sex;
        this.email = email;
        this.mrn = mrn;
        this.dob = dob;
        this.healthId = healthId;
        this.deceasedDate = deceasedDate;
        this.maritalStatus = maritalStatus;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMrn() {
        return mrn;
    }

    public void setMrn(String mrn) {
        this.mrn = mrn;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getHealthId() {
        return healthId;
    }

    public void setHealthId(String healthId) {
        this.healthId = healthId;
    }

    public Date getDeceasedDate() {
        return deceasedDate;
    }

    public void setDeceasedDate(Date deceasedDate) {
        this.deceasedDate = deceasedDate;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    @Override
    public String toString() {
        return "PatientData{" +
            "id=" + pid +
            ", title='" + title + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", middleName='" + middleName + '\'' +
            ", street='" + street + '\'' +
            ", postalCode='" + postalCode + '\'' +
            ", city='" + city + '\'' +
            ", state='" + state + '\'' +
            ", countryCode='" + countryCode + '\'' +
            ", mobilePhone='" + mobilePhone + '\'' +
            ", homePhone='" + homePhone + '\'' +
            ", workPhone='" + workPhone + '\'' +
            ", sex='" + sex + '\'' +
            ", email='" + email + '\'' +
            ", mrn='" + mrn + '\'' +
            ", dob=" + dob +
            ", healthId='" + healthId + '\'' +
            ", deceasedDate=" + deceasedDate +
            ", maritalStatus='" + maritalStatus + '\'' +
            '}';
    }
}