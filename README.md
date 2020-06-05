# APIAssignmentsDay1
API Assignments Day 1

Authorization -> No Auth

1. Find the top 5 Country with Highest New Cases 
2. Find the top 5 Country with lowest New Deaths Cases
3. Find the Statu of your Country
4. Verify the response HTTP status code = 200
5. Verify the Response Time < 600 ms
6. verify the Content Type = json

Implement the above Scenario On both PostMan and Rest Assured


#2) Paypal

Manual Steps:

1) Manually Create your own Credentials: https://developer.paypal.com/home/
2) Read the API documents from here:
	https://developer.paypal.com/docs/api/overview/
3) Create Token Manually

Automation Steps:

https://developer.paypal.com/docs/api/catalog-products/v1/#products_create
#1) Create a new product with hard coded value in the body [PostMan]
	a) Verify the status code
	b) Verify the response contains category and type as expected

#2) Create multiple products [using dataprovider + RestAssured]

#3) Verify that the created products are listed
