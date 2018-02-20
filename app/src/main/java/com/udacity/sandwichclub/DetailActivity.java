package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    TextView origin, description, ingredients, also_known;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        ImageView ingredientsIv = findViewById(R.id.image_iv);
        origin = findViewById(R.id.origin_tv); // origin,description,ingredients,also_known
        description = findViewById(R.id.description_tv);
        ingredients = findViewById(R.id.ingredients_tv);
        also_known = findViewById(R.id.also_known_tv);


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

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        //        Log.e("tag", position + " is " + json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }


        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        String s1 = sandwich.getPlaceOfOrigin();

        if (!s1.isEmpty()) {

            origin.setText(sandwich.getPlaceOfOrigin());

        } else if (sandwich.getPlaceOfOrigin().equals("") || TextUtils.isEmpty(s1)) {
            origin.setText("---");
        }

        description.setText(sandwich.getDescription());


        String getAlsoKnownAs = String.valueOf(sandwich.getAlsoKnownAs());
        String getAlsoKnownAsfinal = getAlsoKnownAs.substring(1, getAlsoKnownAs.length() - 1);
        if (getAlsoKnownAsfinal != null && !getAlsoKnownAsfinal.isEmpty()) {
//            boolean s = !getAlsoKnownAs.isEmpty();
//            Log.e("TAG", "populateUI: "+ (getAlsoKnownAs != null) +"  "+ s + "getAlsoKnownAs= "+ getAlsoKnownAs);
            also_known.setText(getAlsoKnownAsfinal);

        } else {
            also_known.setText("---");
        }

        String sandwaichIngredients = String.valueOf(sandwich.getIngredients());
        if (sandwaichIngredients != null && !sandwaichIngredients.isEmpty()) {
            ingredients.setText(TextUtils.join(", ",sandwich.getIngredients()));
           String s =  TextUtils.join(",",sandwich.getIngredients());
          //  Log.e("TAG", "TEST textutils.join result. before was like this ("+sandwich.getIngredients()+ ") and after like this : ("+ s +")");

        } else {
            ingredients.setText("---");
        }

    }
}
