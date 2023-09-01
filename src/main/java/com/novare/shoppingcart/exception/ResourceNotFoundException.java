package com.novare.shoppingcart.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND,reason = "Resources Not Found")
public class ResourceNotFoundException extends RuntimeException{
private static final Long serialVersionUID=1L;
public ResourceNotFoundException(String message){
    super(message);
}
}
