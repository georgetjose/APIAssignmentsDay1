package covid19;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.Set;

public class covid19DataFlow 
{
		Response response;
		Map<String,Integer> map;
		Map<String,Integer> map2;
		
		@BeforeSuite
    	void myCovid19DataFlow()
    	{
    		RestAssured.baseURI="https://covid-19.dataflowkit.com";
    		response = RestAssured
    							.given()
    							.log().all()
    							.get("v1"); 		
    	}
		
		@Test(enabled=true, priority =1)
    	void myCovid19HigestNewCases()
    	{
			JsonPath jsonResponse = response.jsonPath();
    		List<String> allItems = jsonResponse.getList("$");
    		map = new HashMap<String,Integer>(); 
			for(int i=0;i<allItems.size()-1;i++)
    		{
    			String newCaseText = jsonResponse.getString("["+i+"].\"New Cases_text\"");
    			String countryText = jsonResponse.getString("["+i+"].\"Country_text\"");
    			String newCase = newCaseText.replaceAll("\\D", "");
    			if(newCase=="")
    				newCase = newCaseText.replaceAll("", "0");
    			map.put(countryText, Integer.parseInt(newCase));
    		}
			Set < Entry < String, Integer >> entries = map.entrySet();

			Comparator < Entry < String, Integer >> valueComparator = new Comparator < Entry < String, Integer >> () 
			{
				@Override public int compare(Entry < String, Integer > e1, Entry < String, Integer > e2) 
				{
					Integer v1 = e1.getValue();
					Integer v2 = e2.getValue();
					return v2.compareTo(v1);
            	}
			};

			List < Entry < String, Integer >> listOfEntries = new ArrayList < Entry < String, Integer >> (entries);

			Collections.sort(listOfEntries, valueComparator);

			LinkedHashMap < String, Integer > sortedByValue = new LinkedHashMap < String, Integer > (listOfEntries.size());

			for (Entry < String, Integer > entry: listOfEntries) 
			{
				sortedByValue.put(entry.getKey(), entry.getValue());
			}
    
			Set < Entry < String, Integer >> entrySetSortedByValue = sortedByValue.entrySet(); 
			Iterator< Entry < String, Integer >> i=entrySetSortedByValue.iterator();
			System.out.println("\n1) The top 5 Country with Highest New Cases\n");
			i.next();
			for(int j=0;j<5;j++)
			{	
				System.out.println(i.next());
			}
			System.out.println("\n");
    	}
        
		@Test(enabled=true, priority =2)
    	void myCovid19LowestDeaths()
    	{
			JsonPath jsonResponse = response.jsonPath();
			List<String> allItems = jsonResponse.getList("$");
			map2 = new HashMap<String,Integer>(); 
			
			for(int k=0;k<allItems.size()-1;k++)
			{
				String newDeathCaseText = jsonResponse.getString("["+k+"].\"New Deaths_text\"");
				String countryText = jsonResponse.getString("["+k+"].\"Country_text\"");
				String newDeathCase = newDeathCaseText.replaceAll("\\D", "");
				if(newDeathCase=="")
					newDeathCase = newDeathCaseText.replaceAll("", "0");
				map2.put(countryText, Integer.parseInt(newDeathCase));
			}
			Set < Entry < String, Integer >> entries2 = map2.entrySet();

			Comparator < Entry < String, Integer >> valueComparator2 = new Comparator < Entry < String, Integer >> () 
			{
				@Override public int compare(Entry < String, Integer > e3, Entry < String, Integer > e4) {
				Integer v3 = e3.getValue();
				Integer v4 = e4.getValue();
				return v3.compareTo(v4);
			}
		};

		List < Entry < String, Integer >> listOfEntries2 = new ArrayList < Entry < String, Integer >> (entries2);

		Collections.sort(listOfEntries2, valueComparator2);

		LinkedHashMap < String, Integer > sortedByValue2 = new LinkedHashMap < String, Integer > (listOfEntries2.size());

		for (Entry < String, Integer > entry2: listOfEntries2) 
		{
			sortedByValue2.put(entry2.getKey(), entry2.getValue());
		}

		Set < Entry < String, Integer >> entrySetSortedByValue2 = sortedByValue2.entrySet(); 
		Iterator< Entry < String, Integer >> i2=entrySetSortedByValue2.iterator();
		System.out.println("\n2) The top 5 Country with Lowest New Death Cases\n");
		for(int j=0;j<5;j++)
		{	
			System.out.println(i2.next());
		}
		System.out.println("\n");
    }
		
	@Test(enabled=true, priority =3)
	void myCovid19MyCountryStatus()
	{
		JsonPath jsonResponse = response.jsonPath();
		List<String> allItems = jsonResponse.getList("$"); 
		
		for(int k=0;k<allItems.size()-1;k++)
		{
			if(jsonResponse.getString("["+k+"].\"Country_text\"").equals("India"))
			{
				System.out.println("\n3) Status of My Country\n");
				System.out.println("Country Name: "+jsonResponse.getString("["+k+"].\"Country_text\""));
				System.out.println("Active Cases: "+jsonResponse.getString("["+k+"].\"Active Cases_text\""));
				System.out.println("New Cases: "+jsonResponse.getString("["+k+"].\"New Cases_text\""));
				System.out.println("New Deaths: "+jsonResponse.getString("["+k+"].\"New Deaths_text\""));
				System.out.println("Total Cases: "+jsonResponse.getString("["+k+"].\"Total Cases_text\""));
				System.out.println("Total Deaths: "+jsonResponse.getString("["+k+"].\"Total Deaths_text\""));
				System.out.println("Total Recovered: "+jsonResponse.getString("["+k+"].\"Total Recovered_text\""));
				
				jsonResponse.getString("["+k+"].\"Country_text\"");	
				jsonResponse.getString("["+k+"].\"New Deaths_text\"");
				
			}
		}	
	}	
	
	@Test(enabled=true, priority =4)
    void myCovid19Response()
    {
		System.out.println("\n4) Verification of Status Code\n");
		System.out.println("Status code ="+response.getStatusCode());
		if(response.getStatusCode()==200)
			System.out.println("The request is successfull!!");
		else
			System.out.println("The request is a failure!!");
    }
	
	@Test(enabled=true, priority =5)
    void myCovid19ResponseTime()
    {
		System.out.println("\n5) Verification of Response Time\n");
		System.out.println("Response Time ="+response.getTime());
		if(response.getTime()<600)
			System.out.println("The Response is under 600ms. Good Performance!!");
		else
			System.out.println("The Response is over 600ms. Bad Performance!!");
    }
	
	@Test(enabled=true, priority =6)
    void myCovid19ContentTypeContentType()
    {
		System.out.println("\n6) Verification of Content Type\n");
		System.out.println("Content Type ="+response.getContentType());
		if(response.getContentType().equals("application/json"))
			System.out.println("The response content Type is JSON!!");
		else
			System.out.println("The response content Type is not JSON!!");
		System.out.println("\n");
    }
	
}