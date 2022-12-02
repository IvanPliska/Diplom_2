package order;

import client.Client;
import io.qameta.allure.Step;
import io.restassured.filter.Filter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.ValidatableResponse;
import user.User;

import static io.restassured.RestAssured.given;

public class OrdersClient extends Client {

    private static final String PATH_ORDER = "/api/orders";


    private final io.restassured.filter.Filter requestFilter = new RequestLoggingFilter();
    private final Filter responseFiler = new ResponseLoggingFilter();

    @Step("Create new order")
    public ValidatableResponse createOrder(Order order) {
        return given()
                .with()
                .filters(requestFilter, responseFiler)
                .spec(getSpec())
                .body(order)
                .when()
                .post(PATH_ORDER)
                .then();
    }

    @Step("Get orders")
    public ValidatableResponse getOrders(Order order) {
        return given()
                .with()
                .filters(requestFilter, responseFiler)
                .spec(getSpec())
                .body(order)
                .when()
                .get(PATH_ORDER)
                .then();
    }
}
