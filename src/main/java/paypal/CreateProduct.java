package paypal;

import java.io.File;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class CreateProduct 
{
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
		RestAssured.authentication =RestAssured.oauth2("A21AAFTW_GEtZcozI9xFcm4gyGd-4X7gvOxwwFKO8_4wbF5ul3htL8YfHxFPWavMHpeaGf2kPqwOiCFdEJx23C6wUqg074HDg");
		
	
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
		System.out.println("\n-----------The Product details available in database are:--------------\n");
		System.out.println(jsonGetResponse.getString("$"));
		
	}
		
}
