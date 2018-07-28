package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        JSONObject sandwichObject = new JSONObject(json);

        // get name object
        JSONObject name = sandwichObject.getJSONObject("name");

        String mainName = name.getString("mainName");

        JSONArray alsoKnownAs = name.getJSONArray("alsoKnownAs");
        ArrayList<String> knowAs = new ArrayList<>();
        for (int i = 0; i < alsoKnownAs.length(); i++) {
            knowAs.add(alsoKnownAs.getString(i));
        }

        String placeOfOrigin = sandwichObject.getString("placeOfOrigin");
        String description = sandwichObject.getString("description");
        String image = sandwichObject.getString("image");

        JSONArray ingredientsJson = sandwichObject.getJSONArray("ingredients");
        ArrayList<String> ingredients = new ArrayList<>();
        for (int i = 0; i < ingredientsJson.length(); i++) {
            ingredients.add(ingredientsJson.getString(i));
        }

        return new Sandwich(mainName, knowAs, placeOfOrigin, description, image, ingredients);
    }
}
