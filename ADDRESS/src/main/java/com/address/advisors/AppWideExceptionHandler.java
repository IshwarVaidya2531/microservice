package com.address.advisors;

import com.address.exceptions.CustomException;
import com.address.exceptions.NotFoundException;
import com.address.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@CrossOrigin
public class AppWideExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Response<Object>> handleRuntimeException(RuntimeException ex){
        return  new ResponseEntity<>(new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(),null,false),HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Response<Object>> handleNotFoundException(NotFoundException ex) {
        return  new ResponseEntity<>(new Response<>(HttpStatus.NOT_FOUND.value(), ex.getMessage(),null,false),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Response<Object>> handleCustomException(CustomException ex) {
        return  new ResponseEntity<>(new Response<>(ex.getStatus() != null ? ex.getStatus().value() : 500, ex.getMessage(),null,false),ex.getStatus() != null ? ex.getStatus() : HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
