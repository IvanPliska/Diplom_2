package user;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import lombok.SneakyThrows;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;

public class UserDefaultTest {
    private User user;
    private UserClient userClient;
    String token;


    @SneakyThrows
    @Before
    public void setUp() {
        TimeUnit.SECONDS.sleep(3);
        userClient = new UserClient();
        user = UserGenerator.generateDefaultUser();

    }

    @After
    public void cleanUp() {
        userClient.deleteUser(token);
    }

    @DisplayName("Created new user")
    @Test
    public void userCanBeCreated() {
        ValidatableResponse responseCreate = userClient.createUser(user);
        token = responseCreate.extract().path("accessToken").toString().substring(7);
        boolean isUserCreated = responseCreate.extract().path("success");
        int statusCode = responseCreate.extract().statusCode();

        assertEquals("User created is not ok", true, isUserCreated);
        assertEquals("User created status code uncorrected", SC_OK, statusCode);
    }

    @DisplayName("Can not created new user with replicate login")
    @Test
    public void userCanNotBeCreatedTwice() {
        ValidatableResponse responseCreate = userClient.createUser(user);
        ValidatableResponse responseCreateTwice = userClient.createUser(user);
        token = responseCreate.extract().path("accessToken").toString().substring(7);
        String isUserNotCreated = responseCreateTwice.extract().path("message");
        int statusCode = responseCreateTwice.extract().statusCode();

        assertEquals("User is created ", "User already exists", isUserNotCreated);
        assertEquals("User created status code is corrected", SC_FORBIDDEN, statusCode);
    }

}
