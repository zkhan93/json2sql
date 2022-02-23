package com.peerislands.schema;

import java.util.ArrayList;
import java.util.List;

import com.peerislands.schema.error.InvalidWhereClauseJoinOpException;


public class WhereClauseSet implements SQLBase{
    private static final String AND = "AND";
    private static final String OR = "OR";

    private String joinOp;
    private List<WhereClauseWrapper> items = new ArrayList<>();

    public WhereClauseSet(String joinOp) throws InvalidWhereClauseJoinOpException{
        this.joinOp = cleaned(joinOp);
    }
    
    private String cleaned(String value) throws InvalidWhereClauseJoinOpException{
        String cleaned = value.trim().toUpperCase();
        if (!cleaned.equals(AND) && !cleaned.equals(OR))
            throw new InvalidWhereClauseJoinOpException();
        return cleaned;
    }

    public void add(WhereClauseWrapper whereClause){
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

