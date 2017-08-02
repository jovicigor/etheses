package rs.fon.pzr.rest.util;

import rs.fon.pzr.core.exception.InvalidArgumentException;

public class ParamaterCheck {

    public static void mandatory(String paramName, Object paramValue) {
        if (paramValue == null) {
            throw new InvalidArgumentException(paramName + " je obavezno polje!");
        }
    }
}
