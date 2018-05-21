/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication6;

import java.io.*;
import java.text.MessageFormat;

/**
 *
 * @author Chelsea
 */
public class fileReading {
    
    static final String TEMPLATE = " <column name=\"{0}\" visible=\"true\">\n" +
"   <data>{0}</data>\n" +
"   <header/>\n" +
"   <label>{1}</label>\n" +
"   <width>150</width>\n" +
"   <datatype>{2}</datatype>\n" +
"  </column>";

   public final static String DELIMITER="\\,";
    public static void main(String[] args) throws IOException{
        //Reader fileReader = new FileReader("D:\\University\\Intern\\week2\\day1\\mappingfiles\\deb_master_all.csv");
        
        File f = new File ("D:\\University\\Intern\\week3\\Accounts_Table.csv");
        BufferedReader bF = new BufferedReader (new FileReader(f));
        //System.out.println("Loaded successfully");
       StringBuilder sB = new StringBuilder();
        try{
          String read="";
          while((read = bF.readLine()) != null){
              
            String[] columns = read.split(DELIMITER);
            
            String c1 = columns[0];
            String c2 = columns[1].trim();
            String c3 = columns[2].toLowerCase();
            
            String dataType="";
            if(c3.equals("date")){
                dataType = "java.util.Date";
            }
            else if(c3.equals("string")){
                dataType = "java.lang.String";
            }
            else if(c3.equals("currency")){
                dataType = "java.lang.Double";
            }
            else if(c3.equals("integer")){
                dataType = "java.lang.Integer";
            }
            
            String tag = MessageFormat.format(TEMPLATE, new String[]{c2,c1,dataType});
            
            //store all lines into the string buffer
            sB.append(tag+"/n");
            
            //System.out.println(tag);
            
                    }
          long epoch = System.currentTimeMillis();
          String fileName = "table_"+ epoch +".xml";
          FileWriter fW = new FileWriter(new File("D:\\University\\Intern\\week3\\"+fileName));
          fW.write(sB.toString());
          System.out.println("File "+fileName+" written successfully");
          
        }
        catch (IOException e){
        e.printStackTrace();
        }
        }
}
