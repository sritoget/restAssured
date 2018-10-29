package getRequest;

import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static org.hamcrest.Matchers.*;

import org.json.simple.JSONObject;

public class GetData {

	@Test
	public void testResponseCode()
	{
		Response resp=get("https://spring-movies-cf.cfapps.io/");
		int code=resp.getStatusCode();
		System.out.println("Response status code is: "+code);
		Assert.assertEquals(code,200);	
	}
	
	@Test
	public void testDoesWonderWomenMovieImageExist()
	{
		Response resp=get("https://spring-movies-cf.cfapps.io/");
		String data=resp.asString();
		System.out.println("Whole Data is : "+data);
		Assert.assertTrue(data.contains("wonderWoman.jpg"));
	}
	
	//Below will only work with JSON-Server in place
	// https://github.com/typicode/json-server
	
	
	@Test
	public void testJsonServerNewBookRequest(String bookId)
	{
		RequestSpecification request=RestAssured.given();
		request.header("Content-Type","application/json");
		
		JSONObject json=new JSONObject();
		json.put("id", bookId);
		json.put("title", "Pirates");
		json.put("author", "Srikrishna");
		
		request.body(json.toJSONString());
		
		Response resp=request.post("http://localhost:3000/posts");
		
		int code=resp.getStatusCode();
		Assert.assertEquals(code, 201);
	}
		
	@Test
	public void testJsonServerDeleteBookRequest()
	{
		RequestSpecification request=RestAssured.given();
		Response resp=request.delete("http://localhost:3000/posts/13");
		
		int code=resp.getStatusCode();
		Assert.assertEquals(code, 200);
		
	}
	
	@Test
	public void testJsonServerUpdateBookRequest()
	{
		String bId="13";
		testJsonServerNewBookRequest(bId);
		RequestSpecification request=RestAssured.given();
		request.header("Content-Type","application/json");
		
		JSONObject json=new JSONObject();
		json.put("id", bId);
		json.put("title", "Men in Black");
		json.put("author", "Srikrishna");
		
		request.body(json.toJSONString());
		
		Response resp=request.put("http://localhost:3000/posts/"+bId);
		
		int code=resp.getStatusCode();
		Assert.assertEquals(code, 200);
		
	}
	
	@Test
	public void testUsingGivenWhenThen()
	{
		given().
		get("http://localhost:3000/posts/13").
		then().
		statusCode(200).log().all();
	}
	
	
	@Test
	public void testValidatingUsingEqualToExpression()
	{
		given().
		get("http://localhost:3000/posts/13").
		then().body("author", equalTo("Srikrishna")).log().all();
	}
	
	@Test
	public void testValidatingUsingHasItemsExpression()
	{
		given().
		get("http://localhost:3000/posts").
		then().body("author", hasItems("Srikrishna")).log().all();
	}
	
	
	@Test
	public void testGetResponseAsString()
	{
		String response=get("http://localhost:3000/posts").asString();
		System.out.println("Response :"+response);	
	}
	
}
