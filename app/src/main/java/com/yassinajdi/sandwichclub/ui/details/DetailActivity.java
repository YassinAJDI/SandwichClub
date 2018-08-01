package com.yassinajdi.sandwichclub.ui.details;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.flexbox.FlexboxLayout;
import com.yassinajdi.sandwichclub.R;
import com.yassinajdi.sandwichclub.databinding.ActivityDetailBinding;
import com.yassinajdi.sandwichclub.model.Sandwich;
import com.yassinajdi.sandwichclub.utils.GlideApp;
import com.yassinajdi.sandwichclub.utils.UiUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private ActivityDetailBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        final int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        setupToolbar();

        SandwichViewModel mViewModel = obtainViewModel(this, position);

        // Subscribe to sandwich changes
        mViewModel.getSandwich().observe(this, new Observer<Sandwich>() {
            @Override
            public void onChanged(@Nullable Sandwich sandwich) {
                if (sandwich != null) {
                    populateUI(sandwich);
                } else {
                    closeOnError();
                }
            }
        });
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private SandwichViewModel obtainViewModel(FragmentActivity activity, int position) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication(), position);
        return ViewModelProviders.of(activity, factory).get(SandwichViewModel.class);
    }

    private void setupToolbar() {
        Toolbar toolbar = mBinding.toolbar;
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    private void populateUI(Sandwich sandwich) {
        // Sandwich image
        GlideApp.with(this)
                .load(sandwich.getImage())
                .placeholder(R.color.md_grey_200)
                .into(mBinding.imageRecipe);

        // Sandwich main name
        mBinding.textSandwichName.setText(sandwich.getMainName());

        // Sandwich origin
        String placeOfOrigin = sandwich.getPlaceOfOrigin();
        if (placeOfOrigin.isEmpty()) {
            mBinding.textOrigin.setVisibility(View.GONE);
        } else {
            UiUtils.setTextViewDrawableColor(this, mBinding.textOrigin, R.color.text_black_54);
            mBinding.textOrigin.setText(placeOfOrigin);
        }

        // Programmatically create & add "also known as" labels
        List<String> names = sandwich.getAlsoKnownAs();
        if (!names.isEmpty()) {
            for (String name : names) {
                TextView textView = new TextView(this);
                textView.setText(name);
                textView.setBackground(ContextCompat.getDrawable(this, R.drawable.chip_shape));
                TextViewCompat.setTextAppearance(textView, R.style.Chips);
                FlexboxLayout.LayoutParams layoutParams = new FlexboxLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0, 0, 8, 8);
                textView.setLayoutParams(layoutParams);
                mBinding.flexbox.addView(textView);
            }
        } else {
            mBinding.flexbox.setVisibility(View.GONE);
        }

        // Sandwich description
        UiUtils.setTextViewDrawableColor(this, mBinding.textView3, R.color.text_black_54);
        mBinding.descriptionTv.setText(sandwich.getDescription());

        // Ingredients List
        UiUtils.setTextViewDrawableColor(this, mBinding.textView4, R.color.text_black_54);
        List<String> ingredients = sandwich.getIngredients();
        for (String ingredient : ingredients) {
            TextView textView = new TextView(this);
            textView.setText(ingredient);
            TextViewCompat.setTextAppearance(textView, R.style.Chips);
            textView.setPadding(0, 32, 0, 32);
            textView.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(
                    this, R.drawable.bullet), null, null, null);
            textView.setBackground(ContextCompat.getDrawable(this, R.drawable.dashed_line));
            textView.setCompoundDrawablePadding(32);
            mBinding.ingredientsList.addView(textView);
        }

        mBinding.executePendingBindings();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
