package com.peerislands.parser.json;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONException;
import org.json.JSONObject;

public class SelectParserTest {

    @Test
    void parseValid() {
        String json = "{\"table\": \"employee\"}";
        assertDoesNotThrow(() -> {
            new SelectParser().parse(new JSONObject(json));
        });
    }

    @Test
    void parseInvalid() {
        String json = "{\"tabl\": \"employee\"}";
        assertThrows(JSONException.class, () -> {
            new SelectParser().parse(new JSONObject(json));
        });
    }
}
