package com.takwolf.android.insetswidget.constraintlayout;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.takwolf.android.insetswidget.InsetsLayout;

public class InsetsConstraintLayout extends ConstraintLayout implements InsetsLayout {
    public InsetsConstraintLayout(@NonNull Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public InsetsConstraintLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public InsetsConstraintLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    public InsetsConstraintLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        // TODO
    }
}
