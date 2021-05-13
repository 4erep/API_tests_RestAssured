package tests.regresIn;

import config.Endpoints;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static java.net.HttpURLConnection.*;
import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static utils.FileUtils.readStringFromFile;

public class RegresInTests extends BaseTest{

    @Test
    void successCreateNewUser() {
        given()
                .body("{ \"name\": \"Ivan Petrov\", \"job\": \"Tester\" }")
                .when()
                .post(Endpoints.USERS.getPath("/details/16/1"))
                .then()
                .statusCode(HTTP_CREATED)
                .body("name", is("Ivan Petrov"))
                .body("job", is("Tester"))
                .body("id", is(notNullValue()))
                .log().body();
    }

    @Test
    void successLoginWithDataInFileTest() {
        String data = readStringFromFile("./src/test/resources/login_data.txt");
        given()
                .body(data)
                .when()
                .post(Endpoints.LOGIN.getPath("/details/16/1"))
                .then()
                .statusCode(HTTP_OK)
                .log().body()
                .body("token", is(notNullValue()));
    }

    @Test
    void successUsersListTest() {
        given()
                .when()
                .get(Endpoints.USERS.addPath("/3"))
                .then()
                .statusCode(HTTP_OK)
                .log().body()
                .body(matchesJsonSchemaInClasspath("User.json"))
                .body("data.first_name", is("Emma"))
                .body("support.text", is("To keep ReqRes free, contributions towards server costs are appreciated!"));
    }

    @Test
    void successDeleteUserTest() {
        given()
                .when()
                .delete(Endpoints.USERS.addPath("/3"))
                .then()
                .statusCode(HTTP_NO_CONTENT)
                .log().body();
    }

    @Test
    void unsuccessfulRegisterUserTest() {
        String data = readStringFromFile("./src/test/resources/register_data.txt");
        given()
                .body(data)
                .when()
                .post(Endpoints.REGISTER.getPath("/details/16/1"))
                .then()
                .statusCode(HTTP_BAD_REQUEST)
                .log().body()
                .body("error", is("Missing password"));
    }
}
