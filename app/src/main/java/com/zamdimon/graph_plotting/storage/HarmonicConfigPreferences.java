package com.zamdimon.graph_plotting.storage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.zamdimon.graph_plotting.logic.HarmonicConfig;

import java.util.HashMap;
import java.util.Map;

public class HarmonicConfigPreferences {
    /** Name of the preferences file */
    public static final String PREFERENCES_NAME = "harmonic_config";

    /** Hashmap of tags for the preferences */
    private Map<String, String> tags = new HashMap<String, String>(){{
        put("leftLimit", "leftLimit");
        put("rightLimit", "rightLimit");
        put("cyclicFrequency", "cyclicFrequency");
    }};

    private HarmonicConfig config;

    public HarmonicConfigPreferences(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        float leftLimit = preferences.getFloat(tags.get("leftLimit"), HarmonicConfig.MIN_LEFT_LIMIT);
        float rightLimit = preferences.getFloat(tags.get("rightLimit"), HarmonicConfig.MAX_RIGHT_LIMIT);
        float frequency = preferences.getFloat(tags.get("cyclicFrequency"), HarmonicConfig.DEFAULT_CYCLIC_FREQUENCY);

        Log.e("HarmonicConfigPreferences", "leftLimit: " + leftLimit + " rightLimit: " + rightLimit + " frequency: " + frequency);

        this.config = new HarmonicConfig(leftLimit, rightLimit, frequency);
    }

    public HarmonicConfigPreferences(@NonNull HarmonicConfig config) {
        this.config = config;
    }

    public void saveFrequency(Context context, float frequency) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putFloat(tags.get("cyclicFrequency"), frequency);
        editor.apply();

        this.config.updateCyclicFrequency(frequency);
    }

    public void saveLimits(Context context, float leftLimit, float rightLimit) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putFloat(tags.get("leftLimit"), leftLimit);
        editor.putFloat(tags.get("rightLimit"), rightLimit);
        editor.apply();

        this.config.updateLimits(leftLimit, rightLimit);
    }

    public void savePreferences(Context context, HarmonicConfig config) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putFloat(tags.get("leftLimit"), config.getLeftLimit());
        editor.putFloat(tags.get("rightLimit"), config.getRightLimit());
        editor.putFloat(tags.get("cyclicFrequency"), config.getCyclicFrequency());
        editor.apply();

        this.config = config;
    }

    public HarmonicConfig getPrefences() {
        return config;
    }
}
