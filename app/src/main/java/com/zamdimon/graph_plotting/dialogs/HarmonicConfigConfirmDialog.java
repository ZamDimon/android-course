package com.zamdimon.graph_plotting.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.zamdimon.graph_plotting.R;
import com.zamdimon.graph_plotting.logic.HarmonicConfig;

/**
 * Dialog that is displayed when the configuration upload is successful.
 */
public class HarmonicConfigConfirmDialog extends DialogFragment {
    private final HarmonicConfig config;

    private final Runnable acceptCallback;

    private final Runnable cancelCallback;

    /**
     * Creates the dialog.
     * @param config the configuration to be displayed
     * @param acceptCallback the callback being called when the user accepts the upload
     * @param cancelCallback the callback being called when the user cancels the upload
     */
    public HarmonicConfigConfirmDialog(@NonNull HarmonicConfig config, Runnable acceptCallback, Runnable cancelCallback) {
        this.config = config;
        this.acceptCallback = acceptCallback;
        this.cancelCallback = cancelCallback;
    }

    /**
     * Forms the message of the dialog.
     * @return the message
     */
    private String formMessage() {
        return "Left limit: " + config.getLeftLimit() + "\n" +
                "Right limit: " + config.getRightLimit() + "\n" +
                "Cyclic frequency: " + config.getCyclicFrequency();
    }

    /**
     * Creates the dialog.
     * @param savedInstanceState the saved instance state
     * @return the dialog
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        FragmentActivity activity = getActivity();
        assert activity != null : "Activity must not be null";

        Context context = getContext();
        assert context != null : "Context must not be null";

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(activity);
        builder.setTitle(R.string.config_upload_title)
                .setMessage(formMessage())
                .setPositiveButton(R.string.config_upload_confirm, (dialog, id) -> {
                    acceptCallback.run();

                    // Displaying a toast
                    CharSequence text = "Upload successful!";
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                })
                .setNegativeButton(R.string.config_upload_cancel, (dialog, id) -> cancelCallback.run());

        return builder.create();
    }
}
