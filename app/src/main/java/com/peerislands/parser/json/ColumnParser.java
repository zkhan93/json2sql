package com.peerislands.parser.json;

import java.util.ArrayList;
import java.util.List;

import com.peerislands.parser.error.InvalidInputException;
import com.peerislands.schema.Column;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ColumnParser{

    public List<Column> parse(JSONArray items) throws InvalidInputException{
        ArrayList<Column> columns = new ArrayList<>();
        JSONObject column;
        for (int i = 0; i < items.length(); i++) {
            column = items.getJSONObject(i);
            columns.add(parse(column));
        }
        return columns;
    }

    public Column parse(JSONObject item) throws InvalidInputException{
        try {
            String table = item.optString("table");
            String column = item.getString("name");
            return new Column(table, column);
        } catch (JSONException e) {
            throw new InvalidInputException(String.format("not a valid column json %s", item.toString()));
        }
    }

    
}
