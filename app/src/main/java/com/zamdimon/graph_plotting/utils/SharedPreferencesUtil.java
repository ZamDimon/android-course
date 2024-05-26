package com.zamdimon.graph_plotting.utils;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class SharedPreferencesUtil {

    public static void saveSharedPreferencesToJson(Context context, String prefsName, String fileName) {
        // Retrieve SharedPreferences
        SharedPreferences sharedPreferences = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE);
        Map<String, ?> allEntries = sharedPreferences.getAll();

        // Convert to JSON Object
        JSONObject jsonObject = new JSONObject();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            try {
                jsonObject.put(entry.getKey(), entry.getValue());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // Write JSON to File
        File file = new File(context.getFilesDir(), fileName);
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(jsonObject.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
