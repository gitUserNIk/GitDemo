import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import pojo.Api;
import pojo.GetCourses;
import pojo.WebAutomation;

import static io.restassured.RestAssured.*;

import java.util.List;

public class OAuthorization {

	public static void main(String args[])
	{
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		//Get Access Token
		String strResponse = given().log().all().formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
		.formParam("grant_type", "client_credentials")
		.formParam("scope", "trust")
		.when().post("oauthapi/oauth2/resourceOwner/token")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js = new JsonPath(strResponse);
		String accessToken = js.getString("access_token");
		System.out.println("Access Token: "+accessToken);
		
		//Get Details
		GetCourses courseDetails = given().queryParam("access_token", accessToken)
		.when().get("oauthapi/getCourseDetails")
		.then().log().all().extract().response().as(GetCourses.class);
		
		System.out.println(courseDetails.getInstructor());
		
		List<Api> apiCourse = courseDetails.getCourses().getApi();
		for(int i=0; i<apiCourse.size();i++)
		{
			if(apiCourse.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing"))
			{
				System.out.println(apiCourse.get(i).getPrice());
			}
		}
		
		//Print all Web Automation Course Title
		List<WebAutomation> webAuto = courseDetails.getCourses().getWebAutomation();
		for(int i=0; i<webAuto.size();i++)
		{
			System.out.println(webAuto.get(i).getCourseTitle());
		}
		
		
	}
	
}
