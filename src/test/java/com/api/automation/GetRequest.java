package com.api.automation;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import groovy.json.JsonException;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

public class GetRequest {

	@Test
	@Parameters({"BaseURI", "EmpId"})
	public void getRequest(String baseURI, String empId) {
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.get(String.format("%s/%s/%s", baseURI, "employee", empId));
		Assert.assertEquals(200, response.statusCode(), "Correct status code returned");

		// Retrieve the body of the Response
		ResponseBody body = response.getBody();

		// By using the ResponseBody.asString() method, we can convert the body
		// into the string representation.
		String bodyAsString = body.asString();
		JSONParser parser = new JSONParser();  
		try {
			JSONObject mainJson = (JSONObject) parser.parse(bodyAsString);
			JSONObject empDetails = (JSONObject) parser.parse(mainJson.get("data").toString());
			Assert.assertNotNull(empDetails.get("employee_name"), "Employee Name Should not be Null");
			Assert.assertNotNull(empDetails.get("id"), "Employee ID Should not be Null");
		} catch (ParseException e) {
			throw new JsonException("Unable to Parse JSON");
		}
	}

}
