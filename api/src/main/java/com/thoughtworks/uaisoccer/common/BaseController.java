package com.thoughtworks.uaisoccer.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public abstract class BaseController<E extends IdentifiedEntity> {

    @ExceptionHandler(value = ObjectNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    protected Response<E> objectNotFoundHandler(ObjectNotFoundException e) {
        Response<E> response = new Response<>();
        response.setMessage(e.getMessage());
        return response;
    }
}
