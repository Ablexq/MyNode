

```xml
<de.hdodenhof.circleimageview.CircleImageView
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/profile_image"
    android:layout_width="96dp"
    android:layout_height="96dp"
    android:src="@drawable/profile"
    app:civ_border_width="2dp"
    app:civ_border_color="#FF000000"/>
```

# 自定义viewgroup时不走ondraw方法

当我们自定义一个View时会重写他的3个方法，onMeasure(),onLayout(),onDraw()方法，
但是自定义一个ViewGroup的时候要重写onMeasure(),onLayout(),dispatchDraw()这3个方法。

但是我们设置了viewgroup的背景后，也会调用ondraw方法

[自定义ViewGroup不走onDraw()方法的问题](https://www.jianshu.com/p/7d2f4cba2042)

# onMeasure

```
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        System.out.println("===============onMeasure==================");

        //measureChildren或者measureChild之后 
        //child : getMeasuredWidth、getMeasuredHeight才会获取到值
        
        //测量child方式一
//        measureChildren(widthMeasureSpec, heightMeasureSpec);

        //测量child方式二
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != View.GONE) {
                measureChild(child, widthMeasureSpec, heightMeasureSpec);
            }
        }

        //必须
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //或者
        //setMeasuredDimension(getMeasuredWidth(),getMeasuredHeight());
    }
```


# MarginLayoutParams

``` 
LayoutParams in ViewGroup (android.view)
    MarginLayoutParams in ViewGroup (android.view)
        LayoutParams in ActionBar (android.app)
            LayoutParams in Toolbar (android.widget)
        LayoutParams in GridLayout (android.widget)
        LayoutParams in FrameLayout (android.widget)
        LayoutParams in LinearLayout (android.widget)
            LayoutParams in RadioGroup (android.widget)
            LayoutParams in TableRow (android.widget)
            LayoutParams in TableLayout (android.widget)
            LayoutParams in ActionMenuView (android.widget)
        LayoutParams in RelativeLayout (android.widget)
    LayoutParams in AbsoluteLayout (android.widget)
    LayoutParams in AbsListView (android.widget)
    LayoutParams in WindowManager (android.view)
    LayoutParams in Gallery (android.widget)
```


[MarginLayoutParams--一个可以在代码中直接设置margin的方法](https://blog.csdn.net/u011374875/article/details/52150471)

[自定义控件知识储备-View的绘制流程](https://blog.csdn.net/yisizhu/article/details/51527557#t2)

[自定义控件知识储备-LayoutParams的那些事](https://blog.csdn.net/yisizhu/article/details/51582622)

[一个FlowLayout带你学会自定义ViewGroup](https://blog.csdn.net/yisizhu/article/details/51679219)

如果要为children定义布局属性，如layout_gravity，则需要自定义LayoutParams，并且重写ViewGroup相关的方法。

# requestLayout与invalidate的区别

参考资料：
Android View 深度分析requestLayout、invalidate与postInvalidate
https://blog.csdn.net/a553181867/article/details/51583060

笔记：

1 requestLayout会标记View的mPrivateFlags的PFLAG_FORCE_LAYOUT,并且逐层向上调用，标记父View的标记位，最终调用ViewRootImpl的requestLayot

2 ViewRootImpl的RequestLayout方法会调用scheduleTraversals方法，最终调用measure，layout，draw三个过程

3 invalidate方法会设置PFLAG_DIRTY标记位， 并且循环向上调用，最终调用ViewRootImpl的invalidateChildParent方法，
在该方法中，合并所有dirty区域，并且调用scheduleTraversals方法，
因为没有设置PFLAG_FORCE_LAYOUT标记位，因此不会measure和layout，只会draw

总结：

requestLayout会重新measure，layout，draw整个View树

invalidate只会重新draw需要重新绘制的区域，不会measure和layout














