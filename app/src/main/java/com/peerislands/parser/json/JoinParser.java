package com.peerislands.parser.json;

import java.util.ArrayList;
import java.util.List;

import com.peerislands.parser.BaseParser;
import com.peerislands.parser.ListParser;
import com.peerislands.parser.error.InvalidConditionException;
import com.peerislands.parser.error.InvalidInputException;
import com.peerislands.schema.Column;
import com.peerislands.schema.ConditionFactory;
import com.peerislands.schema.ConditionSet;
import com.peerislands.schema.ConditionWrapper;
import com.peerislands.schema.Join;
import com.peerislands.schema.error.InvalidConditionJoinOpException;
import com.peerislands.schema.error.InvalidJoinTypeException;
import com.peerislands.schema.error.InvalidOperatorException;
import com.peerislands.schema.error.InvalidValueException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JoinParser implements BaseParser<JSONObject, Join>, ListParser<JSONArray, Join>{

    public List<Join> parseMany(JSONArray items) throws InvalidInputException {
        List<Join> joins = new ArrayList<>();
        JSONObject item;
        for (int i = 0; i < items.length(); i++) {
            item = items.getJSONObject(i);
            joins.add(parse(item));
        }
        return joins;
    }

    public Join parse(JSONObject item) throws InvalidInputException {
        String joinType = item.optString("type");
        String table = item.getString("table");
        JSONObject condition = item.getJSONObject("condition");
        ConditionWrapper conditionWrapper = buildJoinConditionWrapper(condition);
        try {
            return new Join(joinType, table, conditionWrapper);
        } catch (InvalidJoinTypeException | InvalidConditionJoinOpException e) {
            e.printStackTrace();
            throw new InvalidInputException(e.getMessage());
        }
    }

    private ConditionWrapper buildJoinConditionWrapper(JSONObject item) throws InvalidInputException {
        ConditionWrapper condition;
        JSONArray conditionsWithAnd = item.optJSONArray("and");
        JSONArray conditionsWithOr = item.optJSONArray("or");
        if (conditionsWithAnd != null && conditionsWithOr != null)
            throw new InvalidConditionException(item.toString());

        if (conditionsWithAnd != null) {
            condition = buildJoinConditionSet("AND", conditionsWithAnd);
        } else if (conditionsWithOr != null) {
            condition = buildJoinConditionSet("OR", conditionsWithOr);
        } else {
            condition = buildJoinCondition(item);
        }
        return condition;
    }

    private ConditionWrapper buildJoinConditionSet(String conditionJoinOp, JSONArray items)
            throws InvalidInputException {
        ConditionSet whereClauses = null;
        try {
            whereClauses = new ConditionSet(conditionJoinOp);
            for (int i = 0; i < items.length(); i++) {
                whereClauses.add(buildJoinConditionWrapper(items.getJSONObject(i)));
            }
            return new ConditionWrapper(whereClauses);
        } catch (InvalidConditionJoinOpException e) {
            e.printStackTrace();
            throw new InvalidInputException("Invalid where clause op type");
        }
    }

    private ConditionWrapper buildJoinCondition(JSONObject item) throws InvalidInputException {
        try {
            String op = item.getString("operator");
            JSONObject left = item.getJSONObject("left");
            JSONObject right = item.getJSONObject("right");

            Column rightColumn = new ColumnParser().parse(right);
            Column leftColumn = new ColumnParser().parse(left);
            return new ConditionWrapper(ConditionFactory.create(rightColumn, op, leftColumn));
        } catch (JSONException | InvalidOperatorException | InvalidValueException e) {
            e.printStackTrace();
            throw new InvalidConditionException(item.toString());
        }
    }
}
