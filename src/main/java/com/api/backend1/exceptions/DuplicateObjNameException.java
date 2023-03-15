package com.api.backend1.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateObjNameException extends RuntimeException {
    public DuplicateObjNameException(String message) {
        super(message);
    }
}
