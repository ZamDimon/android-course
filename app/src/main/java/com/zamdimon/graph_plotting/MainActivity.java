package com.zamdimon.graph_plotting;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.zamdimon.graph_plotting.databinding.ActivityMainBinding;
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
        this.binding.topAppBar.setOnMenuItemClickListener(item -> true);

        // Setting sliders
        initializeFrequencySlider();
        initializeLimitsSlider();

        // Setting the harmonic plot
        this.harmonicPlot = new HarmonicPlot(HarmonicPlot.MIN_LEFT_LIMIT, HarmonicPlot.MAX_RIGHT_LIMIT, 1.0f);
        this.harmonicPlot.drawPlot(binding.plot);
    }

    private void initializeLimitsSlider() {
        binding.xLimitsSlider.setValues(HarmonicPlot.MIN_LEFT_LIMIT, HarmonicPlot.MAX_RIGHT_LIMIT);
        binding.xLimitsSlider.addOnChangeListener((slider, value, fromUser) -> {
            float leftLimit = binding.xLimitsSlider.getValues().get(0);
            float rightLimit = binding.xLimitsSlider.getValues().get(1);
            harmonicPlot.updateLimits(leftLimit, rightLimit);
            harmonicPlot.drawPlot(binding.plot);
        });
    }

    private void initializeFrequencySlider() {
        binding.frequencySlider.addOnChangeListener((rangeSlider, frequency, b) -> {
            harmonicPlot.updateCyclicFrequency(frequency);
            harmonicPlot.drawPlot(binding.plot);
        });
    }
}

