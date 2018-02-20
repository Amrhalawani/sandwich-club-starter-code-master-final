package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {


    public static Sandwich parseSandwichJson(String json) {
        Log.e("TAG", " JsonUtils working now ");

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);

            String placeOfOrigin = jsonObject.getString("placeOfOrigin");
            String description = jsonObject.getString("description");
            String image = jsonObject.optString("image");

            JSONObject name_JO = jsonObject.getJSONObject("name");
            String mainName = name_JO.getString("mainName");
            JSONArray alsoKnownAs_JA = name_JO.getJSONArray("alsoKnownAs");
            List<String> alsoKnownAsL = list(alsoKnownAs_JA);

            JSONArray ingredients_JA = jsonObject.getJSONArray("ingredients");
            List<String> ingredientsList = list(ingredients_JA);
            Log.e("TAG", "mainName = "+ mainName + "\n" + "alsoKnownAsList "+ alsoKnownAsL  );

            Sandwich sandwich = new Sandwich(mainName, alsoKnownAsL, placeOfOrigin, description, image, ingredientsList);
            return sandwich;
            //     Log.e("TAG"," inside jsonutile "+ sandwich);
        } catch (JSONException e) {
            Log.e("TAG", "LocalizedMessage located at  " + e.getLocalizedMessage());
        }

        return null;

    }

    public static List<String> list(JSONArray jsonArray) throws JSONException {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(jsonArray.getString(i));
        }
        return list;
    }
}
