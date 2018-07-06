package com.powerapps.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Crawler {
  
  public static final List<String> files = new ArrayList<>();
  public static final String rootPath = "/media/joshua/martian/ptrworkspace/mbsb_dev/clientconfig/";
  
  
  public Matcher matcher(final String candidate, final String pattern) {
    Pattern p = Pattern.compile(pattern);
    Matcher m = p.matcher(candidate);
    //return m.find();
    return m;
  }
  
  
  public void crawlPages(String fileName) {
    File f = new File(fileName);
    try(BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream(f)))) {
      String line;
      while((line = r.readLine()) != null) {
        //Matcher m = matcher(line, "[a-zA-Z\\-\\_]+(.frm|.tbl)");
        Matcher m = matcher(line, "[a-zA-Z\\-\\_]+(.frm|.tbl)");
        boolean matches = m.find();
        if(matches) {
          String group  = m.group(0);
          crawlPages(rootPath + group);
          files.add(group);
        }
      }
    } catch(Exception e) {
      e.printStackTrace();
    }
  }
  
  public static void main(String args[]) {
    new Crawler().crawlPages(rootPath + "wlm-main.frm");
    //System.out.println(files.toString());
    
    for(String x : files) {
      System.out.println(x);
    }
  }

}
