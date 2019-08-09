package com.example.mynode;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.*;
import android.widget.ImageView;
import android.widget.Scroller;
import android.widget.TextView;
import org.jetbrains.annotations.NotNull;


public class CustomViewGroup extends ViewGroup {
    // 计算所有child view 要占用的空间
    int desireWidth = 0;
    int desireHeight = 0;

    private Scroller mScroller;//弹性滑动对象，用于实现View的弹性滑动
    private VelocityTracker velocityTracker;//速度追踪，

    private Context context;
    private String[][] totalList = new String[][]{};
    private String[] data3 = new String[]{};

    private Paint linePaint;
    /**
     * 连线属性
     */
    private int lineColor;
    private int lineWidth;

    /**
     * 节点与节点间中线长度
     */
    private int lineLength1;
    private int lineHeight1;
    private int lineLength2;
    private int lineHeight2;
    private int lineLength3;
    private int lineHeight3;
    private int marginTop;

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


    /**
     * 负责设置子控件的测量模式和大小 根据所有子控件设置自己的宽和高
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        System.out.println("===============onMeasure==================");
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            if (childView.getVisibility() != View.GONE) {
                desireWidth += childView.getMeasuredWidth();
                desireHeight += childView.getMeasuredHeight();
                measureChild(childView, widthMeasureSpec, heightMeasureSpec);
            }
        }
        desireWidth += getPaddingLeft() + getPaddingRight();
        desireHeight += getPaddingTop() + getPaddingBottom();
        desireWidth = Math.max(desireWidth, getSuggestedMinimumWidth());
        desireHeight = Math.max(desireHeight, getSuggestedMinimumHeight());
        setMeasuredDimension(modeWidth == MeasureSpec.EXACTLY ? sizeWidth : desireWidth,
                modeHeight == MeasureSpec.EXACTLY ? sizeHeight : desireHeight);
    }

    /**
     * 管理子View显示的位置
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (totalList.length < 3 || totalList[0].length < 2 || totalList[1].length < 2 || totalList[2].length < 1) {
            return;
        }

        System.out.println("===============onLayout==================");
        int childCount = getChildCount();
        System.out.println("childCount=========" + childCount);
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            int measuredWidth = childView.getMeasuredWidth();
            int measuredHeight = childView.getMeasuredHeight();
            System.out.println("width" + i + "===============" + measuredWidth);
            System.out.println("height" + i + "===============" + measuredHeight);


            if (i == 0) {//第一行 第一个
                childView.layout(screenWidth / 2 + lineLength2 + measuredWidth / 2 - lineLength1 - measuredWidth, marginTop,
                        screenWidth / 2 + lineLength2 + measuredWidth / 2 - lineLength1, marginTop + measuredHeight);
            } else if (i == 1) {//第一行 第二个
                childView.layout(screenWidth / 2 + lineLength2 + measuredWidth / 2 + lineLength1, marginTop,
                        screenWidth / 2 + lineLength2 + measuredWidth / 2 + lineLength1 + measuredWidth, marginTop + measuredHeight);
            } else if (i == 2) {//第二行 第一个
                childView.layout(screenWidth / 2 - lineLength2 - measuredWidth, marginTop + measuredHeight / 2 + lineHeight1,
                        screenWidth / 2 - lineLength2, marginTop + measuredHeight / 2 + lineHeight1 + measuredHeight);
            } else if (i == 3) {//第二行 第二个
                childView.layout(screenWidth / 2 + lineLength2, marginTop + measuredHeight / 2 + lineHeight1,
                        screenWidth / 2 + lineLength2 + measuredWidth, marginTop + measuredHeight / 2 + lineHeight1 + measuredHeight);
            }
            //===========================================
            if (data3.length > 0 && i > 3) {
                int middleIndex;
                if (data3.length % 2 == 0) {//偶数个
                    middleIndex = (data3.length - 1) / 2 + 4 + 1;
                } else {//奇数个
                    middleIndex = (data3.length - 1) / 2 + 4;
                }

                if (i == middleIndex) {
                    childView.layout(screenWidth / 2 - measuredWidth / 2, marginTop + measuredHeight + lineHeight1 + lineHeight2,
                            screenWidth / 2 + measuredWidth / 2, marginTop + measuredHeight * 2 + lineHeight1 + lineHeight2);
                } else {
                    childView.layout(screenWidth / 2 + lineLength3 * (i - middleIndex) - measuredWidth / 2,
                            marginTop + measuredHeight + lineHeight1 + lineHeight2,
                            screenWidth / 2 + lineLength3 * (i - middleIndex) + measuredWidth / 2,
                            marginTop + measuredHeight * 2 + lineHeight1 + lineHeight2);
                }
            }
        }
    }

    private void init() {
        lineColor = Color.parseColor("#000000");
        lineWidth = DensityUtil.dp2px(context, 2.5f);
        linePaint = new Paint();
        linePaint.setColor(lineColor);
        linePaint.setStyle(Paint.Style.FILL);
        linePaint.setStrokeWidth(lineWidth);
        lineLength1 = DensityUtil.dp2px(context, 30);
        lineLength2 = DensityUtil.dp2px(context, 60);
        lineLength3 = DensityUtil.dp2px(context, 90);

        lineHeight1 = DensityUtil.dp2px(context, 60);
        lineHeight2 = DensityUtil.dp2px(context, 75);
        lineHeight3 = DensityUtil.dp2px(context, 15.5f);

        marginTop = DensityUtil.dp2px(context, 40f);

        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        screenWidth = outMetrics.widthPixels;

        mScroller = new Scroller(getContext());
        velocityTracker = VelocityTracker.obtain();
        addView(totalList);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        drawLine(canvas);
    }

    private void drawLine(Canvas canvas) {
        if (totalList.length < 3 || totalList[0].length < 2 || totalList[1].length < 2 || totalList[2].length < 1) {
            return;
        }
        View childAt0 = getChildAt(0);
        View childAt1 = getChildAt(1);
        View childAt2 = getChildAt(2);
        View childAt3 = getChildAt(3);
        System.out.println("===============dispatchDraw==================" + childAt1.getLeft());
        //绘制第一行横线
        canvas.drawLine(childAt0.getRight(),
                childAt0.getTop() + (childAt0.getHeight() >> 1),
                childAt1.getLeft(),
                childAt0.getTop() + (childAt0.getHeight() >> 1),
                linePaint);
        //绘制第一行竖线
        canvas.drawLine((childAt1.getLeft() + childAt0.getRight()) >> 1,
                childAt0.getTop() + (childAt0.getHeight() >> 1),
                (childAt1.getLeft() + childAt0.getRight()) >> 1,
                childAt3.getTop(),
                linePaint);
        //绘制第二行横线
        canvas.drawLine(childAt2.getRight(),
                childAt2.getTop() + (childAt2.getHeight() >> 1),
                childAt3.getLeft(),
                childAt2.getTop() + (childAt2.getHeight() >> 1),
                linePaint);

        if (data3.length > 0) {
            int middleIndex;
            if (data3.length % 2 == 0) {//偶数个
                middleIndex = (data3.length - 1) / 2 + 4 + 1;
            } else {//奇数个
                middleIndex = (data3.length - 1) / 2 + 4;
            }

            for (int i = 0; i < data3.length; i++) {
                if (i + 4 == middleIndex) {
                    View childAtMiddle = getChildAt(middleIndex);
                    //绘制第三行主竖线
                    canvas.drawLine((childAt3.getLeft() + childAt2.getRight()) >> 1,
                            childAt2.getTop() + (childAt2.getHeight() >> 1),
                            (childAt3.getLeft() + childAt2.getRight()) >> 1,
                            childAtMiddle.getTop(),
                            linePaint);
                } else {
                    View childAtX = getChildAt(i + 4);
                    //竖线
                    canvas.drawLine(screenWidth / 2 + lineLength3 * (i + 4 - middleIndex),
                            childAtX.getTop() - lineHeight3,
                            screenWidth / 2 + lineLength3 * (i + 4 - middleIndex),
                            childAtX.getTop(),
                            linePaint);
                    if (i + 4 < middleIndex) {
                        //横线
                        canvas.drawLine(screenWidth / 2 + lineLength3 * (i + 4 - middleIndex),
                                childAtX.getTop() - lineHeight3,
                                screenWidth / 2 + lineLength3 * (i + 4 - middleIndex + 1),
                                childAtX.getTop() - lineHeight3,
                                linePaint);
                    } else {
                        //横线
                        canvas.drawLine(screenWidth / 2 + lineLength3 * (i + 4 - middleIndex),
                                childAtX.getTop() - lineHeight3,
                                screenWidth / 2 + lineLength3 * (i + 4 - middleIndex - 1),
                                childAtX.getTop() - lineHeight3,
                                linePaint);
                    }
                }
            }
        }
    }

    public void setTotalList(String[][] totalList) {
        System.out.println("===============set==================" + totalList.length);
        this.totalList = totalList;
        if (totalList.length > 2) {
            data3 = totalList[2];
        }
        addView(totalList);
    }

    private void addView(String[][] totalList) {
        int childCount = getChildCount();
        if (childCount > 0) {
            removeAllViews();
        }
        if (totalList.length > 2) {
            final String[] level0 = totalList[0];
            final String[] level1 = totalList[1];
            final String[] level2 = totalList[2];
            forEachAddView(0, level0);
            forEachAddView(1, level1);
            forEachAddView(2, level2);
        }
        invalidate();
    }

    private void forEachAddView(int row, String[] levelList) {
        if (levelList.length > 0) {
            for (int i = 0; i < levelList.length; i++) {
                System.out.println("0============" + levelList[i]);
                addView(row, i, levelList[i]);
            }
        }
    }

    @NotNull
    private View addView(final int row, final int column, final String text) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_node, null);
        TextView textView = ((TextView) itemView.findViewById(R.id.tv));
        ImageView iv1 = ((ImageView) itemView.findViewById(R.id.iv1));
        ImageView iv2 = ((ImageView) itemView.findViewById(R.id.iv2));
        ImageView iv3 = ((ImageView) itemView.findViewById(R.id.iv3));
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.WHITE);
        textView.setText(text);
        textView.setBackgroundColor(Color.parseColor("#999999"));
        addView(itemView);

        itemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickNodeListener.onClickNode(row, column, text);
            }
        });

        iv1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickNodeListener.onClickNodeChild(0);
            }
        });

        iv2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickNodeListener.onClickNodeChild(1);
            }
        });

        iv3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickNodeListener.onClickNodeChild(2);
            }
        });


        return itemView;
    }


    /*===========================================================================*/

    //屏幕宽度
    private int screenWidth;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        velocityTracker.addMovement(event);

        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //如果动画还没有结束，再次点击时结束上次动画，即开启这次新的ACTION_DOWN的动画
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                break;

            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastX;
                scrollBy(-deltaX, 0);
                break;

            case MotionEvent.ACTION_UP:
                break;
        }
        mLastX = x;
        mLastY = y;
        return true;
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    //分别记录上次滑动的坐标, 仅用于onTouchEvent处理滑动使用
    private int mLastX = 0;
    private int mLastY = 0;

    //分别记录上次滑动的坐标（onINterceptTouchEvent）
    private int mLastXIntercept = 0;
    private int mLastYIntercept = 0;


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastXIntercept;
                int deltaY = y - mLastYIntercept;
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        mLastX = x;
        mLastY = y;
        mLastXIntercept = x;
        mLastYIntercept = y;

        return super.onInterceptTouchEvent(ev);
    }

    private OnClickNodeListener onClickNodeListener;

    public void setClickListener(OnClickNodeListener onClickNodeListener) {
        this.onClickNodeListener = onClickNodeListener;
    }

    public interface OnClickNodeListener {
        void onClickNode(int row, int column, String string);

        void onClickNodeChild(int position);
    }

}
