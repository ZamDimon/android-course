package com.zamdimon.graph_plotting.storage;

import com.zamdimon.graph_plotting.logic.HarmonicConfig;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

/**
 * Class responsible for managing the preferences of the HarmonicConfig object.
 * Basically, it is a wrapper for HarmonicConfig which allows to save and load the configuration
 * from the SharedPreferences.
 */
public class HarmonicConfigPreferences {
    /** Name of the preferences file */
    public static final String PREFERENCES_NAME = "harmonic_config";

    /** Configuration file which is synced with the current shared preferences */
    private HarmonicConfig config;

    /**
     * Constructs the preferences wrapper. It loads the configuration from the shared preferences.
     * If the preferences are not set, it uses the default values. Updates the inner HarmonicConfig.
     * @param context the context of the application
     */
    public HarmonicConfigPreferences(@NonNull Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        float leftLimit = preferences.getFloat(HarmonicConfigFields.LEFT_LIMIT, HarmonicConfig.MIN_LEFT_LIMIT);
        float rightLimit = preferences.getFloat(HarmonicConfigFields.RIGHT_LIMIT, HarmonicConfig.MAX_RIGHT_LIMIT);
        float frequency = preferences.getFloat(HarmonicConfigFields.CYCLIC_FREQUENCY, HarmonicConfig.DEFAULT_CYCLIC_FREQUENCY);

        this.config = new HarmonicConfig(leftLimit, rightLimit, frequency);
    }

    /**
     * Saves the frequency to the shared preferences and updates the inner configuration.
     * @param context the context of the application
     * @param frequency the new frequency to be saved
     */
    public void saveFrequency(@NonNull Context context, float frequency) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putFloat(HarmonicConfigFields.CYCLIC_FREQUENCY, frequency);
        editor.apply();

        this.config.updateCyclicFrequency(frequency);
    }

    /**
     * Saves the limits to the shared preferences and updates the inner configuration.
     * @param context the context of the application
     * @param leftLimit the new left limit to be saved
     * @param rightLimit the new right limit to be saved
     */
    public void saveLimits(@NonNull Context context, float leftLimit, float rightLimit) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putFloat(HarmonicConfigFields.LEFT_LIMIT, leftLimit);
        editor.putFloat(HarmonicConfigFields.RIGHT_LIMIT, rightLimit);
        editor.apply();

        this.config.updateLimits(leftLimit, rightLimit);
    }

    /**
     * Saves the configuration to the shared preferences.
     * @param context the context of the application
     * @param config the configuration to be saved
     */
    public void saveConfig(@NonNull Context context, HarmonicConfig config) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putFloat(HarmonicConfigFields.LEFT_LIMIT, config.getLeftLimit());
        editor.putFloat(HarmonicConfigFields.RIGHT_LIMIT, config.getRightLimit());
        editor.putFloat(HarmonicConfigFields.CYCLIC_FREQUENCY, config.getCyclicFrequency());
        editor.apply();

        this.config = config;
    }

    /**
     * @return the current configuration
     */
    public HarmonicConfig getConfig() {
        return config;
    }
}
