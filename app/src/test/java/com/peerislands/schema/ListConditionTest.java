package com.peerislands.schema;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import com.peerislands.dialect.Dialect;
import com.peerislands.dialect.DialectFactory;
import com.peerislands.dialect.error.InvalidDialectException;
import com.peerislands.schema.error.InvalidOperatorException;
import com.peerislands.schema.error.InvalidValueException;

public class ListConditionTest {
    Dialect mysqlDialect;
    ArrayList<Object> names;
    Column col;
    Operator op;

    @BeforeEach
    public void setUp() {
        try {
            col = new Column("name");
            op = new Operator("in");
            mysqlDialect = DialectFactory.get("mysql");
        } catch (InvalidDialectException | InvalidOperatorException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void parseString() {
        try {
            names = new ArrayList<>();
            names.add("Apple");
            names.add("Mango");

            ListCondition condition = new ListCondition(col, op, names);
            String sql = condition.sql(mysqlDialect);
            assertEquals(sql, "name IN (\"Apple\", \"Mango\")");
        } catch (InvalidValueException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void parseInt() {
        try {
            names = new ArrayList<>();
            names.add(10);
            names.add(35);

            ListCondition condition = new ListCondition(col, op, names);
            String sql = condition.sql(mysqlDialect);
            assertEquals(sql, "name IN (10, 35)");
        } catch (InvalidValueException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void parseInvalidValue() {

        names = new ArrayList<>();
        names.add(new Error("not a SQLBase or any primitive data type object"));
        assertThrows(
                InvalidValueException.class, () -> {
                    new ListCondition(col, op, names).sql(mysqlDialect);
                });
    }
}
