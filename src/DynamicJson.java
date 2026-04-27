import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.PayLoad;
import files.ReusableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class DynamicJson {

	//Add Book
	@Test(dataProvider = "BookData")
	public void addBook(String isbn, String aisle)
	{ 
		
		RestAssured.baseURI = "http://216.10.245.166";
		String addBookResponse = given().log().all().header("Content-Type","application/json").body(PayLoad.addBook(isbn,aisle)).
		when().post("Library/Addbook.php").
		then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath jsonAddBookResponse = ReusableMethods.stringToJson(addBookResponse);
		
		String strBookID = jsonAddBookResponse.get("ID");
		System.out.println("ID: "+strBookID);
		
	}
	
	//Delete Book
	@Test
	public void deleteBook()
	{
		RestAssured.baseURI = "http://216.10.245.166";
		given().log().all().header("Content-Type","application/json").
		body("{ \r\n"
				+ "\"ID\" : \"oigb084\"\r\n"
				+ "} \r\n"
				+ "").
		when().post("Library/DeleteBook.php").
		then().log().all().assertThat().statusCode(200).extract().response().asString();
	}
	
	@DataProvider(name="BookData")
	public Object[][] getData()
	{
		return new Object[][] {{"lkfw","765"}, {"kwqm","953"}, {"oiwx","097"}};
	}
	
}
