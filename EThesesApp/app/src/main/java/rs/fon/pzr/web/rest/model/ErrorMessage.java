package rs.fon.pzr.web.rest.model;

public class ErrorMessage {

	private String error;

	public ErrorMessage() {

	}

	public ErrorMessage(String errorMessage) {
		this.error = errorMessage;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}
