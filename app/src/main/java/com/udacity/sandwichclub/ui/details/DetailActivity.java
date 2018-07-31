package com.udacity.sandwichclub.ui.details;

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
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.flexbox.FlexboxLayout;
import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.R;
import com.udacity.sandwichclub.databinding.ActivityDetailBinding;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.ui.sandwichlist.SandwichListViewModel;
import com.udacity.sandwichclub.utils.UiUtils;

import java.util.List;

import timber.log.Timber;

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

        SandwichViewModel mViewModel = obtainViewModel(this);

        mViewModel.setCurrentPosition(position);

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

    private SandwichViewModel obtainViewModel(FragmentActivity activity) {
        return ViewModelProviders.of(activity).get(SandwichViewModel.class);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    private void populateUI(Sandwich sandwich) {
        // sandwich image
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(mBinding.imageRecipe);

        // sandwich main name
        mBinding.textSandwichName.setText(sandwich.getMainName());

        // sandwich origin
        String placeOfOrigin = sandwich.getPlaceOfOrigin();
        if (placeOfOrigin.isEmpty()) {
            mBinding.textOrigin.setVisibility(View.GONE);
        } else {
            UiUtils.setTextViewDrawableColor(this, mBinding.textOrigin, R.color.colorAccent);
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

        // sandwich description
        UiUtils.setTextViewDrawableColor(this, mBinding.textView3, R.color.colorAccent);
        mBinding.descriptionTv.setText(sandwich.getDescription());

        // ingredients
        UiUtils.setTextViewDrawableColor(this, mBinding.textView4, R.color.colorAccent);
        List<String> ingredients = sandwich.getIngredients();
        for (String ingredient : ingredients) {
            TextView textView = new TextView(this);
            textView.setText(ingredient);
            TextViewCompat.setTextAppearance(textView, R.style.Chips);
            textView.setPadding(0, 8, 0, 8);
            textView.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(
                    this, R.drawable.bullet), null, null, null);
            textView.setCompoundDrawablePadding(32);
            mBinding.ingredients.addView(textView);
        }

        mBinding.executePendingBindings();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
