package com.pizzeria.pizzaapp.config;

import com.pizzeria.pizzaapp.config.exceptions.ServiceException;
import com.pizzeria.pizzaapp.config.exceptions.UnauthorizedException;
import com.pizzeria.pizzaapp.dto.ErrorDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.DataBinder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

import static com.pizzeria.pizzaapp.util.Translation.toLocale;
import static java.util.stream.Collectors.toMap;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

  private final Logger logger = LoggerFactory.getLogger(AppExceptionHandler.class);

  @InitBinder
  private void activateDirectFieldAccess(DataBinder dataBinder) {
    dataBinder.initDirectFieldAccess();
  }

  @ExceptionHandler(ServiceException.class)
  public final ResponseEntity<ErrorDto> handleServiceExceptions(ServiceException ex, WebRequest request) {
    ErrorDto errorDto = new ErrorDto();
    errorDto.message = ex.getLocalizedMessage();
    return new ResponseEntity<>(errorDto, BAD_REQUEST);
  }

  @ExceptionHandler(UnauthorizedException.class)
  public final ResponseEntity<Object> handleUnauthorizedException(UnauthorizedException ex, WebRequest request) {
    ErrorDto errorDto = new ErrorDto();
    errorDto.message = ex.getLocalizedMessage();
    return new ResponseEntity<>(errorDto, UNAUTHORIZED);
  }

  @ExceptionHandler(Exception.class)
  public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
    logger.error("Unexpected error occurred while executing request: " + ex.getMessage(), ex);
    return new ResponseEntity<>(new ErrorDto(toLocale("unexpected-error")), INTERNAL_SERVER_ERROR);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                HttpHeaders headers,
                                                                HttpStatus status,
                                                                WebRequest request) {
    final Map<String, String> fields = ex.getBindingResult().getFieldErrors().stream()
      .collect(toMap(FieldError::getField, this::getFieldErrorMessage));
    ErrorDto errorDto = new ErrorDto("Validation error!", fields);
    return new ResponseEntity<>(errorDto, BAD_REQUEST);
  }

  private String getFieldErrorMessage(FieldError fieldError) {
    final String message = fieldError.getDefaultMessage();
    return message == null ? "" : message;
  }
}
