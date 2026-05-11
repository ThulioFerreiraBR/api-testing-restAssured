package tests;

import base.BaseTest;
import clients.ProductClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.Matchers.*;

public class ProductTest extends BaseTest {

    // -------------------------------------------------------------------------
    // Constants
    // -------------------------------------------------------------------------

    private static final long RESPONSE_TIME_MS = 3000L;
    private static final List<String> ALLOWED_STATUS = List.of("In Stock", "Low Stock");

    // -------------------------------------------------------------------------
    // Clients
    // -------------------------------------------------------------------------

    private final ProductClient productClient = new ProductClient();

    // -------------------------------------------------------------------------
    // Happy path
    // -------------------------------------------------------------------------

    @Test
    @Tag("smoke")
    @DisplayName("Should return products successfully")
    void shouldReturnProductsSuccessfully() {

        productClient.getProducts()
                .then()
                .statusCode(200)
                .header("Content-Type", containsString("application/json"))
                .time(lessThan(RESPONSE_TIME_MS))
                .body("products", notNullValue())
                .body("products.size()", greaterThan(0))
                .body("products.id", everyItem(greaterThan(0)))
                .body("products.title", everyItem(not(emptyOrNullString())))
                .body("products.price", everyItem(greaterThan(0F)))
                .body("products.stock", everyItem(greaterThanOrEqualTo(0)))
                .body("products.availabilityStatus", everyItem(in(ALLOWED_STATUS)))
                .body("products.category", everyItem(not(emptyOrNullString())))
                .body("total", greaterThan(0))
                .body("limit", greaterThan(0))
                .body("skip", greaterThanOrEqualTo(0));
    }
}
