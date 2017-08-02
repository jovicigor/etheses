package rs.fon.pzr.security.exception;

public class AuthenticationException extends RuntimeException {

	public AuthenticationException() {
		super("Bad credentials");
	}
}
