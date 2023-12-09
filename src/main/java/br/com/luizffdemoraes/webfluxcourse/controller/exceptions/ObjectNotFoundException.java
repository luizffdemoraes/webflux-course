package br.com.luizffdemoraes.webfluxcourse.controller.exceptions;

public class ObjectNotFoundException extends RuntimeException{
    public ObjectNotFoundException(String message) {
        super(message);
    }
}
