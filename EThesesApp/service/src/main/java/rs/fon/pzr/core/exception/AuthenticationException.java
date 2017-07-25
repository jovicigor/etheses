package rs.fon.pzr.core.exception;

public class AuthenticationException extends RuntimeException {

	public AuthenticationException() {
		super("Bad credentials");
	}
}
