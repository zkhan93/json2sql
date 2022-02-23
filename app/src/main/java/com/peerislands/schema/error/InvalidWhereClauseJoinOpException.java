package com.peerislands.schema.error;

public class InvalidWhereClauseJoinOpException extends SchemaException{
    public InvalidWhereClauseJoinOpException(){
        super("Invalid Where clauses combination type, valid values are 'AND' and 'OR' ");
    }
}
