package com.zamdimon.graph_plotting.storage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.activity.result.ActivityResult;

import com.zamdimon.graph_plotting.utils.SharedPreferencesUtil;

import org.json.JSONObject;

public class HarmonicConfigSave {
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
    }

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