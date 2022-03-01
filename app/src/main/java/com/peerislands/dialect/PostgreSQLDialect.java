package com.peerislands.dialect;

import java.util.HashMap;
import java.util.Map;

import com.peerislands.schema.SchemaConstants;

public class PostgreSQLDialect extends Dialect{
    public static final String QUOTE_CHAR = "'";
    public static final boolean IS_CASE_SENSITIVE = true;
    
    @Override
    public String getQuoteChar() {
        return QUOTE_CHAR;
    }

    public String getDateFn() {
        return "NOW()";
    }

    @Override
    public Map<String, String> getOperatorSymbolsMap() {
        Map<String, String> map = new HashMap<>();
        map.put(SchemaConstants.NEQ, "!=");
        return map;
    }
}
