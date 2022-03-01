package com.peerislands.schema;

import java.util.List;

import com.peerislands.dialect.Dialect;
import com.peerislands.schema.error.InvalidValueException;

import java.math.BigDecimal;
import java.util.Arrays;

public class SimpleCondition extends Condition<Object> {
    List<Class<?>> simpleTypes = Arrays.asList(Integer.class, String.class, BigDecimal.class);

    public SimpleCondition(Column column, Operator opSymbol, Object value) throws InvalidValueException {
        super(column, opSymbol, value);
    }

    @Override
    String parseValue(Dialect dialect, Object value) {
        return parseSimpleValue(dialect, value);
    }

    @Override
    void validate(Object value) throws InvalidValueException {
        validatePrimitiveValues(value);
    };

}
