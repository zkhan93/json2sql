package com.peerislands.schema;

import java.util.ArrayList;

import com.peerislands.dialect.Dialect;
import com.peerislands.schema.error.InvalidValueException;

public class ListCondition extends Condition<ArrayList<Object>> {

    public ListCondition(Column column, Operator opSymbol, ArrayList<Object> value) throws InvalidValueException {
        super(column, opSymbol, value);
    }

    @Override
    String parseValue(Dialect dialect, ArrayList<Object> values) {
        StringBuilder strb = new StringBuilder();
        Object value;
        strb.append('(');
        for (int i = 0; i < values.size(); i++) {
            value = values.get(i);
            if (primitiveTypes.contains(value.getClass())) {
                strb.append(parseSimpleValue(dialect, value));
            } else {
                String sql = ((SQLBase) value).sql(dialect);
                strb.append(sql);
            }
            if (i < values.size() - 1)
                strb.append(", ");
        }
        strb.append(')');
        return strb.toString();
    }

    @Override
    void validate(ArrayList<Object> values) throws InvalidValueException {

        Object val;
        for (int i = 0; i < values.size(); i++) {
            val = values.get(i);
            if (!primitiveTypes.contains(val.getClass()) && !(val instanceof SQLBase)) {
                throw new InvalidValueException(String.format("Invalid value type for %s", operator.getName()));
            }
        }
    };
}
