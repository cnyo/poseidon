package com.nnk.springboot.security.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException() {
        super("Not found");
    }
}
