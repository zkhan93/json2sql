package com.peerislands.parser.json;

import java.util.List;

import com.peerislands.parser.BaseParser;
import com.peerislands.parser.error.InvalidInputException;
import com.peerislands.schema.Column;
import com.peerislands.schema.SelectQuery;

import com.peerislands.schema.ConditionWrapper;
import com.peerislands.schema.Join;

import org.json.JSONArray;
import org.json.JSONObject;

public class SelectParser implements BaseParser<JSONObject>{

    public SelectQuery parse(JSONObject json) throws InvalidInputException {

        String table = json.getString("table");
        ConditionWrapper whereClause = null;
        List<Column> columns = null;
        List<Join> joins = null;

        JSONArray jsonColumns = json.optJSONArray("columns");
        if (jsonColumns != null)
            columns = new ColumnParser().parse(jsonColumns);

        JSONArray jsonJoins = json.optJSONArray("joins");

        if (jsonJoins != null)
            joins = new JoinParser().parse(jsonJoins);

        JSONObject where = json.optJSONObject("where");
        if (where != null)
            whereClause = new WhereConditionParser().parse(where);

        return new SelectQuery(table, columns, joins, whereClause);
    }

}
