package com.peerislands.schema;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import com.peerislands.dialect.Dialect;
import com.peerislands.schema.error.InvalidValueException;

public abstract class Condition<T> implements SQLBase {
    List<Class<?>> primitiveTypes = Arrays.asList(Integer.class, String.class, BigDecimal.class);

    Column column;
    Operator operator;
    T value;

    public Condition(Column column, Operator op, T value) throws InvalidValueException {
        this.column = column;
        this.operator = op;
        validate(value);
        this.value = value;
    }

    abstract String parseValue(Dialect dialect, T value);
        
    abstract void validate(T value) throws InvalidValueException;

    public String sql(Dialect dialect) {
        return column.sql(dialect) + " " + operator.sql(dialect) + " " + parseValue(dialect, value);
    }

    String parseSimpleValue(Dialect dialect, Object value){
        if (value instanceof String)
            return dialect.getQuoteChar() + value.toString() + dialect.getQuoteChar();
        else
            return value.toString();
    }

    void validatePrimitiveValues(Object value) throws InvalidValueException{
        if (!primitiveTypes.contains(value.getClass())) {
            throw new InvalidValueException(String.format("%s is an invalid value type for %s", value.getClass(), operator));
        }
    }
}
