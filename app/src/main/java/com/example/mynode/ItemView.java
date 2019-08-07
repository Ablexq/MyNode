package com.example.mynode;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemView extends ViewGroup {

    private ImageView img1;
    private ImageView img2;
    private ImageView img3;
    private TextView textView;
    private int type;

    public ItemView(Context context) {
        this(context, null);
    }

    public ItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View view = LayoutInflater.from(context).inflate(R.layout.item_node, this);
        textView = ((TextView) view.findViewById(R.id.tv));
        img1 = ((ImageView) view.findViewById(R.id.iv1));
        img2 = ((ImageView) view.findViewById(R.id.iv2));
        img3 = ((ImageView) view.findViewById(R.id.iv3));

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
