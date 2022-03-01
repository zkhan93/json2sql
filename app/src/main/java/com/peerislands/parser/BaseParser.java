package com.peerislands.parser;

import com.peerislands.parser.error.InvalidInputException;
import com.peerislands.schema.SQLBase;

public interface BaseParser<T, D extends SQLBase> {
    abstract public D parse(T content) throws InvalidInputException;
}
