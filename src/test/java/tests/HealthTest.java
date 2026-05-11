package tests;

import base.BaseTest;
import clients.HealthClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

public class HealthTest extends BaseTest {

    // -------------------------------------------------------------------------
    // Constants
    // -------------------------------------------------------------------------

    private static final long RESPONSE_TIME_MS = 3000L;

    // -------------------------------------------------------------------------
    // Clients
    // -------------------------------------------------------------------------

    private final HealthClient healthClient = new HealthClient();

    // -------------------------------------------------------------------------
    // Happy path
    // -------------------------------------------------------------------------

    @Test
    @Tag("smoke")
    @DisplayName("Should return API status successfully")
    void shouldReturnApiStatusSuccessfully() {

        healthClient.getStatus()
                .then()
                .statusCode(200)
                .header("Content-Type", containsString("application/json"))
                .time(lessThan(RESPONSE_TIME_MS))
                .body("status", equalTo("ok"))
                .body("method", equalTo("GET"));
    }
}