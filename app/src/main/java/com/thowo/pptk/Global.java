package com.thowo.pptk;

import android.widget.Toast;

import com.thowo.jmframework.JmoFunctions;
import com.thowo.jmframework.db.jmoConnection;

import java.io.File;
import java.util.Calendar;

import static com.thowo.jmframework.JmoFunctions.createFile;
import static com.thowo.jmframework.JmoFunctions.fileExist;
import static com.thowo.jmframework.JmoFunctions.readTxtFile;
import static com.thowo.jmframework.JmoFunctions.toast;
import static com.thowo.jmframework.JmoFunctions.trace;
import static com.thowo.jmframework.JmoFunctions.writeTxtFile;

public class Global {
    public static  String CONFIG_PATH="";

    private static File DBFile;
    private static int tahun;

    public static File getDBFile(){
        return DBFile;
    }

    public static void setDBFile(File dbFile){
        DBFile=dbFile;
    }

    public static void setTahun(int thn){
        tahun=thn;
    }

    public static int getTahun(){
        return tahun;
    }

    public static int getTAFromConfig(){
        //TextView txtDb= findViewById(R.id.txtDB);
        File config= new File(Global.CONFIG_PATH);
        trace(config.getAbsoluteFile());
        if(!fileExist(config)){
            if(!createFile(config)){
                toast(R.string.err_create_config_file_msg);
                return 0;
            }
            //return null;
        }else{
            trace("so ada");
        }


        String curTA=readTxtFile(config,"TA");
        trace("CURTA: "+curTA);
        int ta=0;
        if(!curTA.equals("")){
            ta= Integer.parseInt(curTA);
        }

        if(ta<=0){
            ta= (int) JmoFunctions.now(Calendar.YEAR);
            if(ta<=0)return 0;
            writeTxtFile(Global.CONFIG_PATH,"TA", String.valueOf(ta));
        }


        return ta;
    }

    public static File getDBFromConfig(){
        //TextView txtDb= findViewById(R.id.txtDB);
        File config= new File(Global.CONFIG_PATH);
        trace(config.getAbsoluteFile());
        if(!fileExist(config)){
            if(!createFile(config)){
                toast(R.string.err_create_config_file_msg);
                return null;
            }
            //return null;
        }else{
            trace("so ada");
        }


        String curDB=readTxtFile(config,"DB");
        trace("CURDB: "+curDB);
        if(curDB.equals("")){
            return null;
        }
        File db=new File(curDB);
        if(!fileExist(db)) return null;

        //txtDb.setText(db.getName());
        //Button btnMsk=findViewById(R.id.btnMasuk);
        //btnMsk.setEnabled(true);



        return db;
    }
}
