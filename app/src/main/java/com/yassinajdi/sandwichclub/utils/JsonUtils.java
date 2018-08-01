package com.yassinajdi.sandwichclub.utils;

import android.support.annotation.NonNull;

import com.yassinajdi.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private final static String KEY_NAME = "name";
    private final static String KEY_MAIN_NAME = "mainName";
    private final static String KEY_ALSO_KNOWN_AS = "alsoKnownAs";
    private final static String KEY_PLACE_OF_ORIGIN = "placeOfOrigin";
    private final static String KEY_DESCRIPTION = "description";
    private final static String KEY_IMAGE_URL = "image";
    private final static String KEY_INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        JSONObject sandwichObject = new JSONObject(json);

        // name object
        JSONObject name = sandwichObject.getJSONObject(KEY_NAME);

        // mainName
        String mainName = name.getString(KEY_MAIN_NAME);

        // Also known as
        JSONArray alsoKnownAs = name.getJSONArray(KEY_ALSO_KNOWN_AS);
        List<String> knowAs = getStrings(alsoKnownAs);

        // Place of origin
        String placeOfOrigin = sandwichObject.getString(KEY_PLACE_OF_ORIGIN);

        // Description
        String description = sandwichObject.getString(KEY_DESCRIPTION);

        // Image
        String image = sandwichObject.getString(KEY_IMAGE_URL);

        // Ingredients
        JSONArray ingredientsJson = sandwichObject.getJSONArray(KEY_INGREDIENTS);
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
