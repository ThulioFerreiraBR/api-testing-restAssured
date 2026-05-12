package clients;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class AuthClient {

    private static final String BASE_PATH = "/auth/login";

    public Response login(Map<String, Object> body) {

        return given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post(BASE_PATH);
    }
}