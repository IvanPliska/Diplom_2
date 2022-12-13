package orders;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import lombok.SneakyThrows;
import order.IngredientGenerator;
import order.Order;
import order.OrdersClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.User;
import user.UserClient;
import user.UserGenerator;

import java.util.concurrent.TimeUnit;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;

public class OrdersCreateFalseTest {

    private User user;
    private UserClient userClient;
    private OrdersClient ordersClient;
    private Order order;

    String token;

    @SneakyThrows
    @Before
    public void setUp() {
        TimeUnit.SECONDS.sleep(3);
        userClient = new UserClient();
        user = UserGenerator.generateDefaultUser();
        ordersClient = new OrdersClient();
        ValidatableResponse responseCreate = userClient.createUser(user);
        token = responseCreate.extract().path("accessToken").toString().substring(7);
    }

    @After
    public void cleanUp() {
        userClient.deleteUser(token);
    }

    @DisplayName("Created new order without authorisation")
    @Test
    public void orderCanNotBeCreatedWithoutAuthorisation() {
        order = IngredientGenerator.generateBun();
        ValidatableResponse responseCreateOrder = ordersClient.createOrderWithoutToken(order);
        boolean isOrderCreated = responseCreateOrder.extract().path("success");
        int statusCode = responseCreateOrder.extract().statusCode();

        assertEquals("Order created is not ok", true, isOrderCreated);
        assertEquals("Order created status code uncorrected", SC_OK, statusCode);
    }

    @DisplayName("Created new order without ingredients")
    @Test
    public void orderCanNotBeCreatedWithoutIngredients() {
        order = IngredientGenerator.generateNull();
        ValidatableResponse responseCreateOrder = ordersClient.createOrderWithToken(order, token);
        boolean isOrderCreated = responseCreateOrder.extract().path("success");
        int statusCode = responseCreateOrder.extract().statusCode();
        String actualMessage = responseCreateOrder.extract().path("message");

        assertEquals("Order created is not ok", false, isOrderCreated);
        assertEquals("Order created status code uncorrected", SC_BAD_REQUEST, statusCode);
        assertEquals("Order massage uncorrected", "Ingredient ids must be provided", actualMessage);
    }

    @DisplayName("Created new order with false ingredients")
    @Test
    public void orderCanNotBeCreatedWithFalseIngredients() {
        order = IngredientGenerator.generateFalseIngredient();
        ValidatableResponse responseCreateOrder = ordersClient.createOrderWithToken(order, token);
        int statusCode = responseCreateOrder.extract().statusCode();

        assertEquals("Order created status code uncorrected", SC_INTERNAL_SERVER_ERROR, statusCode);
    }
}

