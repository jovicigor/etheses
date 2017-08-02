package rs.fon.pzr.rest.exception.mapper;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import rs.fon.pzr.security.exception.AuthenticationException;
import rs.fon.pzr.rest.exception.InvalidTicketException;
import rs.fon.pzr.rest.model.ErrorMessage;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionMapper {
	
	private final Logger logger = Logger.getLogger(ExceptionMapper.class);

	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(AuthenticationException.class)
	@ResponseBody
	ErrorMessage handleUnsucessfullAuthentication(HttpServletRequest req, Exception ex) {
		return new ErrorMessage(ex.getMessage());
	}	
	 
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(RuntimeException.class)
	@ResponseBody
	ErrorMessage handleIvalidArguments(HttpServletRequest req, Exception ex) {
		logger.error("RuntimeException: ",ex);
		return new ErrorMessage(ex.getMessage());
	}

	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(InvalidTicketException.class)
	@ResponseBody
	ErrorMessage handleUnauthorisedRequest(HttpServletRequest req, Exception ex) {
		return new ErrorMessage(ex.getMessage());
	}
	
}
