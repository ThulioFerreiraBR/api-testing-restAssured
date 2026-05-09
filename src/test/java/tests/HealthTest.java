package tests;

import base.BaseTest;
import clients.HealthClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

public class HealthTest extends BaseTest {

    HealthClient healthClient = new HealthClient();

    @Test
    @DisplayName("Should return API status successfully")
    void shouldReturnApiStatusSuccessfully() {

        healthClient.getStatus()
            .then()
            .statusCode(200)
            .header("Content-Type", containsString("application/json"))
            .time(lessThan(2000L))
            .body("status", equalTo("ok"))
            .body("method", equalTo("GET"));
    }
}