package com.thowo.pptk;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
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
        setContentView(R.layout.activity_setting,R.string.setting_title,false);

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
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onResume() {
        super.onResume();
        init();
    }

    @Override
    public void finish(){
        if(myMenu.findItem(R.id.menuItemOKSetting).isVisible()){
            super.confirmExit("Exit?");
            return;
        }
        super.finish();
    }




    private void init(){

        JMEditText edtTA=findViewById(R.id.edtTASet);
        edtTA.setText(String.valueOf(Global.getTahun()));

        edtTA.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setChanges(String.valueOf(s));
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
        JMEditText edtTA=findViewById(R.id.edtTASet);
        setChanges(String.valueOf(edtTA.getText()));
    }

    private void setConfig(File db){
        if(db==null){
            toast(R.string.msg_no_database);
            return;
        }
        JMEditText edtTA=findViewById(R.id.edtTASet);
        writeTxtFile(Global.CONFIG_PATH,"DB",db.getAbsolutePath());
        writeTxtFile(Global.CONFIG_PATH,"TA", String.valueOf(edtTA.getText()));
        trace(db.getAbsoluteFile());

        Global.setDBFile(dbFileSelected);
        Global.setTahun(Integer.parseInt(String.valueOf(edtTA.getText())));
        setChanges(String.valueOf(edtTA.getText()));
        toast(R.string.msg_config_saved);
    }

    private void setChanges(String taInput){
        if(myMenu==null)return;
        String curDBStr="";
        if(Global.getDBFile()!=null)curDBStr=Global.getDBFile().getAbsolutePath();
        String myDBStr="";
        if(dbFileSelected!=null)myDBStr=dbFileSelected.getAbsolutePath();
        myMenu.findItem(R.id.menuItemOKSetting).setVisible(!((Global.getTahun()==Integer.parseInt(String.valueOf(taInput))) && (curDBStr.equals(myDBStr))));
        JmoFunctions.trace("curDBStr = "+ curDBStr);
        JmoFunctions.trace("myDBStr = "+ myDBStr);
    }

}
