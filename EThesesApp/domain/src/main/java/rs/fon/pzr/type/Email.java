package rs.fon.pzr.type;

import org.apache.commons.validator.routines.EmailValidator;
import rs.fon.pzr.guards.EmptyGuard;

public class Email {
    private final String email;

    private Email(String email) {
        this.email = email;
    }

    public static Email fromString(String email) {
        EmptyGuard.validateString("email", email);
        boolean valid = EmailValidator.getInstance().isValid(email);
        if (!valid) {
            throw new EmailParseException(String.format("%s is not a valid email address", email));
        }
        return new Email(email);
    }

    public static boolean isValid(String email) {
        return EmailValidator.getInstance().isValid(email);
    }

    public String asString() {
        return email;
    }
}

