package com.emekamomodu.dronewebservice.exception.custom;

/**
 * @author CMOMODU
 * @version 1.0
 * @date 12/31/21 4:52 PM
 */
public class ObjectAlreadyExistsException extends RuntimeException {

    public ObjectAlreadyExistsException(String message) {
        super(message);
    }

}
