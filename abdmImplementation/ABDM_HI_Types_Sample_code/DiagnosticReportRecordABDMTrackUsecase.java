package in.nrces.ndhm.fhir.r4.clinicalartifacts;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.hl7.fhir.common.hapi.validation.support.CommonCodeSystemsTerminologyService;
import org.hl7.fhir.common.hapi.validation.support.InMemoryTerminologyServerValidationSupport;
import org.hl7.fhir.common.hapi.validation.support.PrePopulatedValidationSupport;
import org.hl7.fhir.common.hapi.validation.support.SnapshotGeneratingValidationSupport;
import org.hl7.fhir.common.hapi.validation.support.ValidationSupportChain;
import org.hl7.fhir.common.hapi.validation.validator.FhirInstanceValidator;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.Base64BinaryType;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Bundle.BundleEntryComponent;
import org.hl7.fhir.r4.model.Bundle.BundleType;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.Composition;
import org.hl7.fhir.r4.model.Composition.CompositionStatus;
import org.hl7.fhir.r4.model.Composition.SectionComponent;
import org.hl7.fhir.r4.model.ContactPoint;
import org.hl7.fhir.r4.model.ContactPoint.ContactPointSystem;
import org.hl7.fhir.r4.model.ContactPoint.ContactPointUse;
import org.hl7.fhir.r4.model.DateTimeType;
import org.hl7.fhir.r4.model.DateType;
import org.hl7.fhir.r4.model.DecimalType;
import org.hl7.fhir.r4.model.DiagnosticReport;
import org.hl7.fhir.r4.model.DiagnosticReport.DiagnosticReportStatus;
import org.hl7.fhir.r4.model.Enumerations.AdministrativeGender;
import org.hl7.fhir.r4.model.Identifier;
import org.hl7.fhir.r4.model.InstantType;
import org.hl7.fhir.r4.model.Meta;
import org.hl7.fhir.r4.model.Narrative;
import org.hl7.fhir.r4.model.Narrative.NarrativeStatus;
import org.hl7.fhir.r4.model.Observation;
import org.hl7.fhir.r4.model.Observation.ObservationReferenceRangeComponent;
import org.hl7.fhir.r4.model.Observation.ObservationStatus;
import org.hl7.fhir.r4.model.Organization;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.Practitioner;
import org.hl7.fhir.r4.model.Quantity;
import org.hl7.fhir.r4.model.Reference;
import org.hl7.fhir.r4.model.Specimen;
import org.hl7.fhir.r4.model.Specimen.SpecimenCollectionComponent;
import org.hl7.fhir.r4.model.StructureDefinition;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.context.support.DefaultProfileValidationSupport;
import ca.uhn.fhir.parser.DataFormatException;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.validation.FhirValidator;
import ca.uhn.fhir.validation.SingleValidationMessage;
import ca.uhn.fhir.validation.ValidationResult;

public class DiagnosticReportRecordABDMTrackUsecase {

	
	static FhirContext ctx = FhirContext.forR4();

	static FhirInstanceValidator instanceValidator;
	static FhirValidator validator;

	public static void main(String[] args) throws DataFormatException, IOException
	{
		//Initialize validation support and loads all required profiles
		init();

		// Populate the resource
		Bundle diagnosticReportLabBundle = populateDiagnosticReportLabBundle();

		// Validate it. Validate method return result of validation in boolean
		// If validation result is true then parse, serialize operations are performed	
		if(validate(diagnosticReportLabBundle))
		{
			System.out.println("Validated populated DiagnosticReportLab bundle successfully");

			// Instantiate a new parser
			IParser parser; 

			// Enter file path (Eg: C://generatedexamples//bundle-prescriptionrecord.json)
			// Depending on file type xml/json instantiate the parser
			File file;
			Scanner scanner = new Scanner(System.in);
			System.out.println("\nEnter file path to write bundle");
			String filePath = scanner.nextLine();
			if(FilenameUtils.getExtension(filePath).equals("json"))
			{
				parser = ctx.newJsonParser();
			}
			else if(FilenameUtils.getExtension(filePath).equals("xml"))
			{
				parser = ctx.newXmlParser();
			}
			else
			{
				System.out.println("Invalid file extention!");
				if(scanner!=null)
					scanner.close();
				return;
			}

			// Indent the output
			parser.setPrettyPrint(true);

			// Serialize populated bundle
			String serializeBundle = parser.encodeResourceToString(diagnosticReportLabBundle);

			// Write serialized bundle in xml/json file
			file = new File(filePath);
			file.createNewFile();	
			FileWriter writer = new FileWriter(file);
			writer.write(serializeBundle);
			writer.flush();
			writer.close();
			scanner.close();

			// Parse the xml/json file
			IBaseResource resource = parser.parseResource(new FileReader(new File(filePath)));

			// Validate Parsed file
			if(validate(resource)){
				System.out.println("Validated parsed file successfully");
			}
			else{
				System.out.println("Failed to validate parsed file");
			}
		}
		else
		{
			System.out.println("Failed to validate populate Prescription bundle");
		}
	}
	
	// Populate Composition for DiagnosticReport Lab
	static Composition populateDiagnosticReportRecordLabCompositionResource() {
		Composition composition = new Composition();

		// Set logical id of this artifact
		composition.setId("1");

		// Set metadata about the resource - Version Id, Lastupdated Date, Profile
		Meta meta = composition.getMeta();
		meta.setVersionId("1");
		meta.setLastUpdatedElement(new InstantType("2020-07-09T15:32:26.605+05:30"));
		meta.addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/DiagnosticReportRecord");

		// Set language of the resource content
//			composition.setLanguage("en-IN");

		// Plain text representation of the concept
		Narrative text = composition.getText();
		text.setStatus((NarrativeStatus.GENERATED));
		text.setDivAsString(
				"<div xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en-IN\"><h4>Narrative with Details</h4><p><b>id:</b> 1</p><p><b>status:</b> final</p><p><b>category:</b> Hematology service (Details : {http://snomed.info/sct} code '708184003' = 'Laboratory service')</p><p><b>code:</b> Laboratory report (Details : {http://loinc.org} code '11502-2' = 'Laboratory report')</p><p><b>subject:</b> Prakash Kumar. Generated Summary: id: 1; Medical Record Number = 1234 (System : {https://healthid.ndhm.gov.in}); active; Prakash Kumar ; ph: +919818512600(HOME); gender: male; birthDate: 1958-11-26</p>  <p><b>issued:</b> 2021-11-27T11:45:33+11:00</p><p><b>performer:</b> XYZ Lab Pvt.Ltd.</p><p><b>resultInterpreter:</b>Dr.DEF. Generated Summary: id: 1; Medical License number = 7601003178999 (System : {doctor.ndhm.gov.in})</p><h3>Lab Report for Prakash Kumar :</h3><p>- Glucose (fasting): 178 mg/dl (normal range: 65 to 109 mg/dl)\n - Lipid panel : \n\tTotal cholesterol: 162 mg/dl (normal: less than 200 mg/dl) \n\t - HDL cholesterol: 43 mg/dl (normal: greater than or equal to 40 mg/dl)\n\t - LDL cholesterol (calculated): 84 mg/dl (normal: less than 100 mg/dl)\n\t - Triglycerides: 177 mg/dl (normal: less than 150 mg/dl) \n\t - Cholesterol-to-HDL ratio: 3.8 (normal: less than 5.0) \n - A1C: 8.1% (normal: 4�6%)\n - Urine microalbumin: 45 mg (normal: less than 30 mg)</p><p>XYZ Lab Pvt.Ltd., Inc signed: Dr. DEF Pathologist</p></div>");

		// Set version-independent identifier for the Composition
		Identifier identifier = composition.getIdentifier();
		identifier.setSystem("https://ndhm.in/phr");
		identifier.setValue("645bb0c3-ff7e-4123-bef5-3852a4784813");

		// Status can be preliminary | final | amended | entered-in-error
		composition.setStatus(CompositionStatus.FINAL);

		// Kind of composition ("Diagnostic studies report")
		CodeableConcept type = composition.getType();
		type.addCoding(new Coding("http://snomed.info/sct", "721981007", "Diagnostic studies report"));
		type.setText("Diagnostic Report- Lab");

		// Set subject - Who and/or what the composition/DiagnosticReport record is
		// about
		composition.setSubject(new Reference().setReference("Patient/1"));

		// Set Timestamp
		composition.setDateElement(new DateTimeType("2021-11-27T11:46:09+05:30"));

		// Set author - Who and/or what authored the composition/DiagnosticReport record
		composition.addAuthor(new Reference().setReference("Practitioner/1"));

		// Set a Human Readable name/title
		composition.setTitle("Diagnostic Report- Lab");

		// Composition is broken into sections / DiagnosticReport Lab record contains
		// single section to define the relevant medication requests
		// Entry is a reference to data that supports this section
		Reference reference1 = new Reference();
		reference1.setReference("DiagnosticReport/1");
		reference1.setType("DiagnosticReport");

		SectionComponent section = new SectionComponent();
		section.setTitle("Hematology report");
		section.setCode(new CodeableConcept(new Coding("http://snomed.info/sct", "4241000179101", "Laboratory report")))
				.addEntry(reference1);
		composition.addSection(section);

		return composition;
	}

	// Populate Patient Resource
	public static Patient populatePatientResource() {
		Patient patient = new Patient();
		patient.setId("1");
		patient.getMeta().setVersionId("1").setLastUpdatedElement(new InstantType("2020-07-09T14:58:58.181+05:30"))
				.addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/Patient");
		patient.getText().setStatus(NarrativeStatus.GENERATED).setDivAsString(
				"<div xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en-IN\">Prakash Kumar, 63 year, Male</div>");
		patient.addIdentifier()
				.setType(new CodeableConcept(
						new Coding("http://terminology.hl7.org/CodeSystem/v2-0203", "MR", "Medical record number")))
				.setSystem("https://ndhm.in/SwasthID").setValue("2-7225-4829-5255");
		patient.addName().setText("Prakash Kumar");
		patient.addTelecom().setSystem(ContactPointSystem.PHONE).setValue("+919818512600").setUse(ContactPointUse.HOME);
		patient.setGender(AdministrativeGender.MALE).setBirthDateElement(new DateType("1958-11-26"));
		return patient;
	}

	// Populate Practitioner Resource
	public static Practitioner populatePractitionerResource() {
		Practitioner practitioner = new Practitioner();
		practitioner.setId("1");
		practitioner.getMeta().setVersionId("1").setLastUpdatedElement(new InstantType("2019-05-29T14:58:58.181+05:30"))
				.addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/Practitioner");
		practitioner.getText().setStatus(NarrativeStatus.GENERATED).setDivAsString(
				"<div xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en-IN\" >Dr. DEF, MD (Pathology)</div>");
		practitioner.addIdentifier()
				.setType(new CodeableConcept(
						new Coding("http://terminology.hl7.org/CodeSystem/v2-0203", "MD", "Medical License number")))
				.setSystem("https://doctor.ndhm.gov.in").setValue("21-1521-3828-3227");
		practitioner.addName().setText("Dr. DEF");
		return practitioner;
	}

	// Populate Organization Resource
	public static Organization populateOrganizationResource() {
		Organization organization = new Organization();
		organization.setId("1");
		organization.getMeta().addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/Organization");
		organization.getIdentifier()
				.add(new Identifier()
						.setType(new CodeableConcept(
								new Coding("http://terminology.hl7.org/CodeSystem/v2-0203", "PRN", "Provider number")))
						.setSystem("https://facility.ndhm.gov.in").setValue("4567878"));
		organization.setName("XYZ Lab Pvt.Ltd");
		List<ContactPoint> list = new ArrayList<ContactPoint>();
		ContactPoint contact1 = new ContactPoint();
		contact1.setSystem(ContactPointSystem.PHONE).setValue("+91 243 2634 1234").setUse(ContactPointUse.WORK);
		ContactPoint contact2 = new ContactPoint();
		contact2.setSystem(ContactPointSystem.EMAIL).setValue("contact@labs.xyz.org").setUse(ContactPointUse.WORK);
		list.add(contact1);
		list.add(contact2);
		organization.setTelecom(list);
		return organization;
	}

	// Populate "Observation/BloodGlucose" Resource
	public static Observation populateBloodGlucoseResource() {
		Observation observation = new Observation();
		observation.setId("7");
		observation.getMeta()
				.addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/ObservationGeneralAssessment");
		observation.getText().setStatus(NarrativeStatus.GENERATED);
		observation.getText().setDivAsString(
				"<div xmlns=\"http://www.w3.org/1999/xhtml\"><p>Patient/1 is having Glucose (fasting): 178 mg/dl (normal range: 65 to109 mg/dl)</p></div>");
		observation.setStatus(ObservationStatus.FINAL);
		observation.setCode(new CodeableConcept(
				new Coding("http://loinc.org", "76629-5", "Fasting glucose [Moles/volume] in Blood"))
						.setText("Glucose [Mass/volume] in Blood"));
		observation.setSubject(new Reference().setReference("Patient/1"));
		observation.setValue(new Quantity().setValue(9.9).setUnit("mmol/l").setSystem("http://unitsofmeasure.org")
				.setCode("mmol/L"));

		observation.getReferenceRange()
				.add(new ObservationReferenceRangeComponent()
						.setHigh(new Quantity().setValueElement(new DecimalType("6.2")).setCode("258813002")
								.setUnit("mmol/l").setSystem("http://unitsofmeasure.org").setCode("mmol/l")));

		observation.addPerformer(new Reference().setReference("Practitioner/1"));
		observation.addInterpretation(new CodeableConcept(
				new Coding("http://terminology.hl7.org/CodeSystem/v3-ObservationInterpretation", "H", "High")));

		observation.getReferenceRange().add(
				new ObservationReferenceRangeComponent().setLow(new Quantity().setValueElement(new DecimalType("3.1"))
						.setCode("mmol/L").setUnit("mmol/l").setSystem("http://unitsofmeasure.org").setCode("mmol/l")));
		return observation;

	}

	// Populate "Observation/LipidPanel" Resource
	public static Observation populateLipidPanelResource() {
		Observation observation = new Observation();
		observation.setId("8");
		observation.getMeta().addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/Observation");
		observation.getText().setStatus(NarrativeStatus.GENERATED);
		observation.getText().setDivAsString(
				"<div xmlns=\"http://www.w3.org/1999/xhtml\"><p>Patient/1 has Lipid panel observations,</p><p> - Lipid panel : \n\tTotal cholesterol: 162 mg/dl (normal: less than 200 mg/dl) \n\t - HDL cholesterol: 43 mg/dl (normal: greater than or equal to 40 mg/dl)\n\t - LDL cholesterol (calculated): 84 mg/dl (normal: less than 100 mg/dl)\n\t - Triglycerides: 177 mg/dl (normal: less than 150 mg/dl) \n\t - Cholesterol-to-HDL ratio: 3.8 (normal: less than 5.0)</p></div>");
		observation.setStatus(ObservationStatus.FINAL);

		observation.setCode(new CodeableConcept(
				new Coding("http://loinc.org", "57698-3", "Lipid panel with direct LDL - Serum or Plasma"))
						.setText("Lipid panel with direct LDL - Serum or Plasma"));

		observation.setSubject(new Reference().setReference("Patient/1"));

		observation.addPerformer(new Reference().setReference("Practitioner/1"));

		observation.addComponent().setCode(new CodeableConcept(
				new Coding("http://loinc.org", "2093-3", "Cholesterol [Mass/volume] in Serum or Plasma")));
		observation.addComponent().setValue(
				new Quantity().setValue(162).setUnit("mg/dl").setSystem("http://unitsofmeasure.org").setCode("mg/dl"));

		observation.addComponent().setCode(new CodeableConcept(
				new Coding("http://loinc.org", "2085-9", "Cholesterol in HDL [Mass/volume] in Serum or Plasma")));

		observation.addComponent().setValue(
				new Quantity().setValue(43).setUnit("mg/dl").setSystem("http://unitsofmeasure.org").setCode("mg/dl"));

		observation.addComponent().setCode(new CodeableConcept(
				new Coding("http://loinc.org", "13457-7", "Cholesterol in LDL [Mass/volume] in Serum or Plasma by calculation")));

		observation.addComponent().setValue(
				new Quantity().setValue(84).setUnit("mg/dl").setSystem("http://unitsofmeasure.org").setCode("mg/dl"));

		observation.addComponent().setCode(new CodeableConcept(
				new Coding("http://loinc.org", "2571-8", "Triglyceride [Mass/volume] in Serum or Plasma")));

		observation.addComponent().setValue(
				new Quantity().setValue(177).setUnit("mg/dl").setSystem("http://unitsofmeasure.org").setCode("mg/dl"));

		observation.addComponent().setCode(new CodeableConcept(
				new Coding("http://loinc.org", "9830-1", "Cholesterol.total/Cholesterol in HDL [Mass Ratio] in Serum or Plasma")));

		observation.addComponent().setValue(new Quantity().setValue(3.8).setUnit("{ratio}"));
		return observation;

	}

	public static Observation populateHemoglobinResource() {

		Observation observation = new Observation();
		observation.setId("9");
		observation.getMeta().addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/Observation");
		observation.getText().setStatus(NarrativeStatus.GENERATED).setDivAsString(
				"<div xmlns=\"http://www.w3.org/1999/xhtml\"><p>Patient/1 is having A1C: 8.1% (normal: 4–6%)</p></div>");

		observation.setStatus(ObservationStatus.FINAL);

		observation.setCode(new CodeableConcept(
				new Coding("http://loinc.org", "41995-2", "Hemoglobin A1c [Mass/volume] in Blood")));

		observation.setSubject(new Reference().setReference("Patient/1"));

		observation.addPerformer(new Reference().setReference("Practitioner/1"));

		observation.setValue(
				new Quantity().setValue(8.1).setUnit("%").setSystem("http://unitsofmeasure.org").setCode("%"));

		observation.getReferenceRange().add(
				new ObservationReferenceRangeComponent().setLow(new Quantity().setValueElement(new DecimalType("4"))
						.setCode("%").setUnit("%").setSystem("http://unitsofmeasure.org")));

		observation.getReferenceRange().add(
				new ObservationReferenceRangeComponent().setHigh(new Quantity().setValueElement(new DecimalType("6"))
						.setCode("%").setUnit("%").setSystem("http://unitsofmeasure.org")));
		return observation;

	}

	public static Observation populateMicroalbuminResource() {

		Observation observation = new Observation();
		observation.setId("10");
		observation.getMeta().addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/Observation");
		observation.getText().setStatus(NarrativeStatus.GENERATED).setDivAsString(
				"<div xmlns=\"http://www.w3.org/1999/xhtml\"><p>Patient/1 is having Urine microalbumin: 45 mg (normal: less than 30 mg)</p></div>");

		observation.setStatus(ObservationStatus.FINAL);

		observation.setCode(
				new CodeableConcept(new Coding("http://loinc.org", "14957-5", "Microalbumin [Mass/volume] in Urine")));

		observation.setSubject(new Reference().setReference("Patient/1"));

		observation.addPerformer(new Reference().setReference("Practitioner/1"));

		observation.setValue(
				new Quantity().setValue(45).setUnit("mg").setSystem("http://unitsofmeasure.org").setCode("mg"));

		observation.getReferenceRange().add(
				new ObservationReferenceRangeComponent().setHigh(new Quantity().setValueElement(new DecimalType("30"))
						.setCode("mg").setUnit("mg").setSystem("http://unitsofmeasure.org")));
		return observation;

	}


	public static DiagnosticReport populateDiagonosticReportLabResource() {
		DiagnosticReport diagnosticReportLab = new DiagnosticReport();
		diagnosticReportLab.setId("1");
		diagnosticReportLab.getMeta().setVersionId("1")
				.setLastUpdatedElement(new InstantType("2020-07-09T15:32:26.605+05:30"))
				.addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/DiagnosticReportLab");
		diagnosticReportLab.setStatus(DiagnosticReportStatus.FINAL);

		diagnosticReportLab.getText().setStatus(NarrativeStatus.GENERATED).setDivAsString(
				"<div xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en-IN\" >Diagnostic report for patient/1 <p>- Glucose (fasting): 178 mg/dl (normal range: 65 to 109 mg/dl)\n - Lipid panel : \n\tTotal cholesterol: 162 mg/dl (normal: less than 200 mg/dl) \n\t - HDL cholesterol: 43 mg/dl (normal: greater than or equal to 40 mg/dl)\n\t - LDL cholesterol (calculated): 84 mg/dl (normal: less than 100 mg/dl)\n\t - Triglycerides: 177 mg/dl (normal: less than 150 mg/dl) \n\t - Cholesterol-to-HDL ratio: 3.8 (normal: less than 5.0) \n - A1C: 8.1% (normal: 4–6%)\n - Urine microalbumin: 45 mg (normal: less than 30 mg)</p></div>");

		diagnosticReportLab.addCategory(
				new CodeableConcept(new Coding("http://snomed.info/sct", "708184003", "Laboratory service")));

		diagnosticReportLab
				.setCode(new CodeableConcept(new Coding("http://loinc.org", "11502-2", "Laboratory report")));

		diagnosticReportLab.setSubject(new Reference().setReference("Patient/1").setDisplay("Prakash Kumar"));

		diagnosticReportLab.getResultsInterpreter()
				.add(new Reference().setReference("Practitioner/1").setDisplay("Dr. DEF"));

		diagnosticReportLab.addPerformer(new Reference().setReference("Organization/1").setDisplay("XYZ Lab Pvt.Ltd."));

		diagnosticReportLab.setConclusion(
				"uncontrolled type 2 diabetes mellitus, Hyperlipidemia, Elevated urine microalbumin level");

		diagnosticReportLab.addSpecimen(new Reference().setReference("Specimen/1"));

		diagnosticReportLab.addResult(new Reference().setReference("Observation/Observation-cholesterol"))
				.addResult(new Reference().setReference("Observation/7"))
				.addResult(new Reference().setReference("Observation/8"))
				.addResult(new Reference().setReference("Observation/9"))
				.addResult(new Reference().setReference("Observation/10"));

		diagnosticReportLab.addPresentedForm().setContentType("application/pdf").setLanguage("en-IN")
				.setTitle("Diagnostic Report").setDataElement(new Base64BinaryType("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"));

		return diagnosticReportLab;

	}

	// Populate Specimen Resource
	public static Specimen populateSpecimenResource() {
		Specimen specimen = new Specimen();
		specimen.setId("1");
		specimen.getMeta().addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/Specimen");
		specimen.setType(new CodeableConcept(new Coding("http://snomed.info/sct", "119297000", "Blood specimen")));
		specimen.setSubject(new Reference().setReference("Patient/1"));
		specimen.setReceivedTimeElement(new DateTimeType("2021-11-26T06:40:17Z"));

		specimen.setCollection(
				new SpecimenCollectionComponent().setCollected(new DateTimeType("2021-11-26T06:40:17Z")));
		return specimen;
	}

	// Populate Specimen Resource
	public static Specimen populateSpecimen2Resource() {
		Specimen specimen = new Specimen();
		specimen.setId("2");
		specimen.getMeta().addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/Specimen");
		specimen.setType(new CodeableConcept(new Coding("http://snomed.info/sct", "122575003", "Urine specimen")));
		specimen.setSubject(new Reference().setReference("Patient/1"));
		specimen.setReceivedTimeElement(new DateTimeType("2021-11-26T06:40:17Z"));

		specimen.setCollection(
				new SpecimenCollectionComponent().setCollected(new DateTimeType("2021-11-26T06:40:17Z")));
		return specimen;
	}

//	==============================================
	


	static Bundle populateDiagnosticReportLabBundle()
	{
		Bundle diagnosticReportBundle = new Bundle();

		// Set logical id of this artifact
		diagnosticReportBundle.setId("DiagnosticReport-Lab-example-03");

		// Set metadata about the resource - Version Id, Lastupdated Date, Profile
		Meta meta = diagnosticReportBundle.getMeta();
		meta.setVersionId("1");
		meta.setLastUpdatedElement(new InstantType("2020-07-09T15:32:26.605+05:30"));
		meta.addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/DocumentBundle");

		// Set Confidentiality as defined by affinity domain
		meta.addSecurity(new Coding("http://terminology.hl7.org/CodeSystem/v3-Confidentiality", "V", "very restricted"));

		// Set version-independent identifier for the Composition
		Identifier identifier = diagnosticReportBundle.getIdentifier();
		identifier.setValue("3cf54fc4-0178-4127-bb99-b20711404881");
		identifier.setSystem("http://hip.in");

		// Set Bundle Type
		diagnosticReportBundle.setType(BundleType.DOCUMENT);

		// Set Timestamp
		diagnosticReportBundle.setTimestampElement(new InstantType("2020-07-09T15:32:26.605+05:30"));

		// Add resources entries for bundle with Full URL
		List<BundleEntryComponent> listBundleEntries = diagnosticReportBundle.getEntry();

		BundleEntryComponent bundleEntry1 = new BundleEntryComponent();
		bundleEntry1.setFullUrl("Composition/Composition-01");
		bundleEntry1.setResource(populateDiagnosticReportRecordLabCompositionResource());

		BundleEntryComponent bundleEntry2 = new BundleEntryComponent();
		bundleEntry2.setFullUrl("Patient/Patient-01");
		bundleEntry2.setResource(populatePatientResource());

		BundleEntryComponent bundleEntry3 = new BundleEntryComponent();
		bundleEntry3.setFullUrl("Practitioner/Practitioner-01");
		bundleEntry3.setResource(populatePractitionerResource());

		BundleEntryComponent bundleEntry4 = new BundleEntryComponent();
		bundleEntry4.setFullUrl("Organization/Organization-01");
		bundleEntry4.setResource(populateOrganizationResource());

		BundleEntryComponent bundleEntry5 = new BundleEntryComponent();
		bundleEntry5.setFullUrl("DiagnosticReport/DiagnosticReport-01");
		bundleEntry5.setResource(populateBloodGlucoseResource());

//		BundleEntryComponent bundleEntry6 = new BundleEntryComponent();
//		bundleEntry6.setFullUrl("Observation/Observation-cholesterol");
//		bundleEntry6.setResource(populateLipidPanelResource());

		BundleEntryComponent bundleEntry7 = new BundleEntryComponent();
		bundleEntry7.setFullUrl("Observation/Observation-triglyceride");
		bundleEntry7.setResource(populateHemoglobinResource());

		BundleEntryComponent bundleEntry8 = new BundleEntryComponent();
		bundleEntry8.setFullUrl("Specimen/Specimen-01");
		bundleEntry8.setResource(populateMicroalbuminResource());

		BundleEntryComponent bundleEntry9 = new BundleEntryComponent();
		bundleEntry9.setFullUrl("ServiceRequest/ServiceRequest-01");
		bundleEntry9.setResource(populateSpecimenResource());

		BundleEntryComponent bundleEntry10 = new BundleEntryComponent();
		bundleEntry10.setFullUrl("DocumentReference/DocumentReference-01");
		bundleEntry10.setResource(populateSpecimen2Resource());

		BundleEntryComponent bundleEntry11 = new BundleEntryComponent();
		bundleEntry11.setFullUrl("DocumentReference/DocumentReference-01");
		bundleEntry11.setResource(populateDiagonosticReportLabResource());
		
		
		
		
		listBundleEntries.add(bundleEntry1);
		listBundleEntries.add(bundleEntry2);
		listBundleEntries.add(bundleEntry3);
		listBundleEntries.add(bundleEntry4);
		listBundleEntries.add(bundleEntry5);
//		listBundleEntries.add(bundleEntry6);
		listBundleEntries.add(bundleEntry7);
		listBundleEntries.add(bundleEntry8);
		listBundleEntries.add(bundleEntry9);
		listBundleEntries.add(bundleEntry11);

		return diagnosticReportBundle;
	}

	static void init() throws DataFormatException, FileNotFoundException
	{

		// Create xml parser object for reading profiles
		IParser parser = ctx.newXmlParser();

		// Create a chain that will hold our modules
		ValidationSupportChain supportChain = new ValidationSupportChain();
		
		// Add Default Profile Support
		// DefaultProfileValidationSupport supplies base FHIR definitions. This is generally required
		// even if you are using custom profiles, since those profiles will derive from the base
		// definitions.
		DefaultProfileValidationSupport defaultSupport = new DefaultProfileValidationSupport(ctx);
		
		// Create a PrePopulatedValidationSupport which can be used to load custom definitions.
		// In this example we're loading all the custom Profile Structure Definitions, in other scenario we might
		// load many StructureDefinitions, ValueSets, CodeSystems, etc.
		PrePopulatedValidationSupport prePopulatedSupport = new PrePopulatedValidationSupport(ctx);
		StructureDefinition sd ;
		
		/** LOADING PROFILES **/
		// Read all Profile Structure Definitions 
		String[] fileList = new File("E:\\NDHM\\NDHM_FHIR_Profiles\\").list(new WildcardFileFilter("*.xml"));
		for(String file:fileList)
		{
			//Parse All Profiles and add to prepopulated support
			sd = parser.parseResource(StructureDefinition.class, new FileReader("E:\\NDHM\\NDHM_FHIR_Profiles\\"+file));
			prePopulatedSupport.addStructureDefinition(sd);
		}

		//Add Snapshot Generation Support
		SnapshotGeneratingValidationSupport snapshotGenerator = new SnapshotGeneratingValidationSupport(ctx);

		//Add prepopulated support consisting all structure definitions and Terminology support
		supportChain.addValidationSupport(defaultSupport);
		supportChain.addValidationSupport(prePopulatedSupport);
		supportChain.addValidationSupport(snapshotGenerator);
		supportChain.addValidationSupport(new InMemoryTerminologyServerValidationSupport(ctx));
		supportChain.addValidationSupport(new CommonCodeSystemsTerminologyService(ctx));

		// Create a validator using the FhirInstanceValidator module and register.
		instanceValidator = new FhirInstanceValidator(supportChain);
		validator = ctx.newValidator().registerValidatorModule(instanceValidator);

	}

	/**
	 * This method validates the FHIR resources 
	 */
	static boolean validate(IBaseResource resource)
	{
		// Validate
		ValidationResult result = validator.validateWithResult(resource);

		// The result object now contains the validation results
		for (SingleValidationMessage next : result.getMessages()) {
			System.out.println(next.getSeverity().name() + " : " + next.getLocationString() + " " + next.getMessage());
		}

		return result.isSuccessful();
	}



}
