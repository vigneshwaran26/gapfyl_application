package com.gapfyl.util;

import com.gapfyl.dto.errors.ErrorMessageDTO;
import com.gapfyl.dto.errors.ErrorResponseDTO;
import com.gapfyl.exceptions.GapFylException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;
import java.util.Locale;

/**
 * @author vignesh
 * Created on 14/04/21
 **/

@Slf4j
@ControllerAdvice
public class UserControllerAdvice extends ResponseEntityExceptionHandler {

    @Autowired
    MessageSource messageSource;

    @ExceptionHandler(GapFylException.class)
    public ResponseEntity<ErrorResponseDTO> gapFylExceptionHandler(GapFylException ex) {
        log.debug("processing GapFyl exception & transforming to json", ex);

        String value = ex.getMessage();
        try {
            value = messageSource.getMessage(ex.getMessage(), ex.getArguments(), Locale.getDefault());
        } catch (Exception e) {
            log.error("no message found for key", ex.getMessage());
        }

        ErrorMessageDTO errorMessage = new ErrorMessageDTO(ex.getCode(), value);
        ErrorResponseDTO responseDTO = new ErrorResponseDTO(Arrays.asList(errorMessage));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
