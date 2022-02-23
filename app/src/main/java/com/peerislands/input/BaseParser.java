package com.peerislands.input;

import com.peerislands.schema.SQLBase;

public interface BaseParser {
    public void parse(String content);
    public SQLBase build();
}
