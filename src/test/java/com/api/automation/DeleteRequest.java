package com.api.automation;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class DeleteRequest {

	@Test
	@Parameters({"BaseURI", "EmpId"})
	public void deleteRequest(String baseURI, String empId) {
		
		RequestSpecification request = RestAssured.given();

		// Add a header stating the Request body is a JSON
		request.header("Content-Type", "application/json");

		// Delete the request and check the response
		Response response = request.delete(String.format("%s/%s/%s", baseURI, "delete", empId));

		int statusCode = response.getStatusCode();
		System.out.println(statusCode);
		System.out.println(response.asString());
		Assert.assertEquals(statusCode, 200);

		String jsonString = response.asString();

		Assert.assertEquals(jsonString.contains("successfully! deleted Records"), true);

	}

	

}
