package com.zamdimon.graph_plotting;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.zamdimon.graph_plotting.databinding.ActivityMainBinding;
import com.zamdimon.graph_plotting.plot.HarmonicConfig;
import com.zamdimon.graph_plotting.plot.HarmonicPlot;

public class MainActivity extends AppCompatActivity {
    /** Represents the binding of the activity which makes
     * it easier to manage elements of the activity */
    private ActivityMainBinding binding = null;

    /** Represents the plot to be displayed */
    private HarmonicPlot harmonicPlot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        // Setting the top bar
        initializeTopBarMenu();

        // Setting sliders
        initializeFrequencySlider();
        initializeLimitsSlider();

        // Setting the harmonic plot
        this.harmonicPlot = new HarmonicPlot();
        this.harmonicPlot.drawPlot(binding.plot);
    }

    private void initializeLimitsSlider() {
        binding.xLimitsSlider.setValues(HarmonicConfig.MIN_LEFT_LIMIT, HarmonicConfig.MAX_RIGHT_LIMIT);
        binding.xLimitsSlider.addOnChangeListener((slider, value, fromUser) -> {
            float leftLimit = binding.xLimitsSlider.getValues().get(0);
            float rightLimit = binding.xLimitsSlider.getValues().get(1);
            harmonicPlot.updateLimits(leftLimit, rightLimit);
            harmonicPlot.drawPlot(binding.plot);
        });
    }

    private void initializeFrequencySlider() {
        binding.frequencySlider.setValues(HarmonicConfig.DEFAULT_CYCLIC_FREQUENCY);
        binding.frequencySlider.addOnChangeListener((rangeSlider, frequency, b) -> {
            harmonicPlot.updateCyclicFrequency(frequency);
            harmonicPlot.drawPlot(binding.plot);
        });
    }

    private void initializeTopBarMenu() {
        binding.topAppBar.setOnMenuItemClickListener(item -> true);
    }
}

