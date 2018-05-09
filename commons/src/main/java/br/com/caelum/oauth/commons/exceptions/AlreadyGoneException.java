package br.com.caelum.oauth.commons.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.GONE)
public class AlreadyGoneException extends RuntimeException {
}
