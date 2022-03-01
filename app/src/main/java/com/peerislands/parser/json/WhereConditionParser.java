package com.peerislands.parser.json;

import java.util.ArrayList;

import com.peerislands.parser.BaseParser;
import com.peerislands.parser.error.InvalidConditionException;
import com.peerislands.parser.error.InvalidInputException;
import com.peerislands.schema.Column;
import com.peerislands.schema.ConditionFactory;
import com.peerislands.schema.ConditionSet;
import com.peerislands.schema.ConditionWrapper;
import com.peerislands.schema.SQLBase;
import com.peerislands.schema.error.InvalidConditionJoinOpException;
import com.peerislands.schema.error.InvalidOperatorException;
import com.peerislands.schema.error.InvalidValueException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WhereConditionParser implements BaseParser<JSONObject, ConditionWrapper>{

    public ConditionWrapper parse(JSONObject json) throws InvalidInputException {
        ConditionWrapper where;
        JSONArray whereClausesWithAnd = json.optJSONArray("and");
        JSONArray whereClausesWithOr = json.optJSONArray("or");
        if (whereClausesWithAnd != null && whereClausesWithOr != null)
            throw new InvalidConditionException(json.toString());

        if (whereClausesWithAnd != null) {
            where = buildWhereClauseSet("AND", whereClausesWithAnd);
        } else if (whereClausesWithOr != null) {
            where = buildWhereClauseSet("OR", whereClausesWithOr);
        } else {
            where = buildWhereClause(json);
        }
        return where;
    }

    private ConditionWrapper buildWhereClause(JSONObject json) throws InvalidInputException {
        try {
            String op = json.getString("operator");
            JSONObject jsonColumn = json.getJSONObject("fieldName");
            Object value = json.get("fieldValue");

            if (value instanceof JSONArray) {
                value = ((JSONArray) value).toList();
            } else if (value instanceof JSONObject) {
                // it can be a sub-query
                SQLBase subQuery = new SelectParser().parse((JSONObject) value);
                ArrayList<SQLBase> tmp = new ArrayList<SQLBase>();
                tmp.add(subQuery);
                value = tmp;
            }
            Column column = new ColumnParser().parse(jsonColumn);
            return new ConditionWrapper(ConditionFactory.create(column, op, value));
        } catch (JSONException | InvalidOperatorException | InvalidValueException e) {
            e.printStackTrace();
            throw new InvalidConditionException(json.toString());
        }

    }

    private ConditionWrapper buildWhereClauseSet(String op, JSONArray json) throws InvalidInputException {
        ConditionSet whereClauses = null;
        try {
            whereClauses = new ConditionSet(op);
            for (int i = 0; i < json.length(); i++) {
                whereClauses.add(parse(json.getJSONObject(i)));
            }
            return new ConditionWrapper(whereClauses);
        } catch (InvalidConditionJoinOpException e) {
            e.printStackTrace();
            throw new InvalidInputException("Invalid where clause op type");
        }
    }

}
