package com.peerislands;


public class App {
    
    public static void main(String[] args) throws Exception {
        // argument parsing can be improved
        String filename = args[0];
        String dialect = args[1];
        try{
            new App().start(filename, dialect);
        }
        catch(Exception e){
            System.err.println("Failed to convert to SQL");
            e.printStackTrace();
        }
    }

    public void start(String filename, String dialect) throws Exception{
        String sql =  new SQLBuilder().read(filename).type("json").dialect(dialect).build();
        System.out.println(sql);
    }

}
