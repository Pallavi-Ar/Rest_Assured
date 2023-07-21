package assignment;

import assignment.Credentials;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

//assignment.Credentials class contains the authorization token
public class RestassuredAssignment extends Credentials {
    //GET
    @Test
    //function for GET
    void getUsers() {
        //getting the data
        Response response = (Response) given().get("https://reqres.in/api/users?page=2").getBody();
        //printing the response
        System.out.println(response.prettyPrint());
    }

    //POST
    @Test
    //function for POST
    void createUser() {
        //giving baseURI and basePath
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api/users?page=2";

        //data for creating the user
        String req = "{\"name\" : \"Pallavi\", \"job\" : \"trainee\"}";

        try {
            //posting data and printing response
            Response resp = given().header("Content-Type","application/json")
                    .contentType("application/json").relaxedHTTPSValidation()
                    .body(req).log().all()
                    .when().post();

            System.out.println(resp.prettyPrint());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    //PATCH
    @Test
    //creating function for updating
    void updateUser() {
        //baseuri
        RestAssured.baseURI = "https://reqres.in";

        //data to update
        String request = "{\"name\" : \"John\"}";
        Response response1 = null;

        try{
            //updating and printing response
            response1 = given().contentType(ContentType.JSON)
                    .body(request)
                    .patch("/api/users/2");
            System.out.println(response1.prettyPrint());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    //DELETE
    @Test
    //function to delete user
    void deleteUser() {
        //baseuri
        RestAssured.baseURI = "https://reqres.in";
        Response response2 = null;

        try {
            //if status is 204 it means that deleting was successful
            response2 = given().contentType(ContentType.JSON).delete("/api/users/2");
            if(response2.getStatusCode() == 204) {
                System.out.println("204");
            }
            else {
                System.out.println("not deleted");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    //AUTHENTICATION
    //performing authentication on gorest as reqres does not support authentication
    @Test
    //function for authenticating
    void authenticate() {
        //baseURI and basePath
        RestAssured.baseURI="https://gorest.co.in";
        RestAssured.basePath="/public/v2/users";
//             String payload = "{\"name\" : \"Pallavi\", \"email\" : \"p87@gmail.com\", \"gender\" : \"female\", \"status\" : \"active\"}";
        //creating json object of data of the user to be created
        JSONObject json=new JSONObject();
        json.put("name", "Pallavi");
        json.put("email", "p0@gmail.com");
        json.put("gender", "female");
        json.put("status", "active");

        //passing authentication token, creating the user and printing the response
        Response response = given().header("Authorization",authToken)
                .header("Content-Type","application/json")
                .body(json).when().post()
                .then().extract().response();
        System.out.println(response.prettyPrint());
    }
}