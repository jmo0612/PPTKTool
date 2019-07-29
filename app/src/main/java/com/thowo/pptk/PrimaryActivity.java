package com.thowo.pptk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.thowo.jmframework.JmoFunctions;
import com.thowo.jmframework.component.JMActivity;
import com.thowo.jmframework.db.ResultView;

import static com.thowo.jmframework.JmoFunctions.getCurrentConnection;

public class PrimaryActivity extends JMActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primary);
        init();
    }

    private void init(){
        TextView txtTrace=findViewById(R.id.txtTrace);
        txtTrace.setText(getCurrentConnection().queryLocal("select * from tb_prog where kd_prog=1").getResult().getString("ket_prog"));
    }
}
