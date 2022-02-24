package com.peerislands.schema;

import java.util.ArrayList;

import com.peerislands.schema.error.InvalidValueException;

public class BetweenCondition extends Condition<ArrayList<Object>>{
    public BetweenCondition(Column column, String operatorName, ArrayList<Object> value) throws InvalidValueException {
        super(column, operatorName, value);
    }
    
    @Override String parseValue(ArrayList<Object> value){
        Object start = value.get(0);
        Object end = value.get(1);
        return parseSimpleValue(start) + " AND " + parseSimpleValue(end);
    }

    @Override void validate(ArrayList<Object> value) throws InvalidValueException{
        if (value.size() < 2){
            throw new InvalidValueException("Less than 2 values provided for BETWEEN operator");
        }
        if (value.size() > 2){
            throw new InvalidValueException("More than 2 values provided for BETWEEN operator");
        }
        Object start = value.get(0);
        Object end = value.get(1);
        
        validatePrimitiveValues(start);
        validatePrimitiveValues(end);
    }
}
