package com.github.rjansem.microservices.training.error;

import com.github.rjansem.microservices.training.exception.CodeMessageErrorDTO;
import com.github.rjansem.microservices.training.exception.ListCodeMessageErrorDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ValidationException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Intercepte les exceptions sortantes de l'API pour les wrapper si nécessaire
 *
 * @author rjansem
 * @author rjansem
 */
@ControllerAdvice("com.github.rjansem.microservices.training")
public class ApiExceptionAdvising {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiExceptionAdvising.class);

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ListCodeMessageErrorDTO> handle(MethodArgumentNotValidException exception) {
        List<CodeMessageErrorDTO> errorMsgs = exception.getBindingResult().getFieldErrors()
                .stream()
                .map(f -> new CodeMessageErrorDTO("BAD_REQUEST", f.getField() + " " + f.getDefaultMessage()))
                .collect(Collectors.toList());
        LOGGER.error("Erreur de validation : {}", errorMsgs.stream().map(CodeMessageErrorDTO::getLabel).collect(Collectors.joining(System.lineSeparator())));
        return new ResponseEntity<>(new ListCodeMessageErrorDTO(errorMsgs), HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ListCodeMessageErrorDTO> handle(ValidationException exception) {
        LOGGER.error("Erreur de validation : " + exception.getMessage());
        CodeMessageErrorDTO error = new CodeMessageErrorDTO("BAD_REQUEST", exception.getMessage());
        return new ResponseEntity<>(new ListCodeMessageErrorDTO(Arrays.asList(error)), HttpStatus.BAD_REQUEST);
    }

}
