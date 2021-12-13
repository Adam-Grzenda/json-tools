package pl.put.poznan.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.put.poznan.DTO.ApiErrorResponse;

@ControllerAdvice
public class ApiErrorControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {JsonProcessingException.class})
    protected ResponseEntity<Object> handleJsonParsingConflict(JsonProcessingException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getOriginalMessage()));
    }
}
