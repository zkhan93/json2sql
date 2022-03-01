package com.peerislands;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.peerislands.dialect.Dialect;
import com.peerislands.dialect.DialectFactory;
import com.peerislands.dialect.error.InvalidDialectException;
import com.peerislands.parser.error.InvalidInputException;
import com.peerislands.parser.json.SelectParser;
import com.peerislands.schema.SelectQuery;

import org.json.JSONException;
import org.json.JSONObject;

public class SQLBuilder {
    private String rawContent;
    private String type;
    private Dialect dialect;

    public SQLBuilder type(String inputFormat){
        this.type = inputFormat.trim().toLowerCase();
        return this;
    }

    public SQLBuilder dialect(String dialect) throws InvalidDialectException{
        this.dialect = DialectFactory.get(dialect);
        return this;
    }
    
    public SQLBuilder read(String filename){
        StringBuilder strb = new StringBuilder();
        String line = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(filename)));
            do {
                line = br.readLine();
                if (line != null)
                    strb.append(line);
            } while (line != null);
        } catch (IOException e) {
            System.err.println(String.format("Unable to Open file {}", filename));
        }
        this.rawContent = strb.toString();
        return this;
    }

    
    public String build(){
        String res = "";
        if(type.equals("json")){
            try{
                JSONObject root = new JSONObject(rawContent);
                SelectQuery select = new SelectParser().parse(root);
                res = select.sql(dialect);
            }catch (JSONException e){
                System.err.println("Error parsing content to JSON");
                e.printStackTrace();
            }catch(InvalidInputException e){
                e.printStackTrace();
                System.err.println("Error converting to JSON to SQL");
            }
        }
        return res;
    }
}
