package com.zamdimon.graph_plotting.storage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;

import com.zamdimon.graph_plotting.utils.SharedPreferencesUtil;

import org.json.JSONObject;

/**
 * Helper class for managing the saving of HarmonicConfig
 */
public class HarmonicConfigSave {
    /**
     * Handles the result of the save activity
     * @param context the context of the application
     * @param result the result of the activity
     */
    public static void onSaveActivityResult(Context context, ActivityResult result) {
        if (result.getResultCode() != Activity.RESULT_OK) {
            return;
        }

        Intent data = result.getData();
        if (data == null) {
            return;
        }
        Uri createFileUri = data.getData();
        if (createFileUri == null) {
            return;
        }
        JSONObject jsonObject = SharedPreferencesUtil.getSharedPreferencesAsJson(context, HarmonicConfigPreferences.PREFERENCES_NAME);
        SharedPreferencesUtil.saveJsonToFile(context, createFileUri, jsonObject);

        // Displaying a toast
        CharSequence text = "File saved successfully!";
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    /**
     * Creates an intent for saving the .json file with preferences
     * @return the intent for saving the .json file
     */
    public static Intent formSaveIntent() {
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        final String MIME_TYPE = "application/json";
        intent.setType(MIME_TYPE);
        final String EXTRA_FILE_NAME = "shared_prefs.json";
        intent.putExtra(Intent.EXTRA_TITLE, EXTRA_FILE_NAME);

        return intent;
    }
}
