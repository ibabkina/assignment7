package org.merit.securityjwt.assignment7.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author irinababkina
 *
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ExceedsCombinedBalanceLimitException extends Exception {

	/**
	 * 
	 */
	public ExceedsCombinedBalanceLimitException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public ExceedsCombinedBalanceLimitException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public ExceedsCombinedBalanceLimitException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ExceedsCombinedBalanceLimitException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public ExceedsCombinedBalanceLimitException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
