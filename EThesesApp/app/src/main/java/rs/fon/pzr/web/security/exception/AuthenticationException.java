package rs.fon.pzr.web.security.exception;

public class AuthenticationException extends RuntimeException {

	public AuthenticationException() {
		super("Bad credentials");
	}
}
