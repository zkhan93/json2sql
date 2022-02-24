package com.peerislands.schema;

public class ConditionWrapper implements SQLBase{
    private Condition<?> whereClause;
    private ConditionSet whereClauseSet;

    public ConditionWrapper(ConditionSet whereClauseSet){
        this.whereClauseSet = whereClauseSet;
    }

    public ConditionWrapper(Condition<?> whereClause){
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
