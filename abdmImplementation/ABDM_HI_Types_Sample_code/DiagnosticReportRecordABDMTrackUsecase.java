public class DiagnosticsReport {

	
	static FhirContext ctx = FhirContext.forR4();

	static FhirInstanceValidator instanceValidator;
	static FhirValidator validator;

//	==============================================
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
//	public static Observation populateLipidPanelResource() {
//		Observation observation = new Observation();
//		observation.setId("8");
//		observation.getMeta().addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/Observation");
//		observation.getText().setStatus(NarrativeStatus.GENERATED);
//		observation.getText().setDivAsString(
//				"<div xmlns=\"http://www.w3.org/1999/xhtml\"><p>Patient/1 has Lipid panel observations,</p><p> - Lipid panel : \n\tTotal cholesterol: 162 mg/dl (normal: less than 200 mg/dl) \n\t - HDL cholesterol: 43 mg/dl (normal: greater than or equal to 40 mg/dl)\n\t - LDL cholesterol (calculated): 84 mg/dl (normal: less than 100 mg/dl)\n\t - Triglycerides: 177 mg/dl (normal: less than 150 mg/dl) \n\t - Cholesterol-to-HDL ratio: 3.8 (normal: less than 5.0)</p></div>");
//		observation.setStatus(ObservationStatus.FINAL);
//
//		observation.setCode(new CodeableConcept(
//				new Coding("http://loinc.org", "57698-3", "Lipid panel with direct LDL - Serum or Plasma"))
//						.setText("Lipid panel with direct LDL - Serum or Plasma"));
//
//		observation.setSubject(new Reference().setReference("Patient/1"));
//
//		observation.addPerformer(new Reference().setReference("Practitioner/1"));
//
//		observation.addComponent().setCode(new CodeableConcept(
//				new Coding("http://loinc.org", "2093-3", "Cholesterol [Mass/volume] in Serum or Plasma")));
//		observation.addComponent().setValue(
//				new Quantity().setValue(162).setUnit("mg/dl").setSystem("http://unitsofmeasure.org").setCode("mg/dl"));
//
//		observation.addComponent().setCode(new CodeableConcept(
//				new Coding("http://loinc.org", "2085-9", "Cholesterol [Mass/volume] in Serum or Plasma")));
//
//		observation.addComponent().setValue(
//				new Quantity().setValue(43).setUnit("mg/dl").setSystem("http://unitsofmeasure.org").setCode("mg/dl"));
//
//		observation.addComponent().setCode(new CodeableConcept(
//				new Coding("http://loinc.org", "13457-7", "Cholesterol [Mass/volume] in Serum or Plasma")));
//
//		observation.addComponent().setValue(
//				new Quantity().setValue(84).setUnit("mg/dl").setSystem("http://unitsofmeasure.org").setCode("mg/dl"));
//
//		observation.addComponent().setCode(new CodeableConcept(
//				new Coding("http://loinc.org", "2571-8", "Cholesterol [Mass/volume] in Serum or Plasma")));
//
//		observation.addComponent().setValue(
//				new Quantity().setValue(177).setUnit("mg/dl").setSystem("http://unitsofmeasure.org").setCode("mg/dl"));
//
//		observation.addComponent().setCode(new CodeableConcept(
//				new Coding("http://loinc.org", "9830-1", "Cholesterol [Mass/volume] in Serum or Plasma")));
//
//		observation.addComponent().setValue(new Quantity().setValue(177).setUnit("{ratio}"));
//		return observation;
//
//	}

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
				.setTitle("Diagnostic Report").setDataElement(new Base64BinaryType("UEsDBBQABgAIAAAAIQAykW9XZgEAAKUAZTUAAAAA"));

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
		String[] fileList = new File("E:\\FHIR\\CDAC\\fhir_sample_code\\definitions\\").list(new WildcardFileFilter("*.xml"));
		for(String file:fileList)
		{
			//Parse All Profiles and add to prepopulated support
			sd = parser.parseResource(StructureDefinition.class, new FileReader("E:\\FHIR\\CDAC\\fhir_sample_code\\definitions\\"+file));
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
