package tests;

import base.BaseTest;
import clients.ProductClient;
import utils.AuthUtils;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.hamcrest.Matchers.*;

public class ProductTest extends BaseTest {

    // -------------------------------------------------------------------------
    // Constants
    // -------------------------------------------------------------------------

    private static final long RESPONSE_TIME_MS = 3000L;
    private static final List<String> ALLOWED_STATUS = List.of("In Stock", "Low Stock");
    private static final int INVALID_ID = Integer.MAX_VALUE;
    private static final int NEGATIVE_ID = -1;
    private static final String INVALID_TOKEN = "invalidToken";
    private static final String MSG_PRODUCT_NOT_FOUND = "^Product with id '(-?\\d+)' not found$";
    private static final String MSG_REQUIRED_TOKEN = "Access Token is required";
    private static final String MSG_EXPIRED_INVALID_TOKEN = "Invalid/Expired Token!";

    // -------------------------------------------------------------------------
    // Clients
    // -------------------------------------------------------------------------

    private final ProductClient productClient = new ProductClient();

    // -------------------------------------------------------------------------
    // Get all products
    // -------------------------------------------------------------------------

    @Test
    @Tag("smoke")
    @DisplayName("Should return all products successfully")
    void shouldReturnAllProductsSuccessfully() {

        productClient.getAllProducts()
                .then()
                .statusCode(200)
                .header("Content-Type", containsString("application/json"))
                .time(lessThan(RESPONSE_TIME_MS))
                .body("products", notNullValue())
                .body("products.size()", greaterThan(0))
                .body("products.id", everyItem(greaterThan(0)))
                .body("products.title", everyItem(not(emptyOrNullString())))
                .body("products.price", everyItem(greaterThan(0F)))
                .body("products.category", everyItem(not(emptyOrNullString())))
                .body("products.stock", everyItem(greaterThanOrEqualTo(0)))
                .body("products.availabilityStatus", everyItem(in(ALLOWED_STATUS)))
                .body("total", greaterThan(0))
                .body("limit", greaterThan(0))
                .body("skip", greaterThanOrEqualTo(0));
    }

    // -------------------------------------------------------------------------
    // Get product by id
    // -------------------------------------------------------------------------

    @Test
    @Tag("smoke")
    @DisplayName("Should return product by fixed valid ID")
    void shouldReturnProductByFixedId() {

        productClient.getProductById(1)
                .then()
                .statusCode(200)
                .header("Content-Type", containsString("application/json"))
                .time(lessThan(RESPONSE_TIME_MS))
                .body("id", equalTo(1))
                .body("title", not(emptyOrNullString()))
                .body("price", greaterThan(0F))
                .body("category", not(emptyOrNullString()))
                .body("stock", greaterThanOrEqualTo(0))
                .body("availabilityStatus", in(ALLOWED_STATUS));
    }

    // -------------------------------------------------------------------------
    // Invalid products
    // -------------------------------------------------------------------------


    @Test
    @Tag("regression")
    @DisplayName("Should return product by random valid ID")
    void shouldReturnProductByRandomId() {

        Response productsResponse = productClient.getAllProducts();
        int randomIndex = ThreadLocalRandom.current().nextInt(productsResponse.path("products.size()"));

        Integer productId = productsResponse.path("products[" + randomIndex + "].id");
        String title = productsResponse.path("products[" + randomIndex + "].title");
        Float price = productsResponse.path("products[" + randomIndex + "].price");
        String category = productsResponse.path("products[" + randomIndex + "].category");
        String brand = productsResponse.path("products[" + randomIndex + "].brand");
        Integer stock = productsResponse.path("products[" + randomIndex + "].stock");
        String availability = productsResponse.path("products[" + randomIndex + "].availabilityStatus");

        productClient.getProductById(productId)
                .then()
                .statusCode(200)
                .header("Content-Type", containsString("application/json"))
                .time(lessThan(RESPONSE_TIME_MS))
                .body("id", equalTo(productId))
                .body("title", equalTo(title))
                .body("price", equalTo(price))
                .body("category", equalTo(category))
                .body("brand", equalTo(brand))
                .body("stock", equalTo(stock))
                .body("availabilityStatus", equalTo(availability));
    }

    @Test
    @Tag("regression")
    @DisplayName("Should not return product with non-existent ID")
    void shouldNotReturnProductWithNonExistentId() {

        productClient.getProductById(INVALID_ID)
                .then()
                .statusCode(404)
                .header("Content-Type", containsString("application/json"))
                .time(lessThan(RESPONSE_TIME_MS))
                .body("message", matchesRegex(MSG_PRODUCT_NOT_FOUND));
    }

    @Test
    @Tag("regression")
    @DisplayName("Should not return product with negative ID")
    void shouldNotReturnProductWithNegativeId() {

        productClient.getProductById(NEGATIVE_ID)
                .then()
                .statusCode(404)
                .header("Content-Type", containsString("application/json"))
                .time(lessThan(RESPONSE_TIME_MS))
                .body("message", matchesRegex(MSG_PRODUCT_NOT_FOUND));
    }

    // -------------------------------------------------------------------------
    // Authenticated products
    // -------------------------------------------------------------------------

    @Test
    @Tag("smoke")
    @DisplayName("Should return authenticated product successfully")
    void shouldReturnAuthenticatedProductsSuccessfully() {

        String token = AuthUtils.getValidToken();

        productClient.getAuthProducts(token)
                .then()
                .statusCode(200)
                .header("Content-Type", containsString("application/json"))
                .time(lessThan(RESPONSE_TIME_MS))
                .body("products", notNullValue())
                .body("products.size()", greaterThan(0))
                .body("products.id", everyItem(greaterThan(0)))
                .body("products.title", everyItem(not(emptyOrNullString())))
                .body("products.price", everyItem(greaterThan(0F)))
                .body("products.category", everyItem(not(emptyOrNullString())))
                .body("products.stock", everyItem(greaterThanOrEqualTo(0)))
                .body("products.availabilityStatus", everyItem(in(ALLOWED_STATUS)))
                .body("total", greaterThan(0))
                .body("limit", greaterThan(0))
                .body("skip", greaterThanOrEqualTo(0));
    }

    @Test
    @Tag("regression")
    @DisplayName("Should not return products without token")
    void shouldNotReturnProductsWithoutToken() {

        productClient.getAuthProducts()
                .then()
                .statusCode(401)
                .header("Content-Type", containsString("application/json"))
                .time(lessThan(RESPONSE_TIME_MS))
                .body("message", equalTo(MSG_REQUIRED_TOKEN));
    }

    @Test
    @Tag("regression")
    @DisplayName("Should not return products with invalid token")
    void shouldNotReturnProductsWithInvalidToken() {

        productClient.getAuthProducts(INVALID_TOKEN)
                .then()
                .statusCode(401)
                .header("Content-Type", containsString("application/json"))
                .time(lessThan(RESPONSE_TIME_MS))
                .body("message", equalTo(MSG_EXPIRED_INVALID_TOKEN));
    }

    // -------------------------------------------------------------------------
    // Create product
    // -------------------------------------------------------------------------

    @Test

    @DisplayName("Should create product successfully")
    void shouldCreateProductSuccessfully() {

    }



}
