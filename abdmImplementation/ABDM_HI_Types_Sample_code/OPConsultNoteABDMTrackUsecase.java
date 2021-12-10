
/**
 * The OPConsultNoteSample class populates, validates, parse and serializes
 * Clinical Artifact - OPConsultNote
 */
public class OPConsultNoteSample {

	// The FHIR context is the central starting point for the use of the HAPI FHIR
	// API
	// It should be created once, and then used as a factory for various other types
	// of objects (parsers, clients, etc.)
	static FhirContext ctx = FhirContext.forR4();

	static FhirInstanceValidator instanceValidator;
	static FhirValidator validator;

	public static void main(String[] args) throws DataFormatException, IOException {
		// Initialize validation support and loads all required profiles
		init();

		// Populate the resource
		Bundle OPConsultNoteBundle = populateOPConsultNoteBundle();

		// Validate it. Validate method return result of validation in boolean
		// If validation result is true then parse, serialize operations are performed
		if (validate(OPConsultNoteBundle)) {
			System.out.println("Validated populated OPConsultNote bundle successfully");

			// Instantiate a new parser
			IParser parser;

			// Enter file path (Eg: C://generatedexamples//bundle-prescriptionrecord.json)
			// Depending on file type xml/json instantiate the parser
			File file;
			Scanner scanner = new Scanner(System.in);
			System.out.println("\nEnter file path to write bundle");
			String filePath = scanner.nextLine();
			if (FilenameUtils.getExtension(filePath).equals("json")) {
				parser = ctx.newJsonParser();
			} else if (FilenameUtils.getExtension(filePath).equals("xml")) {
				parser = ctx.newXmlParser();
			} else {
				System.out.println("Invalid file extention!");
				if (scanner != null)
					scanner.close();
				return;
			}

			// Indent the output
			parser.setPrettyPrint(true);

			// Serialize populated bundle
			String serializeBundle = parser.encodeResourceToString(OPConsultNoteBundle);

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
			if (validate(resource)) {
				System.out.println("Validated parsed file successfully");
			} else {
				System.out.println("Failed to validate parsed file");
			}
		} else {
			System.out.println("Failed to validate populate Prescription bundle");
		}
	}

	static Bundle populateOPConsultNoteBundle() {

		Bundle opCounsultNoteBundle = new Bundle();

		// Set logical id of this artifact
		opCounsultNoteBundle.setId("OPConsultNote-example-05");

		// Set metadata about the resource - Version Id, Lastupdated Date, Profile
		Meta meta = opCounsultNoteBundle.getMeta();
		meta.setVersionId("1");
		meta.setLastUpdatedElement(new InstantType("2021-11-25T15:32:26.605+05:30"));
		meta.addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/DocumentBundle");

		// Set Confidentiality as defined by affinity domain
		meta.addSecurity(
				new Coding("http://terminology.hl7.org/CodeSystem/v3-Confidentiality", "V", "very restricted"));

		// Set version-independent identifier for the Composition
		Identifier identifier = opCounsultNoteBundle.getIdentifier();
		identifier.setValue("305fecc2-4ba2-46cc-9ccd-efa755aff51d");
		identifier.setSystem("http://hip.in").setValue("305fecc2-4ba2-46cc-9ccd-efa755aff51d");

		// Set Bundle Type
		opCounsultNoteBundle.setType(BundleType.DOCUMENT);

		// Set Timestamp
		opCounsultNoteBundle.setTimestampElement(new InstantType("2021-11-25T15:32:26.605+05:30"));

		// Add resources entries for bundle with Full URL
		List<BundleEntryComponent> listBundleEntries = opCounsultNoteBundle.getEntry();

		BundleEntryComponent bundleEntry1 = new BundleEntryComponent();
		bundleEntry1.setFullUrl("Composition/1");
		bundleEntry1.setResource(populateOPConsultNoteCompositionResource());

		BundleEntryComponent bundleEntry2 = new BundleEntryComponent();
		bundleEntry2.setFullUrl("Practitioner/1");
		bundleEntry2.setResource(populatePractitionerResource());

		BundleEntryComponent bundleEntry3 = new BundleEntryComponent();
		bundleEntry3.setFullUrl("Organization/1");
		bundleEntry3.setResource(populateOrganizationResource());

		BundleEntryComponent bundleEntry4 = new BundleEntryComponent();
		bundleEntry4.setFullUrl("Patient/1");
		bundleEntry4.setResource(populatePatientResource());

		BundleEntryComponent bundleEntry5 = new BundleEntryComponent();
		bundleEntry5.setFullUrl("Encounter/1");
		bundleEntry5.setResource(populateEncounterResource());

		BundleEntryComponent bundleEntry6 = new BundleEntryComponent();
		bundleEntry6.setFullUrl("FamilyMemberHistory/1");
		bundleEntry6.setResource(populateFamilyMemberHistoryResource());

		BundleEntryComponent bundleEntry7 = new BundleEntryComponent();
		bundleEntry7.setFullUrl("FamilyMemberHistory/2");
		bundleEntry7.setResource(populateSecondFamilyMemberHistoryResource());

		BundleEntryComponent bundleEntry8 = new BundleEntryComponent();
		bundleEntry8.setFullUrl("Observation/1");
		bundleEntry8.setResource(populateBodyWeightResource());

		BundleEntryComponent bundleEntry9 = new BundleEntryComponent();
		bundleEntry9.setFullUrl("Observation/2");
		bundleEntry9.setResource(populateBodyHeightResource());

		BundleEntryComponent bundleEntry10 = new BundleEntryComponent();
		bundleEntry10.setFullUrl("Observation/3");
		bundleEntry10.setResource(populateBloodPressureResource());

		BundleEntryComponent bundleEntry11 = new BundleEntryComponent();
		bundleEntry11.setFullUrl("Observation/4");
		bundleEntry11.setResource(populateHeartResource());

		BundleEntryComponent bundleEntry12 = new BundleEntryComponent();
		bundleEntry12.setFullUrl("Observation/5");
		bundleEntry12.setResource(populateEyeResource());

		BundleEntryComponent bundleEntry13 = new BundleEntryComponent();
		bundleEntry13.setFullUrl("Observation/6");
		bundleEntry13.setResource(populateEye2Resource());

		BundleEntryComponent bundleEntry14 = new BundleEntryComponent();
		bundleEntry14.setFullUrl("Observation/7");
		bundleEntry14.setResource(populateSedentaryResource());

		BundleEntryComponent bundleEntry15 = new BundleEntryComponent();
		bundleEntry15.setFullUrl("Condition/1");
		bundleEntry15.setResource(populateConditionResource());

		BundleEntryComponent bundleEntry16 = new BundleEntryComponent();
		bundleEntry16.setFullUrl("Condition/2");
		bundleEntry16.setResource(populateCondition2Resource());

		BundleEntryComponent bundleEntry17 = new BundleEntryComponent();
		bundleEntry17.setFullUrl("Condition/3");
		bundleEntry17.setResource(populateCondition3Resource());

		BundleEntryComponent bundleEntry18 = new BundleEntryComponent();
		bundleEntry18.setFullUrl("Condition/4");
		bundleEntry18.setResource(populateCondition4Resource());

		BundleEntryComponent bundleEntry19 = new BundleEntryComponent();
		bundleEntry19.setFullUrl("Condition/5");
		bundleEntry19.setResource(populateCondition5Resource());

		BundleEntryComponent bundleEntry20 = new BundleEntryComponent();
		bundleEntry20.setFullUrl("Condition/6");
		bundleEntry20.setResource(populateCondition6Resource());

		BundleEntryComponent bundleEntry21 = new BundleEntryComponent();
		bundleEntry21.setFullUrl("Condition/7");
		bundleEntry21.setResource(populateCondition7Resource());

		BundleEntryComponent bundleEntry22 = new BundleEntryComponent();
		bundleEntry22.setFullUrl("Condition/8");
		bundleEntry22.setResource(populateCondition8Resource());

		BundleEntryComponent bundleEntry23 = new BundleEntryComponent();
		bundleEntry23.setFullUrl("Condition/9");
		bundleEntry23.setResource(populateCondition9Resource());

		BundleEntryComponent bundleEntry24 = new BundleEntryComponent();
		bundleEntry24.setFullUrl("Condition/10");
		bundleEntry24.setResource(populateCondition10Resource());

		BundleEntryComponent bundleEntry25 = new BundleEntryComponent();
		bundleEntry25.setFullUrl("Condition/11");
		bundleEntry25.setResource(populateCondition11Resource());

		BundleEntryComponent bundleEntry26 = new BundleEntryComponent();
		bundleEntry26.setFullUrl("Condition/12");
		bundleEntry26.setResource(populateCondition12Resource());

		BundleEntryComponent bundleEntry27 = new BundleEntryComponent();
		bundleEntry27.setFullUrl("Condition/13");
		bundleEntry27.setResource(populateCondition13Resource());

//

		BundleEntryComponent bundleEntry28 = new BundleEntryComponent();
		bundleEntry28.setFullUrl("ServiceRequest/1");
		bundleEntry28.setResource(populateServiceRequest1Resource());

		BundleEntryComponent bundleEntry29 = new BundleEntryComponent();
		bundleEntry29.setFullUrl("ServiceRequest/2");
		bundleEntry29.setResource(populateServiceRequest2Resource());

		BundleEntryComponent bundleEntry30 = new BundleEntryComponent();
		bundleEntry30.setFullUrl("ServiceRequest/3");
		bundleEntry30.setResource(populateServiceRequest3Resource());

		BundleEntryComponent bundleEntry31 = new BundleEntryComponent();
		bundleEntry31.setFullUrl("ServiceRequest/4");
		bundleEntry31.setResource(populateServiceRequest4Resource());

		BundleEntryComponent bundleEntry32 = new BundleEntryComponent();
		bundleEntry32.setFullUrl("DocumentReference/1");
		bundleEntry32.setResource(populateDocumentReferenceResource());

		listBundleEntries.add(bundleEntry1);
		listBundleEntries.add(bundleEntry2);
		listBundleEntries.add(bundleEntry3);
		listBundleEntries.add(bundleEntry4);
		listBundleEntries.add(bundleEntry5);
		listBundleEntries.add(bundleEntry6);
		listBundleEntries.add(bundleEntry7);
		listBundleEntries.add(bundleEntry8);
		listBundleEntries.add(bundleEntry9);
		listBundleEntries.add(bundleEntry10);
		listBundleEntries.add(bundleEntry11);
		listBundleEntries.add(bundleEntry12);
		listBundleEntries.add(bundleEntry13);
		listBundleEntries.add(bundleEntry14);
		listBundleEntries.add(bundleEntry15);
		listBundleEntries.add(bundleEntry16);
		listBundleEntries.add(bundleEntry17);
		listBundleEntries.add(bundleEntry18);
		listBundleEntries.add(bundleEntry19);
		listBundleEntries.add(bundleEntry20);
		listBundleEntries.add(bundleEntry21);
		listBundleEntries.add(bundleEntry22);
		listBundleEntries.add(bundleEntry23);
		listBundleEntries.add(bundleEntry24);
		listBundleEntries.add(bundleEntry25);
		listBundleEntries.add(bundleEntry26);

		listBundleEntries.add(bundleEntry27);
		listBundleEntries.add(bundleEntry28);
		listBundleEntries.add(bundleEntry29);
		listBundleEntries.add(bundleEntry30);
		listBundleEntries.add(bundleEntry31);
		listBundleEntries.add(bundleEntry32);

//		

//		opCounsultNoteBundle.setSignature(new Signature().addType(new Coding("urn:iso-astm:E1762-95:2013", "1.2.840.10065.1.12.1.1", "Author's Signature"))) ;

//		opCounsultNoteBundle.getSignature().setWho(new Reference().setReference("Practitioner/1")).setSigFormat("image/jpeg");

		return opCounsultNoteBundle;
	}

	/**
	 * This method initiates loading of FHIR default profiles and NDHM profiles for
	 * validation
	 */
	static void init() throws DataFormatException, FileNotFoundException {

		// Create xml parser object for reading profiles
		IParser parser = ctx.newXmlParser();

		// Create a chain that will hold our modules
		ValidationSupportChain supportChain = new ValidationSupportChain();

		// Add Default Profile Support
		// DefaultProfileValidationSupport supplies base FHIR definitions. This is
		// generally required
		// even if you are using custom profiles, since those profiles will derive from
		// the base
		// definitions.
		DefaultProfileValidationSupport defaultSupport = new DefaultProfileValidationSupport(ctx);

		// Create a PrePopulatedValidationSupport which can be used to load custom
		// definitions.
		// In this example we're loading all the custom Profile Structure Definitions,
		// in other scenario we might
		// load many StructureDefinitions, ValueSets, CodeSystems, etc.
		PrePopulatedValidationSupport prePopulatedSupport = new PrePopulatedValidationSupport(ctx);
		StructureDefinition sd;

		/** LOADING PROFILES **/
		// Read all Profile Structure Definitions
		String[] fileList = new File("E:\\FHIR\\CDAC\\fhir_sample_code\\definitions\\")
				.list(new WildcardFileFilter("*.xml"));
		for (String file : fileList) {
			// Parse All Profiles and add to prepopulated support
			sd = parser.parseResource(StructureDefinition.class,
					new FileReader("E:\\FHIR\\CDAC\\fhir_sample_code\\definitions\\" + file));
			prePopulatedSupport.addStructureDefinition(sd);
		}

		// Add Snapshot Generation Support
		SnapshotGeneratingValidationSupport snapshotGenerator = new SnapshotGeneratingValidationSupport(ctx);

		// Add prepopulated support consisting all structure definitions and Terminology
		// support
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
	static boolean validate(IBaseResource resource) {
		// Validate
		ValidationResult result = validator.validateWithResult(resource);

		// The result object now contains the validation results
		for (SingleValidationMessage next : result.getMessages()) {
			System.out.println(next.getSeverity().name() + " : " + next.getLocationString() + " " + next.getMessage());
		}

		return result.isSuccessful();
	}

	// Populate Composition for OPConsultNote
	static Composition populateOPConsultNoteCompositionResource() {
		Composition composition = new Composition();

		// Set logical id of this artifact
		composition.setId("1");

		// Set metadata about the resource - Version Id, Lastupdated Date, Profile
		Meta meta = composition.getMeta();
		meta.setVersionId("1");
		meta.setLastUpdatedElement(new InstantType("2021-11-25T15:32:26.605+05:30"));
		meta.addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/OPConsultRecord");

		// Set language of the resource content
//			composition.setLanguage("en-IN");

		// Plain text representation of the concept
		Narrative text = composition.getText();
		text.setStatus((NarrativeStatus.GENERATED));

		text.setDivAsString(
				"<div xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en-IN\" ><h4>Narrative with Details</h4><p>This is a OP Consult Note for Patient Prakash Kumar. Generated Summary: id: 1; Medical Record Number = 1234 (System : {https://healthid.ndhm.gov.in}); active; Prakash Kumar ; ph: +919818512600(HOME); gender: male; birthDate: 1958-11-26</p></div>");

		Identifier identifier = composition.getIdentifier();
		identifier.setSystem("https://ndhm.in/phr");
		identifier.setValue("645bb0c3-ff7e-4123-bef5-3852a4784813");

		// Status can be preliminary | final | amended | entered-in-error
		composition.setStatus(CompositionStatus.FINAL);

		// Kind of composition ("Clinical consultation report")
		CodeableConcept type = composition.getType();
		type.addCoding(new Coding("http://snomed.info/sct", "371530004", "Clinical consultation report"));
		type.setText("Clinical Consultation report");

		// composition.setType(new CodeableConcept(new Coding("http://snomed.info/sct",
		// "371530004", "Clinical consultation report")).setText("Clinical Consultation
		// report"));

		// Set subject - Who and/or what the composition/OPConsultNote record is about
		Reference reference = new Reference();
		reference.setReference("Patient/1");
		reference.setDisplay("Prakash Kumar");
		composition.setSubject(reference);

		// Set Context of the Composition
		composition.setEncounter(new Reference().setReference("Encounter/1"));

		// Set Timestamp
		composition.setDateElement(new DateTimeType("2021-11-25T15:32:26.605+05:30"));

		// Set author - Who and/or what authored the composition/OPConsultNote record
		Reference referenceAuthor = new Reference();
		referenceAuthor.setReference("Practitioner/1");
		referenceAuthor.setDisplay("Dr DEF");
		composition.addAuthor(referenceAuthor);

		// Set a Human Readable name/title
		composition.setTitle("Consultation Report");

		// Set Custodian - Organization which maintains the composition
		Reference referenceCustodian = new Reference();
		referenceCustodian.setReference("Organization/1");
		referenceCustodian.setDisplay("UVW Hospital");
		composition.setCustodian(referenceCustodian);

		// Composition is broken into sections / OPConsultNote record contains single
		// section to define the relevant medication requests
		// Entry is a reference to data that supports this section
		SectionComponent section1 = new SectionComponent();
		section1.setTitle("Chief complaints");
		section1.setCode(
				new CodeableConcept(new Coding("http://snomed.info/sct", "422843007", "Chief complaint section")))
				.addEntry(new Reference().setReference("Condition/1"))
				.addEntry(new Reference().setReference("Condition/2"));

		SectionComponent section2 = new SectionComponent();
		section2.setTitle("PhysicalExamination");
		section2.setCode(
				new CodeableConcept(new Coding("http://snomed.info/sct", "425044008", "Physical exam section")))
				.addEntry(new Reference().setReference("Observation/1"))
				.addEntry(new Reference().setReference("Observation/2"))
				.addEntry(new Reference().setReference("Observation/3"))
				.addEntry(new Reference().setReference("Observation/4"))
				.addEntry(new Reference().setReference("Observation/5"))
				.addEntry(new Reference().setReference("Observation/6"))
				.addEntry(new Reference().setReference("Observation/7"));

		SectionComponent section3 = new SectionComponent();
		section3.setTitle("Medical History");
		section3.setCode(
				new CodeableConcept(new Coding("http://snomed.info/sct", "371529009", "History and physical report")))
				.addEntry(new Reference().setReference("Condition/3"))
				.addEntry(new Reference().setReference("Condition/4"))
				.addEntry(new Reference().setReference("Condition/5"))
				.addEntry(new Reference().setReference("Condition/6"));

		SectionComponent section4 = new SectionComponent();
		section4.setTitle("Family History");
		section4.setCode(
				new CodeableConcept(new Coding("http://snomed.info/sct", "422432008", "Family history section")))
				.addEntry(new Reference().setReference("FamilyMemberHistory/1"));

		SectionComponent section5 = new SectionComponent();

		List<SectionComponent> sectionList = new ArrayList<SectionComponent>();
		sectionList.add(section1);
		sectionList.add(section2);
		sectionList.add(section3);
		sectionList.add(section4);
		sectionList.add(section5);

		composition.setSection(sectionList);

		return composition;
	}

	// Populate Practitioner Resource!!!!
	public static Practitioner populatePractitionerResource() {
		Practitioner practitioner = new Practitioner();
		practitioner.setId("1");
		practitioner.getMeta().setVersionId("1").setLastUpdatedElement(new InstantType("2021-11-25T15:32:26.605+05:30"))
				.addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/Practitioner");
		practitioner.getText().setStatus(NarrativeStatus.GENERATED).setDivAsString(
				"<div xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en-IN\">Dr. DEF, MD (Medicine)</div>");
		practitioner.addIdentifier()
				.setType(new CodeableConcept(
						new Coding("http://terminology.hl7.org/CodeSystem/v2-0203", "MD", "Medical License number")))
				.setSystem("https://doctor.ndhm.gov.in").setValue("21-1521-3828-3227");
		practitioner.addName().setText("Dr. DEF");
		return practitioner;
	}

	// Populate Organization Resource!!!!
	public static Organization populateOrganizationResource() {
		Organization organization = new Organization();
		organization.setId("1");
		organization.getMeta().addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/Organization");
		organization.getIdentifier()
				.add(new Identifier()
						.setType(new CodeableConcept(
								new Coding("http://terminology.hl7.org/CodeSystem/v2-0203", "PRN", "Provider number")))
						.setSystem("https://facility.ndhm.gov.in").setValue("4567878"));
		organization.setName("UVW Hospital");
		List<ContactPoint> list = new ArrayList<ContactPoint>();
		ContactPoint contact1 = new ContactPoint();
		contact1.setSystem(ContactPointSystem.PHONE).setValue("+91 273 2139 3632").setUse(ContactPointUse.WORK);
		ContactPoint contact2 = new ContactPoint();
		contact2.setSystem(ContactPointSystem.EMAIL).setValue("contact@facility.uvw.org").setUse(ContactPointUse.WORK);
		list.add(contact1);
		list.add(contact2);
		organization.setTelecom(list);
		return organization;
	}

	// Populate Patient Resource!!!!
	public static Patient populatePatientResource() {
		Patient patient = new Patient();
		patient.setId("1");
		patient.getMeta().setVersionId("1").setLastUpdatedElement(new InstantType("2021-11-25T14:58:58.181+05:30"))
				.addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/Patient");
		patient.getText().setStatus(NarrativeStatus.GENERATED).setDivAsString(
				"<div xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en-IN\" >Prakash Kumar, 63 year, Male</div>");
		patient.addIdentifier()
				.setType(new CodeableConcept(
						new Coding("http://terminology.hl7.org/CodeSystem/v2-0203", "MR", "Medical record number")))
				.setSystem("https://healthid.ndhm.gov.in").setValue("22-7225-4829-5255");
		patient.addName().setText("Prakash Kumar");
		patient.addTelecom().setSystem(ContactPointSystem.PHONE).setValue("+919818512600").setUse(ContactPointUse.HOME);
		patient.setGender(AdministrativeGender.MALE).setBirthDateElement(new DateType("1958-11-26"));
		return patient;
	}

	// Populate Encounter Resource!!!!!
	public static Encounter populateEncounterResource() {
		Encounter encounter = new Encounter();
		encounter.setId("1");
		encounter.setStatus(EncounterStatus.FINISHED);
		encounter.getMeta().setLastUpdatedElement(new InstantType("2021-11-25T14:58:58.181+05:30"))
				.addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/Encounter");
		encounter.getText().setStatus(NarrativeStatus.GENERATED).setDivAsString(
				"<div xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en-IN\">Out Patient Consultation Encounter</div>");

		encounter.getIdentifier().add(new Identifier().setSystem("https://ndhm.in").setValue("S100"));

		encounter.setClass_(new Coding("http://terminology.hl7.org/CodeSystem/v3-ActCode", "AMB", "ambulatory"));

		encounter.setSubject(new Reference().setReference("Patient/1"));

		encounter.setPeriod(new Period().setStartElement(new DateTimeType("2021-11-25T14:58:58.181+05:30")));
//			encounter
//					.setHospitalization(new EncounterHospitalizationComponent().setDischargeDisposition(new CodeableConcept(
//							new Coding("http://terminology.hl7.org/CodeSystem/discharge-disposition", "home", "Home"))
//									.setText("Discharged to Home Care")));

		encounter.addDiagnosis().setCondition(new Reference().setReference("Condition/1"))
				.setUse(new CodeableConcept(new Coding("http://snomed.info/sct", "33962009", "Chief complaint")))
				.setCondition(new Reference().setReference("Condition/1"))
				.setCondition(new Reference().setReference("Condition/1"));

		encounter.addDiagnosis().setCondition(new Reference().setReference("Condition/2"))
				.setUse(new CodeableConcept(new Coding("http://snomed.info/sct", "33962009", "Chief complaint")))
				.setCondition(new Reference().setReference("Condition/1"))
				.setCondition(new Reference().setReference("Condition/2"));

		encounter.addDiagnosis().setCondition(new Reference().setReference("Condition/7"))
				.setUse(new CodeableConcept(new Coding("http://snomed.info/sct", "148006", "Preliminary diagnosis")))
				.setCondition(new Reference().setReference("Condition/1"))
				.setCondition(new Reference().setReference("Condition/7"));

		encounter.addDiagnosis().setCondition(new Reference().setReference("Condition/8"))
				.setUse(new CodeableConcept(new Coding("http://snomed.info/sct", "148006", "Preliminary diagnosis")))
				.setCondition(new Reference().setReference("Condition/1"))
				.setCondition(new Reference().setReference("Condition/8"));

		encounter.addDiagnosis().setCondition(new Reference().setReference("Condition/9"))
				.setUse(new CodeableConcept(new Coding("http://snomed.info/sct", "148006", "Preliminary diagnosis")))
				.setCondition(new Reference().setReference("Condition/1"))
				.setCondition(new Reference().setReference("Condition/9"));

		encounter.addDiagnosis().setCondition(new Reference().setReference("Condition/10"))
				.setUse(new CodeableConcept(new Coding("http://snomed.info/sct", "148006", "Preliminary diagnosis")))
				.setCondition(new Reference().setReference("Condition/1"))
				.setCondition(new Reference().setReference("Condition/10"));

		encounter.addDiagnosis().setCondition(new Reference().setReference("Condition/11"))
				.setUse(new CodeableConcept(new Coding("http://snomed.info/sct", "148006", "Preliminary diagnosis")))
				.setCondition(new Reference().setReference("Condition/1"))
				.setCondition(new Reference().setReference("Condition/11"));

		encounter.addDiagnosis().setCondition(new Reference().setReference("Condition/12"))
				.setUse(new CodeableConcept(new Coding("http://snomed.info/sct", "148006", "Preliminary diagnosis")))
				.setCondition(new Reference().setReference("Condition/1"))
				.setCondition(new Reference().setReference("Condition/12"));

		encounter.addDiagnosis().setCondition(new Reference().setReference("Condition/13"))
				.setUse(new CodeableConcept(new Coding("http://snomed.info/sct", "148006", "Preliminary diagnosis")))
				.setCondition(new Reference().setReference("Condition/1"))
				.setCondition(new Reference().setReference("Condition/13"));

		return encounter;
	}

	// Populate Family Member History Resource!!!!!
	public static FamilyMemberHistory populateFamilyMemberHistoryResource() {
		FamilyMemberHistory familyMemberHistory = new FamilyMemberHistory();
		familyMemberHistory.setId("1");
		familyMemberHistory.getMeta()
				.addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/FamilyMemberHistory");

		familyMemberHistory.getText().setStatus(NarrativeStatus.GENERATED)
				.setDivAsString("div xmlns=\"http://www.w3.org/1999/xhtml\">Father had type 2 diabetes</div>");

		familyMemberHistory.addIdentifier().setValue("12345");
		familyMemberHistory.setStatus(FamilyHistoryStatus.COMPLETED);
		familyMemberHistory.getCondition()
				.add(new FamilyMemberHistoryConditionComponent(new CodeableConcept(
						new Coding("http://snomed.info/sct", "44054006", "populateFamilyMemberHistoryResource"))
								.setText("Type 2 diabetes mellitus")));

		familyMemberHistory.setPatient(new Reference().setReference("Patient/1"));
		familyMemberHistory.setDateElement(new DateTimeType("2011-03-18"));
		familyMemberHistory.setRelationship(
				new CodeableConcept(new Coding("http://terminology.hl7.org/CodeSystem/v3-RoleCode", "FTH", "father")));
		familyMemberHistory
				.setSex(new CodeableConcept(new Coding("http://hl7.org/fhir/administrative-gender", "male", "Male")));

		return familyMemberHistory;
	}

	// Populate Family Member History Resource!!!!!
	public static FamilyMemberHistory populateSecondFamilyMemberHistoryResource() {
		FamilyMemberHistory familyMemberHistory = new FamilyMemberHistory();
		familyMemberHistory.setId("1");
		familyMemberHistory.getMeta()
				.addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/FamilyMemberHistory");

		familyMemberHistory.getText().setStatus(NarrativeStatus.GENERATED)
				.setDivAsString("div xmlns=\"http://www.w3.org/1999/xhtml\">Mother had type 2 diabetes</div>");

		familyMemberHistory.addIdentifier().setValue("12345");
		familyMemberHistory.setStatus(FamilyHistoryStatus.COMPLETED);
		familyMemberHistory.getCondition()
				.add(new FamilyMemberHistoryConditionComponent(new CodeableConcept(new Coding("http://snomed.info/sct",
						"315619001", "FH myocardial infarction male first degree age known"))
								.setText("Type 2 diabetes mellitus")));

		familyMemberHistory.setPatient(new Reference().setReference("Patient/1"));
		familyMemberHistory.setDateElement(new DateTimeType("2011-03-18"));
		familyMemberHistory.setRelationship(
				new CodeableConcept(new Coding("http://terminology.hl7.org/CodeSystem/v3-RoleCode", "MTH", "mother")));
		familyMemberHistory.setSex(
				new CodeableConcept(new Coding("http://hl7.org/fhir/administrative-gender", "female", "female")));

		return familyMemberHistory;
	}

	// Populate Observation/body-weight Resource!!!
	public static Observation populateBodyWeightResource() {
		Observation observation = new Observation();
		observation.setId("1");
		observation.getMeta().addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/Observation");
		observation.getText().setStatus(NarrativeStatus.GENERATED);
		observation.getText()
				.setDivAsString("<div xmlns=\"http://www.w3.org/1999/xhtml\"><p>Patient/1 weight is 85 kg</p></div>");
		observation.setStatus(ObservationStatus.FINAL);

		observation.setCode(new CodeableConcept(new Coding("http://snomed.info/sct", "27113001", "Body weight"))
				.setText("Body weight"));
		observation.setSubject(new Reference().setReference("Patient/1"));
		observation.addPerformer(new Reference().setReference("Practitioner/1"));
		observation.setValue(
				new Quantity().setValue(85).setUnit("kg").setSystem("http://unitsofmeasure.org").setCode("kg"));

		return observation;
	}

	// Populate Observation/body-height Resource!!!!
	public static Observation populateBodyHeightResource() {
		Observation observation = new Observation();
		observation.setId("2");
		observation.getMeta().addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/Observation");
		observation.getText().setStatus(NarrativeStatus.GENERATED);
		observation.getText().setDivAsString(
				"<div xmlns=\"http://www.w3.org/1999/xhtml\"><p>Patient/1 height is 66 Inches (5 feet 6 inches)</p></div>");
		observation.setStatus(ObservationStatus.FINAL);

		observation.setCode(new CodeableConcept(new Coding("http://snomed.info/sct", "1153637007", "Body height"))
				.setText("Body height"));
		observation.setSubject(new Reference().setReference("Patient/1"));

		observation.setValue(
				new Quantity().setValue(66).setUnit("in").setSystem("http://unitsofmeasure.org").setCode("[in_i]"));

		return observation;
	}

	// Populate "Observation/blood-pressure" Resource!!!!
	public static Observation populateBloodPressureResource() {
		Observation observation = new Observation();
		observation.setId("3");
		observation.getMeta().addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/Observation");
		observation.getText().setStatus(NarrativeStatus.GENERATED);
		observation.getText().setDivAsString(
				"<div xmlns=\"http://www.w3.org/1999/xhtml\"><p>Blood pressure: sitting, right arm 140/90 mmHg</p></div>");

		observation.addIdentifier(new Identifier().setSystem("urn:ietf:rfc:3986")
				.setValue("urn:uuid:187e0c12-8dd2-67e2-99b2-bf273c878281"));

		observation.setStatus(ObservationStatus.FINAL);

//				observation.addCategory(new CodeableConcept(new Coding("http://terminology.hl7.org/CodeSystem/observation-category", "vital-signs", "Vital Signs")));
//				

		observation.setCode(
				new CodeableConcept(new Coding("http://snomed.info/sct", "163035008", "Sitting blood pressure"))
						.setText("Sitting blood pressure"));

		observation.setSubject(new Reference().setReference("Patient/1"));

//				observation.setEffective(new DateTimeType("2020-09-29"));

		observation.addPerformer(new Reference().setReference("Practitioner/1"));

//				
//				observation.addInterpretation(new CodeableConcept(
//						new Coding("http://terminology.hl7.org/CodeSystem/v3-ObservationInterpretation", "L", "low"))
//								.setText("Below low normal"));
//				

		observation.getBodySite().addCoding(new Coding("http://snomed.info/sct", "368209003", "Right arm"));

		List<ObservationComponentComponent> componentList = observation.getComponent();

		ObservationComponentComponent component = new ObservationComponentComponent();

		component.setCode(new CodeableConcept(
				new Coding("http://snomed.info/sct", "407554009", "Sitting systolic blood pressure")));

		component.setValue(
				new Quantity().setValue(140).setUnit("mmHg").setSystem("http://unitsofmeasure.org").setCode("mm[Hg]"));

		component.addInterpretation(new CodeableConcept(
				new Coding("http://terminology.hl7.org/CodeSystem/v3-ObservationInterpretation", "H", "High"))
						.setText("High"));

		ObservationComponentComponent component1 = new ObservationComponentComponent();

		component1.setCode(new CodeableConcept(
				new Coding("http://snomed.info/sct", "407555005", "Sitting diastolic blood pressure")));

		component1.setValue(
				new Quantity().setValue(90).setUnit("mmHg").setSystem("http://unitsofmeasure.org").setCode("mm[Hg]"));

		component1.addInterpretation(new CodeableConcept(
				new Coding("http://terminology.hl7.org/CodeSystem/v3-ObservationInterpretation", "H", "High"))
						.setText("High"));

		componentList.add(component);
		componentList.add(component1);

		return observation;
	}

	// Populate Observation Resource!!!!
	public static Observation populateHeartResource() {
		Observation observation = new Observation();
		observation.setId("4");
		observation.getMeta().addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/Observation");
		observation.getText().setStatus(NarrativeStatus.GENERATED);
		observation.getText().setDivAsString(
				"<div xmlns=\"http://www.w3.org/1999/xhtml\"><p>Patient/1 is having Heart rate and heart rhythm regular, no murmurs or gallops</p></div>");
		observation.setStatus(ObservationStatus.FINAL);

		// observation.addCategory(new CodeableConcept(
//					new Coding("http://terminology.hl7.org/CodeSystem/observation-category", "vital-signs", "Vital Signs"))
//							.setText("vital Signs"));

		observation.setCode(
				new CodeableConcept(new Coding("http://snomed.info/sct", "364074009", "Regularity of heart rhythm"))
						.setText("Regularity of heart rhythm"));
		observation.setSubject(new Reference().setReference("Patient/1"));
//			observation.setEffective(new DateTimeType("2020-09-29"));
//			observation.setValue(
//					new Quantity().setValue(16.2).setUnit("kg/m2").setSystem("http://unitsofmeasure.org").setCode("kg/m2"));

		return observation;
	}

	// Populate Observation Resource!!!!
	public static Observation populateEyeResource() {
		Observation observation = new Observation();
		observation.setId("5");
		observation.getMeta().addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/Observation");
		observation.getText().setStatus(NarrativeStatus.GENERATED);

		observation.getText().setDivAsString(
				"<div xmlns=\"http://www.w3.org/1999/xhtml\"><p>Patient/1 is using corrective lenses</p></div>");
		observation.setStatus(ObservationStatus.FINAL);

		observation.setCode(new CodeableConcept(
				new Coding("http://snomed.info/sct", "285049007", "Pupils equal, react to light and accommodation"))
						.setText("Pupils equal, react to light and accommodation"));
		observation.setSubject(new Reference().setReference("Patient/1"));
//			observation.setValue(new Quantity().setValue(10000).setUnit("steps").setSystem("http://unitsofmeasure.org")
//					.setCode("steps"));

		return observation;
	}

	// Populate Observation Resource!!!!
	public static Observation populateEye2Resource() {
		Observation observation = new Observation();
		observation.setId("6");
		observation.getMeta().addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/Observation");
		observation.getText().setStatus(NarrativeStatus.GENERATED);

		observation.getText().setDivAsString(
				"<div xmlns=\"http://www.w3.org/1999/xhtml\"><p>Patient/1 is having eyes pupils equal and reactive to light and accommodation</p></div>");
		observation.setStatus(ObservationStatus.FINAL);

		observation.setCode(new CodeableConcept(
				new Coding("http://snomed.info/sct", "386667005", "Pupils equal, react to light and accommodation"))
						.setText("Pupils equal, react to light and accommodation"));
		observation.setSubject(new Reference().setReference("Patient/1"));
//			observation.setValue(new Quantity().setValue(10000).setUnit("steps").setSystem("http://unitsofmeasure.org")
//					.setCode("steps"));

		return observation;
	}

	// Populate Observation Resource!!!!
	public static Observation populateSedentaryResource() {
		Observation observation = new Observation();
		observation.setId("7");
		observation.getMeta().addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/Observation");
		observation.getText().setStatus(NarrativeStatus.GENERATED);

		observation.getText().setDivAsString(
				"<div xmlns=\"http://www.w3.org/1999/xhtml\"><p>Patient/1 is having Sedentary lifestyle</p></div>");
		observation.setStatus(ObservationStatus.FINAL);

		observation
				.setCode(new CodeableConcept(new Coding("http://snomed.info/sct", "415510005", "Sedentary lifestyle")));
		observation.setSubject(new Reference().setReference("Patient/1"));

		observation.addPerformer(new Reference().setReference("Practitioner/1"));
//			observation.setValue(new Quantity().setValue(10000).setUnit("steps").setSystem("http://unitsofmeasure.org")
//					.setCode("steps"));

		return observation;
	}

	// Populate Condition Resource!!!!
	public static Condition populateConditionResource() {
		Condition condition = new Condition();
		condition.setId("1");
		condition.getMeta().addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/Condition");
		condition.getText().setStatus(NarrativeStatus.GENERATED).setDivAsString(
				"<div xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en-IN\">Increased frequency of urination</div>");
		condition.setSubject(new Reference().setReference("Patient/1"));
		condition.getCode()
				.addCoding(new Coding("http://snomed.info/sct", "162116003", "Increased frequency of urination"))
				.setText("Abdominal pain");

		condition.setSubject(new Reference().setReference("Patient/1"));
		condition.setClinicalStatus(new CodeableConcept(
				new Coding("http://terminology.hl7.org/CodeSystem/condition-clinical", "active", "Active")));

		return condition;
	}

	// Populate Condition Resource!!!!
	public static Condition populateCondition2Resource() {
		Condition condition = new Condition();
		condition.setId("2");
		condition.getMeta().addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/Condition");
		condition.getText().setStatus(NarrativeStatus.GENERATED)
				.setDivAsString("<div xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en-IN\" >Hazy vision</div>");
		condition.setSubject(new Reference().setReference("Patient/1"));
		condition.getCode().addCoding(new Coding("http://snomed.info/sct", "246636008", "Hazy vision"))
				.setText("Hazy vision");

		condition.setSubject(new Reference().setReference("Patient/1"));
		condition.setClinicalStatus(new CodeableConcept(
				new Coding("http://terminology.hl7.org/CodeSystem/condition-clinical", "active", "Active")));

		return condition;
	}

	// Populate Condition Resource!!!!!
	public static Condition populateCondition3Resource() {
		Condition condition = new Condition();
		condition.setId("3");
		condition.getMeta().addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/Condition");

		condition.getText().setStatus(NarrativeStatus.GENERATED).setDivAsString(
				"<div xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en-IN\" >Past Medical Problem of Type 2 diabetes mellitus</div>");

		condition.setSubject(new Reference().setReference("Patient/1"));

		condition.getCode().addCoding(new Coding("http://snomed.info/sct", "44054006", "Type 2 diabetes mellitus"))
				.setText("Type 2 diabetes mellitus");
		return condition;
	}

	// Populate Condition Resource!!!!!
	public static Condition populateCondition4Resource() {
		Condition condition = new Condition();
		condition.setId("4");
		condition.getMeta().addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/Condition");
		condition.getText().setStatus(NarrativeStatus.GENERATED).setDivAsString(
				"<div xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en-IN\">Past Medical Problem of Hyperglycemia</div>");
		condition.setSubject(new Reference().setReference("Patient/1"));
		condition.getCode().addCoding(new Coding("http://snomed.info/sct", "80394007", "Hyperglycemia"))
				.setText("Hyperglycemia");
		return condition;
	}

	// Populate Condition Resource
	public static Condition populateCondition5Resource() {
		Condition condition = new Condition();
		condition.setId("5");
		condition.getMeta().addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/Condition");
		condition.getText().setStatus(NarrativeStatus.GENERATED).setDivAsString(
				"<div xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en-IN\">Past Medical Problem of Nocturia</div>");
		condition.setSubject(new Reference().setReference("Patient/1"));
		condition.getCode().addCoding(new Coding("http://snomed.info/sct", "139394000", "Nocturia"))
				.setText("Nocturia");
		return condition;
	}

	// Populate Condition Resource
	public static Condition populateCondition6Resource() {
		Condition condition = new Condition();
		condition.setId("6");
		condition.getMeta().addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/Condition");
		condition.getText().setStatus(NarrativeStatus.GENERATED).setDivAsString(
				"<div xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en-IN\">Past Medical Problem of Elevated blood-pressure reading without diagnosis of hypertension</div>");
		condition.setSubject(new Reference().setReference("Patient/1"));
		condition.getCode()
				.addCoding(new Coding("http://snomed.info/sct", "371622005",
						"Elevated blood-pressure reading without diagnosis of hypertension"))
				.setText("Elevated blood-pressure reading without diagnosis of hypertension");
		return condition;
	}

	// Populate Condition Resource
	public static Condition populateCondition7Resource() {
		Condition condition = new Condition();
		condition.setId("7");
		condition.getMeta().addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/Condition");
		condition.getText().setStatus(NarrativeStatus.GENERATED).setDivAsString(
				"<div xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en-IN\" >Type II diabetes mellitus uncontrolled</div>");
		condition.setSubject(new Reference().setReference("Patient/1"));
		condition.getCode()
				.addCoding(new Coding("http://snomed.info/sct", "443694000", "Type II diabetes mellitus uncontrolled"))
				.setText("Type II diabetes mellitus uncontrolled");
		return condition;
	}

	// Populate Condition Resource
	public static Condition populateCondition8Resource() {
		Condition condition = new Condition();
		condition.setId("8");
		condition.getMeta().addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/Condition");
		condition.getText().setStatus(NarrativeStatus.GENERATED).setDivAsString(
				"<div xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en-IN\">Hypertensive disorder, systemic arterial</div>");
		condition.setSubject(new Reference().setReference("Patient/1"));
		condition.getCode()
				.addCoding(new Coding("http://snomed.info/sct", "38341003", "Hypertensive disorder, systemic arterial"))
				.setText("Hypertensive disorder, systemic arterial");
		return condition;
	}

	// Populate Condition Resource
	public static Condition populateCondition9Resource() {
		Condition condition = new Condition();
		condition.setId("9");
		condition.getMeta().addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/Condition");
		condition.getText().setStatus(NarrativeStatus.GENERATED).setDivAsString(
				"<div xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en-IN\">Hypertension with albuminuria</div>");
		condition.setSubject(new Reference().setReference("Patient/1"));
		condition.getCode()
				.addCoding(new Coding("http://snomed.info/sct", "397748008", "Hypertension with albuminuria"))
				.setText("Hypertension with albuminuria");
		return condition;
	}

	// Populate Condition Resource
	public static Condition populateCondition10Resource() {
		Condition condition = new Condition();
		condition.setId("10");
		condition.getMeta().addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/Condition");
		condition.getText().setStatus(NarrativeStatus.GENERATED).setDivAsString(
				"<div xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en-IN\" >Exercise below recommended level</div>");
		condition.setSubject(new Reference().setReference("Patient/1"));
		condition.getCode()
				.addCoding(new Coding("http://snomed.info/sct", "413300002", "Exercise below recommended level"))
				.setText("Exercise below recommended level");
		return condition;
	}

	// Populate Condition Resource
	public static Condition populateCondition11Resource() {
		Condition condition = new Condition();
		condition.setId("11");
		condition.getMeta().addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/Condition");
		condition.getText().setStatus(NarrativeStatus.GENERATED).setDivAsString(
				"<div xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en-IN\" >High carbohydrate diet</div>");
		condition.setSubject(new Reference().setReference("Patient/1"));
		condition.getCode().addCoding(new Coding("http://snomed.info/sct", "226120004", "High carbohydrate diet"))
				.setText("High carbohydrate diet");
		return condition;
	}

	// Populate Condition Resource
	public static Condition populateCondition12Resource() {
		Condition condition = new Condition();
		condition.setId("12");
		condition.getMeta().addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/Condition");
		condition.getText().setStatus(NarrativeStatus.GENERATED).setDivAsString(
				"<div xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en-IN\">Noncompliance with self-monitoring regimen</div>");
		condition.setSubject(new Reference().setReference("Patient/1"));
		condition.getCode()
				.addCoding(new Coding("http://snomed.info/sct", "444781000124106",
						"Noncompliance with self-monitoring regimen"))
				.setText("Noncompliance with self-monitoring regimen");
		return condition;
	}

	// Populate Condition Resource
	public static Condition populateCondition13Resource() {
		Condition condition = new Condition();
		condition.setId("12");
		condition.getMeta().addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/Condition");
		condition.getText().setStatus(NarrativeStatus.GENERATED).setDivAsString(
				"<div xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en-IN\" >Deficient knowledge of disease process</div>");
		condition.setSubject(new Reference().setReference("Patient/1"));
		condition.getCode()
				.addCoding(new Coding("http://snomed.info/sct", "129864005", "Deficient knowledge of disease process"))
				.setText("Deficient knowledge of disease process");
		return condition;
	}

	// Populate Service Request Resource
	public static ServiceRequest populateServiceRequest1Resource() {
		ServiceRequest serviceRequest = new ServiceRequest();
		serviceRequest.setId("1");
		serviceRequest.getMeta().addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/ServiceRequest");
		serviceRequest.setStatus(ServiceRequestStatus.ACTIVE);
		serviceRequest.setIntent(ServiceRequestIntent.ORIGINALORDER);
		serviceRequest
				.setCode(new CodeableConcept(new Coding("http://snomed.info/sct", "252150008", "Fasting lipid profile"))
						.setText("Fasting lipid profile"));

		serviceRequest.setSubject(new Reference().setReference("Patient/1"));

		serviceRequest.setOccurrence(new DateTimeType("2021-11-25T15:32:26.605+05:30"));
		serviceRequest.setRequester(new Reference().setReference("Practitioner/1").setDisplay("Dr. DEF"));
		return serviceRequest;
	}

	// Populate Service Request Resource
	public static ServiceRequest populateServiceRequest2Resource() {
		ServiceRequest serviceRequest = new ServiceRequest();
		serviceRequest.setId("2");
		serviceRequest.getMeta().addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/ServiceRequest");
		serviceRequest.setStatus(ServiceRequestStatus.ACTIVE);
		serviceRequest.getText().setStatus(NarrativeStatus.GENERATED).setDivAsString(
				"<div xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en-IN\" >Service Request for Urine microalbumin</div>");
		serviceRequest.setIntent(ServiceRequestIntent.ORIGINALORDER);

		serviceRequest
				.setCode(new CodeableConcept(new Coding("http://snomed.info/sct", "252150008", "Fasting lipid profile"))
						.setText("Fasting lipid profile"));

		serviceRequest.addCategory(
				new CodeableConcept(new Coding("http://snomed.info/sct", "108252007", "Laboratory procedure")));
		serviceRequest
				.setCode(new CodeableConcept(new Coding("http://snomed.info/sct", "46716003", "Urine microalbumin")));

		serviceRequest.setSubject(new Reference().setReference("Patient/1"));

//			serviceRequest.setOccurrence(new DateTimeType("2021-11-25T15:32:26.605+05:30"));
		serviceRequest.setRequester(new Reference().setReference("Practitioner/1").setDisplay("Dr. DEF"));
		return serviceRequest;
	}

	public static ServiceRequest populateServiceRequest3Resource() {
		ServiceRequest serviceRequest = new ServiceRequest();
		serviceRequest.setId("3");
		serviceRequest.getMeta().addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/ServiceRequest");
		serviceRequest.setStatus(ServiceRequestStatus.ACTIVE);
		serviceRequest.getText().setStatus(NarrativeStatus.GENERATED).setDivAsString(
				"<div xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en-IN\" >Service Request for Hemoglobin A1c measurement</div>");
		serviceRequest.setIntent(ServiceRequestIntent.ORIGINALORDER);

		serviceRequest.setCode(
				new CodeableConcept(new Coding("http://snomed.info/sct", "43396009", "Hemoglobin A1c measurement"))
						.setText("Hemoglobin A1c measurement"));

		serviceRequest.addCategory(
				new CodeableConcept(new Coding("http://snomed.info/sct", "108252007", "Laboratory procedure")));

		serviceRequest.setSubject(new Reference().setReference("Patient/1"));

		serviceRequest.setOccurrence(new DateTimeType("2021-11-25T15:32:26.605+05:30"));
		serviceRequest.setRequester(new Reference().setReference("Practitioner/1").setDisplay("Dr. DEF"));
		return serviceRequest;
	}

	public static ServiceRequest populateServiceRequest4Resource() {
		ServiceRequest serviceRequest = new ServiceRequest();
		serviceRequest.setId("4");
		serviceRequest.getMeta().addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/ServiceRequest");
		serviceRequest.setStatus(ServiceRequestStatus.ACTIVE);
		serviceRequest.getText().setStatus(NarrativeStatus.GENERATED).setDivAsString(
				"<div xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en-IN\" >Service Request for Fasting blood glucose measurement</div>");
		serviceRequest.setIntent(ServiceRequestIntent.ORIGINALORDER);

		serviceRequest.setCode(new CodeableConcept(
				new Coding("http://snomed.info/sct", "271062006", "Fasting blood glucose measurement"))
						.setText("Fasting blood glucose measurement"));

		serviceRequest.addCategory(
				new CodeableConcept(new Coding("http://snomed.info/sct", "108252007", "Laboratory procedure")));

		serviceRequest.setSubject(new Reference().setReference("Patient/1"));

		serviceRequest.setOccurrence(new DateTimeType("2021-11-25T15:32:26.605+05:30"));
		serviceRequest.setRequester(new Reference().setReference("Practitioner/1").setDisplay("Dr. DEF"));
		return serviceRequest;
	}

	// Populate Document Reference Resource
	public static DocumentReference populateDocumentReferenceResource() {
		DocumentReference documentReference = new DocumentReference();
		documentReference.setId("1");
		documentReference.getMeta().addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/DocumentReference");
		documentReference.setStatus(DocumentReferenceStatus.CURRENT);
		documentReference.setDocStatus(ReferredDocumentStatus.FINAL);
		documentReference.setSubject(new Reference().setReference("Patient/1"));
		documentReference
				.setType(new CodeableConcept(new Coding("http://snomed.info/sct", "4241000179101", "Laboratory report"))
						.setText("Laboratory report"));
		documentReference.getContent().add(new DocumentReferenceContentComponent(new Attachment()
				.setContentType("application/pdf").setLanguage("en-IN").setTitle("Laboratory report")
				.setCreationElement(new DateTimeType("2019-05-29T14:58:58.181+05:30"))
				.setDataElement(new Base64BinaryType(
						"JVBERi0xLjUKJeLjz9MKOCAwIG9iagRU9GCg=="))));

		return documentReference;
	}

}
