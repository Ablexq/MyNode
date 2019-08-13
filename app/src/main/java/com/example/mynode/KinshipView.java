package com.example.mynode;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;
import org.jetbrains.annotations.NotNull;

/**
 *     private String[] data1 = new String[]{"A1", "A2"};
 *     private String[] data2 = new String[]{"B1", "B2"};
 *     private String[] data31 = new String[]{"C1"};
 *     private String[][] total1 = {data1, data2, data31};
 *     KinshipView  kinshipView = (KinshipView) this.findViewById(R.id.cvg);
 *     kinshipView.setTotalList(total1);
 *
 *     <declare-styleable name="KinshipView">
 *         <attr name="lineColor" format="color|reference"/>
 *         <attr name="lineWidth" format="dimension|reference"/>
 *         <attr name="lineLengthTop" format="dimension|reference"/>
 *         <attr name="lineHeightTop" format="dimension|reference"/>
 *         <attr name="lineLengthMiddle" format="dimension|reference"/>
 *         <attr name="lineHeightMiddle" format="dimension|reference"/>
 *         <attr name="lineLengthBottom" format="dimension|reference"/>
 *         <attr name="lineHeightBottom" format="dimension|reference"/>
 *     </declare-styleable>
 *
 *         <xxx.xxx.xxx.KinshipView
 *             android:id="@+id/cvg"
 *             android:layout_width="match_parent"
 *             android:background="#ff8041"
 *             android:paddingTop="20dp"
 *             android:layout_margin="30dp"
 *             android:layout_gravity="center_horizontal"
 *             android:paddingBottom="20dp"
 *             app:lineColor="@color/colorPrimary"
 *             app:lineWidth="2dp"
 *             app:lineLengthTop="30dp"
 *             app:lineLengthMiddle="@dimen/dp_60"
 *             app:lineLengthBottom="@dimen/dp_90"
 *             app:lineHeightTop="@dimen/dp_60"
 *             app:lineHeightMiddle="75dp"
 *             app:lineHeightBottom="15.5dp"
 *             android:layout_height="wrap_content"/>
 *
 */
public class KinshipView extends ViewGroup {
    private int screenWidth;
    private int mLastX = 0;
    private int mLastY = 0;

    private Context context;
    private String[][] totalList = new String[][]{};
    private String[] data3 = new String[]{};

    /**
     * 节点连接线
     */
    private Paint linePaint;
    private int lineLength1;
    private int lineHeight1;
    private int lineLength2;
    private int lineHeight2;
    private int lineLength3;
    private int lineHeight3;

    public KinshipView(Context context) {
        this(context, null);
    }

    public KinshipView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public KinshipView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int desireHeight = 0;
        int desireWidth = 0;
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            if (childView.getVisibility() != View.GONE) {
                measureChild(childView, widthMeasureSpec, heightMeasureSpec);
            }
        }
        desireHeight += getChildAt(0).getMeasuredHeight() * 2 + lineHeight1 + lineHeight2;
        desireHeight += getPaddingTop() + getPaddingBottom();
        desireHeight = Math.max(desireHeight, getSuggestedMinimumHeight());
        desireWidth = screenWidth;
        setMeasuredDimension(modeWidth == MeasureSpec.EXACTLY ? sizeWidth : desireWidth,
                modeHeight == MeasureSpec.EXACTLY ? sizeHeight : desireHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (totalList.length < 3 || totalList[0].length < 2 || totalList[1].length < 2 || totalList[2].length < 1) {
            return;
        }

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            int measuredWidth = childView.getMeasuredWidth();
            int measuredHeight = childView.getMeasuredHeight();

            if (i == 0) {//第一行 第一个
                childView.layout(getMeasuredWidth() / 2 + lineLength2 + measuredWidth / 2 - lineLength1 - measuredWidth, getPaddingTop(),
                        getMeasuredWidth() / 2 + lineLength2 + measuredWidth / 2 - lineLength1, getPaddingTop() + measuredHeight);
            } else if (i == 1) {//第一行 第二个
                childView.layout(getMeasuredWidth() / 2 + lineLength2 + measuredWidth / 2 + lineLength1, getPaddingTop(),
                        getMeasuredWidth() / 2 + lineLength2 + measuredWidth / 2 + lineLength1 + measuredWidth, getPaddingTop() + measuredHeight);
            } else if (i == 2) {//第二行 第一个
                childView.layout(getMeasuredWidth() / 2 - lineLength2 - measuredWidth, getPaddingTop() + measuredHeight / 2 + lineHeight1,
                        getMeasuredWidth() / 2 - lineLength2, getPaddingTop() + measuredHeight / 2 + lineHeight1 + measuredHeight);
            } else if (i == 3) {//第二行 第二个
                childView.layout(getMeasuredWidth() / 2 + lineLength2, getPaddingTop() + measuredHeight / 2 + lineHeight1,
                        getMeasuredWidth() / 2 + lineLength2 + measuredWidth, getPaddingTop() + measuredHeight / 2 + lineHeight1 + measuredHeight);
            }
            if (data3.length > 0 && i > 3) {
                int middleIndex;
                if (data3.length % 2 == 0) {//偶数个
                    middleIndex = (data3.length - 1) / 2 + 4 + 1;
                } else {//奇数个
                    middleIndex = (data3.length - 1) / 2 + 4;
                }

                if (i == middleIndex) {
                    childView.layout(getMeasuredWidth() / 2 - measuredWidth / 2, getPaddingTop() + measuredHeight + lineHeight1 + lineHeight2,
                            getMeasuredWidth() / 2 + measuredWidth / 2, getPaddingTop() + measuredHeight * 2 + lineHeight1 + lineHeight2);
                } else {
                    childView.layout(getMeasuredWidth() / 2 + lineLength3 * (i - middleIndex) - measuredWidth / 2,
                            getPaddingTop() + measuredHeight + lineHeight1 + lineHeight2,
                            getMeasuredWidth() / 2 + lineLength3 * (i - middleIndex) + measuredWidth / 2,
                            getPaddingTop() + measuredHeight * 2 + lineHeight1 + lineHeight2);
                }
            }
        }
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.KinshipView);
        int lineColor = typedArray.getColor(R.styleable.KinshipView_lineColor, Color.parseColor("#000000"));
        int lineWidth = (int) typedArray.getDimension(R.styleable.KinshipView_lineWidth, DensityUtil.dp2px(context, 2.5f));
        lineLength1 = (int) typedArray.getDimension(R.styleable.KinshipView_lineLengthTop, DensityUtil.dp2px(context, 30));
        lineLength2 = (int) typedArray.getDimension(R.styleable.KinshipView_lineLengthMiddle, DensityUtil.dp2px(context, 60));
        lineLength3 = (int) typedArray.getDimension(R.styleable.KinshipView_lineLengthBottom, DensityUtil.dp2px(context, 90));
        lineHeight1 = (int) typedArray.getDimension(R.styleable.KinshipView_lineHeightTop, DensityUtil.dp2px(context, 60));
        lineHeight2 = (int) typedArray.getDimension(R.styleable.KinshipView_lineHeightMiddle, DensityUtil.dp2px(context, 75));
        lineHeight3 = (int) typedArray.getDimension(R.styleable.KinshipView_lineHeightBottom, DensityUtil.dp2px(context, 15.5f));
        typedArray.recycle();

        linePaint = new Paint();
        linePaint.setColor(lineColor);
        linePaint.setStyle(Paint.Style.FILL);
        linePaint.setStrokeWidth(lineWidth);

        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        screenWidth = outMetrics.widthPixels;

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
                    canvas.drawLine(getMeasuredWidth() / 2 + lineLength3 * (i + 4 - middleIndex),
                            childAtX.getTop() - lineHeight3,
                            getMeasuredWidth() / 2 + lineLength3 * (i + 4 - middleIndex),
                            childAtX.getTop(),
                            linePaint);
                    if (i + 4 < middleIndex) {
                        //横线
                        canvas.drawLine(getMeasuredWidth() / 2 + lineLength3 * (i + 4 - middleIndex),
                                childAtX.getTop() - lineHeight3,
                                getMeasuredWidth() / 2 + lineLength3 * (i + 4 - middleIndex + 1),
                                childAtX.getTop() - lineHeight3,
                                linePaint);
                    } else {
                        //横线
                        canvas.drawLine(getMeasuredWidth() / 2 + lineLength3 * (i + 4 - middleIndex),
                                childAtX.getTop() - lineHeight3,
                                getMeasuredWidth() / 2 + lineLength3 * (i + 4 - middleIndex - 1),
                                childAtX.getTop() - lineHeight3,
                                linePaint);
                    }
                }
            }
        }
    }

    public void setTotalList(String[][] totalList) {
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;

            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastX;
                scrollBy(-deltaX, 0);
                break;

            case MotionEvent.ACTION_UP:
                break;

            default:
                break;
        }
        mLastX = x;
        mLastY = y;
        return true;
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
