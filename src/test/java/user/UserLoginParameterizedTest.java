package user;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;


@RunWith(Parameterized.class)
public class UserLoginParameterizedTest {

    private UserClient userClient;
    private Credentials credentials;
    private User user;
    private int statusCode;
    private  String massage;
    private String token;


    @Before
    public void setUp(){
        userClient = new UserClient();
    }

    @After
    public void cleanUp() {
        userClient.deleteUser(token);}

    public UserLoginParameterizedTest(Credentials credentials, int statusCode, String massage, User user) {
        this.credentials = credentials;
        this.statusCode = statusCode;
        this.massage = massage;
        this.user = user;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{
                {LoginGenerator.getFalseUser(), SC_UNAUTHORIZED, "email or password are incorrect", UserGenerator.generateDefaultUser()},
                {LoginGenerator.getWithPasswordOnly(), SC_UNAUTHORIZED, "email or password are incorrect", UserGenerator.generateDefaultUser()},
                {LoginGenerator.getEmptyBoxUser(),SC_UNAUTHORIZED, "email or password are incorrect", UserGenerator.generateDefaultUser()},
        };
    }

    @DisplayName("Parameterized test to not login new user")
    @Test
    public void userCanNotBeLogin() {
        ValidatableResponse responseCreate = userClient.createUser(user);
        ValidatableResponse loginUser = userClient.loginUser(credentials);
        token = responseCreate.extract().path("accessToken").toString().substring(7);
        String actualMessage = loginUser.extract().path("message");
        int actualStatusCode = loginUser.extract().statusCode();

        assertEquals("Status code is not 40*", statusCode, actualStatusCode);
        assertEquals("Error massage uncorrected", massage, actualMessage);
    }

}
