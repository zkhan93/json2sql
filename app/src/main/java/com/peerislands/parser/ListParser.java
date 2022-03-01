package com.peerislands.parser;

import java.util.List;

import com.peerislands.parser.error.InvalidInputException;
import com.peerislands.schema.SQLBase;

public interface ListParser<T, D extends SQLBase> {
    abstract public List<D> parseMany(T content) throws InvalidInputException;
}
