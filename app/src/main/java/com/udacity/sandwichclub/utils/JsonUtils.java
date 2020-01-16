package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    private final static String TAG = JsonUtils.class.getName();

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = new Sandwich();
        try {
            JSONObject sandwich_data = new JSONObject(json);
            JSONObject name_data = sandwich_data.getJSONObject("name");

            sandwich.setMainName(name_data.getString("mainName"));
            sandwich.setDescription(sandwich_data.getString("description"));
            sandwich.setPlaceOfOrigin(sandwich_data.getString("placeOfOrigin"));
            sandwich.setAlsoKnownAs(extractJSONArrayAsStringCollection(name_data.getJSONArray("alsoKnownAs")));
            sandwich.setImage(sandwich_data.getString("image"));
            sandwich.setIngredients(extractJSONArrayAsStringCollection(sandwich_data.getJSONArray("ingredients")));

            return sandwich;
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());

            return null;
        }
    }

    private static List<String> extractJSONArrayAsStringCollection(JSONArray jsonArray) throws JSONException {
        List<String> list = new ArrayList<>();
        for(int index = 0; index < jsonArray.length(); index++){
            list.add(jsonArray.getString(index));
        }

        return list;
    }
}
