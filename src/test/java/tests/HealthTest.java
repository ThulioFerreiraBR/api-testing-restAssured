package tests;

import base.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class HealthTest extends BaseTest {

    @Test
    @DisplayName("Should return API status successfully")
    void shouldReturnApiStatusSuccessfully() {

        given()
                .when()
                    .get("/test")
                .then()
                    .statusCode(200)
                    .body("status", equalTo("ok"))
                    .body("method", equalTo("GET"));
    }
}