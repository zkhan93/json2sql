package com.peerislands.schema;

import java.util.ArrayList;
import java.util.List;

import com.peerislands.dialect.Dialect;
import com.peerislands.utils.StringUtils;

public class SelectQuery implements SQLBase{
    String table;
    List<Column> columns;
    ConditionWrapper where;
    List<Join> joins;

    public SelectQuery(String table, List<Column> columns, List<Join> joins, ConditionWrapper where){
        this.table = table;
        this.columns = columns;
        this.where = where;
        this.joins = joins;
    }

    public String sql(Dialect dialect) {

        StringBuffer strb = new StringBuffer();

        strb.append("SELECT");
        strb.append(' ');
        
        if(columns != null){
            List<String> strColumns = new ArrayList<>();
            for(int i=0; i<this.columns.size(); i++){
                strColumns.add(this.columns.get(i).sql(dialect));
            }
            strb.append(StringUtils.join(strColumns, ", "));
        }else{
            strb.append("*");
        }
        strb.append(' ');
        strb.append("FROM");
        strb.append(' ');
        strb.append(this.table);
        if(joins != null){
            strb.append(' ');
            Join join;
            for(int i=0;i<joins.size();i++){
                join = joins.get(i);
                strb.append('\n');
                strb.append(join.sql(dialect));
                strb.append(' ');
            }
        }
        if(where!=null){
            strb.append("WHERE");
            strb.append(' ');
            strb.append(where.sql(dialect));
        }
        return strb.toString();
    }
}