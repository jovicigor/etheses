package rs.fon.pzr.guards;

import org.springframework.util.StringUtils;

public class EmptyGuard {

    public static void validateString(String propertyName, String value) {
        if (StringUtils.isEmpty(value)) {
            String message = String.format("%s cannot be null or empty.", propertyName);
            throw new IllegalArgumentException(message);
        }
    }
}
