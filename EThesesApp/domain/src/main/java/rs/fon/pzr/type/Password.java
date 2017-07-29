package rs.fon.pzr.type;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import rs.fon.pzr.guards.EmptyGuard;

public class Password {

    private static final String REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).{6,13}$";
    private static final String EXCEPTION_MESSAGE = "Šifra ne sme sadržati razmake, mora imati barem jedno malo slovo, "
            + "jedno veliko slovo, jednu cifru i sadržati između 6 i 13 karaktera.";

    private final String password;
    private static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private Password(String password) {
        this.password = password;
    }

    public static boolean isValid(String pass) {
        return pass.matches(REGEX);
    }

    public static Password fromString(String password) {
        EmptyGuard.validateString("password", password);
        if (!isValid(password))
            throw new IllegalStateException(EXCEPTION_MESSAGE);
        password = passwordEncoder.encode(password);
        return new Password(password);
    }

    public String asEncodedString() {
        return password;
    }
}
