package com.takwolf.android.insetswidget;

import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.ViewGroup;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleableRes;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsAnimationCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class InsetsLayoutHelper {
    @NonNull
    private final ViewGroup viewGroup;

    @InsetsWidget.InsetsType
    private int insetsType;
    private boolean insetsSmoothResize;
    private boolean insetsPaddingLeft;
    private boolean insetsPaddingTop;
    private boolean insetsPaddingRight;
    private boolean insetsPaddingBottom;
    @ColorInt
    private int insetsColorLeft;
    @ColorInt
    private int insetsColorTop;
    @ColorInt
    private int insetsColorRight;
    @ColorInt
    private int insetsColorBottom;

    @NonNull
    private Insets insets = InsetsUtils.EMPTY_INSETS;

    private final Paint paint = new Paint();

    public InsetsLayoutHelper(@NonNull ViewGroup viewGroup) {
        this.viewGroup = viewGroup;
        viewGroup.setWillNotDraw(true);
        ViewCompat.setOnApplyWindowInsetsListener(viewGroup, (v, windowInsets) -> {
            if (!insetsSmoothResize) {
                updateLayoutPadding(windowInsets);
            }
            return windowInsets;
        });
        ViewCompat.setWindowInsetsAnimationCallback(viewGroup, new WindowInsetsAnimationCompat.Callback(WindowInsetsAnimationCompat.Callback.DISPATCH_MODE_CONTINUE_ON_SUBTREE) {
            @NonNull
            @Override
            public WindowInsetsCompat onProgress(@NonNull WindowInsetsCompat windowInsets, @NonNull List<WindowInsetsAnimationCompat> runningAnimations) {
                if (insetsSmoothResize) {
                    updateLayoutPadding(windowInsets);
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

    public void initInsetsPaddingLeft(@NonNull TypedArray a, @StyleableRes int index) {
        insetsPaddingLeft = a.getBoolean(index, true);
    }

    public void initInsetsPaddingTop(@NonNull TypedArray a, @StyleableRes int index) {
        insetsPaddingTop = a.getBoolean(index, true);
    }

    public void initInsetsPaddingRight(@NonNull TypedArray a, @StyleableRes int index) {
        insetsPaddingRight = a.getBoolean(index, true);
    }

    public void initInsetsPaddingBottom(@NonNull TypedArray a, @StyleableRes int index) {
        insetsPaddingBottom = a.getBoolean(index, true);
    }

    public void initInsetsColorLeft(@NonNull TypedArray a, @StyleableRes int index) {
        insetsColorLeft = a.getColor(index, Color.TRANSPARENT);
    }

    public void initInsetsColorTop(@NonNull TypedArray a, @StyleableRes int index) {
        insetsColorTop = a.getColor(index, Color.TRANSPARENT);
    }

    public void initInsetsColorRight(@NonNull TypedArray a, @StyleableRes int index) {
        insetsColorRight = a.getColor(index, Color.TRANSPARENT);
    }

    public void initInsetsColorBottom(@NonNull TypedArray a, @StyleableRes int index) {
        insetsColorBottom = a.getColor(index, Color.TRANSPARENT);
    }

    public void onAttachedToWindow() {
        updateLayoutPadding(ViewCompat.getRootWindowInsets(viewGroup));
    }

    private void updateLayoutPadding(@Nullable WindowInsetsCompat windowInsets) {
        Insets insets = InsetsUtils.getInsets(windowInsets, insetsType);
        if (!this.insets.equals(insets)) {
            this.insets = insets;
            viewGroup.setPadding(
                    insetsPaddingLeft ? insets.left : 0,
                    insetsPaddingTop ? insets.top : 0,
                    insetsPaddingRight ? insets.right : 0,
                    insetsPaddingBottom ? insets.bottom : 0
            );
            boolean shouldDraw = false;
            if (insets.top > 0 && insetsColorTop != Color.TRANSPARENT) {
                shouldDraw = true;
            }
            if (insets.bottom > 0 && insetsColorBottom != Color.TRANSPARENT) {
                shouldDraw = true;
            }
            if (insets.left > 0 && insetsColorLeft != Color.TRANSPARENT) {
                shouldDraw = true;
            }
            if (insets.right > 0 && insetsColorRight != Color.TRANSPARENT) {
                shouldDraw = true;
            }
            viewGroup.setWillNotDraw(!shouldDraw);
            viewGroup.invalidate();
        }
    }

    public void dispatchDraw(@NonNull Canvas canvas) {
        int x = viewGroup.getScrollX();
        int y = viewGroup.getScrollY();
        int width = viewGroup.getWidth();
        int height = viewGroup.getHeight();
        if (insets.top > 0 && insetsColorTop != Color.TRANSPARENT) {
            paint.setColor(insetsColorTop);
            canvas.drawRect(x + insets.left, y, x + width - insets.right, y + insets.top, paint);
        }
        if (insets.bottom > 0 && insetsColorBottom != Color.TRANSPARENT) {
            paint.setColor(insetsColorBottom);
            canvas.drawRect(x + insets.left, y + height - insets.bottom, x + width - insets.right, y + height, paint);
        }
        if (insets.left > 0 && insetsColorLeft != Color.TRANSPARENT) {
            paint.setColor(insetsColorLeft);
            canvas.drawRect(x, y, x + insets.left, y + height, paint);
        }
        if (insets.right > 0 && insetsColorRight != Color.TRANSPARENT) {
            paint.setColor(insetsColorRight);
            canvas.drawRect(x + width - insets.right, y, x + width, y + height, paint);
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
            updateLayoutPadding(ViewCompat.getRootWindowInsets(viewGroup));
        }
    }

    public boolean isInsetsSmoothResize() {
        return insetsSmoothResize;
    }

    public void setInsetsSmoothResize(boolean insetsSmoothResize) {
        if (this.insetsSmoothResize != insetsSmoothResize) {
            this.insetsSmoothResize = insetsSmoothResize;
            if (!insetsSmoothResize) {
                updateLayoutPadding(ViewCompat.getRootWindowInsets(viewGroup));
            }
        }
    }

    public boolean getInsetsPaddingLeft() {
        return insetsPaddingLeft;
    }

    public void setInsetsPaddingLeft(boolean insetsPaddingLeft) {
        if (this.insetsPaddingLeft != insetsPaddingLeft) {
            this.insetsPaddingLeft = insetsPaddingLeft;
            updateLayoutPadding(ViewCompat.getRootWindowInsets(viewGroup));
        }
    }

    public boolean getInsetsPaddingTop() {
        return insetsPaddingTop;
    }

    public void setInsetsPaddingTop(boolean insetsPaddingTop) {
        if (this.insetsPaddingTop != insetsPaddingTop) {
            this.insetsPaddingTop = insetsPaddingTop;
            updateLayoutPadding(ViewCompat.getRootWindowInsets(viewGroup));
        }
    }

    public boolean getInsetsPaddingRight() {
        return insetsPaddingRight;
    }

    public void setInsetsPaddingRight(boolean insetsPaddingRight) {
        if (this.insetsPaddingRight != insetsPaddingRight) {
            this.insetsPaddingRight = insetsPaddingRight;
            updateLayoutPadding(ViewCompat.getRootWindowInsets(viewGroup));
        }
    }

    public boolean getInsetsPaddingBottom() {
        return insetsPaddingBottom;
    }

    public void setInsetsPaddingBottom(boolean insetsPaddingBottom) {
        if (this.insetsPaddingBottom != insetsPaddingBottom) {
            this.insetsPaddingBottom = insetsPaddingBottom;
            updateLayoutPadding(ViewCompat.getRootWindowInsets(viewGroup));
        }
    }

    @ColorInt
    public int getInsetsColorLeft() {
        return insetsColorLeft;
    }

    public void setInsetsColorLeft(@ColorInt int insetsColorLeft) {
        if (this.insetsColorLeft != insetsColorLeft) {
            this.insetsColorLeft = insetsColorLeft;
            viewGroup.invalidate();
        }
    }

    @ColorInt
    public int getInsetsColorTop() {
        return insetsColorTop;
    }

    public void setInsetsColorTop(@ColorInt int insetsColorTop) {
        if (this.insetsColorTop != insetsColorTop) {
            this.insetsColorTop = insetsColorTop;
            viewGroup.invalidate();
        }
    }

    @ColorInt
    public int getInsetsColorRight() {
        return insetsColorRight;
    }

    public void setInsetsColorRight(@ColorInt int insetsColorRight) {
        if (this.insetsColorRight != insetsColorRight) {
            this.insetsColorRight = insetsColorRight;
            viewGroup.invalidate();
        }
    }

    @ColorInt
    public int getInsetsColorBottom() {
        return insetsColorBottom;
    }

    public void setInsetsColorBottom(@ColorInt int insetsColorBottom) {
        if (this.insetsColorBottom != insetsColorBottom) {
            this.insetsColorBottom = insetsColorBottom;
            viewGroup.invalidate();
        }
    }
}
