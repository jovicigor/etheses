package rs.fon.pzr.guards;

import java.util.Objects;

public class NullGuard {

    public static void validate(String propertyName, Object value) {
        if (Objects.isNull(value)) {
            String message = String.format("%s cannot be null.", propertyName);
            throw new IllegalArgumentException(message);
        }
    }
}
