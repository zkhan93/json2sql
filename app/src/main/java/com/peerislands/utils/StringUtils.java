package com.peerislands.utils;

import java.util.List;

public final class StringUtils {

    public static String join(String[] strings, String separator){
        StringBuffer strb = new StringBuffer();

        for(int i=0; i < strings.length; i++){
            strb.append(strings[i]);
            if(i < strings.length - 1)
                strb.append(separator);
        }
        return strb.toString();
    }

    public static String join(List<String> strings, String separator){
        return join(strings.toArray(new String[0]), separator);
    }

    public static <T> String sqlfy(T value){
        return value!=null ? "\"" + value.toString() + "\"" : "";
    }
    
}
