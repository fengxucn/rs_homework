package com.rs.utils;

import sun.jvm.hotspot.types.WrongTypeException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class Configs {
    public static final String SMALL_FILES = "./small_file";
    public static final String SMALL_FILE_NAME_PREFIX = SMALL_FILES + "/tmp_";
    public static final String MAP_RESULT = "./map/";
    public static final String FINAL_RESULT = "./result/";

    private static Properties prop = null;
    public static void loadProps(String path){
        if(prop == null) {
            prop = new Properties();
            try {
                InputStream input = new FileInputStream(path);
                // load a properties file
                prop.load(input);

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static long MAX_SIZE_EACH_TASK(){
        String size = prop.getProperty("max_size_each_task", "64k");
        int value = Integer.parseInt(size.substring(0, size.length()-1));
        if(size.endsWith("k") || size.endsWith("K")){
            return value * 1024;
        }
        else if(size.endsWith("m") || size.endsWith("M")){
            return value * 1024 *1024;
        }
        else if(size.endsWith("g") || size.endsWith("G")){
            return value * 1024 * 1024 * 1024;
        }
        throw new WrongTypeException("the size should end with k K m M or g G");
    }

    public static Set<String> getStopWords(){
        Set<String> sws = new HashSet<String>();
        for(String word : prop.getProperty("stop_words").split(",")){
            sws.add(word.trim());
        }
        return  sws;
    }

    public static boolean needEscapeStopWords(){
        return Boolean.parseBoolean(prop.getProperty("escape_stop_words", "true"));
    }

    public static Set<String> getUrls(){
        return new HashSet<String>(Arrays.asList(prop.getProperty("urls").split(",")));
    }

    public static int getTopN(){
        return Integer.parseInt(prop.getProperty("topN", "5"));
    }

    public static int getExecutorNumber(){
        return Integer.parseInt(prop.getProperty("executor_number", "5"));
    }
}
