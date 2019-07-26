package com.gh_hitech.devicecontroller.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.gh_hitech.devicecontroller.R;

/**
 * @author yijigu
 */
public class ImageAndTextView extends View {

    private final int SPACING = 16;
    private Context mContext;
    private String mText;
    private float mTextSize;
    private Bitmap mImage;
    private boolean mChecked;
    private Paint mPaint = new Paint();
    private Rect textRect = new Rect();
    private Rect imageRect = new Rect();
    private int imageId, imageId_pressed, imageId_not_click, imageId_selector, mTextColor;

    public ImageAndTextView(Context context) {
        this(context, null);
    }

    public ImageAndTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ImageAndTextView);
        mText = a.getString(R.styleable.ImageAndTextView_android_text);
        if (mText == null) {
            mText = "Text";
        }
        mTextColor = a.getColor(R.styleable.ImageAndTextView_textColor,
                Color.BLACK);
        mTextSize = a.getDimension(R.styleable.ImageAndTextView_textSize, 36);
        imageId = a.getResourceId(R.styleable.ImageAndTextView_src,
                R.drawable.kaiji_1_r);
        imageId_pressed = a.getResourceId(
                R.styleable.ImageAndTextView_src_pressed, imageId);
        imageId_not_click = a.getResourceId(
                R.styleable.ImageAndTextView_src_noClick, imageId);
        imageId_selector = a.getResourceId(
                R.styleable.ImageAndTextView_src_selector, imageId);
        final boolean checked = a.getBoolean(
                R.styleable.ImageAndTextView_checked, false);
        setChecked(checked);
        // 调用结束后务必调用recycle()方法，否则这次的设定会对下次的使用造成影响
        a.recycle();
        mImage = BitmapFactory.decodeResource(mContext.getResources(), imageId);
        mPaint.setTextSize(mTextSize);
        // 计算文本尺寸
        mPaint.getTextBounds(mText, 0, mText.length(), textRect);
    }

    public ImageAndTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int mWidth = getWidth();
        int mHeight = getHeight();

        mPaint.setColor(mTextColor);
        mPaint.setTextSize(mTextSize);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawText(mText, mWidth / 2 - textRect.width() / 2, (textRect.height() + SPACING) * 2 + mImage.getHeight(), mPaint);

        imageRect.left = mWidth / 2 - mImage.getWidth() / 2;
        imageRect.right = mWidth / 2 + mImage.getWidth() / 2;
//        imageRect.top = (mHeight-textRect.height()-SPACING)/2-mImage.getHeight()/2;
        imageRect.top = (textRect.height() + SPACING) / 2;
        imageRect.bottom = (textRect.height() + SPACING) / 2 + mImage.getHeight();

        canvas.drawBitmap(mImage, null, imageRect, mPaint);
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (isFocused()) {
            mImage = BitmapFactory.decodeResource(getResources(), imageId_selector);
        } else if (isChecked()) {
            if (!isEnabled()) {
                return;
            }
            toggle();
            mImage = BitmapFactory.decodeResource(getResources(), imageId_pressed);
        } else {
            if (isEnabled()) {
                toggle();
                mImage = BitmapFactory.decodeResource(getResources(), imageId);
                mTextColor = Color.BLACK;
            } else {
                mImage = BitmapFactory.decodeResource(getResources(), imageId_not_click);
                mTextColor = Color.GRAY;
            }
        }
        invalidate();
    }

    public boolean isChecked() {
        return mChecked;
    }

    public void setChecked(boolean checked) {
        mChecked = checked;
    }

    public void toggle() {
        setChecked(!mChecked);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width;
        int height;
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specize = MeasureSpec.getSize(widthMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            width = specize;
        } else {
            int widthByImage = 0;
            int widthByText = 0;
            widthByImage = getPaddingLeft() + getPaddingRight() + mImage.getWidth();
            widthByText = getPaddingLeft() + getPaddingRight() + textRect.width();
            int w = Math.max(widthByImage, widthByText);
            width = Math.min(w, specize);
        }

        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specize = MeasureSpec.getSize(heightMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            height = specize;
        } else {
            int h = getPaddingBottom() + getPaddingTop() + mImage.getHeight() + textRect.height() + SPACING;
            height = Math.min(h, specize);
        }

        setMeasuredDimension(width, height);
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
        mPaint.getTextBounds(mText, 0, mText.length(), textRect);
        invalidate();
    }

//    @SuppressLint("ClickableViewAccessibility")
//    @Override
//    public boolean onTouchEvent(MotionEvent event)
//    {
//        // 如果设置的为不能点击 直接return
//        if (!isEnabled())
//        {
//            return super.onTouchEvent(event);
//        }
//        switch (event.getAction())
//        {
//            case MotionEvent.ACTION_DOWN:
//                mImage = BitmapFactory.decodeResource(getResources(),
//                        imageId_checked);
//                break;
//            case MotionEvent.ACTION_UP:
//                mImage = BitmapFactory.decodeResource(getResources(), imageId);
//                break;
//                default:
//        }
//        invalidate();
//        return super.onTouchEvent(event);
//    }

    public void setTextColor(int textColor) {
        mTextColor = textColor;
        mPaint.setColor(mTextColor);
        invalidate();
    }
}
