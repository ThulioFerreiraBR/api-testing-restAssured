package base;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;

public class BaseTest {

    @BeforeEach
    void setup() {

        RestAssured.baseURI = "https://dummyjson.com";
    }
}