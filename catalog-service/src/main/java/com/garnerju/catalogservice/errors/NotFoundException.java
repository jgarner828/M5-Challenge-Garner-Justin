package com.garnerju.catalogservice.errors;



public class NotFoundException extends RuntimeException{
    public NotFoundException() {
        super();
    }

    public NotFoundException(String msg) {
        super(msg);
    }
}



