package clients;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ProductClient {

    private static final String BASE_PATH = "/products";

    public Response getProducts() {
        return given()
                .when()
                .get(BASE_PATH);
    }

    public Response getProductById(int id) {
        return given()
                .pathParam("id", id)
                .when()
                .get(BASE_PATH + "/{id}");
    }
}
