package com.peerislands.schema;

public class WhereClauseWrapper implements SQLBase{
    private WhereClause<?> whereClause;
    private WhereClauseSet whereClauseSet;

    public WhereClauseWrapper(WhereClauseSet whereClauseSet){
        this.whereClauseSet = whereClauseSet;
    }

    public WhereClauseWrapper(WhereClause<?> whereClause){
        this.whereClause = whereClause;
    }

    public String sql(){
        if (whereClause!=null)
            return whereClause.sql();
        if (whereClauseSet!=null)
            return whereClauseSet.sql();
        return "";
    }

}
