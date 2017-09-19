package widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ruiyi.appbar.R;

import utils.DensityUtils;

/**
 * Created by ruiyi on 2016/9/19.
 */

public class Appbar extends RelativeLayout implements View.OnClickListener {

    private static final int LEFT_TEXT_VISIBLE = 1;
    private static final int LEFT_IMAGE_VISIBLE = 1 << 1;
    private static final int TITLE_TEXT_VISIBLE = 1 << 2;
    private static final int TITLE_IMAGE_VISIBLE = 1 << 3;
    private static final int RIGHT_TEXT_VISIBLE = 1 << 4;
    private static final int RIGHT_IMAGE_VISIBLE = 1 << 5;
    private static final int RIGHT_IMAGE2_VISIBLE = 1 << 6;
    private static final int BOTTOM_LINE_VISIBLE = 1 << 7;
    private TextView mLeftTextView;
    private ImageView mLeftImageView;
    private TextView mTitleTextView;
    private ImageView mTitleImageView;
    private TextView mRightTextView;
    private ImageView mRightImageView;
    private ImageView mRightImageView2;
    private ImageView mRightImageTagView;
    private View mBottomLine;

    private int customView;
    private Drawable background = null;
    private int mChildVisible = 0x06;
    private String mTitleText;
    private String mSubTitleText;
    private int mTitleTextSize = -1;
    private ColorStateList mTitleTextColor;
    private int mTitleImage;
    private int mTitleImageWidth;
    private int mTitleImageHeight;

    private String mLeftText;
    private int mLeftTextSize;
    private int mLeftTextColor;
    private Drawable mLeftDrawable;
    private float mLeftDrawableWidth;
    private float mLeftDrawableHeight;

    private boolean isLineVisible = true;
    private String mRightText;
    private int mRightTextSize;
    private ColorStateList mRightTextColor;
    private Drawable mRightDrawable;
    private Drawable mRightDrawable2;
    private int mRightDrawableWidth;
    private int mDRightrawableHeight;
    private Appbar.OnAppbarClickListener listener;

    private boolean forced;

    public Appbar(Context context) {
        this(context, null);
    }

    public Appbar(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.appbarStyle);
    }

    public Appbar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setId(R.id.appbar);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.Appbar, defStyle, 0);
        for (int i = 0; i < typedArray.getIndexCount(); i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.Appbar_custom_view:
                    customView = typedArray.getResourceId(attr, -1);
                    break;
                case R.styleable.Appbar_child_visible:
                    mChildVisible = typedArray.getInt(attr, mChildVisible);
                    break;
                case R.styleable.Appbar_title_text:
                    mTitleText = typedArray.getString(attr);
                    break;
                case R.styleable.Appbar_subtitle_text:
                    mSubTitleText = typedArray.getString(attr);
                    break;
                case R.styleable.Appbar_title_text_size:
                    mTitleTextSize = typedArray.getDimensionPixelSize(attr, mTitleTextSize);
                    break;
                case R.styleable.Appbar_title_text_color:
                    mTitleTextColor = typedArray.getColorStateList(attr);
                    break;
                case R.styleable.Appbar_title_image:
                    break;
                case R.styleable.Appbar_title_image_width:
                    break;
                case R.styleable.Appbar_title_image_height:
                    break;
                case R.styleable.Appbar_left_text:
                    mLeftText = typedArray.getString(attr);
                    break;
                case R.styleable.Appbar_left_text_size:
                    break;
                case R.styleable.Appbar_left_text_color:
                    break;
                case R.styleable.Appbar_left_image:
                    mLeftDrawable = typedArray.getDrawable(attr);
                    break;
                case R.styleable.Appbar_left_image_width:
                    mLeftDrawableWidth = typedArray.getDimension(attr, -1);
                    break;
                case R.styleable.Appbar_left_image_height:
                    mLeftDrawableHeight = typedArray.getDimension(attr, -1);
                    break;
                case R.styleable.Appbar_right_text:
                    mRightText = typedArray.getString(attr);
                    break;
                case R.styleable.Appbar_right_text_size:
                    break;
                case R.styleable.Appbar_right_text_color:
                    mRightTextColor = typedArray.getColorStateList(attr);
                    break;
                case R.styleable.Appbar_right_image:
                    mRightDrawable = typedArray.getDrawable(attr);
                    break;
                case R.styleable.Appbar_right_image2:
                    mRightDrawable2 = typedArray.getDrawable(attr);
                    break;
                case R.styleable.Appbar_line_visible:
                    isLineVisible = typedArray.getBoolean(attr, true);
                    break;
            }
        }
        typedArray.recycle();
        initActionBarView();
    }


    private void initActionBarView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setPadding(getPaddingLeft(), getPaddingTop() + DensityUtils.getStatusBarHeight(getContext()),
                    getPaddingRight(), getPaddingBottom());
        }
        if (customView > 0) {
            View.inflate(getContext(), customView, this);
        } else {
            initGeneralBar();
        }
    }

    private void initGeneralBar() {
        View view = View.inflate(getContext(), R.layout.view_appbar, this);
        LayoutParams layoutParams = new LayoutParams(
                LayoutParams.MATCH_PARENT, DensityUtils.dip2px(getContext(), 48));
        view.setLayoutParams(layoutParams);
        mLeftTextView = (TextView) findViewById(R.id.left_text);
        mLeftTextView.setOnClickListener(this);
        mLeftTextView.setSelected(true);
        mLeftImageView = (ImageView) view.findViewById(R.id.left_image);
        mLeftImageView.setOnClickListener(this);
//        mTitleTextView.getPaint().setFakeBoldText(true);
        mTitleTextView = (TextView) view.findViewById(R.id.title_text);
        mTitleTextView.setOnClickListener(this);
        mRightTextView = (TextView) view.findViewById(R.id.right_text);
        mRightTextView.setOnClickListener(this);
        mRightTextView.setSelected(true);
        mRightImageView = (ImageView) view.findViewById(R.id.right_image);
        mRightImageView.setOnClickListener(this);
        mRightImageView2 = (ImageView) view.findViewById(R.id.right_image2);
        mRightImageView2.setOnClickListener(this);
        mRightImageTagView = (ImageView) view.findViewById(R.id.right_image_tag);
        mBottomLine = view.findViewById(R.id.bottom_line);

        if (getBackground() == null) {
            setBackgroundResource(R.drawable.bg_appbar_main);
        }
        initActionBarData();
        initActionBarVisible();
    }

    private void initActionBarData() {

        if (!TextUtils.isEmpty(mLeftText)) {
            mLeftTextView.setText(mLeftText);
        }
        if (!TextUtils.isEmpty(mTitleText)) {
            mTitleTextView.setText(mTitleText);
        }
        if (mTitleTextSize != -1) {
            mTitleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTitleTextSize);
        }

        if (mLeftDrawable != null) {
            mLeftImageView.setImageDrawable(mLeftDrawable);
        }
        if (mLeftDrawableWidth > 0) {
            int width = (int) mLeftDrawableWidth
                    + mLeftImageView.getPaddingLeft() + mLeftImageView.getPaddingRight();
            mLeftImageView.setMaxWidth(width);
        }
        if (mLeftDrawableHeight > 0) {
            int height = (int) mLeftDrawableHeight
                    + mLeftImageView.getPaddingTop() + mLeftImageView.getPaddingBottom();
            mLeftImageView.setMaxHeight(height);
        }

        if (!TextUtils.isEmpty(mRightText)) {
            mRightTextView.setText(mRightText);
        }

        if (mRightDrawable != null) {
            mRightImageView.setImageDrawable(mRightDrawable);
        }
        if (mRightDrawable2 != null) {
            mRightImageView2.setImageDrawable(mRightDrawable2);
        }
        if (mTitleTextColor != null) {
            mTitleTextView.setTextColor(mTitleTextColor);
        }
        if (mRightTextColor != null) {
            mRightTextView.setTextColor(mRightTextColor);
//        } else {
//            mRightTextView.setTextColor(getResources().getColor(R.color.selector_color_clickable));
        }
    }

    private void initActionBarVisible() {
        if ((LEFT_TEXT_VISIBLE & mChildVisible) == 0) {
            mLeftTextView.setVisibility(View.GONE);
        } else {
            mLeftTextView.setVisibility(View.VISIBLE);
        }
        if ((LEFT_IMAGE_VISIBLE & mChildVisible) == 0) {
            mLeftImageView.setVisibility(View.GONE);
        } else {
            mLeftImageView.setVisibility(View.VISIBLE);
        }
        if ((TITLE_TEXT_VISIBLE & mChildVisible) == 0) {
            mTitleTextView.setVisibility(View.GONE);
        } else {
            mTitleTextView.setVisibility(View.VISIBLE);
        }
        if ((RIGHT_TEXT_VISIBLE & mChildVisible) == 0) {
            mRightTextView.setVisibility(View.GONE);
        } else {
            mRightTextView.setVisibility(View.VISIBLE);
        }
        if ((RIGHT_IMAGE_VISIBLE & mChildVisible) == 0) {
            mRightImageView.setVisibility(View.GONE);
        } else {
            mRightImageView.setVisibility(View.VISIBLE);
        }
        if ((RIGHT_IMAGE2_VISIBLE & mChildVisible) == 0) {
            mRightImageView2.setVisibility(View.GONE);
        } else {
            mRightImageView2.setVisibility(View.VISIBLE);
        }
        if ((BOTTOM_LINE_VISIBLE & mChildVisible) == 0) {
            mBottomLine.setVisibility(View.GONE);
        } else {
            mBottomLine.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        if (listener == null) {
            return;
        }
        switch (v.getId()) {
            case R.id.left_text:
                listener.onAppbarClick(v, Appbar.OnAppbarClickListener.POS_LEFT_TEXT);
                break;
            case R.id.left_image:
                listener.onAppbarClick(v, Appbar.OnAppbarClickListener.POS_LEFT_IMAGE);
                break;
            case R.id.title_text:
                listener.onAppbarClick(v, Appbar.OnAppbarClickListener.POS_TITLE_TEXT);
                break;
            case R.id.right_text:
                listener.onAppbarClick(v, Appbar.OnAppbarClickListener.POS_RIGHT_TEXT);
                break;
            case R.id.right_image:
                listener.onAppbarClick(v, Appbar.OnAppbarClickListener.POS_RIGHT_IMAGE);
                break;
            case R.id.right_image2:
                listener.onAppbarClick(v, Appbar.OnAppbarClickListener.POS_RIGHT_IMAGE2);
                break;
        }
    }

    public void setTitleText(String text) {
        mTitleTextView.setText(text);
    }

    public void setTitleTextVisibility(int visibility) {
        mTitleTextView.setVisibility(visibility);
    }

    public TextView getRightTextView() {
        return mRightTextView;
    }

    public void setRightText(String text) {
        mRightTextView.setText(text);
    }

    public void setRightTextColor(int color) {
        mRightTextView.setTextColor(color);
    }

    public void setRightVisibility(int visibility) {
        mRightTextView.setVisibility(visibility);
    }

    public void setRightTextClickable(boolean clickable) {
        mRightTextView.setSelected(clickable);
        mRightTextView.setClickable(clickable);
    }

    public ImageView getRightImageView() {
        return mRightImageView;
    }

    public boolean getRightImageViewClickable() {
        return mRightImageView.isClickable();
    }

    public void setRightImageRes(int resId) {
        mRightImageView.setImageResource(resId);
    }

    public void setRightImageVisibility(int visibility) {
        mRightImageView.setVisibility(visibility);
    }

    public void setRightImageTagVisibility(int visibility) {
        mRightImageTagView.setVisibility(visibility);
    }

    public void setRightImageClickable(boolean clickable) {
        mRightImageView.setSelected(clickable);
        mRightImageView.setClickable(clickable);
    }

    public void setLeftImageVisible(int visibility) {
        mLeftImageView.setVisibility(visibility);
    }

    public View getRightTagView() {
        return mRightImageTagView;
    }

    public ImageView getLeftImageView() {
        return mLeftImageView;
    }


    public interface OnAppbarClickListener {
        int POS_LEFT_TEXT = 0;
        int POS_LEFT_IMAGE = 1;
        int POS_TITLE_TEXT = 2;
        int POS_TITLE_IMAGE = 3;
        int POS_RIGHT_TEXT = 4;
        int POS_RIGHT_IMAGE = 5;
        int POS_SUBTITLE_TEXT = 6;
        int POS_RIGHT_IMAGE2 = 7;

        void onAppbarClick(View view, int postion);
    }

    public void setOnAppbarClickListener(Appbar.OnAppbarClickListener listener) {
        setOnAppbarClickListener(listener, false);
    }

    public void setOnAppbarClickListener(Appbar.OnAppbarClickListener listener, boolean forced) {
        if (this.listener == null || !this.forced || forced) {
            this.listener = listener;
        }
        this.forced = forced;
    }
}
