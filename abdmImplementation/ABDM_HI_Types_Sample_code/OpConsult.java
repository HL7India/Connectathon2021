package in.nrces.ndhm.fhir.r4.clinicalartifacts;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.hl7.fhir.common.hapi.validation.support.CommonCodeSystemsTerminologyService;
import org.hl7.fhir.common.hapi.validation.support.InMemoryTerminologyServerValidationSupport;
import org.hl7.fhir.common.hapi.validation.support.PrePopulatedValidationSupport;
import org.hl7.fhir.common.hapi.validation.support.SnapshotGeneratingValidationSupport;
import org.hl7.fhir.common.hapi.validation.support.ValidationSupportChain;
import org.hl7.fhir.common.hapi.validation.validator.FhirInstanceValidator;
import org.hl7.fhir.r4.model.StructureDefinition;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.context.support.DefaultProfileValidationSupport;
import ca.uhn.fhir.parser.DataFormatException;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.validation.FhirValidator;

public class OpConsult {

	static FhirContext ctx = FhirContext.forR4();
	static FhirInstanceValidator instanceValidator;
	static FhirValidator validator;
	
	public static void main(String[] args) throws DataFormatException, FileNotFoundException {
		 init();
	}
	
	static void init() throws DataFormatException, FileNotFoundException {
		IParser parser= ctx.newXmlParser();
		
		ValidationSupportChain supportChain= new ValidationSupportChain();
		
		DefaultProfileValidationSupport defaultSupport = new DefaultProfileValidationSupport(ctx);
		
		PrePopulatedValidationSupport prepopvalidationSupport= new PrePopulatedValidationSupport(ctx);
		
		StructureDefinition sd;
		
		String[] fileList =new File("E:\\NDHM\\NDHM_FHIR_Profiles\\").list(new WildcardFileFilter("*.xml"));
		
		for(String file: fileList) {
			sd= parser.parseResource(StructureDefinition.class, new FileReader("E:\\NDHM\\NDHM_FHIR_Profiles\\"+file));
			prepopvalidationSupport.addStructureDefinition(sd);
		}
		
		SnapshotGeneratingValidationSupport sanpgenerator= new SnapshotGeneratingValidationSupport(ctx);
		
		supportChain.addValidationSupport(defaultSupport);
		supportChain.addValidationSupport(prepopvalidationSupport);
		supportChain.addValidationSupport(sanpgenerator);
		supportChain.addValidationSupport(new InMemoryTerminologyServerValidationSupport(ctx));
		supportChain.addValidationSupport(new CommonCodeSystemsTerminologyService(ctx));
		
		instanceValidator = new FhirInstanceValidator(supportChain);
		validator= ctx.newValidator().registerValidatorModule(instanceValidator);
		
	}
}
