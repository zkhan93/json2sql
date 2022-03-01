package com.peerislands.schema;

import java.util.Arrays;
import java.util.List;

import com.peerislands.dialect.Dialect;
import com.peerislands.schema.error.InvalidConditionJoinOpException;
import com.peerislands.schema.error.InvalidJoinTypeException;

public class Join implements SQLBase{
    private static final List<String> validJoinTypes = Arrays.asList("inner", "outer", "left", "right");

    private String type;
    private String table;
    private ConditionWrapper condition;
    
    public Join(String type, String table, ConditionWrapper condition) throws InvalidJoinTypeException, InvalidConditionJoinOpException{
        this.table = table;
        this.type = cleanedJoinType(type);
        this.condition = condition;
    }
    
    private String cleanedJoinType(String type) throws InvalidJoinTypeException{
        if (type==null)
            return "INNER";
        String cleaned = type.trim().toLowerCase();
        if(!validJoinTypes.contains(cleaned)){
            throw new InvalidJoinTypeException(type);
        }
        return cleaned.toUpperCase();
    }

    @Override
    public String sql(Dialect dialect) {
        StringBuilder strb = new StringBuilder();
        strb.append(type);
        strb.append(' ');
        strb.append("JOIN");
        strb.append(' ');
        strb.append(table);
        strb.append(' ');
        strb.append("ON");
        strb.append(' ');
        strb.append(condition.sql(dialect));
        return strb.toString();
    }
    
}
