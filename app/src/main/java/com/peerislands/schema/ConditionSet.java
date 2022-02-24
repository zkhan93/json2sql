package com.peerislands.schema;

import java.util.ArrayList;
import java.util.List;

import com.peerislands.schema.error.InvalidConditionJoinOpException;


public class ConditionSet implements SQLBase{
    private static final String AND = "AND";
    private static final String OR = "OR";

    private String joinOp;
    private List<ConditionWrapper> items = new ArrayList<>();

    public ConditionSet(String joinOp) throws InvalidConditionJoinOpException{
        this.joinOp = cleaned(joinOp);
    }
    
    private String cleaned(String value) throws InvalidConditionJoinOpException{
        String cleaned = value.trim().toUpperCase();
        if (!cleaned.equals(AND) && !cleaned.equals(OR))
            throw new InvalidConditionJoinOpException(value);
        return cleaned;
    }

    public void add(ConditionWrapper whereClause){
        items.add(whereClause);
    }

    public String sql(){
        StringBuffer strb = new StringBuffer();
        strb.append('(');
        for (int i=0; i< items.size(); i++){
            strb.append(items.get(i).sql());
            if (i < items.size() - 1){
                strb.append(' ');
                strb.append(this.joinOp);
                strb.append(' ');
            }
        }
        strb.append(')');
        return strb.toString();
    }    
}

