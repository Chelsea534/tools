package com.powerapps.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kollect.etl.util.FileUtils;

public class Crawler {
  
  private static final Logger LOG = LoggerFactory.getLogger(Crawler.class);
  public static final List<String> FILE_LIST = new ArrayList<>();
  public static String ROOT_PATH;
  
  public Crawler(String rootPath) {
    ROOT_PATH = rootPath;
  }
  
  
  public Matcher matcher(final String candidate, final String pattern) {
    Pattern p = Pattern.compile(pattern);
    Matcher m = p.matcher(candidate);
    return m;
  }
  
  
  void writeToFile(File f) {
    new FileUtils().writeListToFile(f, FILE_LIST, true);
  }
  
  
  public void crawlPages(String fileName) {
    File f = new File(fileName);
    try(BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream(f)))) {
      String line;
      while((line = r.readLine()) != null) {
        Matcher m = matcher(line, "[a-zA-Z\\-\\_]+(.frm|.tbl)");
        boolean matches = m.find();
        if(matches) {
          String group  = m.group(0);
          if(!FILE_LIST.contains(group)) {
            FILE_LIST.add(group);
            crawlPages(ROOT_PATH + group);
            
          }

        }
      }
    } catch(Exception e) {
      e.printStackTrace();
    }
  }
  
  public static void main(String args[]) {
    final String rootPath = args[0];
    final String rootPage = args[1];
    final String crawlOut = args[2];
    Crawler crawler = new Crawler(rootPath);
    crawler.crawlPages(rootPath + rootPage);
    crawler.writeToFile(new File(crawlOut));
    for(String msg : FILE_LIST) {
      LOG.info(msg);
    }
  }

}
