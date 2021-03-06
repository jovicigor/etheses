package rs.fon.pzr.web.rest.model;

import org.hibernate.validator.constraints.NotEmpty;

public class LoginData {

    @NotEmpty(message = "email is required.")
    private String email;

    @NotEmpty(message = "email is required")
    private String password;

    public LoginData() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
