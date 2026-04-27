import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import files.PayLoad;
import files.ReusableMethods;

public class Basics {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		//Validate Add place API
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json").
		body(new String(Files.readAllBytes(Paths.get("I:\\API Testing\\DemoProject\\AddPlaceJson.txt")))).when().post("/maps/api/place/add/json").
		then().log().all().assertThat().statusCode(200).body("status", equalTo("OK")).
		header("Content-Type", "application/json;charset=UTF-8").extract().response().asString();
		
		JsonPath jsonAddPlaceResponse = ReusableMethods.stringToJson(response);
		String strPlaceId = jsonAddPlaceResponse.getString("place_id");
		System.out.println("Place ID = "+strPlaceId);
		
		//Validate Get place API
		String strNewAddress = "222 Summer Walk, USA";
		given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json").
		body("{\r\n"
				+ "\"place_id\":\""+strPlaceId+"\",\r\n"
				+ "\"address\":\""+strNewAddress+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}\r\n"
				+ "").
		when().put("maps/api/place/update/json").
		then().log().all().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));
		
		//Validate Get Place API
		String strGetPlaceResponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", strPlaceId).
		when().get("maps/api/place/get/json").andReturn().
		then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath jsonGetPlaceResponse = ReusableMethods.stringToJson(strGetPlaceResponse);
		String strActualAddress = jsonGetPlaceResponse.getString("address");
		
		//Validate if the address is updated
		if(strNewAddress.equals(strActualAddress))
		{
			System.out.println("The Address is updated "+strActualAddress);
		}
		else
		{
			System.out.println("Address is not updated "+strActualAddress);
		}
		}
	
		
	
		

}
