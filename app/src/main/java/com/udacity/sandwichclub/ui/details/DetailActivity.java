package com.udacity.sandwichclub.ui.details;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.R;
import com.udacity.sandwichclub.databinding.ActivityDetailBinding;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.ui.sandwichlist.SandwichListViewModel;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private ActivityDetailBinding mBinding;

    private SandwichViewModel mViewModel;

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

        mViewModel = obtainViewModel(this);

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
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void populateUI(Sandwich sandwich) {
        // sandwich image
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(mBinding.imageRecipe);

        // sandwich main name
        mBinding.textSandwichName.setText(sandwich.getMainName());
        // sandwich description
        mBinding.descriptionTv.setText(sandwich.getDescription());
        // sandwich origin
        mBinding.originTv.setText(sandwich.getPlaceOfOrigin());

    }
}
