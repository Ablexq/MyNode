package com.example.mynode.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;

import java.util.List;

public class MyNodeView extends ViewGroup {

    private List<ImageInfo> imageInfoList;
    private Context context;
    public static final String TAG = MyNodeView.class.getSimpleName();

    public MyNodeView(Context context) {
        this(context, null);
    }

    public MyNodeView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public MyNodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.context = context;
        init();
    }

    private void init() {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int height = 0;

        for (int i = 0; i < imageInfoList.size(); i++) {
            height += (imageInfoList.get(i).height);
        }

        setMeasuredDimension(widthSize, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        if (imageInfoList == null) {
            return;
        }
        int left = 0;
        int top = t;
        int bottom = b;

        int size = imageInfoList.size();
        for (int i = 0; i < size; i++) {
            if (i == 0) {
                bottom = imageInfoList.get(i).height;
            } else {
                top = bottom;
                bottom = imageInfoList.get(i).height + top;
            }
            ImageView imageView = new ImageView(context);
//            LayoutParams param = new MarginLayoutParams(imageInfoList.get(i).width, imageInfoList.get(i).height);
//            addView(imageView, param);
            addView(imageView);
            String imageUrl = imageInfoList.get(i).imageUrl;
            Glide.with(context)
                    .load(imageUrl)
                    .fitCenter()
                    .into(imageView);
            imageView.layout(left, top, r, bottom);
            final int finalI = i;
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    String imageUrl = imageInfoList.get(finalI).imageUrl;
                    int width = imageInfoList.get(finalI).width;
                    int height = imageInfoList.get(finalI).height;
                    clickListener.imageClick(width, height, imageUrl, finalI);
                }
            });
            Log.d(TAG, "================:" + left + "  " + top + "  " + r + "   " + bottom);
        }
    }

    private void generateImageView(String imageUrl) {
        ImageView imageView = new ImageView(context);
    }

    public void setImageInfoList(List<ImageInfo> list) {
        this.imageInfoList = list;
    }


    private ClickListener clickListener;

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface ClickListener {
        void imageClick(int width, int height, String imageUrl, int pos);
    }
}
