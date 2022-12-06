package user;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.junit.Assert.assertEquals;

public class UserChangeTest {
    private User user;
    private UserClient userClient;
    private String token;


    @Before
    public void setUp() {
        userClient = new UserClient();
        user = UserGenerator.generateDefaultUser();

    }

    @After
    public void cleanUp() {
        userClient.deleteUser(token);
    }

    @DisplayName("Change user")
    @Test
    public void userCanBeChange() {
        ValidatableResponse responseCreate = userClient.createUser(user);
        token = responseCreate.extract().path("accessToken").toString().substring(7);
        ValidatableResponse responseChange = userClient.changeUserWithToken(user, token);
        boolean isUserCreated = responseChange.extract().path("success");
        int statusCode = responseChange.extract().statusCode();

        assertEquals("User changed is not ok", true, isUserCreated);
        assertEquals("User changed status code uncorrected", SC_OK, statusCode);
    }

    @DisplayName("Change user impossible")
    @Test
    public void userCanNotBeChange() {
        ValidatableResponse responseCreate = userClient.createUser(user);
        token = responseCreate.extract().path("accessToken").toString().substring(7);
        ValidatableResponse responseChange = userClient.changeUserWithoutToken(user);
        boolean isUserChanged = responseChange.extract().path("success");
        int statusCode = responseChange.extract().statusCode();
        String actualMassage = responseChange.extract().path("message");

        assertEquals("User changed is ok", false, isUserChanged);
        assertEquals("User changed status code uncorrected", SC_UNAUTHORIZED, statusCode);
        assertEquals("User is change", "You should be authorised", actualMassage);
    }

}
