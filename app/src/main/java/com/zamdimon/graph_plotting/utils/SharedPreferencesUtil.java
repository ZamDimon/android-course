package com.zamdimon.graph_plotting.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Map;

/**
 * Utility class for handling SharedPreferences
 */
public class SharedPreferencesUtil {
    /**
     * Converts SharedPreferences to JSON Object
     * @param context the context of the application
     * @param prefsName the name of the SharedPreferences file
     * @return JSON Object containing the SharedPreferences
     */
    public static JSONObject getSharedPreferencesAsJson(Context context, String prefsName) {
        // Retrieve SharedPreferences
        SharedPreferences sharedPreferences = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE);
        Map<String, ?> allEntries = sharedPreferences.getAll();

        // Convert to JSON Object
        JSONObject jsonObject = new JSONObject();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            try {
                jsonObject.put(entry.getKey(), entry.getValue());
            } catch (JSONException e) {
                Log.e("SharedPreferencesUtil", "Error putting key-value into json object", e);
            }
        }
        return jsonObject;
    }

    /**
     * Saves JSON Object to file
     * @param context the context of the application
     * @param uri the URI of the file to save
     * @param jsonObject the JSON Object to save
     */
    public static void saveJsonToFile(Context context, Uri uri, JSONObject jsonObject) {
        try {
            OutputStream outputStream = context.getContentResolver().openOutputStream(uri);
            if (outputStream != null) {
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
                outputStreamWriter.write(jsonObject.toString());
                outputStreamWriter.close();
                outputStream.close();
            }
        } catch (IOException e) {
            Log.e("SharedPreferencesUtil", "Error saving JSON to file", e);
        }
    }
}
