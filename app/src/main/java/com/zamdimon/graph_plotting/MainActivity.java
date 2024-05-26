package com.zamdimon.graph_plotting;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.zamdimon.graph_plotting.databinding.ActivityMainBinding;
import com.zamdimon.graph_plotting.logic.HarmonicConfig;
import com.zamdimon.graph_plotting.logic.HarmonicPlot;
import com.zamdimon.graph_plotting.storage.HarmonicConfigPreferences;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Main activity of the application. It is responsible for managing the UI and the logic of the application.
 */
public class MainActivity extends AppCompatActivity {
    /** Represents the binding of the activity which makes
     * it easier to manage elements of the activity */
    private static ActivityMainBinding binding;

    /** Represents the preferences of the harmonic plot - basically, the settings */
    private HarmonicConfigPreferences plotPreferences;

    private static final int ACTIVITY_CHOOSE_FILE = 666;

    /**
     * Called when the activity is starting.
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        Context context = getApplicationContext();

        // Setting the harmonic plot
        plotPreferences = new HarmonicConfigPreferences(context);
        displayPlot();

        // Setting the top bar
        initializeTopBarMenu();

        // Setting sliders
        initializeFrequencySlider();
        initializeLimitsSlider();

        // Setting updating plot when shared preferences get modified
        subscribeOnSharedPreferenceChanges();
    }

    /**
     * Initializes the limits slider and subscribes to its changes.
     */
    private void initializeLimitsSlider() {
        // Setting limits from preferences
        float initialLeftLimit = plotPreferences.getConfig().getLeftLimit();
        float initialRightLimit = plotPreferences.getConfig().getRightLimit();
        binding.xLimitsSlider.setValues(initialLeftLimit, initialRightLimit);

        // Subscribing to updating the limits
        binding.xLimitsSlider.addOnChangeListener((slider, value, fromUser) -> {
            Context context = getApplicationContext();
            float leftLimit = binding.xLimitsSlider.getValues().get(0);
            float rightLimit = binding.xLimitsSlider.getValues().get(1);
            plotPreferences.saveLimits(context, leftLimit, rightLimit);
        });
    }

    /**
     * Initializes the frequency slider and subscribes to its changes.
     */
    private void initializeFrequencySlider() {
        // Setting frequency from preferences
        float initialCyclicFrequency = plotPreferences.getConfig().getCyclicFrequency();
        binding.frequencySlider.setValues(initialCyclicFrequency);

        // Subscribing to updating the frequency
        binding.frequencySlider.addOnChangeListener((rangeSlider, frequency, b) -> {
            Context context = getApplicationContext();
            plotPreferences.saveFrequency(context, frequency);
        });
    }

    /**
     * Subscribes to shared preferences changes and updates the plot when they change.
     */
    private void subscribeOnSharedPreferenceChanges() {
        SharedPreferences preferences = getSharedPreferences(HarmonicConfigPreferences.PREFERENCES_NAME, Context.MODE_PRIVATE);
        preferences.registerOnSharedPreferenceChangeListener((sharedPreferences, key) -> {
            displayPlot();
        });
    }

    /**
     * Displays the plot on the screen.
     */
    private void displayPlot() {
        HarmonicConfig config = plotPreferences.getConfig();
        HarmonicPlot plot = new HarmonicPlot(config);
        plot.drawPlot(binding.plot);
    }

    /**
     * Initializes the top bar menu.
     */
    private void initializeTopBarMenu() {
        binding.topAppBar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.save) {
                // Save the plot parameters
                return true;
            }
            if (item.getItemId() == R.id.upload) {
                onBrowse();
                // Import the plot parameters
                return true;
            }

            return false;
        });
    }

    public void onBrowse() {
        Intent chooseFile;
        Intent intent;
        chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
        chooseFile.addCategory(Intent.CATEGORY_OPENABLE);
        chooseFile.setType("text/plain");
        intent = Intent.createChooser(chooseFile, "Choose a file");
        startActivityForResult(intent, ACTIVITY_CHOOSE_FILE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ACTIVITY_CHOOSE_FILE) {
            if (resultCode != RESULT_OK || data == null) {
                super.onActivityResult(requestCode, resultCode, data);
                return;
            }
            Uri uri = data.getData();
            if (uri == null) {
                return;
            }
            String fileName = getFileName(uri);
            String fileContent = getFileContent(uri);
            Log.e("File content: ", fileContent);
            Log.e("File name: ", fileName);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public String getFileContent(Uri contentUri) {
        try {
            InputStream in = getContentResolver().openInputStream(contentUri);
            if (in != null) {
                BufferedReader r = new BufferedReader(new InputStreamReader(in));
                StringBuilder total = new StringBuilder();
                for (String line; (line = r.readLine()) != null; ) {
                    total.append(line).append('\n');
                }
                return total.toString();
            } else {
                Log.e("TAG", "Input stream is null");
            }
        } catch (Exception e) {
            Log.e("TAG", "Error while reading file by uri", e);
        }
        return "Could not read content!";
    }

    @SuppressLint("Range")
    public String getFileName(Uri contentUri) {
        String result = null;
        if (contentUri.getScheme() != null && contentUri.getScheme().equals("content")) {
            try (Cursor cursor = getContentResolver().query(contentUri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            }
        }
        if (result == null) {
            result = contentUri.getPath();
            if (result == null) {
                return null;
            }
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }
}

