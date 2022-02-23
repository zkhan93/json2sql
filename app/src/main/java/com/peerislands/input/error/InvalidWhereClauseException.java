package com.peerislands.input.error;

public class InvalidWhereClauseException extends InvalidInputException{
    public InvalidWhereClauseException(String whereBlock){
        super(String.format("Invalid where block in input: %s", whereBlock));
    }
}
