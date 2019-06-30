/* Ecobee Challenge - Question 1 - **Nicholas Goorwah**
 * *******************************
Write a working program code that will take the test_results.json file as an input and provide:
1) For each test suite:
x Test suite name
x Print out the total number of tests that passed and their details
x Print out the total number of tests that failed and their details
x Print out the total number of test that are blocked
x Print out the total number of test that took more than 10 seconds to execute
2) Proper treatment for common error conditions
3) All the detail lists need to be printed in ascending order
4) Speed is first priority, memory is secondary
5) Java or Python */

package com.ecobee.org;
/*Author: Nicholas Goorwah- Ecobee challenge */

import java.util.*;
import java.io.*;
import org.json.simple.*;
import org.json.simple.parser.*;


public class TestResultParser {
	
	//Variables that hold the Test Case(TC)counts for pass, fail, blocked and 
	// TC's that run over 10 seconds
	public static int passCount, failCount, blockCount, overTen = 0;
	//public static List<String> pass1, fail, block, greaterTen = new ArrayList<String>();
	//Lists to hold the details just the test casse name for now.
	public static List<String> pass1 = new ArrayList<String>();
	public static List<String> fail = new ArrayList<String>();
	public static List<String> block = new ArrayList<String>();
	public static List<String> greaterTen = new ArrayList<String>();
	
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		JSONParser jsonParser = new JSONParser();
		JSONObject testResults;
		
		
		try {
			// Use parser to get the file's JSON object
			testResults = (JSONObject)jsonParser.parse(new FileReader("..//Ecobee//src//com//ecobee//org//test_results.json"));
			// For Test Suite: Get a JSONArray
			JSONArray suiteList = (JSONArray) testResults.get("test_suites");
			suiteList.forEach(suite -> parseSuiteObject((JSONObject) suite));
	     } catch (FileNotFoundException e) {
	    	 e.printStackTrace();
	     } catch (IOException e) {
	         e.printStackTrace();
	     } catch (ParseException e) {
	         e.printStackTrace();
	     }
		
	}

	/* parseSuiteObject: JSONObject:  used to parse the suite JSONObject 
	 * from test_results.json it contains the suite_name and the test results
	 * for all tests  */
	private static void parseSuiteObject(JSONObject suite) {
		String suiteName = ((String) suite.get("suite_name")).trim();
		System.out.println("Suite Name:" + suiteName);
		// Get the results JSONArray to be later parsed.
		JSONArray results = (JSONArray) suite.get("results");
		results.forEach(result -> parsePassFailBlockExecute((JSONObject)result));
		
		//Printing data from the TC metrics.
		System.out.println("Pass Count: " + passCount);
		printGenericList(pass1);
		System.out.println("\n");
		System.out.println("Fail Count: " + failCount);
		printGenericList(fail);
		System.out.println("\n");
		System.out.println("Block Count: " + blockCount);
		printGenericList(block);
		System.out.println("\n");
		System.out.println(" TC Over Ten Seconds Count: " + overTen);
		printGenericList(greaterTen);
		System.out.println("\n");
		//Reset the counters for the next suite if it exists.
		passCount = failCount = blockCount = overTen = 0;
		pass1.clear();
		fail.clear(); 
		block.clear();
		greaterTen.clear();
	}

	/*parsePassFailBlockExecute() : JSONObject - Used to parse out the TC data
	 * from the test_results.json as well as sort and count the data metrics in O(n) time.
	 */
	private static void parsePassFailBlockExecute(JSONObject resultSet) {
		//Used trim() here to make sure that there is no leading or 
		//tailing white spaces in the resultSet data metrics.  Also used .toLowerCase
		// to ensure that we don't get any values in fields that have Upper-case values.
		String testName = ((String) resultSet.get("test_name")).trim().toLowerCase();
		String status = ((String) resultSet.get("status")).trim().toLowerCase();
		String time1 = ((String) resultSet.get("time")).trim();
	 	//O(n) time to use a switch statement to find the counts of pass, fail,
		//blocked status.
		switch(status) {
			case "pass":
				pass1.add(testName);
				passCount += 1;
				break;
			case "fail":
				failCount += 1;
				fail.add(testName);
				break;
			case "blocked":
				blockCount += 1;
				block.add(testName);
				break;
			}
		 	
	 	//Time to check if a time exists and is not null or empty string ""
	 	// Then parse the double value from the string.  Ensuring we do
		// not parse invalid value. (Should be formatted: ex. 8.23) 
		if(!time1.equals("") && !time1.equals(null)) {
	 		if(Double.parseDouble(time1) > 10) {
	 			overTen += 1;
	 			greaterTen.add(testName);
	 		}
	 	}
	}
	/*printGenericList: List<String>
	Takes in a list object and then sorts that list before printing
	*/
	public static void printGenericList(List<String> l) {
		//Using Collections interface to sort in ascending order before printing
		Collections.sort(l);
		System.out.print(l);
	}
	
}

