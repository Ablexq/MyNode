package com.example.mynode;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = MainActivity.class.getSimpleName();
    private String[] data1 = new String[]{"A1", "A2"};
    private String[] data2 = new String[]{"B1", "B2"};
    private String[] data31 = new String[]{"C1"};
    private String[] data32 = new String[]{"C1", "C2"};
    private String[] data33 = new String[]{"C1", "C2", "C3"};
    private String[][] total1 = {data1, data2, data31};
    private String[][] total2 = {data1, data2, data32};
    private String[][] total3 = {data1, data2, data33};
    private CustomViewGroup customViewGroup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        customViewGroup = (CustomViewGroup) this.findViewById(R.id.cvg);
        customViewGroup.setTotalList(total1);
        customViewGroup.setClickListener(new CustomViewGroup.OnClickNodeListener() {
            @Override
            public void onClickNode(int postion) {
                Toast.makeText(MainActivity.this,
                         "第" + postion + "个childView",
                        Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                customViewGroup.setTotalList(total1);
                break;
            case R.id.btn2:
                customViewGroup.setTotalList(total2);
                break;
            case R.id.btn3:
                customViewGroup.setTotalList(total3);
                break;
        }
    }
}
