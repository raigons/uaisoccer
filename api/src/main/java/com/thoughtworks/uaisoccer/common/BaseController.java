package com.thoughtworks.uaisoccer.common;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

public abstract class BaseController<E extends IdentifiedEntity> {

    @ExceptionHandler(value = ObjectNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    protected Response<E> objectNotFoundHandler(ObjectNotFoundException e) {
        Response<E> response = new Response<>();
        response.setMessage(e.getMessage());
        return response;
    }

    @ExceptionHandler(value = {ConstraintViolationException.class, DataIntegrityViolationException.class})
    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ResponseBody
    protected Response<E> dataIntegrityViolationHandler() {
        Response<E> response = new Response<>();
        response.setMessage("Could not execute request because it violates a database constraint");
        return response;
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    protected Response<E> invalidArgumentHandler(MethodArgumentNotValidException ex) {
        Response<E> response = new Response<>();
        response.setMessage("Could not execute request due to validation errors");

        for(ObjectError validationError : ex.getBindingResult().getAllErrors()) {
            FieldError fieldError = (FieldError) validationError;
            ValidationError error = new ValidationError(fieldError.getField(), fieldError.getDefaultMessage());
            response.addError(error);
        }

        return response;
    }

    protected ResponseEntity<List<E>> toResponse(List<E> entities) {
        if (entities.size() > 0) {
            return new ResponseEntity<>(entities, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
