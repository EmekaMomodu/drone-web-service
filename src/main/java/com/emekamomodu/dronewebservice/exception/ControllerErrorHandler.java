package com.emekamomodu.dronewebservice.exception;

import com.emekamomodu.dronewebservice.exception.custom.InvalidRequestObjectException;
import com.emekamomodu.dronewebservice.exception.custom.ObjectAlreadyExistsException;
import com.emekamomodu.dronewebservice.exception.custom.ObjectNotFoundException;
import com.emekamomodu.dronewebservice.model.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.MethodNotAllowedException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author CMOMODU
 * @version 1.0
 * @date 12/30/21 11:58 PM
 */
@ControllerAdvice
public class ControllerErrorHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(value = Exception.class)
    protected ResponseEntity<Response> handleException(Exception exception) {
        logger.error(exception.toString());
        exception.printStackTrace();
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    protected ResponseEntity<Response> handleHttpMessageNotReadableException(HttpMessageNotReadableException httpMessageNotReadableException,
                                                                             HttpServletResponse response) {
        logger.error(httpMessageNotReadableException.toString());
        return buildResponse(HttpStatus.UNPROCESSABLE_ENTITY, httpMessageNotReadableException.getMessage());
    }

    @ExceptionHandler(value = MethodNotAllowedException.class)
    protected ResponseEntity<Response> handleMethodNotAllowedException(MethodNotAllowedException methodNotAllowedException, HttpServletRequest request) {
        logger.error(methodNotAllowedException.toString());
        return buildResponse(HttpStatus.METHOD_NOT_ALLOWED, methodNotAllowedException.getMessage());
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<Response> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException httpRequestMethodNotSupportedException,
                                                                                    HttpServletRequest request) {
        logger.error(httpRequestMethodNotSupportedException.toString());
        return buildResponse(HttpStatus.METHOD_NOT_ALLOWED, httpRequestMethodNotSupportedException.getMessage());
    }

    @ExceptionHandler(value = InvalidRequestObjectException.class)
    protected ResponseEntity<Response> handleInvalidRequestObjectException(InvalidRequestObjectException invalidRequestObjectException) {
        logger.error(invalidRequestObjectException.toString());
        return buildResponse(HttpStatus.BAD_REQUEST, invalidRequestObjectException.getMessage());
    }

    @ExceptionHandler(value = ObjectAlreadyExistsException.class)
    protected ResponseEntity<Response> handleObjectAlreadyExistsException(ObjectAlreadyExistsException objectAlreadyExistsException, HttpServletRequest request) {
        logger.error(objectAlreadyExistsException.toString());
        return buildResponse(HttpStatus.CONFLICT, objectAlreadyExistsException.getMessage());
    }

    @ExceptionHandler(value = ObjectNotFoundException.class)
    protected ResponseEntity<Response> handleObjectNotFoundException(ObjectNotFoundException objectNotFoundException, HttpServletRequest request) {
        logger.error(objectNotFoundException.toString());
        return buildResponse(HttpStatus.NOT_FOUND, objectNotFoundException.getMessage());
    }

    private ResponseEntity<Response> buildResponse(HttpStatus status, String message) {
        return new ResponseEntity<>(new Response(message), status);
    }

}
