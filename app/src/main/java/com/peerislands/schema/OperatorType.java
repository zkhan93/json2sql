package com.peerislands.schema;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.peerislands.schema.error.InvalidOperatorException;

public abstract class OperatorType {

    private static HashMap<String, String> nameToSymbolMap = new HashMap<>();

    private static HashMap<String, List<Class<?>>> symbolToValidTypesMap = new HashMap<>();
    private static List<Class<?>> intStringDecimal = Arrays.asList(Integer.class, String.class, BigDecimal.class);
    static {
        nameToSymbolMap.put(Constants.EQ_NAME, Constants.EQ);
        nameToSymbolMap.put(Constants.NEQ_NAME, Constants.NEQ);
        nameToSymbolMap.put(Constants.LT_NAME, Constants.LT);
        nameToSymbolMap.put(Constants.GT_NAME, Constants.GT);
        nameToSymbolMap.put(Constants.GTE_NAME, Constants.GTE);
        nameToSymbolMap.put(Constants.LTE_NAME, Constants.LTE);
        nameToSymbolMap.put(Constants.IN_NAME, Constants.IN);
        nameToSymbolMap.put(Constants.NOTIN_NAME, Constants.NOTIN);
        nameToSymbolMap.put(Constants.BETWEEN_NAME, Constants.BETWEEN);
        nameToSymbolMap.put(Constants.LIKE_NAME, Constants.LIKE);


        symbolToValidTypesMap.put(Constants.EQ, intStringDecimal);
        symbolToValidTypesMap.put(Constants.NEQ, intStringDecimal);
        symbolToValidTypesMap.put(Constants.GTE, intStringDecimal);
        symbolToValidTypesMap.put(Constants.LTE, intStringDecimal);
        symbolToValidTypesMap.put(Constants.GT, intStringDecimal);
        symbolToValidTypesMap.put(Constants.LT, intStringDecimal);
        symbolToValidTypesMap.put(Constants.LIKE, Arrays.asList(String.class));
        symbolToValidTypesMap.put(Constants.BETWEEN, Arrays.asList(ArrayList.class));
        symbolToValidTypesMap.put(Constants.NOTIN, Arrays.asList(ArrayList.class));
        symbolToValidTypesMap.put(Constants.IN, Arrays.asList(ArrayList.class));
    }

    public static String getSymbol(String name) throws InvalidOperatorException{
        String cleaned = name.trim().toLowerCase();
        String symbol = nameToSymbolMap.getOrDefault(cleaned, null);
        if (symbol == null){
            throw new InvalidOperatorException(name);
        }
        return symbol;
    }

    public static List<Class<?>> getValidTypes(String symbol){
        return symbolToValidTypesMap.getOrDefault(symbol, new ArrayList<>());
    }

    
}
