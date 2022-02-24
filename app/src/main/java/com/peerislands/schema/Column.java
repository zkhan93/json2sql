package com.peerislands.schema;

public class Column implements SQLBase {
    String table;
    String name;

    public Column(String name) {
        this.name = name;
        this.table = null;
    }

    public Column(String table, String name) {
        this.table = table;
        this.name = name;
    }

    @Override
    public String sql() {
        return table == null ? name : table + "." + name;
    }

}
