package com.thowo.pptk;

import com.thowo.jmframework.db.jmoConnection;

import java.io.File;

public class Global {
    public static  String CONFIG_PATH="";

    private static File DBFile;
    public static int tahun;

    public static File getDBFile(){
        return DBFile;
    }

    public static void setDBFile(File dbFile){
        DBFile=dbFile;
    }
}
