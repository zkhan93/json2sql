package com.peerislands.dialect.error;

public class InvalidDialectException extends Exception {
    public InvalidDialectException(String dialect){
        super(String.format("Invalid SQL dialect %s", dialect));
    }
}
