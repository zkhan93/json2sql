package com.peerislands;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.peerislands.parser.error.InvalidInputException;
import com.peerislands.parser.json.SelectParser;
import com.peerislands.schema.SelectQuery;

import org.json.JSONException;
import org.json.JSONObject;

public class SQLBuilder {
    private String rawContent;
    private String type;

    public SQLBuilder type(String inputFormat){
        this.type = inputFormat.trim().toLowerCase();
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
                JSONObject root = new JSONObject(this.rawContent);
                SelectQuery select = new SelectParser().parse(root);
                res = select.sql();
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
