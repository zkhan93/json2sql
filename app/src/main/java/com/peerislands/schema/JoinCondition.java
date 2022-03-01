package com.peerislands.schema;

import com.peerislands.dialect.Dialect;
import com.peerislands.schema.error.InvalidValueException;

public class JoinCondition extends Condition<Column>{

    public JoinCondition(Column column, Operator op, Column value) throws InvalidValueException {
        super(column, op, value);
    }

    @Override
    String parseValue(Dialect dialect, Column value) {
        return value.sql(dialect);
    }

    @Override
    void validate(Column value) throws InvalidValueException {
        if(value==null)
            throw new InvalidValueException("Join condition needs both columns");
    }

    
}
