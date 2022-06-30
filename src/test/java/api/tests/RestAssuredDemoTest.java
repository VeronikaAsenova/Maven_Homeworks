package api.tests;


import com.google.gson.annotations.JsonAdapter;
import com.mentormate.common.Constants;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import javax.swing.*;
import java.net.http.HttpClient;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import static io.restassured.RestAssured.*;


class LoginDto {
    private String usernameOrEmail;
    private String password;

    public String getUsernameOrEmail() {
        return usernameOrEmail;
    }

    public void setUsernameOrEmail(String usernameOrEmail) {
        this.usernameOrEmail = usernameOrEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

public class RestAssuredDemoTest {

    String accessToken;
    String username;
    SecureRandom random;
    int userId;

    @BeforeTest
    public void setUp() {
        baseURI = "http://training.skillo-bg.com:3100";
        random = new SecureRandom();
    }


    // SIGN UP
    @Parameters({"password"})
    @Test(groups = "signup")
    public void testSignUp(@Optional("roni95") String password) {
        username = "auto" + String.valueOf(random.nextInt(100000));

        JSONObject body = new JSONObject();

        body.put("username", username);
        body.put("birthday", "05.06.1995");
        body.put("email", username + "@test.com");
        body.put("password", password);
        body.put("publicInfo", "sth here");

        Response response = given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/users")
                .then().log().all()
                .extract()
                .response();

        Assert.assertEquals(response.statusCode(), HttpStatus.SC_CREATED);

        userId = response.jsonPath().get("id");
        Assert.assertTrue(userId != 0);

    }


    // LOGIN
    @Parameters({"password"})
    @Test(groups = "signup", dependsOnMethods = "testSignUp")
    public void testLogin(@Optional("roni95") String password) {

        LoginDto loginDto = new LoginDto();
        loginDto.setUsernameOrEmail(username);
        loginDto.setPassword(password);


        //using JSON simple object instead of POJO
//        JSONObject object = new JSONObject();
//        object.put("usernameOrEmail",username);
//        object.put("password", password);
//
//        JSONArray array = new JSONArray();
//        JSONObject obj1 = new JSONObject();
//        obj1.put("x", 17);
//        obj1.put("y", 34);
//        array.add(obj1);
//
//        JSONObject obj2 = new JSONObject();
//        obj2.put("x", 17);
//        obj2.put("y", 34);
//        array.add(obj2);
//
//        object.put("demo",array);


        Response response = given()
                .log().all()
                .header("Content-Type", "application/json")
//                .body(object)
                .body(loginDto)
                .when()
                .post("/users/login")
                .then()
                .log()
                .all()
                .extract()
                .response();
        int statusCode = response.statusCode();
        Assert.assertEquals(statusCode, HttpStatus.SC_CREATED);

        String usernameFromResponse = response.jsonPath().get("user.username");
        Assert.assertEquals(usernameFromResponse, username);

        int userIdFromResponse = response.jsonPath().get("user.id");
        Assert.assertEquals(userIdFromResponse, userId);

        String token = response.jsonPath().get("token");
        Assert.assertFalse(token.isEmpty());
        accessToken = "Bearer " + token;


    }


    //LIST ALL USERS (GET REQUEST)
    @Test(dependsOnGroups = "signup")
    public void testListAllUsers() {

        Response response = given()
                .log()
                .all()
                .header("Authorization", accessToken)
                .when()
                .get("/users")
                .then()
                .extract()
                .response();
        int statusCode = response.statusCode();
        Assert.assertEquals(statusCode, HttpStatus.SC_OK);

        ArrayList<Object> ListOfUsers = response.jsonPath().get();
        Assert.assertFalse(ListOfUsers.isEmpty());

        //how to extract all user's email:
//        ListOfUsers.forEach(user ->
//        {
//
//            LinkedHashMap userObject = (LinkedHashMap) user;
//            System.out.println(userObject.get("email"));
//        });

    }
}