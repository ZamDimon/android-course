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

public class SharedPreferencesUtil {
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
