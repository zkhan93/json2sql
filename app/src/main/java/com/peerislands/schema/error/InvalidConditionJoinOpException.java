package com.peerislands.schema.error;

public class InvalidConditionJoinOpException extends SchemaException{
    public InvalidConditionJoinOpException(String msg){
        super(String.format("Invalid Where clauses combination type '%s', valid values are 'AND' and 'OR'", msg));
    }
}
