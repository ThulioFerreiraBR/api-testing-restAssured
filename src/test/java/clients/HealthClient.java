package clients;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class HealthClient {

    private static final String BASE_PATH = "/test";

    public Response getStatus() {
        return given()
                .when()
                .get(BASE_PATH);
    }
}
