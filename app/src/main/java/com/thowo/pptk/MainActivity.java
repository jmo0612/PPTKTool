package com.thowo.pptk;

//import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.content.ContextWrapper;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.thowo.jmframework.JmoFunctions;
import com.thowo.jmframework.component.JMActivity;
import com.thowo.jmframework.component.JMButton;
import com.obsez.android.lib.filechooser.*;
import com.thowo.jmframework.component.JMFilePicker;
import com.thowo.jmframework.component.JMFilePickerListener;
import com.thowo.jmframework.component.JMVerticalButton;
import com.thowo.jmframework.db.ResultView;
import com.thowo.jmframework.db.jmoConnection;

import java.io.File;

import static com.thowo.jmframework.JmoFunctions.createFile;
import static com.thowo.jmframework.JmoFunctions.fileExist;
import static com.thowo.jmframework.JmoFunctions.getCurrentConnection;
import static com.thowo.jmframework.JmoFunctions.readTxtFile;
import static com.thowo.jmframework.JmoFunctions.showActivity;
import static com.thowo.jmframework.JmoFunctions.toast;
import static com.thowo.jmframework.JmoFunctions.trace;
import static com.thowo.jmframework.JmoFunctions.writeTxtFile;

public class MainActivity extends JMActivity {

    private static final int BROWSE_DB_REQUEST_CODE = 0;


    private JMVerticalButton btnRealMenu;
    private JMVerticalButton btnTagihMenu;
    private JMVerticalButton btnAngMenu;
    private JMVerticalButton btnSetMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main,false);

        init();

    }
    @Override
    public void onResume() {

        super.onResume();
        init();
    }

    private void init(){
        JmoFunctions.displayTitle("Main Menu");

        ContextWrapper c = new ContextWrapper(this);
        Global.CONFIG_PATH= c.getFilesDir().getAbsolutePath() +"/config.ini";

        Global.setDBFile(getDB());


        btnRealMenu=findViewById(R.id.btnRealMenu);
        btnTagihMenu=findViewById(R.id.btnTagihMenu);
        btnAngMenu=findViewById(R.id.btnAngMenu);
        btnSetMenu=findViewById(R.id.btnSetMenu);

        btnRealMenu.lock();
        btnTagihMenu.lock();
        btnAngMenu.lock();





        btnRealMenu.setMyOnClickedListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnSetMenu.setMyOnClickedListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getCurrentConnection(new jmoConnection(myDB));
                showActivity(SettingActivity.class);
            }
        });




        /*btnTagihMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MainActivity me= (MainActivity) JmoFunctions.getCurrentActivity();
                new JMFilePicker(me, "db", new JMFilePickerListener() {
                    @Override
                    public void onPicked(File chosen) {
                        me.setDB(chosen);
                    }

                    @Override
                    public void onCancel() {

                    }
                });
            }
        });*/

        validateDB();

    }

    private File getDB(){
        //TextView txtDb= findViewById(R.id.txtDB);
        File config= new File(Global.CONFIG_PATH);
        trace(config.getAbsoluteFile());
        if(!fileExist(config)){
            if(!createFile(config)){
                Toast.makeText(getApplicationContext(),"Error creating config file",Toast.LENGTH_LONG).show();
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



    private void validateDB(){
        if(Global.getDBFile()==null) return;
        ResultView r=getCurrentConnection(new jmoConnection(Global.getDBFile())).queryLocal("select * from tb_prog");
        if(r==null)return;
        if(r.getResult()==null){
            toast("Database tidak benar");
            return;
        }

        btnAngMenu.unlock();
        btnRealMenu.unlock();
        btnTagihMenu.unlock();
    }


}
