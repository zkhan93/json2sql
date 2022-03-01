package com.peerislands.schema;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;

import com.peerislands.dialect.Dialect;
import com.peerislands.dialect.DialectFactory;
import com.peerislands.dialect.error.InvalidDialectException;
import com.peerislands.schema.error.InvalidConditionJoinOpException;
import com.peerislands.schema.error.InvalidJoinTypeException;
import com.peerislands.schema.error.InvalidOperatorException;
import com.peerislands.schema.error.InvalidValueException;

public class SelectQueryTest {

    Dialect mysqlDialect;
    String table;
    List<Column> columns;
    Column column;
    Operator equalOperator;

    @BeforeEach
    public void setUp() {

        column = new Column("name");
        columns = Arrays.asList(column, new Column("age"));
        table = "test";
        try {
            equalOperator = new Operator("equal");
            mysqlDialect = DialectFactory.get("mysql");
        } catch (InvalidDialectException | InvalidOperatorException e) {
            e.printStackTrace();
        }
    }

    @Test
    void simpleSelect() {
        SelectQuery query = new SelectQuery(table, columns, null, null);
        String sql = query.sql(mysqlDialect);
        assertEquals(sql, "SELECT name, age FROM test");
    }

    @Test
    void selectWithWhere() {
        String sql;

        try {
            ConditionWrapper where = new ConditionWrapper(new SimpleCondition(column, equalOperator, 12));
            SelectQuery query = new SelectQuery(table, columns, null, where);
            sql = query.sql(mysqlDialect);
            assertEquals(sql, "SELECT name, age FROM test WHERE name = 12");
        } catch (InvalidValueException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void selectWithJoin() {
        String table2 = "table2";
        Column fk = new Column(table2, "fk");
        Column idColumn = new Column(table, "pk");
        ConditionWrapper joinCondition;
        try {
            joinCondition = new ConditionWrapper(new JoinCondition(idColumn, equalOperator, fk));
            Join join = new Join("inner", table2, joinCondition);
            SelectQuery query = new SelectQuery(table, columns, Arrays.asList(join), null);
            String sql = query.sql(mysqlDialect);
            assertEquals(sql, "SELECT name, age FROM test \nINNER JOIN table2 ON test.pk = table2.fk");
        } catch (InvalidJoinTypeException | InvalidConditionJoinOpException | InvalidValueException e) {
            fail(e.getMessage());
        }
    }
}
