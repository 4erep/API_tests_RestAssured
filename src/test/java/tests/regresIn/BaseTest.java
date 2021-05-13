package tests.regresIn;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.given;

public class BaseTest {
    @BeforeAll
    static void setup() {
        RestAssured.requestSpecification = given()
                .baseUri("https://reqres.in/api")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON);
    }
}
