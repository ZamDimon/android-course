package com.zamdimon.graph_plotting.plot;

import android.graphics.Color;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Class responsible for internal logic needed to display a plot.
 */
public class HarmonicPlot {
    /** Represents the minimal left value the plot can start drawing from. */
    public static final float MIN_LEFT_LIMIT = -5.0f;

    /** Represents the maximal right value the plot can end drawing up. */
    public static final float MAX_RIGHT_LIMIT = 5.0f;

    /** Function which is being plotted. By default, as the problem suggests,
     * we draw a function sin(omega*x) + cos(omega*x) */
    private Function<Float, Float> function = x -> (float) (Math.sin(x) + Math.cos(x));

    /** Represents the left x limit from which to draw the plot */
    private float leftLimit;
    /** Represents the right x limit until which to draw the plot */
    private float rightLimit;
    /** Represents the cyclic frequency of the function */
    private float cyclicFrequency;

    /**
     * Constructor for the HarmonicPlot class.
     * @param newLeftLimit the left x limit from which to draw the plot
     * @param newRightLimit the right x limit until which to draw the plot
     * @param newCyclicFrequency the cyclic frequency of the function
     */
    public HarmonicPlot(float newLeftLimit, float newRightLimit, float newCyclicFrequency) {
        this.leftLimit = newLeftLimit;
        this.rightLimit = newRightLimit;
        this.cyclicFrequency = newCyclicFrequency;
    }

    /**
     * Sets the function to be plotted.
     * @param newFunction the new function to be plotted
     */
    public void setFunction(Function<Float, Float> newFunction) {
        this.function = newFunction;
    }

    /**
     * Updates the cyclic frequency of the function.
     * @param newCyclicFrequency the new cyclic frequency of the function
     */
    public void updateCyclicFrequency(float newCyclicFrequency) {
        this.cyclicFrequency = newCyclicFrequency;
    }

    /**
     * Updates the left and right limits of the plot.
     * @param newLeftLimit the new left x limit from which to draw the plot
     * @param newRightLimit the new right x limit until which to draw the plot
     */
    public void updateLimits(float newLeftLimit, float newRightLimit) {
        this.leftLimit = newLeftLimit;
        this.rightLimit = newRightLimit;
    }

    /**
     * Forms the plot points for the plot.
     * @return the XYSeries representing the plot points
     */
    private @NotNull XYSeries formPlotPoints() {
        // Initializing empty lists
        List<Number> xNumbers = new ArrayList<>();
        List<Number> yNumbers = new ArrayList<>();

        final int POINTS_NUMBER = 10;
        for (int i = 0; i < POINTS_NUMBER; ++i) {
            float x = this.leftLimit + (this.rightLimit - this.leftLimit) * i / POINTS_NUMBER;
            float y = this.function.apply(x * this.cyclicFrequency);

            xNumbers.add(x);
            yNumbers.add(y);
        }

        return new SimpleXYSeries(xNumbers, yNumbers, "graph");
    }

    /**
     * Draws the plot on the given XYPlot.
     * @param plot the XYPlot to draw the plot on
     */
    public void drawPlot(@NotNull XYPlot plot) {
        XYSeries series = this.formPlotPoints();

        LineAndPointFormatter pointFormatter = new LineAndPointFormatter(
                null, Color.GREEN, null, null);
        plot.clear();
        plot.addSeries(series, pointFormatter);
        plot.redraw();
    }
}
