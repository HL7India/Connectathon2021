package com.fhir.connectathon.utility;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

public class FileUtility {
	
	static Logger log = Logger.getLogger(FileUtility.class.getName());
	
	static String MSH = "MSH"; 
	
	public static String ORU_HL7_MSG_PATH = "src\\main\\resources\\ORUMessageOutput.txt";
	
	public static String ADT_HL7_MSG_PATH = "src\\main\\resources\\ADTMessageOutput.txt";
	
	
	
	/**
	 * This method reads the HL& messages from file and returns the Arraylist of String
	 * Each string represents one HL7 message 
	 * */
	public static ArrayList<String> getMessages(String fileName) {
		BufferedReader br = null;

		ArrayList<String> msgArray = new ArrayList<String>();
		try {

			String sCurrentLine;

			br = new BufferedReader(new FileReader(fileName));
			String fileContent = "";
			while ((sCurrentLine = br.readLine()) != null) {
				if (sCurrentLine.contains(MSH)) {
					if (fileContent != "") {
						msgArray.add(fileContent);
					}
					fileContent = null;
					fileContent = sCurrentLine;
					
				} else {
					if(sCurrentLine.isEmpty() && fileContent.isEmpty())
					{
						continue;
					}
					fileContent = fileContent  +"\r" +sCurrentLine;
				}

			}
			msgArray.add(fileContent);
		} catch (IOException e) {
			log.error(e.getMessage());
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				log.error(ex.getMessage());
			}
		}
		return msgArray;
	}

}
