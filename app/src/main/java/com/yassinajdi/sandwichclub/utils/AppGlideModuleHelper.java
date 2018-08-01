package com.yassinajdi.sandwichclub.utils;

import android.content.Context;
import android.support.annotation.NonNull;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;
import com.yassinajdi.sandwichclub.R;

@GlideModule
public class AppGlideModuleHelper extends AppGlideModule {

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        builder.setDefaultRequestOptions(
                new RequestOptions()
                        .placeholder(R.color.md_grey_200)
        );
    }
}
