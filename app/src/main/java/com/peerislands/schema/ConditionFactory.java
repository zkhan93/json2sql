package com.peerislands.schema;

import java.util.ArrayList;

import com.peerislands.schema.error.InvalidOperatorException;
import com.peerislands.schema.error.InvalidValueException;

public class ConditionFactory {

    public static Condition<?> create(Column column, String operator, Object value) throws InvalidOperatorException, InvalidValueException {
        String opSymbol = OperatorType.getSymbol(operator);

        if(value instanceof Column){
            return new JoinCondition(column, opSymbol, (Column)value);
        }

        if (opSymbol.equals(Constants.EQ) ||
                opSymbol.equals(Constants.NEQ) ||
                opSymbol.equals(Constants.GT) ||
                opSymbol.equals(Constants.LT) ||
                opSymbol.equals(Constants.GTE) ||
                opSymbol.equals(Constants.LTE) ||
                opSymbol.equals(Constants.LIKE)) {
            
            return new SimpleCondition(column, opSymbol, value);

        } else if (opSymbol.equals(Constants.IN) ||
            opSymbol.equals(Constants.NOTIN)) {    
            return new ListCondition(column, opSymbol, (ArrayList<Object>) value);
            
        } else if (opSymbol.equals(Constants.BETWEEN)) {

            return new BetweenCondition(column, opSymbol, (ArrayList<Object>) value);
        }
        throw new InvalidOperatorException(operator);
    }

}
