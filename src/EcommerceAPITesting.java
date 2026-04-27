import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import pojo.LoginRequest;
import pojo.LoginResponse;
import pojo.OrderDetails;
import pojo.Orders;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;

public class EcommerceAPITesting {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").setContentType(ContentType.JSON).build();
	
	LoginRequest loginRequest = new LoginRequest();
	loginRequest.setUserEmail("lovely.jain@gmail.com");
	loginRequest.setUserPassword("Lovely@123");
	
	RequestSpecification reqSpec = given().log().all().spec(req).body(loginRequest);
	
	LoginResponse loginResponse = new LoginResponse();
	loginResponse = reqSpec.when().post("api/ecom/auth/login").then().log().all().extract().response().as(LoginResponse.class);
	
	String token = loginResponse.getToken();
	String userID = loginResponse.getUserId();
	System.out.println(token);
	System.out.println(userID);
		
	//Add Product
	RequestSpecification baseRequest = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("Authorization", token).build();
	
	RequestSpecification addProductRequest = given().log().all().spec(baseRequest).param("productName", "qwerty").param("productAddedBy", userID)
			.param("productCategory", "fashion").param("productSubCategory", "shirts").param("productPrice", "11500").param("productDescription", "Addias Originals")
			.param("productFor", "women").multiPart("productImage", new File("C:\\Users\\Chirag\\OneDrive\\Desktop\\Strawberry.jpg"));
	
	String addProductResponse = addProductRequest.when().post("api/ecom/product/add-product").then().log().all().extract().response().asString();
	
	JsonPath jsAddProductResponse = new JsonPath(addProductResponse);
	String productId = jsAddProductResponse.get("productId");
	
	//Create Order
	RequestSpecification createBaseRequest = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("Authorization", token).setContentType(ContentType.JSON).build();
	
	OrderDetails orderDetails = new OrderDetails();
	orderDetails.setCountry("India");
	orderDetails.setProductOrderedId(productId);
	
	List<OrderDetails> orderDetailsList = new ArrayList<OrderDetails>();
	orderDetailsList.add(orderDetails);
	
	Orders orders = new Orders();
	orders.setOrders(orderDetailsList);
	
	String createOrderResponse = given().log().all().spec(createBaseRequest).body(orders).when().post("api/ecom/order/create-order").then().log().all().extract().response().asString();
	
	//Delete Product
	String deleteResponse = given().log().all().spec(createBaseRequest).pathParam("productId", productId).when().delete("api/ecom/product/delete-product/{productId}")
	.then().log().all().extract().response().asString();
	
	JsonPath jsDeleteResponse = new JsonPath(deleteResponse);
	Assert.assertEquals("Product Deleted Successfully", jsDeleteResponse.get("message"));
	
	
	
	
	}
}
