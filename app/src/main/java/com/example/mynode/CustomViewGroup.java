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

import java.util.ArrayList;

public class CustomViewGroup extends ViewGroup {


    // 计算所有child view 要占用的空间
    int desireWidth = 0;
    int desireHeight = 0;

    private Scroller mScroller;//弹性滑动对象，用于实现View的弹性滑动
    private VelocityTracker velocityTracker;//速度追踪，

    private GestureDetector detector;
    private final static int TOUCH_STATE_REST = 0;
    private final static int TOUCH_STATE_SCROLLING = 1;
    private int mTouchSlop;
    private int mTouchState = TOUCH_STATE_REST;

    //    private String[] data1 = new String[]{"A1", "A2"};
//    private String[] data2 = new String[]{"B1", "B2"};
//    private String[] data3 = new String[]{"C1", "C2"};
    //    private String[] data3 = new String[]{"C1", "C2", "C3"};
//    private String[][] total = {};
    private int leftMargin = 100;

    private Context context;
    private String[][] totalList = new String[][]{};
    private String[] data3 = new String[]{};

    private Paint linePaint;
    private ArrayList<View> arrayList0 = new ArrayList<>();
    private ArrayList<View> arrayList1 = new ArrayList<>();
    private ArrayList<View> arrayList2 = new ArrayList<>();
    /**
     * 连线属性
     */
    private int lineColor;
    private int lineWidth;

    /**
     * 节点属性
     */
    private int nodeWidth;
    private int nodeHeight;
    /**
     * 节点与节点间中线长度
     */
    private int lineLength1;
    private int lineHeight1;
    private int lineLength2;
    private int lineHeight2;
    private int lineLength3;
    private int lineHeight3;
    private int lineHeight4;
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

        // 获得它的父容器为它设置的测量模式和大小
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
            //onMeasure 会走两次 第一次为0  第二个才有值
            System.out.println("前getMeasuredHeight==========" + child.getMeasuredHeight());
            System.out.println("前getMeasuredWidth===========" + child.getMeasuredWidth());
        }

        //测量child方式一
//        System.out.println("==============measureChildren===================");
//        measureChildren(widthMeasureSpec, heightMeasureSpec);


        //测量child方式二
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            if (childView.getVisibility() != View.GONE) {
                //onMeasure 会走两次 放在measureChild前面，只需要加一次
                desireWidth += childView.getMeasuredWidth();
                desireHeight += childView.getMeasuredHeight();

                measureChild(childView, widthMeasureSpec, heightMeasureSpec);
                //onMeasure 会走两次 都有值
                System.out.println("==============measureChild===================");
                //获取view的测量宽高
                System.out.println("后getMeasuredHeight==========" + childView.getMeasuredHeight());
                System.out.println("后getMeasuredWidth===========" + childView.getMeasuredWidth());


//                //报错：java.lang.ClassCastException: android.view.ViewGroup$LayoutParams cannot be cast to android.view.ViewGroup$MarginLayoutParams
//                MarginLayoutParams lp = (MarginLayoutParams) childView.getLayoutParams();
//                int childWidth = childView.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
//                int childHeight = childView.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
//                System.out.println("2childWidth onMeasure============" + childWidth);
//                System.out.println("2childHeight onMeasure===========" + childHeight);
            }
        }

        System.out.println("desireWidth1111333=============================" + desireWidth);
        System.out.println("desireHeigh111333=============================" + desireHeight);

        // count with padding
        desireWidth += getPaddingLeft() + getPaddingRight();
        desireHeight += getPaddingTop() + getPaddingBottom();
        System.out.println("desireWidth2222333=============================" + desireWidth);
        System.out.println("desireHeight2222333=============================" + desireHeight);

        // see if the size is big enough
        desireWidth = Math.max(desireWidth, getSuggestedMinimumWidth());
        desireHeight = Math.max(desireHeight, getSuggestedMinimumHeight());
        System.out.println("getSuggestedMinimumWidth3333=============================" + getSuggestedMinimumWidth());
        System.out.println("getSuggestedMinimumHeight3333=============================" + getSuggestedMinimumHeight());

        //必须
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //或者
        System.out.println("desireWidth333=============================" + desireWidth);
        System.out.println("desireHeight333=============================" + desireHeight);
        setMeasuredDimension(modeWidth == MeasureSpec.EXACTLY ? sizeWidth : desireWidth, modeHeight == MeasureSpec.EXACTLY ? sizeHeight : desireHeight);
        //或者
//        setMeasuredDimension(
//                resolveSize(desireWidth, widthMeasureSpec),
//                resolveSize(desireHeight, heightMeasureSpec));

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

            if (data3.length == 1) {
                if (i == 4) {//第三行 中间
                    childView.layout(screenWidth / 2 - measuredWidth / 2, marginTop + measuredHeight + lineHeight1 + lineHeight2,
                            screenWidth / 2 + measuredWidth / 2, marginTop + measuredHeight * 2 + lineHeight1 + lineHeight2);
                }
            } else if (data3.length == 2) {
                if (i == 4) {//第三行 左一
                    childView.layout(screenWidth / 2 - lineLength3 - measuredWidth / 2, marginTop + measuredHeight + lineHeight1 + lineHeight2,
                            screenWidth / 2 - lineLength3 + measuredWidth / 2, marginTop + measuredHeight * 2 + lineHeight1 + lineHeight2);
                } else if (i == 5) {//第三行 中间
                    childView.layout(screenWidth / 2 - measuredWidth / 2, marginTop + measuredHeight + lineHeight1 + lineHeight2,
                            screenWidth / 2 + measuredWidth / 2, marginTop + measuredHeight * 2 + lineHeight1 + lineHeight2);
                }
            } else if (data3.length == 3) {
                if (i == 4) {//第三行 左一
                    childView.layout(screenWidth / 2 - lineLength3 - measuredWidth / 2, marginTop + measuredHeight + lineHeight1 + lineHeight2,
                            screenWidth / 2 - lineLength3 + measuredWidth / 2, marginTop + measuredHeight * 2 + lineHeight1 + lineHeight2);
                } else if (i == 5) {//第三行 中间
                    childView.layout(screenWidth / 2 - measuredWidth / 2, marginTop + measuredHeight + lineHeight1 + lineHeight2,
                            screenWidth / 2 + measuredWidth / 2, marginTop + measuredHeight * 2 + lineHeight1 + lineHeight2);
                } else if (i == 6) {//第三行 右一
                    childView.layout(screenWidth / 2 + lineLength3 - measuredWidth / 2, marginTop + measuredHeight + lineHeight1 + lineHeight2,
                            screenWidth / 2 + lineLength3 + measuredWidth / 2, marginTop + measuredHeight * 2 + lineHeight1 + lineHeight2);
                }
            } else if (data3.length > 3) {
                if (i == 4) {//第三行 左一
                    childView.layout(screenWidth / 2 - lineLength3 - measuredWidth / 2,
                            marginTop + measuredHeight + lineHeight1 + lineHeight2,
                            screenWidth / 2 - lineLength3 + measuredWidth / 2,
                            marginTop + measuredHeight * 2 + lineHeight1 + lineHeight2);
                } else if (i == 5) {//第三行 中间
                    childView.layout(screenWidth / 2 - measuredWidth / 2,
                            marginTop + measuredHeight + lineHeight1 + lineHeight2,
                            screenWidth / 2 + measuredWidth / 2,
                            marginTop + measuredHeight * 2 + lineHeight1 + lineHeight2);
                } else if (i == 6) {//第三行 右一
                    childView.layout(screenWidth / 2 + lineLength3 - measuredWidth / 2,
                            marginTop + measuredHeight + lineHeight1 + lineHeight2,
                            screenWidth / 2 + lineLength3 + measuredWidth / 2,
                            marginTop + measuredHeight * 2 + lineHeight1 + lineHeight2);
                } else if (i > 6) {//第三行 右边其他
                    childView.layout(screenWidth / 2 + lineLength3 * (i - 5) - measuredWidth / 2,
                            marginTop + measuredHeight + lineHeight1 + lineHeight2,
                            screenWidth / 2 + lineLength3 * (i - 5) + measuredWidth / 2,
                            marginTop + measuredHeight * 2 + lineHeight1 + lineHeight2);
                }
            }

            //4
            //4 5
            //4 5 6
            //4 5 67
            //4 5 678
            //4 5 6789
            //4 5 6789 10
            //4 5 6789 10 11
            //4 5 6789 10 11 12

//            final int finalI = i;
//            childView.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    onClickNodeListener.onClickNode(finalI);
//                }
//            });


            LayoutParams layoutParams = childView.getLayoutParams();
            System.out.println("2layoutParams==========================" + layoutParams.toString());//ViewGroup$LayoutParams
//            MarginLayoutParams lp = (MarginLayoutParams) layoutParams;
//            int childWidth = childView.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
//            int childHeight = childView.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
//            System.out.println("2childWidth============" + childWidth);
//            System.out.println("2childHeight===========" + childHeight);
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


        nodeHeight = DensityUtil.dp2px(context, 45);
        nodeWidth = DensityUtil.dp2px(context, 45);

        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        screenWidth = outMetrics.widthPixels;

        mScroller = new Scroller(getContext());
        velocityTracker = VelocityTracker.obtain();

//        detector = new GestureDetector(this);
//        ViewConfiguration configuration = ViewConfiguration.get(context);
//        // 获得可以认为是滚动的距离
//        mTouchSlop = configuration.getScaledTouchSlop();


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
        int currentIndex = 0;
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

        /*=================以上为默认=====================*/
        if (data3.length == 1) {
            //绘制第二行竖线
            View childAt4 = getChildAt(4);
            canvas.drawLine((childAt3.getLeft() + childAt2.getRight()) >> 1,
                    childAt2.getTop() + (childAt2.getHeight() >> 1),
                    (childAt3.getLeft() + childAt2.getRight()) >> 1,
                    childAt4.getTop(),
                    linePaint);
        } else if (data3.length == 2) {
            View childAt4 = getChildAt(4);
            View childAt5 = getChildAt(5);

            canvas.drawLine(screenWidth / 2 - lineLength3,
                    childAt4.getTop() - lineHeight3,
                    screenWidth / 2,
                    childAt4.getTop() - lineHeight3,
                    linePaint);

            canvas.drawLine(screenWidth / 2 - lineLength3,
                    childAt4.getTop() - lineHeight3,
                    screenWidth / 2 - lineLength3,
                    childAt4.getTop(),
                    linePaint);

            //绘制第二行竖线
            canvas.drawLine((childAt3.getLeft() + childAt2.getRight()) >> 1,
                    childAt2.getTop() + (childAt2.getHeight() >> 1),
                    (childAt3.getLeft() + childAt2.getRight()) >> 1,
                    childAt5.getTop(),
                    linePaint);
        } else if (data3.length >= 3) {
            View childAt4 = getChildAt(4);
            View childAt5 = getChildAt(5);

            //第三行左边
            canvas.drawLine(screenWidth / 2 - lineLength3,
                    childAt4.getTop() - lineHeight3,
                    screenWidth / 2,
                    childAt4.getTop() - lineHeight3,
                    linePaint);

            canvas.drawLine(screenWidth / 2 - lineLength3,
                    childAt4.getTop() - lineHeight3,
                    screenWidth / 2 - lineLength3,
                    childAt4.getTop(),
                    linePaint);

            //绘制第三行竖线
            canvas.drawLine((childAt3.getLeft() + childAt2.getRight()) >> 1,
                    childAt2.getTop() + (childAt2.getHeight() >> 1),
                    (childAt3.getLeft() + childAt2.getRight()) >> 1,
                    childAt5.getTop(),
                    linePaint);

            //绘制第三行 右边
            int childCount = getChildCount();//2 + 2 + 4/5/6/7.....4孩子共8个7 5孩子共9个78
            for (int i = 0; i < childCount - 2 - 2 - 2; i++) {
                View childAtX = getChildAt(i + 6);

                //第三行右边 第二个 横线
                canvas.drawLine(screenWidth / 2 + lineLength3 * (i),
                        childAtX.getTop() - lineHeight3,
                        screenWidth / 2 + lineLength3 * (i + 1),
                        childAtX.getTop() - lineHeight3,
                        linePaint);

                //第三行右边 第二个 竖线
                canvas.drawLine(screenWidth / 2 + lineLength3 * (i + 1),
                        childAtX.getTop() - lineHeight3,
                        screenWidth / 2 + lineLength3 * (i + 1),
                        childAtX.getTop(),
                        linePaint);
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        System.out.println("===============ondraw==================");
    }

    public void setTotalList(String[][] totalList) {
        System.out.println("===============set==================" + totalList.length);
        this.totalList = totalList;
        if (totalList.length > 2) {
            data3 = totalList[2];
        }
        addView(totalList);
//        requestLayout();
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

            int length0 = level0.length;
            int length1 = level1.length;
            int length2 = level2.length;

            int max1 = Math.max(length0, length1);
            int max2 = Math.max(max1, length2);
            System.out.println("max2=====================" + max2);

            test(0, level0);
            test(1, level1);
            test(2, level2);

//            if (length0 > 0) {
//                for (int i = 0; i < length0; i++) {
//                    System.out.println("0============" + level0[i]);
//
//                    View itemView = addView(level0[i]);
//                    arrayList0.add(itemView);
//
//                    final int finalI = i;
//                    itemView.setOnClickListener(new OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            onClickNodeListener.onClickNode(0, finalI, level0[finalI]);
//                        }
//                    });
//
//                }
//            }
//
//            if (level1.length > 0) {
//                for (int i = 0; i < level1.length; i++) {
//                    System.out.println("1============" + level1[i]);
//
//                    View itemView = addView(level1[i]);
//                    arrayList1.add(itemView);
//
//                    final int finalI = i;
//                    itemView.setOnClickListener(new OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            onClickNodeListener.onClickNode(1, finalI, level1[finalI]);
//                        }
//                    });
//
//                }
//            }
//
//            if (level2.length > 0) {
//                for (int i = 0; i < level2.length; i++) {
//                    System.out.println("2============" + level2[i]);
//
//                    View itemView = addView(level2[i]);
//                    arrayList2.add(itemView);
//
//                    final int finalI = i;
//                    itemView.setOnClickListener(new OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            onClickNodeListener.onClickNode(2, finalI, level2[finalI]);
//                        }
//                    });
//                }
//            }
        }
        invalidate();
    }

    private void test(int row, String[] levelList) {
        if (levelList.length > 0) {
            for (int i = 0; i < levelList.length; i++) {
                System.out.println("0============" + levelList[i]);
                View itemView = addView(row, i, levelList[i]);
                arrayList0.add(itemView);
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
                System.out.println("x6==================" + x);
                System.out.println("getMeasuredWidth6==================" + getMeasuredWidth());
                System.out.println("mLastX6==================" + mLastX);
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


    private void smoothScrollBy(int dx, int dy) {
        //从当前滑动的位置，平滑地过度到目标位置
        mScroller.startScroll(getScrollX(), getScrollY(), dx, dy, 500);
        invalidate();
    }

    /**
     * 弹性滑动到某一位置
     *
     * @param destX 目标X
     * @param destY 目标Y
     */
    public void smoothScrollTo(int destX, int destY) {
        int scrollX = getScrollX();
        int scrollY = getScrollY();
        int offsetX = destX - scrollX;
        int offsetY = destY - scrollY;
        mScroller.startScroll(scrollX, scrollY, offsetX, offsetY, 600);
        invalidate();
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
