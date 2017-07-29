package rs.fon.pzr.model.user;

import rs.fon.pzr.type.Email;
import rs.fon.pzr.type.Password;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UserCredentials {
    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "is_admin")
    private boolean isAdmin;

    protected UserCredentials() {
    }

    public UserCredentials(Email email, Password password) {
        this.email = email.asString();
        this.password = password.asEncodedString();
    }

    public String getPassword() {
        return password;
    }

    public Email getEmail() {
        return Email.fromString(email);
    }

    void makeAdmin() {
        isAdmin = true;
    }

    void removeAdmin() {
        isAdmin = false;
    }

    public boolean isAdmin() {
        return isAdmin;
    }
}
