# FHIRClientDemoProject
This project covers below functionalities 
- V2 to FHIR mapping 
- Upload data to FHIR Server which is deployed on AWS 
- How to use cutom types, extensions and interceptors 

# Project Structure
### com.fhir.connectathon
This package has below classes
- V2ToFHIRConvertorDemo.java : This is main class for V2 to FHIR Mapping and uploding data to FHIR servre 
- CustomTypeDemo : This is main class to show how to use Custom Types

### com.fhir.connectathon.config
This package has below classes
- FhirContextBean: This class returns FhirContext object

### com.fhir.connectathon.constants
This package has below classes
- AWSCognitoConstants: This has details about AWS configiration like clientId, clientSecret, username, password  etc.
- FHIRConstants : This has constansts related to FHIR like fhir base url, system urls.

### com.fhir.connectathon.custom.resources
This pakcage has below class
- CustomPatient:  This class shows how to create and use custom types 

### com.fhir.connectathon.mapper
- PatientMapper: Class to conver HL7 v2 ADT message to FHIR Patient resource
- ObservationMapper: Class to conver HL7 v2 ORU message to FHIR Observation resource

### com.fhir.connectathon.utility
This pakcage has below class
- AWSCongnitoUtility: Methods to get AWS CLient, AWS Cognito access token 
- FileUtility: Methods to read txt files
- FHIRUtility: Methods to upload, read FHIR resorce to server, convert resource to JSON object etc 

### resources
This folder has files for ADT and ORU HL7 v2 messages
- ADTMessageOutput.txt: File to store ADT HL7 V2 messages
- ORUMessageOutput.txt: File to store ORU HL7 V2 messages

### logs
This folder will save logs and error files.
### properties
- log4j.properties:  Configuration file of log4j

### pom.xml
This file contains all dependencies

# How to run project 
- Clone the project using below command: 
  
  git clone https://github.com/learnhl7-arun/hapi-fhir-fcon2k1.git
  
- Import the SampleProject into editor
- Go the file V2ToFHIRConvertorDemo.java
- Right click on the file and run as Java Application

This will upload the Patient and Observation data into FHIR JPA server hosted on AWS
