package rs.fon.elab.pzr.core.exception;

public class AuthenticationException extends RuntimeException {

	public AuthenticationException() {
		super("Bad credentials");
	}
}
