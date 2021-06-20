package pl.dkiszka.accountsapinn.adapters.rest.api;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.dkiszka.accountsapinn.app.account.AccountServiceException;
import pl.dkiszka.accountsapinn.domain.account.AccountCreationException;
import pl.dkiszka.accountsapinn.query.account.AccountNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Dominik Kiszka {dominikk19}
 * @project accounts-api-nn
 * @date 19.06.2021
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
class ApiControllerAdvice extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        var messages = ex.getAllErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList());
        return new ResponseEntity(ExceptionMessage.of(messages), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {AccountServiceException.class,
            AccountCreationException.class})
    ResponseEntity<ExceptionMessage> accountServiceException(AccountServiceException ex) {
        return new ResponseEntity(ExceptionMessage.of(List.of(ex.getLocalizedMessage())), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {AccountNotFoundException.class})
    ResponseEntity<ExceptionMessage> accountNotFoundException(AccountNotFoundException ex) {
        return new ResponseEntity(ExceptionMessage.of(List.of(ex.getLocalizedMessage())), HttpStatus.NOT_FOUND);
    }
}
