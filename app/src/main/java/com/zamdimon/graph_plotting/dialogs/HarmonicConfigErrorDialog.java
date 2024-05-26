package com.zamdimon.graph_plotting.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.zamdimon.graph_plotting.R;

/**
 * Dialog that is displayed when the configuration upload fails.
 */
public class HarmonicConfigErrorDialog extends DialogFragment {
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
        builder.setTitle(R.string.config_upload_error_title)
                .setMessage(R.string.config_upload_error_message);

        return builder.create();
    }
}
