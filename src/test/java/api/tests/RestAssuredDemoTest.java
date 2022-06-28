package api.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.http.HttpClient;

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

    @BeforeTest
    public void setUp() {

        baseURI = "http://training.skillo-bg.com:3100";

    }


    @Test
    public void testLogin() {

        LoginDto loginDto = new LoginDto();
        loginDto.setUsernameOrEmail("roni");
        loginDto.setPassword("roni95");



        Response response = given()
                .log().all()
                .header("Content-Type", "application/json")
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
    }
}