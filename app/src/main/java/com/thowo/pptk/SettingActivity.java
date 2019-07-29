package com.thowo.pptk;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.thowo.jmframework.JmoFunctions;
import com.thowo.jmframework.component.JMActivity;
import com.thowo.jmframework.component.JMButton;
import com.thowo.jmframework.component.JMEditText;
import com.thowo.jmframework.component.JMFilePicker;
import com.thowo.jmframework.component.JMFilePickerListener;

import java.io.File;
import java.util.Calendar;

import static com.thowo.jmframework.JmoFunctions.createFile;
import static com.thowo.jmframework.JmoFunctions.fileExist;
import static com.thowo.jmframework.JmoFunctions.readTxtFile;
import static com.thowo.jmframework.JmoFunctions.toast;
import static com.thowo.jmframework.JmoFunctions.trace;
import static com.thowo.jmframework.JmoFunctions.writeTxtFile;

public class SettingActivity extends JMActivity{

    private File dbFileSelected;
    private Menu myMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting,false);

        init();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        myMenu=menu;
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.setting,myMenu);
        myMenu.findItem(R.id.menuItemOKSetting).setVisible(false);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==R.id.menuItemOKSetting){
            setConfig(dbFileSelected);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onResume() {
        super.onResume();
        init();
    }


    private void init(){
        JmoFunctions.displayTitle("Pengaturan");

        JMEditText edtTA=findViewById(R.id.edtTASet);
        edtTA.setText(String.valueOf(getTA()));

        edtTA.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(myMenu==null)return;
                myMenu.findItem(R.id.menuItemOKSetting).setVisible(getTA()!=Integer.parseInt(String.valueOf(s)));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        final JMEditText edtDB=findViewById(R.id.edtDBSet);
        if(Global.getDBFile()!=null){
            dbFileSelected=Global.getDBFile();
            edtDB.setText(String.valueOf(dbFileSelected.getName()));
        }



        JMButton btnOpenSet=findViewById(R.id.btnOpenSet);
        btnOpenSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SettingActivity me= (SettingActivity) JmoFunctions.getCurrentActivity();
                new JMFilePicker(me, "db", new JMFilePickerListener() {
                    @Override
                    public void onPicked(File chosen) {
                        Global.setDBFile(chosen);
                        edtDB.setText(chosen.getName());
                        setChosen(chosen);
                    }

                    @Override
                    public void onCancel() {

                    }
                });
            }
        });
    }

    private void setChosen(File db){
        dbFileSelected=db;
        if(myMenu==null)return;
        myMenu.findItem(R.id.menuItemOKSetting).setVisible(!Global.getDBFile().getAbsoluteFile().equals(dbFileSelected.getAbsolutePath()));
    }

    private void setConfig(File db){
        if(db==null){
            toast("Database tidak ada");
            return;
        }
        JMEditText edtTA=findViewById(R.id.edtTASet);
        writeTxtFile(Global.CONFIG_PATH,"DB",db.getAbsolutePath());
        writeTxtFile(Global.CONFIG_PATH,"TA", String.valueOf(edtTA.getText()));
        trace(db.getAbsoluteFile());
    }

    private int getTA(){
        //TextView txtDb= findViewById(R.id.txtDB);
        File config= new File(Global.CONFIG_PATH);
        trace(config.getAbsoluteFile());
        if(!fileExist(config)){
            if(!createFile(config)){
                Toast.makeText(getApplicationContext(),"Error creating config file",Toast.LENGTH_LONG).show();
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
        }

        if(ta<=0)return 0;


        return ta;
    }

}
