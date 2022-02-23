package com.peerislands.schema.error;

public class InvalidOperatorException extends SchemaException{
    public InvalidOperatorException(String name){
        super(String.format("Unsupported operator specified, '%s' operator is not supported", name));
    }
    
}
