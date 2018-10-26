package getRequest;

import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;

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
}
