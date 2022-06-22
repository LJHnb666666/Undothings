package com.google.samples.apps.sunflower.bindings;

import android.view.View;

public class BindingAdapter {

    // TextView 的显示和因此   靠此isGone
    @androidx.databinding.BindingAdapter("isGone")
    public static void bindISGone(View view, boolean isGone) {
        view.setVisibility(isGone ? View.GONE : View.VISIBLE);
    }
}
