package com.peerislands.schema;

import java.util.List;

import com.peerislands.schema.error.InvalidValueException;

import java.math.BigDecimal;
import java.util.Arrays;

public class SimpleWhereClause extends WhereClause<Object> {
    List<Class<?>> simpleTypes = Arrays.asList(Integer.class, String.class, BigDecimal.class);

    public SimpleWhereClause(Column column, String opSymbol, Object value) throws InvalidValueException {
        super(column, opSymbol, value);
    }

    @Override
    String parseValue(Object value) {
        return parseSimpleValue(value);
    }

    @Override
    void validate(Object value) throws InvalidValueException {
        validatePrimitiveValues(value);
    };

    

}
