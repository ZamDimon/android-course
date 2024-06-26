package com.zamdimon.graph_plotting;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.zamdimon.graph_plotting.databinding.ActivityMainBinding;
import com.zamdimon.graph_plotting.logic.HarmonicConfig;
import com.zamdimon.graph_plotting.logic.HarmonicPlot;
import com.zamdimon.graph_plotting.storage.HarmonicConfigPreferences;
import com.zamdimon.graph_plotting.storage.HarmonicConfigSave;
import com.zamdimon.graph_plotting.storage.HarmonicConfigUpload;

/**
 * Main activity of the application. It is responsible for managing the UI and the logic of the application.
 */
public class MainActivity extends AppCompatActivity {
    /** Represents the binding of the activity which makes
     * it easier to manage elements of the activity */
    private ActivityMainBinding binding;

    /** Represents the preferences of the harmonic plot - basically, the settings */
    private HarmonicConfigPreferences plotPreferences;

    /**
     * Called when the activity is starting.
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

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

        // Setting the minimum separation between the limits to avoid cases
        // when left limit is exactly the right limit
        final float MIN_SEPARATION = 0.3f;
        binding.xLimitsSlider.setMinSeparationValue(MIN_SEPARATION);

        // Subscribing to updating the limits
        binding.xLimitsSlider.addOnChangeListener((slider, value, fromUser) -> {
            Context context = getApplicationContext();
            float leftLimit = binding.xLimitsSlider.getValues().get(0);
            float rightLimit = binding.xLimitsSlider.getValues().get(1);
            plotPreferences.saveLimits(context, leftLimit, rightLimit);
        });
    }

    /**
     * Refreshes the sliders with the values from the preferences.
     */
    public void refreshSliders() {
        float initialLeftLimit = plotPreferences.getConfig().getLeftLimit();
        float initialRightLimit = plotPreferences.getConfig().getRightLimit();
        binding.xLimitsSlider.setValues(initialLeftLimit, initialRightLimit);

        float initialCyclicFrequency = plotPreferences.getConfig().getCyclicFrequency();
        binding.frequencySlider.setValues(initialCyclicFrequency);
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
        preferences.registerOnSharedPreferenceChangeListener((sharedPreferences, key) -> displayPlot());
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
     * Registers the activity result launcher for saving the file.
     */
    private final ActivityResultLauncher<Intent> saveFileResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> HarmonicConfigSave.onSaveActivityResult(getApplicationContext(), result)
    );

    /**
     * Registers the activity result launcher for uploading the file.
     */
    private final ActivityResultLauncher<Intent> uploadFileResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> HarmonicConfigUpload.onUploadActivityResult(getApplicationContext(), result, getSupportFragmentManager(), (HarmonicConfig config) -> {
                // NOTE: it is quite painful to pass all the necessary parameters inside
                // the HarmonicConfigUpload class, so we just pass the Consumer to the method
                plotPreferences.saveConfig(getApplicationContext(), config);
                displayPlot();
                refreshSliders();
            }));

    /**
     * Initializes the top bar menu.
     */
    private void initializeTopBarMenu() {
        binding.topAppBar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.reset) {
                HarmonicConfig defaultConfig = new HarmonicConfig();
                plotPreferences.saveConfig(getApplicationContext(), defaultConfig);
                displayPlot();
                refreshSliders();
            }
            if (item.getItemId() == R.id.save) {
                Intent intent = HarmonicConfigSave.formSaveIntent();
                saveFileResultLauncher.launch(intent);
                return true;
            }
            if (item.getItemId() == R.id.upload) {
                Intent intent = HarmonicConfigUpload.formUploadIntent();
                uploadFileResultLauncher.launch(intent);
                return true;
            }

            return false;
        });
    }
}

