package files;

import io.restassured.path.json.JsonPath;

public class ReusableMethods {
	
	public static JsonPath stringToJson(String strResponse)
	{
		JsonPath js = new JsonPath(strResponse);
		
		return js;
		
	}

}
