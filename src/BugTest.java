import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;

public class BugTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		RestAssured.baseURI = "https://niharikagupta1904.atlassian.net/";
		
		//Create Issue
		String strCreateIssueResponse = given().log().all().header("Content-Type","application/json").header("Authorization","Basic bmloYXJpa2FndXB0YTE5MDRAZ21haWwuY29tOkFUQVRUM3hGZkdGMHkwSkx1bTl0RUs0XzZoY2pwWmotbWlLX21nT3NpZm9tMkJhdFpKSzF6Y3lvMkxVV0ZkM01GYnFoZEJsb2lRa2phZk13a0toNWdXbDl4UllfNXM3TjNJdE9CcXlXODRmeGwwa0hFQnF0ek9FQ1pkV0ZFV1NrT092ZHhXSDBzUXlYT0cwVGY2VzdCT1RmY1I1WFpHUjFncGFPb01OVUVLWmRlT0k0Tld0dXBUWT1GNUUyOTM1OA==")
		.body("{\r\n"
				+ "    \"fields\": {\r\n"
				+ "       \"project\":\r\n"
				+ "       {\r\n"
				+ "          \"key\": \"SCRUM\"\r\n"
				+ "       },\r\n"
				+ "       \"summary\": \"Link is not working - automation\",\r\n"
				+ "       \"issuetype\": {\r\n"
				+ "          \"name\": \"Bug\"\r\n"
				+ "       }\r\n"
				+ "   }\r\n"
				+ "}")
			.when().post("rest/api/3/issue")
			.then().log().all().assertThat().statusCode(201).extract().response().asString();
		
		JsonPath js = new JsonPath(strCreateIssueResponse);
		String strIssueId = js.getString("id");
		System.out.println("Issue Id: "+strIssueId);
		
		//Add Attachment
		given().log().all().header("X-Atlassian-Token","no-check")
		.header("Authorization","Basic bmloYXJpa2FndXB0YTE5MDRAZ21haWwuY29tOkFUQVRUM3hGZkdGMHkwSkx1bTl0RUs0XzZoY2pwWmotbWlLX21nT3NpZm9tMkJhdFpKSzF6Y3lvMkxVV0ZkM01GYnFoZEJsb2lRa2phZk13a0toNWdXbDl4UllfNXM3TjNJdE9CcXlXODRmeGwwa0hFQnF0ek9FQ1pkV0ZFV1NrT092ZHhXSDBzUXlYT0cwVGY2VzdCT1RmY1I1WFpHUjFncGFPb01OVUVLWmRlT0k0Tld0dXBUWT1GNUUyOTM1OA==")
		.pathParam("IssueId", strIssueId)
		.multiPart("file", new File("I:\\Screenshot.PNG"))
		.when().post("rest/api/2/issue/{IssueId}/attachments")
		.then().log().all().assertThat().statusCode(200);
	}

}
