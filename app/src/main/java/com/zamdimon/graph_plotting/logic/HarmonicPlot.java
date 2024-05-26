package com.zamdimon.graph_plotting.logic;

import android.graphics.Color;
import android.graphics.Paint;

import androidx.annotation.NonNull;

import com.androidplot.xy.CatmullRomInterpolator;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Class responsible for internal logic needed to display a plot.
 */
public class HarmonicPlot {
    /** Function which is being plotted. By default, as the problem suggests,
     * we draw a function sin(omega*x) + cos(omega*x) */
    private Function<Float, Float> function = x -> (float) (Math.sin(x) + Math.cos(x));

    /** Configuration file of our plot. Contains the core data such as frequency or x limits. */
    private final HarmonicConfig config;

    /**
     * Sets the function to be plotted.
     * @param function the new function to be plotted
     */
    public void setFunction(Function<Float, Float> function) {
        this.function = function;
    }

    /**
     * Constructs the HarmonicPlot with default parameters
     */
    public HarmonicPlot() {
        this.config = new HarmonicConfig();
    }

    /**
     * Contructs the HarmonicPlot with specified config
     * @param config Config to initialize the plot with
     */
    public HarmonicPlot(@NonNull HarmonicConfig config) {
        this.config = config;
    }

    /**
     * Updates the limit of the internal config
     * @param leftLimit coordinate from which to start drawing the plot
     * @param rightLimit coordinate until which to draw the plot
     */
    public void updateLimits(float leftLimit, float rightLimit) {
        config.updateLimits(leftLimit, rightLimit);
    }

    /**
     * Updates the cyclic frequency of the internal config
     * @param frequency the new cyclic frequency of the function
     */
    public void updateCyclicFrequency(float frequency) {
        config.updateCyclicFrequency(frequency);
    }

    /**
     * Estimates the number of points needed to draw the plot.
     * @return the estimated number of points needed to draw the plot
     */
    private int estimatePointsNumber() {
        float period = 2 * (float) Math.PI / config.getCyclicFrequency();
        // We want approximately 8 points per period. Meaning, the total
        // number of points is the total number of periods in the specified
        // interval multiplied by 8.
        float periods = config.getLimitLength() / period;
        // We want at least 10 points, otherwise adapt according to the number
        // of periods.
        final int POINTS_PER_PERIOD = 35;
        final int MIN_POINTS = 35;
        return Math.max((int) (periods * POINTS_PER_PERIOD), MIN_POINTS);
    }

    /**
     * Forms the plot points for the plot.
     * @return the XYSeries representing the plot points
     */
    private @NonNull XYSeries formPlotPoints() {
        // Initializing empty lists
        List<Number> xNumbers = new ArrayList<>();
        List<Number> yNumbers = new ArrayList<>();

        int pointsNumber = this.estimatePointsNumber();
        for (int i = 0; i < pointsNumber; ++i) {
            float x = config.getLeftLimit() + config.getLimitLength() * i / pointsNumber;
            float y = function.apply(x * config.getCyclicFrequency());

            xNumbers.add(x);
            yNumbers.add(y);
        }

        final String PLOT_TITLE = "harmonic_plot";
        return new SimpleXYSeries(xNumbers, yNumbers, PLOT_TITLE);
    }

    /**
     * Draws the plot on the given XYPlot.
     * @param plot the XYPlot to draw the plot on
     */
    public void drawPlot(@NonNull XYPlot plot) {
        XYSeries series = formPlotPoints();

        final int LINE_COLOR = Color.BLUE;
        final int POINT_COLOR = Color.GREEN;
        LineAndPointFormatter pointFormatter = new LineAndPointFormatter(LINE_COLOR, POINT_COLOR, null, null);

        // Adding interpolation to make curve a little bit smoother
        final int POINTS_PER_SEGMENT = 20;
        pointFormatter.setInterpolationParams(new CatmullRomInterpolator.Params(POINTS_PER_SEGMENT, CatmullRomInterpolator.Type.Centripetal));

        // Change the line width
        final int STROKE_WIDTH = 10;
        Paint paint = pointFormatter.getLinePaint();
        paint.setStrokeWidth(STROKE_WIDTH);
        pointFormatter.setLinePaint(paint);

        // Clearing and redrawing the plot
        plot.clear();
        plot.addSeries(series, pointFormatter);
        plot.redraw();
    }
}
