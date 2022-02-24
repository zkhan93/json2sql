package com.peerislands;


public class App {
    
    public static void main(String[] args) throws Exception {
        // knows from where to get filename
        // also gets the type of file
        // based on the type of input call the correct parser
        
        String filename = args[0];
        String sql =  new SQLBuilder().read(filename).type("json").build();
        System.out.println(sql);
    }

}
