package user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Credentials {

    private String login;
    private String password;

    public static Credentials from (User user) {
        return new Credentials(user.getLogin(), user.getPassword());
    }
}