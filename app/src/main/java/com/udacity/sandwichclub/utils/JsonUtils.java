package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);

        // get name object
        JSONObject name = jsonObject.getJSONObject("name");

        String mainName = jsonObject.getString("mainName");

        JSONArray alsoKnownAs = name.getJSONArray("alsoKnownAs");
        ArrayList<String> knowAs = new ArrayList<>();
        for (int i = 0; i < alsoKnownAs.length(); i++) {
            knowAs.add(alsoKnownAs.getString(i));
        }

        String placeOfOrigin = jsonObject.getString("placeOfOrigin");
        String description = jsonObject.getString("description");
        String image = jsonObject.getString("image");

        JSONArray ingredientsJson = name.getJSONArray("ingredients");
        ArrayList<String> ingredients = new ArrayList<>();
        for (int i = 0; i < ingredientsJson.length(); i++) {
            ingredients.add(ingredientsJson.getString(i));
        }

        return new Sandwich(mainName, knowAs, placeOfOrigin, description, image, ingredients);
    }
}
