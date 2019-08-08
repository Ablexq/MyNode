package com.example.mynode;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = MainActivity.class.getSimpleName();
    private String[] data1 = new String[]{"A1", "A2"};
    private String[] data2 = new String[]{"B1", "B2"};
    private String[] data31 = new String[]{"C1"};
    private String[] data32 = new String[]{"C1", "C2"};
    private String[] data33 = new String[]{"C1", "C2", "C3"};
    private String[] data34 = new String[]{"C1", "C2", "C3", "C4"};
    private String[] data35 = new String[]{"C1", "C2", "C3", "C4", "C5"};
    private String[] data36 = new String[]{"C1", "C2", "C3", "C4", "C5", "C6"};
    private String[] data37 = new String[]{"C1", "C2", "C3", "C4", "C5", "C6", "C7"};
    private String[] data38 = new String[]{"C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8"};
    private String[] data39 = new String[]{"C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9"};
    private String[] data40 = new String[]{"C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9", "C10"};
    private String[][] total1 = {data1, data2, data31};
    private String[][] total2 = {data1, data2, data32};
    private String[][] total3 = {data1, data2, data33};
    private String[][] total4 = {data1, data2, data34};
    private String[][] total5 = {data1, data2, data35};
    private CustomViewGroup customViewGroup;
    private String[][] total10 = new String[][]{data1, data2, data40};

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
        findViewById(R.id.btn4).setOnClickListener(this);
        findViewById(R.id.btn5).setOnClickListener(this);
        findViewById(R.id.btn_).setOnClickListener(this);
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
            case R.id.btn4:
                customViewGroup.setTotalList(total4);
                break;
            case R.id.btn5:
                customViewGroup.setTotalList(total5);
                break;
            case R.id.btn_:
                customViewGroup.setTotalList(total10);
                break;
        }
    }
}
