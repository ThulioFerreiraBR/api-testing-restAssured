package tests;

import base.BaseTest;
import clients.AuthClient;
import clients.UserClient;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Collections;
import java.util.Map;

import static org.hamcrest.Matchers.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AuthTest extends BaseTest {

    // -------------------------------------------------------------------------
    // Constants
    // -------------------------------------------------------------------------

    private static final long RESPONSE_TIME_MS = 3000L;
    private static final String MSG_INVALID_CREDENTIALS = "Invalid credentials";
    private static final String MSG_USERNAME_PASSWORD = "Username and password required";
    private static final String MSG_INVALID_USERNAME = "Username is not valid";

    // -------------------------------------------------------------------------
    // Clients & shared state
    // -------------------------------------------------------------------------

    private final UserClient userClient = new UserClient();
    private final AuthClient authClient = new AuthClient();

    private String validUsername;
    private String validPassword;

    // -------------------------------------------------------------------------
    // Setup – fetches valid credentials once for the whole class
    // -------------------------------------------------------------------------

    @BeforeAll
    void loadValidCredentials() {
        Response usersResponse = userClient.getUsers();
        usersResponse.then().statusCode(200);

        validUsername = usersResponse.path("users[0].username");
        validPassword = usersResponse.path("users[0].password");
    }

    // -------------------------------------------------------------------------
    // Happy path
    // -------------------------------------------------------------------------

    @Test
    @Tag("smoke")
    @DisplayName("Should login successfully with valid credentials")
    void shouldLoginSuccessfully() {
        Map<String, Object> body = Map.of(
                "username", validUsername,
                "password", validPassword
        );

        authClient.login(body)
                .then()
                .statusCode(200)
                .header("Content-Type", containsString("application/json"))
                .time(lessThan(RESPONSE_TIME_MS))
                .body("accessToken", not(emptyOrNullString()))
                .body("refreshToken", not(emptyOrNullString()))
                .body("username", equalTo(validUsername))
                .body("email", not(emptyOrNullString()))
                .body("id", greaterThan(0));
    }

    // -------------------------------------------------------------------------
    // Invalid credentials
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("Should not login with invalid password")
    void shouldNotLoginWithInvalidPassword() {
        Map<String, Object> body = Map.of(
                "username", validUsername,
                "password", "invalid_password_XYZ"
        );

        authClient.login(body)
                .then()
                .statusCode(400)
                .time(lessThan(RESPONSE_TIME_MS))
                .body("message", equalTo(MSG_INVALID_CREDENTIALS))
                .body("$", not(hasKey("accessToken")))
                .body("$", not(hasKey("refreshToken")));
    }

    @Test
    @DisplayName("Should not login with invalid username")
    void shouldNotLoginWithInvalidUsername() {
        Map<String, Object> body = Map.of(
                "username", "invalid_user_XYZ",
                "password", validPassword
        );

        authClient.login(body)
                .then()
                .statusCode(400)
                .time(lessThan(RESPONSE_TIME_MS))
                .body("message", equalTo(MSG_INVALID_CREDENTIALS))
                .body("$", not(hasKey("accessToken")))
                .body("$", not(hasKey("refreshToken")));
    }

    @Test
    @DisplayName("Should not login with invalid username and invalid password")
    void shouldNotLoginWithBothFieldsInvalid() {
        Map<String, Object> body = Map.of(
                "username", "invalid_user_XYZ",
                "password", "invalid_password_XYZ"
        );

        authClient.login(body)
                .then()
                .statusCode(400)
                .time(lessThan(RESPONSE_TIME_MS))
                .body("message", equalTo(MSG_INVALID_CREDENTIALS))
                .body("$", not(hasKey("accessToken")))
                .body("$", not(hasKey("refreshToken")));
    }

    // -------------------------------------------------------------------------
    // Missing fields
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("Should not login without password")
    void shouldNotLoginWithoutPassword() {
        Map<String, Object> body = Map.of(
                "username", validUsername
        );

        authClient.login(body)
                .then()
                .statusCode(400)
                .time(lessThan(RESPONSE_TIME_MS))
                .body("message", equalTo(MSG_USERNAME_PASSWORD))
                .body("$", not(hasKey("accessToken")))
                .body("$", not(hasKey("refreshToken")));
    }

    @Test
    @DisplayName("Should not login without username")
    void shouldNotLoginWithoutUsername() {
        Map<String, Object> body = Map.of(
                "password", validPassword
        );

        authClient.login(body)
                .then()
                .statusCode(400)
                .time(lessThan(RESPONSE_TIME_MS))
                .body("message", equalTo(MSG_USERNAME_PASSWORD))
                .body("$", not(hasKey("accessToken")))
                .body("$", not(hasKey("refreshToken")));
    }

    @Test
    @DisplayName("Should not login with empty body")
    void shouldNotLoginWithEmptyBody() {
        authClient.login(Collections.emptyMap())
                .then()
                .statusCode(400)
                .time(lessThan(RESPONSE_TIME_MS))
                .body("message", equalTo(MSG_USERNAME_PASSWORD))
                .body("$", not(hasKey("accessToken")))
                .body("$", not(hasKey("refreshToken")));
    }

    // -------------------------------------------------------------------------
    // Invalid data types
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("Should not login when username is an integer")
    void shouldNotLoginWithIntegerAsUsername() {
        Map<String, Object> body = Map.of(
                "username", 123456,
                "password", validPassword
        );

        authClient.login(body)
                .then()
                .statusCode(400)
                .time(lessThan(RESPONSE_TIME_MS))
                .body("message", equalTo(MSG_INVALID_USERNAME))
                .body("$", not(hasKey("accessToken")))
                .body("$", not(hasKey("refreshToken")));
    }

    @Test
    @DisplayName("Should not login when password is a boolean")
    void shouldNotLoginWithBooleanAsPassword() {
        Map<String, Object> body = Map.of(
                "username", validUsername,
                "password", true
        );

        authClient.login(body)
                .then()
                .statusCode(400)
                .time(lessThan(RESPONSE_TIME_MS))
                .body("message", equalTo(MSG_INVALID_CREDENTIALS))
                .body("$", not(hasKey("accessToken")))
                .body("$", not(hasKey("refreshToken")));
    }

    @Test
    @DisplayName("Should not login when both fields have wrong data types")
    void shouldNotLoginWithBothFieldsAsWrongTypes() {
        Map<String, Object> body = Map.of(
                "username", 123456,
                "password", true
        );

        authClient.login(body)
                .then()
                .statusCode(400)
                .time(lessThan(RESPONSE_TIME_MS))
                .body("message", equalTo(MSG_INVALID_USERNAME))
                .body("$", not(hasKey("accessToken")))
                .body("$", not(hasKey("refreshToken")));
    }
}