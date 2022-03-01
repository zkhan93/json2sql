package com.peerislands.dialect;

import java.util.HashMap;
import java.util.Map;

import com.peerislands.schema.SchemaConstants;

public abstract class Dialect {
    
    private HashMap<String, String> nameToSymbolMap = new HashMap<>();
    
    abstract public String getQuoteChar();
    abstract public String getDateFn();
    abstract public Map<String, String> getOperatorSymbolsMap();

    public Dialect(){
        nameToSymbolMap.put(SchemaConstants.EQ, DialectConstants.SYMBOL_EQ);
        nameToSymbolMap.put(SchemaConstants.NEQ, DialectConstants.SYMBOL_NEQ);
        nameToSymbolMap.put(SchemaConstants.LT, DialectConstants.SYMBOL_LT);
        nameToSymbolMap.put(SchemaConstants.GT, DialectConstants.SYMBOL_GT);
        nameToSymbolMap.put(SchemaConstants.GTE, DialectConstants.SYMBOL_GTE);
        nameToSymbolMap.put(SchemaConstants.LTE, DialectConstants.SYMBOL_LTE);
        nameToSymbolMap.put(SchemaConstants.IN, DialectConstants.SYMBOL_IN);
        nameToSymbolMap.put(SchemaConstants.NOTIN, DialectConstants.SYMBOL_NOTIN);
        nameToSymbolMap.put(SchemaConstants.BETWEEN, DialectConstants.SYMBOL_BETWEEN);
        nameToSymbolMap.put(SchemaConstants.LIKE, DialectConstants.SYMBOL_LIKE);
        Map<String, String> updateEntry = getOperatorSymbolsMap();
        if(updateEntry!=null)
            nameToSymbolMap.putAll(updateEntry);
    }
    public String getOperatorSymbol(String operatorName){
        return nameToSymbolMap.getOrDefault(operatorName, null);
    };
}
