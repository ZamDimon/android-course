package com.zamdimon.graph_plotting.storage;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.activity.result.ActivityResult;
import androidx.fragment.app.FragmentManager;

import com.google.gson.Gson;
import com.zamdimon.graph_plotting.dialogs.HarmonicConfigConfirmDialog;
import com.zamdimon.graph_plotting.dialogs.HarmonicConfigErrorDialog;
import com.zamdimon.graph_plotting.logic.HarmonicConfig;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.function.Consumer;

/**
 * Helper class for managing the upload of HarmonicConfig
 */
public class HarmonicConfigUpload {
    /**
     * Handles the result of the upload activity
     * @param context the context of the application
     * @param result the result of the activity
     * @param fragmentManager the fragment manager
     * @param acceptCallback the callback being called when the user accepts the upload
     */
    public static void onUploadActivityResult(Context context, ActivityResult result, FragmentManager fragmentManager, Consumer<HarmonicConfig> acceptCallback) {
        if (result.getResultCode() != Activity.RESULT_OK) {
            return;
        }

        Intent data = result.getData();
        if (data == null) {
            return;
        }
        Uri chooseFileUri = data.getData();
        if (chooseFileUri == null) {
            return;
        }
        HarmonicConfig config = getFileContent(chooseFileUri, context.getContentResolver());
        if (config == null) {
            // Displaying an error
            HarmonicConfigErrorDialog errorDialog = new HarmonicConfigErrorDialog();
            final String UPLOAD_ERROR_DIALOG_TAG = "error_dialog";
            errorDialog.show(fragmentManager, UPLOAD_ERROR_DIALOG_TAG);
            return;
        }

        // Displaying a success button
        HarmonicConfigConfirmDialog dialog = new HarmonicConfigConfirmDialog(config, () -> acceptCallback.accept(config), () -> {});
        final String UPLOAD_DIALOG_TAG = "upload_dialog";
        dialog.show(fragmentManager, UPLOAD_DIALOG_TAG);
    }

    /**
     * Retrieves the content of the file in the form of HarmonicConfig
     * @param contentUri the URI of the content
     * @param contentResolver the content resolver
     * @return the HarmonicConfig object
     */
    public static HarmonicConfig getFileContent(Uri contentUri, ContentResolver contentResolver) {
        try {
            InputStream inputStream = contentResolver.openInputStream(contentUri);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder content = new StringBuilder();
            String line;

            while((line = reader.readLine()) != null) {
                content.append(line);
            }

            reader.close();

            Gson gson = new Gson();
            return gson.fromJson(content.toString(), HarmonicConfig.class);
        } catch(Exception e) {
            return null;
        }
    }

    /**
     * Forms the upload intent
     * @return the upload intent
     */
    public static Intent formUploadIntent() {
        Intent chooseFile;
        Intent intent;
        chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
        chooseFile.addCategory(Intent.CATEGORY_OPENABLE);
        final String MIME_TYPE = "application/json";
        chooseFile.setType(MIME_TYPE);

        final String CHOOSER_TITLE = "Choose a file";
        intent = Intent.createChooser(chooseFile, CHOOSER_TITLE);
        return intent;
    }
}
