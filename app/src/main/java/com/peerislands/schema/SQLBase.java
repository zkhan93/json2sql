package com.peerislands.schema;

import com.peerislands.dialect.Dialect;

public interface SQLBase {

    public String sql(Dialect dialect);
}
