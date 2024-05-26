package com.zamdimon.graph_plotting.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.zamdimon.graph_plotting.R;
import com.zamdimon.graph_plotting.logic.HarmonicConfig;

public class HarmonicConfigConfirmDialog extends DialogFragment {
    private final HarmonicConfig config;

    public HarmonicConfigConfirmDialog(@NonNull HarmonicConfig config) {
        this.config = config;
    }

    private String formMessage() {
        return "Left limit: " + config.getLeftLimit() + "\n" +
                "Right limit: " + config.getRightLimit() + "\n" +
                "Cyclic frequency: " + config.getCyclicFrequency();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
        builder.setTitle(R.string.config_upload_title)
                .setMessage(formMessage())
                .setPositiveButton(R.string.config_upload_confirm, (dialog, id) -> {

                })
                .setNegativeButton(R.string.config_upload_cancel, (dialog, id) -> {

                });

        return builder.create();
    }
}
