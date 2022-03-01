package com.peerislands.parser.error;

import org.json.JSONException;

public class InvalidInputException extends JSONException{

    public InvalidInputException(String msg) {
        super(msg);
    }
    
}
