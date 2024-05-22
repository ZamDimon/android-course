package com.zamdimon.graph_plotting;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYSeries;
import com.google.android.material.slider.RangeSlider;
import com.zamdimon.graph_plotting.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding = null;
    private float sliderValue = 0;

    // TODO: Make configurable
    private final float xMin = -5;
    private final float xMax = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        onSliderChange();
        drawPlot();
    }

    private void onSliderChange() {
        binding.frequencySlider.addOnChangeListener(touchListener);
    }

    private void drawPlot() {
        List<Number> xNumbers = new ArrayList<>();
        List<Number> yNumbers = new ArrayList<>();

        final int POINTS_NUMBER = 100;
        for (int i = 0; i < POINTS_NUMBER; ++i) {
            float x = xMin + (xMax - xMin)*i / POINTS_NUMBER;
            float y = (float) Math.sin(sliderValue * x);

            xNumbers.add(x);
            yNumbers.add(y);
        }


        XYSeries series = new SimpleXYSeries(xNumbers, yNumbers, "Graph");
        LineAndPointFormatter pointFormatter = new LineAndPointFormatter(
                null, Color.GREEN, null, null);
        binding.plot.clear();
        binding.plot.addSeries(series, pointFormatter);
        binding.plot.redraw();
    }


    private final RangeSlider.OnChangeListener touchListener = new RangeSlider.OnChangeListener() {
        @Override
        public void onValueChange(@NonNull RangeSlider rangeSlider, float v, boolean b) {
            sliderValue = v;
            drawPlot();
        }
    };


}

