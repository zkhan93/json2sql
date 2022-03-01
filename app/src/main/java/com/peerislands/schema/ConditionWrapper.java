package com.peerislands.schema;

import com.peerislands.dialect.Dialect;

public class ConditionWrapper implements SQLBase{
    private Condition<?> whereClause;
    private ConditionSet whereClauseSet;

    public ConditionWrapper(ConditionSet whereClauseSet){
        this.whereClauseSet = whereClauseSet;
    }

    public ConditionWrapper(Condition<?> whereClause){
        this.whereClause = whereClause;
    }

    public String sql(Dialect dialect){
        if (whereClause!=null)
            return whereClause.sql(dialect);
        if (whereClauseSet!=null)
            return whereClauseSet.sql(dialect);
        return "";
    }

}
