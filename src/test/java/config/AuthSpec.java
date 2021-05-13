package config;

import io.restassured.http.ContentType;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class AuthSpec {
    public Map<String, String> login(String user, String password) {
        return
                given()
                        .contentType(ContentType.URLENC)
                        .formParam("Email", user)
                        .formParam("Password", password)
                        .when()
                        .post("/login")
                        .then()
                        .log().body()
                        .statusCode(302)
                        .extract().cookies();
    }

    public Map<String, String> loginWithoutCookies() {
        return
                given()
                        .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                        .when()
                        .get("/wishlist")
                        .then()
                        .statusCode(200)
                        .log().body()
                        .extract().cookies();
    }

}
