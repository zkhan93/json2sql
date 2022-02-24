package com.peerislands.parser;

import com.peerislands.parser.error.InvalidInputException;
import com.peerislands.schema.SQLBase;

public interface BaseParser<T> {
    public SQLBase parse(T content) throws InvalidInputException;
}
