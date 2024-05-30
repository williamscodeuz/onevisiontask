package uz.williamscode.onevisiontask.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import uz.williamscode.onevisiontask.dto.ErrorDetails;

import java.sql.SQLException;
import java.util.Date;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception exception,
                                                              WebRequest webRequest){
        log.error(exception.toString());
        return new ResponseEntity<>(
                new ErrorDetails(new Date(), exception.getMessage(), webRequest.getDescription(false)),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ErrorDetails> handleSQLException(SQLException exception,
                                                              WebRequest webRequest){
        log.error(exception.toString());
        return new ResponseEntity<>(
                new ErrorDetails(new Date(), exception.getMessage(), webRequest.getDescription(false)),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
