package factories;

import com.github.javafaker.Faker;
import models.ProductRequest;

public class ProductFactory {

    private static final Faker faker = new Faker();

    // -------------------------------------------------------------------------
    // Valid payload
    // -------------------------------------------------------------------------

    public static ProductRequest createValidProduct() {

        return ProductRequest.builder()
                .title(faker.commerce().productName())
                .description(faker.lorem().sentence(10))
                .price((float) faker.number().randomDouble(2, 10, 5000))
                .discountPercentage((float)faker.number().randomDouble(2, 1, 30))
                .rating((float)faker.number().randomDouble(1, 1, 5))
                .stock(faker.number().numberBetween(1, 200))
                .brand(faker.company().name())
                .category(faker.commerce().department())
                .thumbnail(faker.internet().image())
                .build();
    }

    // -------------------------------------------------------------------------
    // Invalid payloads
    // -------------------------------------------------------------------------

    public static ProductRequest createProductWithoutPrice() {

        return ProductRequest.builder()
                .title(faker.commerce().productName())
                .stock(10)
                .category("electronics")
                .build();
    }

    public static ProductRequest createProductWithNegativeStock() {

        return ProductRequest.builder()
                .title(faker.commerce().productName())
                .price(100)
                .stock(-1)
                .category("electronics")
                .build();
    }

    public static ProductRequest createMinimalProduct() {

        return ProductRequest.builder()
                .title("Test Product")
                .price(1)
                .stock(1)
                .category("test")
                .build();
    }
}