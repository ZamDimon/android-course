package com.zamdimon.graph_plotting.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.zamdimon.graph_plotting.MainActivity;
import com.zamdimon.graph_plotting.R;
import com.zamdimon.graph_plotting.logic.HarmonicConfig;
import com.zamdimon.graph_plotting.storage.HarmonicConfigPreferences;

import java.util.function.Consumer;

public class HarmonicConfigConfirmDialog extends DialogFragment {
    private final HarmonicConfig config;

    private final Runnable acceptCallback;

    private final Runnable cancelCallback;

    public HarmonicConfigConfirmDialog(@NonNull HarmonicConfig config, Runnable acceptCallback, Runnable cancelCallback) {
        this.config = config;
        this.acceptCallback = acceptCallback;
        this.cancelCallback = cancelCallback;
    }

    private String formMessage() {
        return "Left limit: " + config.getLeftLimit() + "\n" +
                "Right limit: " + config.getRightLimit() + "\n" +
                "Cyclic frequency: " + config.getCyclicFrequency();
    }

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
                .setPositiveButton(R.string.config_upload_confirm, (dialog, id) -> acceptCallback.run())
                .setNegativeButton(R.string.config_upload_cancel, (dialog, id) -> cancelCallback.run());

        return builder.create();
    }
}
