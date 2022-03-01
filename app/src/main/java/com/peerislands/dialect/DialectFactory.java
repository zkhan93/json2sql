package com.peerislands.dialect;

import com.peerislands.dialect.error.InvalidDialectException;

public class DialectFactory {
    public static Dialect get(String name) throws InvalidDialectException{
        name = name.trim().toLowerCase();
        Dialect dialect = null;
        if(name.equals("mysql")){
            dialect = new MySQLDialect();
        }
        else if(name.equals("postgresql")){
            dialect = new PostgreSQLDialect();
        }
        if(dialect == null)
            throw new InvalidDialectException(name);
        return dialect;
    }
}
