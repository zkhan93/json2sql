package com.peerislands.schema;



import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import com.peerislands.schema.error.InvalidValueException;

public abstract class Condition<T> implements SQLBase {
    List<Class<?>> primitiveTypes = Arrays.asList(Integer.class, String.class, BigDecimal.class);

    Column column;
    String operator;
    String value;

    public Condition(Column column, String opSymbol, T value) throws InvalidValueException {
        this.column = column;
        this.operator = opSymbol;
        validate(value);
        this.value = parseValue(value);
    }

    abstract String parseValue(T value);
        
    abstract void validate(T value) throws InvalidValueException;

    public String sql() {
        return column.sql() + " " + operator + " " + value;
    }

    String parseSimpleValue(Object value){
        if (value instanceof String)
            return "\"" + value.toString() + "\"";
        else
            return value.toString();
    }

    void validatePrimitiveValues(Object value) throws InvalidValueException{
        if (!primitiveTypes.contains(value.getClass())) {
            throw new InvalidValueException(String.format("%s is an invalid value type for %s", value.getClass(), operator));
        }
    }
}
