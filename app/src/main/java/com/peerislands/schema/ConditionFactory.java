package com.peerislands.schema;

import java.util.ArrayList;
import java.util.Arrays;

import com.peerislands.schema.error.InvalidOperatorException;
import com.peerislands.schema.error.InvalidValueException;

public class ConditionFactory {

    public static Condition<?> create(Column column, String operator, Object value) throws InvalidOperatorException, InvalidValueException {
        Operator op = new Operator(operator);

        if(value instanceof Column){
            return new JoinCondition(column, op, (Column)value);
        }
        if (Arrays.asList(
            SchemaConstants.EQ,
            SchemaConstants.NEQ,
            SchemaConstants.LT,
            SchemaConstants.GT,
            SchemaConstants.GTE,
            SchemaConstants.LTE,
            SchemaConstants.LIKE).contains(op.getName())) {
            
            return new SimpleCondition(column, op, value);

        } else if (op.getName().equals(SchemaConstants.IN) ||
            op.getName().equals(SchemaConstants.NOTIN)) {    
            return new ListCondition(column, op, (ArrayList<Object>) value);
            
        } else if (op.getName().equals(SchemaConstants.BETWEEN)) {

            return new BetweenCondition(column, op, (ArrayList<Object>) value);
        }
        throw new InvalidOperatorException(operator);
    }

}
