package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private Sandwich mSandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

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
        mSandwich = JsonUtils.parseSandwichJson(json);
        if (mSandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(mSandwich.getImage())
                .into(ingredientsIv);

        setTitle(mSandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        TextView alsoKnownAs = findViewById(R.id.also_known_tv);
        TextView placeOfOrigin = findViewById(R.id.origin_tv);
        TextView ingredients = findViewById(R.id.ingredients_tv);
        TextView description = findViewById(R.id.description_tv);

        if(mSandwich.getAlsoKnownAs().size() > 1){
            for(int idx = 0; idx < mSandwich.getAlsoKnownAs().size(); idx++){
                if(idx == mSandwich.getAlsoKnownAs().size() - 1)
                    alsoKnownAs.append("and " + mSandwich.getAlsoKnownAs().get(idx));
                else
                    alsoKnownAs.append(mSandwich.getAlsoKnownAs().get(idx) + ", ");
            }
        } else if(mSandwich.getAlsoKnownAs().size() == 1) {
            alsoKnownAs.append(mSandwich.getAlsoKnownAs().get(0));
        } else {
            alsoKnownAs.setText(getString(R.string.lbl_no_names_recorded));
        }

        if(mSandwich.getPlaceOfOrigin().isEmpty())
            placeOfOrigin.setText(getString(R.string.lbl_unknown));
        else
            placeOfOrigin.setText(mSandwich.getPlaceOfOrigin());

        if(mSandwich.getIngredients().size() > 1){
            for(int idx = 0; idx < mSandwich.getIngredients().size(); idx++){
                ingredients.append(mSandwich.getIngredients().get(idx) + "\n");
            }
        } else if(mSandwich.getIngredients().size() == 1) {
            ingredients.append(mSandwich.getAlsoKnownAs().get(0));
        } else {
            ingredients.setText(getString(R.string.lbl_unknown));
        }

        description.setText(mSandwich.getDescription());
    }
}
