package rs.fon.pzr.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UserLogin {
    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    protected UserLogin() {
    }

    public UserLogin(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
