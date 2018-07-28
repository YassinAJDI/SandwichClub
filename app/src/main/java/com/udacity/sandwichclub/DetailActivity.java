package com.udacity.sandwichclub;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.databinding.ActivityDetailBinding;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private ActivityDetailBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

//        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

//        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
//        String json = sandwiches[position];
//        Sandwich sandwich = JsonUtils.parseSandwichJson(json);


//        populateUI();

//        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

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
