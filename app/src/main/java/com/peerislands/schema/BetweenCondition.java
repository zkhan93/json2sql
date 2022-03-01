package com.peerislands.schema;

import java.util.ArrayList;

import com.peerislands.dialect.Dialect;
import com.peerislands.schema.error.InvalidValueException;

public class BetweenCondition extends Condition<ArrayList<Object>>{
    public BetweenCondition(Column column, Operator op, ArrayList<Object> value) throws InvalidValueException {
        super(column, op, value);
    }
    
    @Override String parseValue(Dialect dialect, ArrayList<Object> value){
        Object start = value.get(0);
        Object end = value.get(1);
        return parseSimpleValue(dialect, start) + " AND " + parseSimpleValue(dialect, end);
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
