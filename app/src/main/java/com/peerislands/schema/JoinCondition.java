package com.peerislands.schema;

import com.peerislands.schema.error.InvalidValueException;

public class JoinCondition extends Condition<Column>{

    public JoinCondition(Column column, String opSymbol, Column value) throws InvalidValueException {
        super(column, opSymbol, value);
    }

    @Override
    String parseValue(Column value) {
        return value.sql();
    }

    @Override
    void validate(Column value) throws InvalidValueException {
        if(value==null)
            throw new InvalidValueException("Join condition needs both columns");
    }

    
}
