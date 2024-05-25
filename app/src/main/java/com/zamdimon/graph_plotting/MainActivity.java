package com.zamdimon.graph_plotting;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.zamdimon.graph_plotting.databinding.ActivityMainBinding;
import com.zamdimon.graph_plotting.logic.HarmonicConfig;
import com.zamdimon.graph_plotting.logic.HarmonicPlot;
import com.zamdimon.graph_plotting.storage.HarmonicConfigPreferences;

public class MainActivity extends AppCompatActivity {
    /** Represents the binding of the activity which makes
     * it easier to manage elements of the activity */
    private static ActivityMainBinding binding;

    private HarmonicConfigPreferences plotPreferences;

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

    private void initializeLimitsSlider() {
        // Setting limits from preferences
        float initialLeftLimit = plotPreferences.getPrefences().getLeftLimit();
        float initialRightLimit = plotPreferences.getPrefences().getRightLimit();
        binding.xLimitsSlider.setValues(initialLeftLimit, initialRightLimit);

        // Subscribing to updating the limits
        binding.xLimitsSlider.addOnChangeListener((slider, value, fromUser) -> {
            Context context = getApplicationContext();
            float leftLimit = binding.xLimitsSlider.getValues().get(0);
            float rightLimit = binding.xLimitsSlider.getValues().get(1);
            plotPreferences.saveLimits(context, leftLimit, rightLimit);
        });
    }

    private void initializeFrequencySlider() {
        // Setting frequency from preferences
        float initialCyclicFrequency = plotPreferences.getPrefences().getCyclicFrequency();
        binding.frequencySlider.setValues(initialCyclicFrequency);

        // Subscribing to updating the frequency
        binding.frequencySlider.addOnChangeListener((rangeSlider, frequency, b) -> {
            Context context = getApplicationContext();
            plotPreferences.saveFrequency(context, frequency);
        });
    }

    private void subscribeOnSharedPreferenceChanges() {
        SharedPreferences preferences = getSharedPreferences(HarmonicConfigPreferences.PREFERENCES_NAME, Context.MODE_PRIVATE);
        preferences.registerOnSharedPreferenceChangeListener((sharedPreferences, key) -> {
            displayPlot();
        });
    }

    private void displayPlot() {
        HarmonicConfig config = plotPreferences.getPrefences();
        HarmonicPlot plot = new HarmonicPlot(config);
        plot.drawPlot(binding.plot);
    }

    private void initializeTopBarMenu() {
        binding.topAppBar.setOnMenuItemClickListener(item -> true);
    }
}

