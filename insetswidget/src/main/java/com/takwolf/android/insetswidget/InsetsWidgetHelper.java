package com.takwolf.android.insetswidget;

import android.content.res.TypedArray;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleableRes;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsAnimationCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class InsetsWidgetHelper {
    @NonNull
    private final View view;

    @InsetsWidget.InsetsType
    private int insetsType;
    private boolean insetsSmoothResize;

    @NonNull
    private Insets insets = InsetsUtils.EMPTY_INSETS;

    public InsetsWidgetHelper(@NonNull View view) {
        this.view = view;
        ViewCompat.setOnApplyWindowInsetsListener(view, (v, windowInsets) -> {
            if (!insetsSmoothResize) {
                updateViewSize(windowInsets);
            }
            return windowInsets;
        });
        ViewCompat.setWindowInsetsAnimationCallback(view, new WindowInsetsAnimationCompat.Callback(WindowInsetsAnimationCompat.Callback.DISPATCH_MODE_CONTINUE_ON_SUBTREE) {
            @NonNull
            @Override
            public WindowInsetsCompat onProgress(@NonNull WindowInsetsCompat windowInsets, @NonNull List<WindowInsetsAnimationCompat> runningAnimations) {
                if (insetsSmoothResize) {
                    updateViewSize(windowInsets);
                }
                return windowInsets;
            }
        });
    }

    public void initInsetsType(@NonNull TypedArray a, @StyleableRes int index) {
        insetsType = a.getInt(index, InsetsWidget.INSETS_TYPE_NONE);
    }

    public void initInsetsSmoothResize(@NonNull TypedArray a, @StyleableRes int index) {
        insetsSmoothResize = a.getBoolean(index, true);
    }

    public void onAttachedToWindow() {
        updateViewSize(ViewCompat.getRootWindowInsets(view));
    }

    private void updateViewSize(@Nullable WindowInsetsCompat windowInsets) {
        Insets insets = InsetsUtils.getInsets(windowInsets, insetsType);
        if (!this.insets.equals(insets)) {
            this.insets = insets;
            view.requestLayout();
        }
    }

    public int getMeasuredWidth(int widthMeasureSpec) {
        return getInsetsViewSize(insets.left + insets.right, widthMeasureSpec);
    }

    public int getMeasuredHeight(int heightMeasureSpec) {
        return getInsetsViewSize(insets.top + insets.bottom, heightMeasureSpec);
    }

    private static int getInsetsViewSize(int requestSize, int measureSpec) {
        int measureMode = View.MeasureSpec.getMode(measureSpec);
        int measureSize = View.MeasureSpec.getSize(measureSpec);
        switch (measureMode) {
            case View.MeasureSpec.UNSPECIFIED:
                return requestSize;
            case View.MeasureSpec.EXACTLY:
                return measureSize;
            case View.MeasureSpec.AT_MOST:
                return Math.min(requestSize, measureSize);
            default:
                return 0;
        }
    }

    @NonNull
    public Insets getInsets() {
        return insets;
    }

    @InsetsWidget.InsetsType
    public int getInsetsType() {
        return insetsType;
    }

    public void setInsetsType(@InsetsWidget.InsetsType int insetsType) {
        if (this.insetsType != insetsType) {
            this.insetsType = insetsType;
            updateViewSize(ViewCompat.getRootWindowInsets(view));
        }
    }

    public boolean isInsetsSmoothResize() {
        return insetsSmoothResize;
    }

    public void setInsetsSmoothResize(boolean insetsSmoothResize) {
        if (this.insetsSmoothResize != insetsSmoothResize) {
            this.insetsSmoothResize = insetsSmoothResize;
            if (!insetsSmoothResize) {
                updateViewSize(ViewCompat.getRootWindowInsets(view));
            }
        }
    }
}
