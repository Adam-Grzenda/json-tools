package pl.put.poznan.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.put.poznan.dto.ApiErrorResponse;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * A class that implements operations performed on json documents
 */
@ControllerAdvice
public class ApiErrorControllerAdvice extends ResponseEntityExceptionHandler {

    /**
     * A method that deals with invalid json processing
     *
     * @param e is a json processing exception
     * @return an object representing the http response with the error code and its content
     */
    @ExceptionHandler(value = {JsonProcessingException.class})
    protected ResponseEntity<Object> handleJsonParsingConflict(JsonProcessingException e) {
        return ResponseEntity.status(BAD_REQUEST)
                .body(new ApiErrorResponse(BAD_REQUEST.value(), e.getOriginalMessage()));
    }

    /**
     * A method that deals with invalid arguments
     *
     * @param e is a illegal argument exception
     * @return an object representing the http response with the error code and its content
     */
    @ExceptionHandler(value = {IllegalArgumentException.class})
    protected ResponseEntity<Object> handleBadMethodRequest(IllegalArgumentException e) {
        return ResponseEntity.status(BAD_REQUEST).body(new ApiErrorResponse(BAD_REQUEST.value(), e.getMessage()));
    }

}
