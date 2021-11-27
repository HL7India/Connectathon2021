package com.hl7.india.hapifhirskeleton;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.hl7.india.hapifhirskeleton.fhir.SimpleRestfulServer;

@SpringBootApplication
public class HapiFhirSkeletonApplication {

	@Autowired
	private ApplicationContext applicationContext;
	
	public static void main(String[] args) {
		SpringApplication.run(HapiFhirSkeletonApplication.class, args);
	}
	
	@Bean
	public ServletRegistrationBean servletRegistrationBean () {
		ServletRegistrationBean registrationBean = new ServletRegistrationBean(new SimpleRestfulServer(applicationContext),
			"/fhir/*");
		return  registrationBean;
	}

}
