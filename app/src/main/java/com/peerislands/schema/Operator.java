package com.peerislands.schema;

import java.util.Arrays;
import java.util.List;

import com.peerislands.dialect.Dialect;
import com.peerislands.schema.error.InvalidOperatorException;

public class Operator implements SQLBase{

    private static List<String> validOperatorNames = Arrays.asList(
        SchemaConstants.EQ,
        SchemaConstants.NEQ,
        SchemaConstants.LT,
        SchemaConstants.GT,
        SchemaConstants.GTE,
        SchemaConstants.LTE,
        SchemaConstants.IN,
        SchemaConstants.NOTIN,
        SchemaConstants.BETWEEN,
        SchemaConstants.LIKE);

    private String name;

    public Operator(String name) throws InvalidOperatorException{
        String nameLower = name.trim().toLowerCase();
        if (!validOperatorNames.contains(nameLower)){
            throw new InvalidOperatorException(name);
        }
        this.name = nameLower;
    }

    public String getName() {
        return name;
    }

    @Override
    public String sql(Dialect dialect) {
        return dialect.getOperatorSymbol(name);
    }
    
}
