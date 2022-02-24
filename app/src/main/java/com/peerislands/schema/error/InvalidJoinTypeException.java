package com.peerislands.schema.error;

public class InvalidJoinTypeException extends SchemaException{

    public InvalidJoinTypeException(String msg) {
        super(String.format("Invalid join type %s", msg));
    }
    
}
