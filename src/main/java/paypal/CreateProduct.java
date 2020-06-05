package paypal;

import java.io.File;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class CreateProduct 
{
	JsonPath jsonPostResponseAccessToken;
	String accessToken;
	@BeforeSuite
	public void creatAccessToken()
	{
		RestAssured.baseURI="https://api.sandbox.paypal.com";
		Response postResponseAccessToken = RestAssured
				.given()
				.header("Content-Type", "application/x-www-form-urlencoded")
				.header("Authorization","Basic QWMwQVo3QXpvUm9UQ09tckNxcFZtMDA4LVVuYWI2WmVlWllYZ2VQTmt6aUVHeGE4ZEpxTklzVzlBUlZqZUtLbHdrRmNRZ2hhb01rSEpNemQ6RU5PcWIyQU1HY2JISGRSaFhMbnh6N0xudFdpUHhpeUVROUR2YUlmQjdraXR3cFF0WHZRa1JqMk44TmFRZV9PNGk4NU5Od2cxYTRkd1Zaa3c=")
				.formParam("grant_type", "client_credentials")
				//.log().all()
				.post("v1/oauth2/token");
		JsonPath jsonPostResponseAccessToken = postResponseAccessToken.jsonPath();
		accessToken = jsonPostResponseAccessToken.getString("access_token");
	}
	
	@DataProvider(name="Files")
	public String[] getFiles()
	{
		String[] files = new String[3];
		files[0]="testData/createProductData1.json";
		files[1]="testData/createProductData2.json";
		files[2]="testData/createProductData3.json";
		return files;
	}
	@Test(dataProvider="Files")
	public void createProduct(String fileName)
	{
		File jsonFile = new File(fileName);
		
		RestAssured.baseURI="https://api.sandbox.paypal.com";
		RestAssured.authentication =RestAssured.oauth2(accessToken);
		
	
		Response postResponse = RestAssured
							.given()
							.contentType(ContentType.JSON)
							.body(jsonFile)
							//.log().all()
							.post("v1/catalogs/products");
		
		JsonPath jsonPostResponse = postResponse.jsonPath();
		String id = jsonPostResponse.getString("id");
		System.out.println("\nNewly Created Product is with Id-----> "+id);
		
		Response getResponse = RestAssured
				.given()
				//.log().all()
				.get("v1/catalogs/products/"+id);
		JsonPath jsonGetResponse = getResponse.jsonPath();
		System.out.println("\n-----------The Product details available in database is:--------------\n");
		System.out.println(jsonGetResponse.getString("$"));
		
	}
		
}
