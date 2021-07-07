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
	}

	/**
	 * @param message
	 */
	public ExceedsCombinedBalanceLimitException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public ExceedsCombinedBalanceLimitException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ExceedsCombinedBalanceLimitException(String message, Throwable cause) {
		super(message, cause);
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
	}
}
