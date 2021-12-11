package com.fhir.connectathon.utility;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.AnonymousAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.services.cognitoidp.model.AuthFlowType;
import com.amazonaws.services.cognitoidp.model.InitiateAuthRequest;
import com.amazonaws.services.cognitoidp.model.InitiateAuthResult;
import com.fhir.connectathon.constants.AWSCognitoConstants;

public class AWSCognitoUtility {
	
	static Logger log = Logger.getLogger(AWSCognitoUtility.class.getName());
	
	/**
	 * This method will configure AWS Cognito client and return the same
	 * This is specific to AWS Cognito Cofiguration
	 * */
	public static AWSCognitoIdentityProvider getAWSCognitoIdentityClient() {
		AnonymousAWSCredentials awsCreds = new AnonymousAWSCredentials();
				
		AWSCognitoIdentityProvider cognitoClient = AWSCognitoIdentityProviderClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .withRegion(Regions.AP_SOUTH_1)
                
                .build();

		return cognitoClient;
	}
	
	/**
	 * This method returns InitiateAuthResult which contains access token, auth token and other session details
	 * This is specific to AWS Cognito Cofiguration
	 * */
	public static InitiateAuthResult signInUserToAWSCognitoPool() {

		try {
			AWSCognitoIdentityProvider cognitoClient = getAWSCognitoIdentityClient();

			String hash = calculateSecretHash(AWSCognitoConstants.AWS_APP_CLIENT_ID,AWSCognitoConstants.AWS_APP_CLIENT_SECREAT,AWSCognitoConstants.AWS_USERNAME);
			final Map <String,String > authParams = new HashMap <String,
			String >();
			authParams.put(AWSCognitoConstants.AWS_USERNAME_KEY , AWSCognitoConstants.AWS_USERNAME);
			authParams.put(AWSCognitoConstants.AWS_SECRET_HASH_KEY,  hash);
			authParams.put(AWSCognitoConstants.AWS_USER_PASSWORD_KEY, AWSCognitoConstants.AWS_USER_PASSWORD);

			final InitiateAuthRequest initiateAuthRequest = new InitiateAuthRequest()
					.withClientId(AWSCognitoConstants.AWS_APP_CLIENT_ID)
					.withAuthFlow(AuthFlowType.USER_PASSWORD_AUTH)
					.withAuthParameters(authParams);

			//result contains session ,accessToken ,auth token etc
			final InitiateAuthResult result = cognitoClient.initiateAuth(initiateAuthRequest);
			return result;
		} catch(Exception e) {
			log.error("Exception occured during sign up user : " + e.getMessage());
		}
		return null;
	}
	
	/**
	 * Calculate Secrete hash for AWS client 
	 * */
	public static String calculateSecretHash(String userPoolClientId, String userPoolClientSecret, String userName) {
	    SecretKeySpec signingKey = new SecretKeySpec(
	            userPoolClientSecret.getBytes(StandardCharsets.UTF_8),
	            AWSCognitoConstants.AWS_SECRET_KEY_ALGO);
	    try {
	        Mac mac = Mac.getInstance(AWSCognitoConstants.AWS_SECRET_KEY_ALGO);
	        mac.init(signingKey);
	        mac.update(userName.getBytes(StandardCharsets.UTF_8));
	        byte[] rawHmac = mac.doFinal(userPoolClientId.getBytes(StandardCharsets.UTF_8));
	        return Base64.getEncoder().encodeToString(rawHmac);
	    } catch (Exception e) {
	        log.error("Error while calculating SecretHash");
	    }
	    return null;
	}
	
	/**
	 * This method will return AWS Cognito Access token which will be passed to FHIR Rest API for authentication 
	 * */
	public static String getAWSAccessToken(){
		String accessToken = null; 
		InitiateAuthResult result =  AWSCognitoUtility.signInUserToAWSCognitoPool();
		accessToken = result.getAuthenticationResult().getAccessToken();
		log.info("Access Token " + accessToken);
		return accessToken;
	}
	
}
