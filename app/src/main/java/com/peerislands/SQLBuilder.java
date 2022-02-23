package com.peerislands;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.peerislands.input.BaseParser;
import com.peerislands.input.JSONparser;
import com.peerislands.schema.SQLBase;

public class SQLBuilder {
    private String rawContent;
    
    private BaseParser parser;

    public SQLBuilder from(String inputFormat){
        if(inputFormat.trim().toLowerCase().equals("json")){
            this.parser = new JSONparser();
        }
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
        if(this.rawContent.length() > 0)
            this.parser.parse(this.rawContent);
        return this;
    }

    
    public String build(){
        SQLBase sql = this.parser.build();
        return sql.sql();
    }
}
