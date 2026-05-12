package clients;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.ProductRequest;

import static io.restassured.RestAssured.given;

public class ProductClient {

    private static final String BASE_PATH = "/products";

    public Response getAllProducts() {
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

    public Response getAuthProducts(String token) {
        return given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/auth/products");
    }

    public Response getAuthProducts() {
        return given()
                .when()
                .get("/auth/products");
    }

    public Response createProduct(ProductRequest payload) {
        return given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post(BASE_PATH + "/add");
    }
}
