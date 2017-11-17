package com.github.rjansem.microservices.training.error;

import com.github.rjansem.microservices.training.exception.CodeMessageErrorDTO;
import com.github.rjansem.microservices.training.exception.ListCodeMessageErrorDTO;
import com.github.rjansem.microservices.training.exception.NOBCException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import rx.exceptions.CompositeException;

import javax.validation.ValidationException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Intercepte les exceptions sortantes de l'API pour les wrapper si nécessaire
 *
 * @author jntakpe
 * @author aazzerrifi
 */
@ControllerAdvice("com.github.rjansem.microservices.training")
public class ApiExceptionAdvising {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiExceptionAdvising.class);

    private static final String UNKNOWN_CODE = "unknown";



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

    @ExceptionHandler(CompositeException.class)
    public ResponseEntity<ListCodeMessageErrorDTO> handleComposite(CompositeException exception) {
        return exception.getExceptions().stream()
                .filter(e -> e.getClass().isAssignableFrom(NOBCException.class))
                .map(e -> (NOBCException) e)
                .findFirst()
                .map(e -> new ResponseEntity<>(extractEfsExceptionInfos(e), HttpStatus.valueOf(e.getExceptionCode().getStatus())))
                .orElseGet(() -> new ResponseEntity<>(defaultExceptionInfos(exception), HttpStatus.INTERNAL_SERVER_ERROR));
    }

    private ListCodeMessageErrorDTO extractEfsExceptionInfos(NOBCException e) {
        Map<String, Object> properties = e.getProperties();
        return new ListCodeMessageErrorDTO(Arrays.asList(new CodeMessageErrorDTO((String) properties.get(NOBCException.EFS_CODE), (String) properties.get(NOBCException.EFS_MESSAGE))));
    }

    private ListCodeMessageErrorDTO defaultExceptionInfos(Exception e) {
        return new ListCodeMessageErrorDTO(Arrays.asList(new CodeMessageErrorDTO(UNKNOWN_CODE, e.getMessage())));
    }
}
