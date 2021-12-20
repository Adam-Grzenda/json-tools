package pl.put.poznan.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * A class that describes the error information
 */
@Getter
@Setter
@AllArgsConstructor
public class ApiErrorResponse {

    private final int status;
    private final String message;
}
