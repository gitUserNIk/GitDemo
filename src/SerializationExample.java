import io.restassured.RestAssured;
import pojo.AddPlace;
import pojo.Location;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

public class SerializationExample {

	public static void main(String[] args) {
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		// TODO Auto-generated method stub
		
		AddPlace addPlace = new AddPlace();
		Location location = new Location();
		
		location.setLat(-38.383494);
		location.setLng(33.427362);
		
		addPlace.setLocation(location);
		
		addPlace.setAccuracy(50);
		addPlace.setName("Frontline house");
		addPlace.setPhone_number("(+91) 983 893 3937");
		addPlace.setAddress("29, side layout, cohen 09");
		
		List<String> types = new ArrayList<String>();
		types.add("shoe park");
		types.add("shop");
		
		addPlace.setTypes(types);
		addPlace.setWebsite("http://google.com");
		addPlace.setLanguage("French-IN");		
		
	
		String addPlaceResponse = given().log().all().queryParam("key", "qaclick123").body(addPlace)
		.when().post("/maps/api/place/add/json")
		.then().log().all().statusCode(200).extract().response().asString();
	}

}
