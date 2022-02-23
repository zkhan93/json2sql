package com.peerislands.schema;

import java.util.ArrayList;
import java.util.List;

import com.peerislands.utils.StringUtils;

public class SelectQuery implements SQLBase{
    String table;
    List<Column> columns;
    WhereClauseWrapper where;

    public SelectQuery(String table, List<Column> columns, WhereClauseWrapper where){
        this.table = table;
        this.columns = columns;
        this.where = where;
    }

    public String sql() {

        StringBuffer strb = new StringBuffer();

        strb.append("SELECT");
        strb.append(' ');
        
        if(columns != null){
            List<String> strColumns = new ArrayList<>();
            for(int i=0; i<this.columns.size(); i++){
                strColumns.add(this.columns.get(i).sql());
            }
            strb.append(StringUtils.join(strColumns, ", "));
        }else{
            strb.append("*");
        }
        strb.append(' ');
        strb.append("FROM");
        strb.append(' ');
        strb.append(this.table);
        strb.append(' ');
        if(where!=null){
            strb.append("WHERE");
            strb.append(' ');
            strb.append(where.sql());
        }
        return strb.toString();
    }
}