package clients;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class AuthClient {

    public Response login(String username, String password) {

        return given()
            .contentType(ContentType.JSON)
            .body(Map.of(
                "username", username,
                "password", password
            ))
            .when()
            .post("/auth/login");
    }
}