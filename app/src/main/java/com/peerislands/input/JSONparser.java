package com.peerislands.input;

import java.util.ArrayList;
import java.util.List;

import com.peerislands.input.error.InvalidInputException;
import com.peerislands.input.error.InvalidWhereClauseException;
import com.peerislands.schema.Column;
import com.peerislands.schema.SQLBase;
import com.peerislands.schema.SelectQuery;
import com.peerislands.schema.WhereClauseFactory;
import com.peerislands.schema.WhereClauseSet;
import com.peerislands.schema.WhereClauseWrapper;
import com.peerislands.schema.error.InvalidOperatorException;
import com.peerislands.schema.error.InvalidValueException;
import com.peerislands.schema.error.InvalidWhereClauseJoinOpException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONparser implements BaseParser {

    private JSONObject root;

    public void parse(String content) {
        this.root = new JSONObject(content);
    }

    public SQLBase build() {
        return this.buildSelect(this.root);
    }

    private SQLBase buildSelect(JSONObject json) {

        String table = json.getString("table");
        List<Column> columns = this.buildColumns(json.getJSONArray("columns"));

        WhereClauseWrapper whereClause;
        try {
            whereClause = this.buildWhere(json.getJSONObject("where"));
        } catch (InvalidInputException e) {
            whereClause = null;
            System.err.println(e.toString());
        }
        return new SelectQuery(table, columns, whereClause);
    }

    private List<Column> buildColumns(JSONArray json) {
        ArrayList<Column> columns = new ArrayList<>();
        for (int i = 0; i < json.length(); i++) {
            columns.add(new Column(json.getString(i)));
        }
        return columns;
    }

    private WhereClauseWrapper buildWhere(JSONObject json) throws InvalidInputException {
        WhereClauseWrapper where;
        JSONArray whereClausesWithAnd = json.optJSONArray("and");
        JSONArray whereClausesWithOr = json.optJSONArray("or");
        if (whereClausesWithAnd != null && whereClausesWithOr != null)
            throw new InvalidWhereClauseException(json.toString());

        if (whereClausesWithAnd != null) {
            where = this.buildWhereClauseSet("AND", whereClausesWithAnd);
        } else if (whereClausesWithOr != null) {
            where = this.buildWhereClauseSet("OR", whereClausesWithOr);
        } else {
            try{
                where = buildWhereClause(json);
            } 
            catch (InvalidInputException e){
                throw new InvalidWhereClauseException(json.toString());
            }
        }
        return where;
    }

    private WhereClauseWrapper buildWhereClause(JSONObject json)  throws InvalidInputException {
        try{
            String op = json.getString("operator");
            String columnName = json.getString("fieldName");
            Object value = json.get("fieldValue");
            if(value instanceof JSONArray){
                value = ((JSONArray)value).toList();
            }
            //  not expecting a JSONObject, only primitive or array or primitives
            return new WhereClauseWrapper(WhereClauseFactory.create(new Column(columnName), op, value));
        }
        catch(JSONException|InvalidOperatorException|InvalidValueException e){
            throw new InvalidWhereClauseException(json.toString());
        }
        
    }

    public WhereClauseWrapper buildWhereClauseSet(String op, JSONArray json) throws InvalidInputException {
            WhereClauseSet whereClauses = null;
            try {
                whereClauses = new WhereClauseSet(op);    
                for (int i = 0; i < json.length(); i++) {
                    whereClauses.add(this.buildWhere(json.getJSONObject(i)));
                }
                return new WhereClauseWrapper(whereClauses);
            } catch (InvalidWhereClauseJoinOpException e) {
                e.printStackTrace();
                throw new InvalidInputException("Invalid where clause op type");
            }
    }
}
