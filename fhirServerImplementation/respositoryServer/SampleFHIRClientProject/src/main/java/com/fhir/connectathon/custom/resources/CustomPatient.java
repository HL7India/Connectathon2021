package com.fhir.connectathon.custom.resources;

import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.StringType;

import ca.uhn.fhir.model.api.annotation.Child;
import ca.uhn.fhir.model.api.annotation.Description;
import ca.uhn.fhir.model.api.annotation.Extension;
import ca.uhn.fhir.model.api.annotation.ResourceDef;
import ca.uhn.fhir.util.ElementUtil;


/**
 * Definition class for adding extensions to the built-in
 * Patient resource type.
 * 
 * Note the "profile" attribute below, which indicates the URL/ID of the
 * profile implemented by this resource. You are not required to supply this,
 * but if you do it will be automatically populated in the resource meta
 * tag if the resource is returned by a server.
 */
@ResourceDef(name="Patient", profile="http://example.com/StructureDefinition/custompatient")
public class CustomPatient extends Patient {

	private static final long serialVersionUID = 6932325586287386961L;
	
	 /**
	    * Each extension is defined in a field. Any valid HAPI Data Type
	    * can be used for the field type. Note that the [name=""] attribute
	    * in the @Child annotation needs to match the name for the bean accessor
	    * and mutator methods.
	    */
	   @Child(name="cast")   
	   @Extension(url="http://fhirconnectathon2021.com/cast", definedLocally=false, isModifier=false)
	   @Description(shortDefinition="Cast Name")
	   private StringType cast;

	   
	   /**
	    * It is important to override the isEmpty() method, adding a check for any
	    * newly added fields. 
	    */
	   @Override
	   public boolean isEmpty() {
	      return super.isEmpty() && ElementUtil.isEmpty(cast);
	   }
	   
	   /** Getter for pet name */
	   public StringType getCast() {
	      if (cast == null) {
	         cast = new StringType();
	      }
	      return cast;
	   }
	   
	   /** Setter for pet name */
	   public void setCast(StringType thePetName) {
	      cast = thePetName;
	   }
}
