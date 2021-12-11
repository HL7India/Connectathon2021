package com.fhir.connectathon.config;

import ca.uhn.fhir.context.FhirContext;

/**
 * This method creates FhirContext for R4 version and returns.
 * */
public class FhirContextBean {
	 
	private static FhirContextBean fhirContextBean = null;
	
	private FhirContext ctxR4 = null;
	
	public FhirContext getCtxR4() {
		return ctxR4;
	}

	private FhirContextBean(){
		ctxR4 = FhirContext.forR4();
	}
	
	public static FhirContext getInstance(){
		if(fhirContextBean == null){
			fhirContextBean = new FhirContextBean();
		}
		 
		return fhirContextBean.getCtxR4();
	}
}
