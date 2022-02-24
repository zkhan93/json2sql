package com.peerislands.parser.error;

public class InvalidConditionException extends InvalidInputException{
    public InvalidConditionException(String input){
        super(String.format("Invalid block in input: %s", input));
    }
}
