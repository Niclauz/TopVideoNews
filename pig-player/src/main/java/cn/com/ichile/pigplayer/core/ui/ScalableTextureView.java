package cn.com.ichile.pigplayer.core.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Matrix;
import android.os.Build;
import android.util.AttributeSet;
import android.view.TextureView;

import cn.com.ichile.pigplayer.utils.Logger;

/**
 * FBI WARNING * MAGIC * DO NOT TOUCH *
 * Created by Nick Wong on 2017/2/15.
 * <p>
 * https://github.com/danylovolokh/VideoPlayerManager
 */

public class ScalableTextureView extends TextureView {

    private static final String TAG = "ScalableTextureView";
    private ScaleType mScaleType;


    //// TODO: 2017/2/15
    private Integer mContentWidth;
    private Integer mContentHeight;

    private float mPivotPointX = 0f;
    private float mPivotPointY = 0f;

    private float mContentScaleX = 1f;
    private float mContentScaleY = 1f;

    private float mContentRotation = 0f;

    private float mContentScaleMultiplier = 1f;

    private int mContentX = 0;
    private int mContentY = 0;

    private final Matrix mTransformMatrix = new Matrix();

    /**
     * scale type
     */
    public enum ScaleType {
        CENTER_CROP, TOP, BOTTOM, FILL
    }

    /**
     * Creates a new TextureView.
     *
     * @param context The context to associate this view with.
     */
    public ScalableTextureView(Context context) {
        super(context);
    }

    /**
     * Creates a new TextureView.
     *
     * @param context The context to associate this view with.
     * @param attrs   The attributes of the XML tag that is inflating the view.
     */
    public ScalableTextureView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Creates a new TextureView.
     *
     * @param context      The context to associate this view with.
     * @param attrs        The attributes of the XML tag that is inflating the view.
     * @param defStyleAttr An attribute in the current theme that contains a
     *                     reference to a style resource that supplies default values for
     */
    public ScalableTextureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Creates a new TextureView.
     *
     * @param context      The context to associate this view with.
     * @param attrs        The attributes of the XML tag that is inflating the view.
     * @param defStyleAttr An attribute in the current theme that contains a
     *                     reference to a style resource that supplies default values for
     *                     the view. Can be 0 to not look for defaults.
     * @param defStyleRes  A resource identifier of a style resource that
     *                     supplies default values for the view, used only if
     *                     defStyleAttr is 0 or can not be found in the theme. Can be 0
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ScalableTextureView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setScaleType(ScaleType scaleType) {
        mScaleType = scaleType;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mContentWidth != null && mContentHeight != null) {
            updateTextureViewSize();
        }
    }

    /**
     * Remeasure TextureViewSize
     */
    public void updateTextureViewSize() {
        Logger.d(TAG, ">> updateTextureViewSize");
        if (mContentHeight == null || mContentWidth == null) {
            throw new RuntimeException("content size can't be null");
        }

        //current view size
        float viewWidth = getMeasuredWidth();
        float viewHeight = getMeasuredHeight();

        //size we want
        float contentWidth = mContentWidth;
        float contentHeight = mContentHeight;

        //scale rate
        float scaleX = 0.1f;
        float scaleY = 0.1f;

        Logger.v(TAG, "updateTextureViewSize, mContentWidth " + mContentWidth + ", mContentHeight " + mContentHeight + ", mScaleType " + mScaleType);
        Logger.v(TAG, "updateTextureViewSize, viewWidth " + viewWidth + ", viewHeight " + viewHeight);

        switch (mScaleType) {
            case FILL:
                if (viewWidth > viewHeight) {   // device in landscape
                    scaleX = (viewHeight * contentWidth) / (viewWidth * contentHeight);
                } else {
                    scaleY = (viewWidth * contentHeight) / (viewHeight * contentWidth);
                }
                break;
            case BOTTOM:
            case CENTER_CROP:
            case TOP:
                if (contentWidth > viewWidth && contentHeight > viewHeight) {
                    scaleX = contentWidth / viewWidth;
                    scaleY = contentHeight / viewHeight;
                } else if (contentWidth < viewWidth && contentHeight < viewHeight) {
                    scaleY = viewWidth / contentWidth;
                    scaleX = viewHeight / contentHeight;
                } else if (viewWidth > contentWidth) {
                    scaleY = (viewWidth / contentWidth) / (viewHeight / contentHeight);
                } else if (viewHeight > contentHeight) {
                    scaleX = (viewHeight / contentHeight) / (viewWidth / contentWidth);
                }
                break;
        }

        Logger.v(TAG, "updateTextureViewSize, scaleX " + scaleX + ", scaleY " + scaleY);

        // Calculate pivot points, in our case crop from center
        float pivotPointX;
        float pivotPointY;

        switch (mScaleType) {
            case TOP:
                pivotPointX = 0;
                pivotPointY = 0;
                break;
            case BOTTOM:
                pivotPointX = viewWidth;
                pivotPointY = viewHeight;
                break;
            case CENTER_CROP:
                pivotPointX = viewWidth / 2;
                pivotPointY = viewHeight / 2;
                break;
            case FILL:
                pivotPointX = mPivotPointX;
                pivotPointY = mPivotPointY;
                break;
            default:
                throw new IllegalStateException("pivotPointX, pivotPointY for ScaleType " + mScaleType + " are not defined");
        }

        Logger.v(TAG, "updateTextureViewSize, pivotPointX " + pivotPointX + ", pivotPointY " + pivotPointY);

        float fitCoef = 1;
        switch (mScaleType) {
            case FILL:
                break;
            case BOTTOM:
            case CENTER_CROP:
            case TOP:
                if (mContentHeight > mContentWidth) { //Portrait video
                    fitCoef = viewWidth / (viewWidth * scaleX);
                } else { //Landscape video
                    fitCoef = viewHeight / (viewHeight * scaleY);
                }
                break;
        }

        mContentScaleX = fitCoef * scaleX;
        mContentScaleY = fitCoef * scaleY;

        mPivotPointX = pivotPointX;
        mPivotPointY = pivotPointY;

        updateMatrixScaleRotate();
        Logger.d(TAG, "<< updateTextureViewSize");
    }


    private void updateMatrixScaleRotate() {
        Logger.d(TAG, ">> updateMatrixScaleRotate, mContentRotation " + mContentRotation + ", mContentScaleMultiplier " + mContentScaleMultiplier + ", mPivotPointX " + mPivotPointX + ", mPivotPointY " + mPivotPointY);

        mTransformMatrix.reset();
        mTransformMatrix.setScale(mContentScaleX * mContentScaleMultiplier, mContentScaleY * mContentScaleMultiplier, mPivotPointX, mPivotPointY);
        mTransformMatrix.postRotate(mContentRotation, mPivotPointX, mPivotPointY);
        setTransform(mTransformMatrix);
        Logger.d(TAG, "<< updateMatrixScaleRotate, mContentRotation " + mContentRotation + ", mContentScaleMultiplier " + mContentScaleMultiplier + ", mPivotPointX " + mPivotPointX + ", mPivotPointY " + mPivotPointY);
    }

    private void updateMatrixTranslate() {

        Logger.d(TAG, "updateMatrixTranslate, mContentX " + mContentX + ", mContentY " + mContentY);


        float scaleX = mContentScaleX * mContentScaleMultiplier;
        float scaleY = mContentScaleY * mContentScaleMultiplier;

        mTransformMatrix.reset();
        mTransformMatrix.setScale(scaleX, scaleY, mPivotPointX, mPivotPointY);
        mTransformMatrix.postTranslate(mContentX, mContentY);
        setTransform(mTransformMatrix);
    }

    @Override
    public void setRotation(float degrees) {

        Logger.d(TAG, "setRotation, degrees " + degrees + ", mPivotPointX " + mPivotPointX + ", mPivotPointY " + mPivotPointY);

        mContentRotation = degrees;

        updateMatrixScaleRotate();
    }

    @Override
    public float getRotation() {
        return mContentRotation;
    }

    @Override
    public void setPivotX(float pivotX) {
        Logger.d(TAG, "setPivotX, pivotX " + pivotX);

        mPivotPointX = pivotX;
    }

    @Override
    public void setPivotY(float pivotY) {
        Logger.d(TAG, "setPivotY, pivotY " + pivotY);

        mPivotPointY = pivotY;
    }

    @Override
    public float getPivotX() {
        return mPivotPointX;
    }

    @Override
    public float getPivotY() {
        return mPivotPointY;
    }

    public float getContentAspectRatio() {
        return mContentWidth != null && mContentHeight != null
                ? (float) mContentWidth / (float) mContentHeight
                : 0;
    }

    /**
     * Use it to animate TextureView content x position
     *
     * @param x
     */
    public final void setContentX(float x) {
        mContentX = (int) x - (getMeasuredWidth() - getScaledContentWidth()) / 2;
        updateMatrixTranslate();
    }

    /**
     * Use it to animate TextureView content x position
     *
     * @param y
     */
    public final void setContentY(float y) {
        mContentY = (int) y - (getMeasuredHeight() - getScaledContentHeight()) / 2;
        updateMatrixTranslate();
    }

    protected final float getContentX() {
        return mContentX;
    }

    protected final float getContentY() {
        return mContentY;
    }

    /**
     * Use it to set content of a TextureView in the center of TextureView
     */
    public void centralizeContent() {
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        int scaledContentWidth = getScaledContentWidth();
        int scaledContentHeight = getScaledContentHeight();


        Logger.d(TAG, "centralizeContent, measuredWidth " + measuredWidth + ", measuredHeight " + measuredHeight + ", scaledContentWidth " + scaledContentWidth + ", scaledContentHeight " + scaledContentHeight);

        mContentX = 0;
        mContentY = 0;

        Logger.d(TAG, "centerVideo, mContentX " + mContentX + ", mContentY " + mContentY);

        updateMatrixScaleRotate();
    }

    public Integer getScaledContentWidth() {
        return (int) (mContentScaleX * mContentScaleMultiplier * getMeasuredWidth());
    }

    public Integer getScaledContentHeight() {
        return (int) (mContentScaleY * mContentScaleMultiplier * getMeasuredHeight());
    }

    public float getContentScale() {
        return mContentScaleMultiplier;
    }

    public void setContentScale(float contentScale) {
        Logger.d(TAG, "setContentScale, contentScale " + contentScale);

        mContentScaleMultiplier = contentScale;
        updateMatrixScaleRotate();
    }

    protected final void setContentHeight(int height) {
        mContentHeight = height;
    }

    protected final Integer getContentHeight() {
        return mContentHeight;
    }

    protected final void setContentWidth(int width) {
        mContentWidth = width;
    }

    protected final Integer getContentWidth() {
        return mContentWidth;
    }

}
