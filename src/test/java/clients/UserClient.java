package clients;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserClient {

    private static final String BASE_PATH = "/users";

    public Response getUsers() {
        return given()
                .when()
                .get(BASE_PATH);
    }
}
