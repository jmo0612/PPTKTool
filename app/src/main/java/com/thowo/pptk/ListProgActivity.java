package com.thowo.pptk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.thowo.jmframework.JmoFunctions;
import com.thowo.jmframework.component.JMActivity;
import com.thowo.jmframework.component.JMListAdapter;
import com.thowo.jmframework.db.ResultView;
import com.thowo.jmframework.db.jmoRowObject;

import java.util.List;

public class ListProgActivity extends JMActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_prog,R.string.list_prog_title,false);
        init();
    }

    private void init(){
        ResultView r=JmoFunctions.getCurrentConnection().queryLocal("select * from tb_prog");
        if(r.getResult()!=null){
            List<jmoRowObject> rObjs=r.toList();
            JmoFunctions.trace(rObjs.size());
            ListView lvListProg=findViewById(R.id.lvListProg);
            lvListProg.setAdapter(new JMListAdapter(lvListProg.getContext(),R.layout.list_view_list_prog,rObjs,""));
        }

    }
}
