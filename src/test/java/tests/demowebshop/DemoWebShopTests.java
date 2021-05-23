package tests.demowebshop;

import config.AuthSpec;
import config.Endpoints;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;

public class DemoWebShopTests extends TestBase {

    @Test
    public void addToCartWithoutAuthorizationAPITest() {
        Map<String, String> cookies = new AuthSpec().loginWithoutCookies();
        given()
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .cookies(cookies)
                .body("product_attribute_16_5_4=14&product_attribute_16_6_5=15&product_attribute_16_3_6=18&product_attribute_16_4_7=44&product_attribute_16_8_8=22&addtocart_16.EnteredQuantity=1")
                .when()
                .post(Endpoints.ADDTOCART.addPath("/details/16/1"))
                .then()
                .statusCode(200)
                .log().body()
                .body("success", is(true))
                .body("updatetopcartsectionhtml", equalTo("(1)"));
    }

    @Test
    public void SendEmailToFriendWithAuthorizationAPITest() {
        Map<String, String> cookiesAuth = new AuthSpec().login("qaguru@qa.guru", "qaguru@qa.guru1");

        Response response =
                given()
                        .contentType(ContentType.URLENC)
                        .cookies(cookiesAuth)
                        .body("FriendEmail=www%40test.ru&YourEmailAddress=qaguru%40qa.guru&PersonalMessage=Tes1&send-email=Send+email")
                        .when()
                        .post(Endpoints.MAILTOAFRIEND.addPath("/4"))
                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().response();

        assertThat(response.asString(), containsString("Your message has been sent"));
    }

}
