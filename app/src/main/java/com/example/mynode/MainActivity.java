package com.example.mynode;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.example.mynode.view.ImageInfo;
import com.example.mynode.view.MyNodeView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    private String[] data1 = new String[]{"A1", "A2"};
    private String[] data2 = new String[]{"B1", "B2"};
    private String[] data31 = new String[]{"C1"};
    private String[] data32 = new String[]{"C1", "C2"};
    private String[] data33 = new String[]{"C1", "C2", "C3"};
    private String[][] total = {data1, data2, data31};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CustomViewGroup customViewGroup = (CustomViewGroup) this.findViewById(R.id.cvg);
        customViewGroup.setTotalList(total);

//        int screenWidth = this.getResources().getDisplayMetrics().widthPixels;
//        MyNodeView MyNodeView = (MyNodeView) findViewById(R.id.imagesviewsgroup);
//        List<ImageInfo> list = new ArrayList<>();
//        list.add(new ImageInfo(screenWidth / 2,
//                (int) (screenWidth / ((float) 1200 / (float) 1600)),
//                "http://img.ugc.goldenmob.com/rozbuzzpro/a1bfdefecd6b4bb98514fda598659fd6.jpg"));
//        list.add(new ImageInfo(screenWidth / 3,
//                (int) (screenWidth / ((float) 2448 / (float) 3264)),
//                "http://img.ugc.goldenmob.com/rozbuzzpro/80b62a65ccc542ce8d25a0d4d1978a1b.jpg"));
//        list.add(new ImageInfo(screenWidth / 4,
//                (int) (screenWidth / ((float) 1200 / (float) 1600)),
//                "http://img.ugc.goldenmob.com/rozbuzzpro/3946b9fc44b846749d9096c2e49ea12e.jpg"));
//        list.add(new ImageInfo(screenWidth / 5,
//                (int) (screenWidth / ((float) 1152 / (float) 1536)),
//                "http://img.ugc.goldenmob.com/rozbuzzpro/80b62a65ccc542ce8d25a0d4d1978a1b.jpg"));
//
//        MyNodeView.setImageInfoList(list);
//        MyNodeView.setClickListener(new MyNodeView.ClickListener() {
//            @Override
//            public void imageClick(int width, int height, String imageUrl, int pos) {
//                Log.e(TAG, "pos===================:" + pos);
//            }
//        });
    }
}
