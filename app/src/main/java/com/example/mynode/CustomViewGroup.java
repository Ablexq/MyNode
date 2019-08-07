package com.example.mynode;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomViewGroup extends ViewGroup {

    private String[] data1 = new String[]{"A1", "A2"};
    private String[] data2 = new String[]{"B1", "B2"};
    private String[] data31 = new String[]{"C1"};
    private String[] data32 = new String[]{"C1", "C2"};
    private String[] data33 = new String[]{"C1", "C2", "C3"};
    private String[][] total = {data1, data2, data31};
    private int leftMargin = 100;

    private Context context;
    private String[][] totalList = new String[][]{};
    private Paint paint;
    private ArrayList<View> arrayList0 = new ArrayList<>();
    private ArrayList<View> arrayList1 = new ArrayList<>();
    private ArrayList<View> arrayList2 = new ArrayList<>();

    public CustomViewGroup(Context context) {
        this(context, null);
    }

    public CustomViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public CustomViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
        System.out.println("===============构造==================");
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        System.out.println("===============onMeasure==================");

        //ViewGroup的属性
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        int atMost = MeasureSpec.AT_MOST;
        int exactly = MeasureSpec.EXACTLY;
        int unspecified = MeasureSpec.UNSPECIFIED;
        System.out.println("sizeWidth===========" + sizeWidth);
        System.out.println("sizeHeight==========" + sizeHeight);
        System.out.println("modeWidth===========" + modeWidth);
        System.out.println("modeHeight==========" + modeHeight);
        System.out.println("1atMost=========" + atMost);//-2147483648
        System.out.println("1exactly============" + exactly);//1073741824
        System.out.println("1unspecified===========" + unspecified);//0

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            System.out.println("前getMeasuredHeight==========" + child.getMeasuredHeight());
            System.out.println("前getMeasuredWidth===========" + child.getMeasuredWidth());
        }

        //测量child方式一
//        System.out.println("==============measureChildren===================");
//        measureChildren(widthMeasureSpec, heightMeasureSpec);

        //测量child方式二
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != View.GONE) {
                System.out.println("==============measureChild===================");
                System.out.println("后getMeasuredHeight==========" + child.getMeasuredHeight());
                System.out.println("后getMeasuredWidth===========" + child.getMeasuredWidth());
                measureChild(child, widthMeasureSpec, heightMeasureSpec);

                MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
                int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
                int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
            }
        }

        //必须
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //或者
//        setMeasuredDimension(
//                modeWidth == MeasureSpec.EXACTLY ? sizeWidth : childTotalWidth + getPaddingLeft() + getPaddingRight(),
//                modeHeight == MeasureSpec.EXACTLY ? sizeHeight : childTotalHeight + getPaddingTop() + getPaddingBottom()
//        );

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        System.out.println("===============onLayout==================");
        int childCount = getChildCount();
        System.out.println("childCount=========" + childCount);
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            int measuredWidth = child.getMeasuredWidth();
            int measuredHeight = child.getMeasuredHeight();
            System.out.println("width===============" + measuredWidth);
            System.out.println("height===============" + measuredHeight);
            switch (i) {
                case 0:
                    child.layout(100, 100, 100 + measuredWidth, 100 + measuredHeight);
                    break;
                case 1:
                    child.layout(400, 100, 400 + measuredWidth, 100 + measuredHeight);
                    break;
                case 2:
                    child.layout(50, 300, 50 + measuredWidth, 300 + measuredHeight);
                    break;
                case 3:
                    child.layout(350, 300, 350 + measuredWidth, 300 + measuredHeight);
                    break;
                case 4:
                    child.layout(200, 500, 200 + measuredWidth, 500 + measuredHeight);
                    break;
            }

//            child.layout(i * (getWidth() / 4),
//                    t,
//                    (i + 1) * (getWidth() / 4),
//                    b);
        }
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(5);
//        setBackgroundColor(Color.parseColor("#ff8041"));

        addView(total);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        int childCount = getChildCount();
        View childAt0 = getChildAt(0);
        View childAt1 = getChildAt(1);
        int right0 = childAt0.getRight();
        int height0 = childAt0.getHeight();
        int top0 = childAt0.getTop();
        int left1 = childAt1.getLeft();
        System.out.println("===============dispatchDraw==================" + left1);

        canvas.drawLine(right0, top0 + height0 / 2, left1, top0 + height0 / 2, paint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        System.out.println("===============ondraw==================");


    }

    public void setTotalList(String[][] totalList) {
//        System.out.println("===============set==================" + totalList.length);
//
//        this.totalList = totalList;
//
//        addView(totalList);

    }

    private void addView(String[][] totalList) {
        if (totalList.length > 2) {
            String[] level0 = totalList[0];
            String[] level1 = totalList[1];
            String[] level2 = totalList[2];

            int length0 = level0.length;
            int length1 = level1.length;
            int length2 = level2.length;

            int max1 = Math.max(length0, length1);
            int max2 = Math.max(max1, length2);
            System.out.println("max2=====================" + max2);

            if (length0 > 0) {
                for (int i = 0; i < length0; i++) {
                    System.out.println("0============" + level0[i]);
                    LayoutParams layoutParams = new LayoutParams(100, 100);
                    TextView textView = new TextView(context);
                    textView.setGravity(Gravity.CENTER);
                    textView.setTextColor(Color.WHITE);
                    textView.setText(level0[i]);
                    textView.setBackgroundColor(Color.parseColor("#999999"));
                    addView(textView, layoutParams);
                    arrayList0.add(textView);
                }
            }

            if (level1.length > 0) {
                for (int i = 0; i < level1.length; i++) {
                    System.out.println("1============" + level1[i]);
                    LayoutParams layoutParams = new LayoutParams(100, 100);
                    TextView textView = new TextView(context);
                    textView.setGravity(Gravity.CENTER);
                    textView.setTextColor(Color.WHITE);
                    textView.setBackgroundColor(Color.parseColor("#333333"));
                    textView.setText(level1[i]);
                    addView(textView, layoutParams);
                    arrayList1.add(textView);
                }
            }

            if (level2.length > 0) {
                for (int i = 0; i < level2.length; i++) {
                    System.out.println("2============" + level2[i]);
                    LayoutParams layoutParams = new LayoutParams(100, 100);
                    TextView textView = new TextView(context);
                    textView.setGravity(Gravity.CENTER);
                    textView.setTextColor(Color.WHITE);
                    textView.setBackgroundColor(Color.parseColor("#666666"));
                    textView.setText(level2[i]);
                    addView(textView, layoutParams);
                    arrayList2.add(textView);
                }
            }
        }
    }
}
