package com.beersAPI.beers.Helper;

import com.beersAPI.beers.Model.Response.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    // Global Exception Handler for ConversionFailedExceptions
    // This Exception is thrown when java tries to convert string to enum with the method StringToEnumConverter
    // We use Custom Handler in order to show a more informative message to the user.
    @ExceptionHandler(ConversionFailedException.class)
    public ResponseEntity<String> handleConflict(RuntimeException ex) {

        try {
            var Response = new Response();
            Response.setMessage("Unknown Beer Type");
            Response.setDeveloperMessage(ex.getMessage());
            Response.setStatus(BAD_REQUEST);
            Response.setStatusCode(BAD_REQUEST.value());

            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            return new ResponseEntity<>(ow.writeValueAsString(Response), HttpStatus.BAD_REQUEST);
        }
        catch (JsonProcessingException jsonEx){
            return new ResponseEntity<>("Unknown Beer Type", HttpStatus.BAD_REQUEST);
        }
    }
}