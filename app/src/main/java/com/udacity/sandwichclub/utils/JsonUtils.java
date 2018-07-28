package com.udacity.sandwichclub.utils;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        JSONObject sandwichObject = new JSONObject(json);

        // name object
        JSONObject name = sandwichObject.getJSONObject("name");

        // mainName
        String mainName = name.getString("mainName");

        // Also known as
        JSONArray alsoKnownAs = name.getJSONArray("alsoKnownAs");
        List<String> knowAs = getStrings(alsoKnownAs);

        // Place of origin
        String placeOfOrigin = sandwichObject.getString("placeOfOrigin");

        // Description
        String description = sandwichObject.getString("description");

        // Image
        String image = sandwichObject.getString("image");

        // Ingredients
        JSONArray ingredientsJson = sandwichObject.getJSONArray("ingredients");
        List<String> ingredients = getStrings(ingredientsJson);

        return new Sandwich(mainName, knowAs, placeOfOrigin, description, image, ingredients);
    }

    @NonNull
    private static List<String> getStrings(JSONArray jsonArray) throws JSONException {
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            strings.add(jsonArray.getString(i));
        }
        return strings;
    }
}
