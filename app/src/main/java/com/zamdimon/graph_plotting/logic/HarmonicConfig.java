package com.zamdimon.graph_plotting.logic;

public class HarmonicConfig {
    /** Represents the minimal left value the plot can start drawing from. */
    public static final float MIN_LEFT_LIMIT = -5.0f;

    /** Represents the maximal right value the plot can end drawing up. */
    public static final float MAX_RIGHT_LIMIT = 5.0f;

    /** Represents the default cyclic frequency available by default */
    public static final float DEFAULT_CYCLIC_FREQUENCY = 1.0f;

    /** Represents the left x limit from which to draw the plot */
    private float leftLimit;
    /** Represents the right x limit until which to draw the plot */
    private float rightLimit;
    /** Represents the cyclic frequency of the function */
    private float cyclicFrequency;

    /**
     * Constructs the config with default values
     */
    public HarmonicConfig() {
        this.leftLimit = MIN_LEFT_LIMIT;
        this.rightLimit = MAX_RIGHT_LIMIT;
        this.cyclicFrequency = DEFAULT_CYCLIC_FREQUENCY;
    }

    /**
     * Constructor for the HarmonicPlot class with the specified fields.
     * @param leftLimit the left x limit from which to draw the plot
     * @param rightLimit the right x limit until which to draw the plot
     * @param cyclicFrequency the cyclic frequency of the function
     */
    public HarmonicConfig(float leftLimit, float rightLimit, float cyclicFrequency) {
        this.leftLimit = leftLimit;
        this.rightLimit = rightLimit;
        this.cyclicFrequency = cyclicFrequency;
    }

    /**
     * Updates the cyclic frequency of the function.
     * @param cyclicFrequency the new cyclic frequency of the function
     */
    public void updateCyclicFrequency(float cyclicFrequency) {
        this.cyclicFrequency = cyclicFrequency;
    }

    /**
     * Updates the left and right limits of the plot.
     * @param leftLimit the new left x limit from which to draw the plot
     * @param rightLimit the new right x limit until which to draw the plot
     */
    public void updateLimits(float leftLimit, float rightLimit) {
        assert rightLimit > leftLimit : "Right limit must be greater than the left one";

        this.leftLimit = leftLimit;
        this.rightLimit = rightLimit;
    }

    /**
     * @return Left x limit from which to draw the plot
     */
    public float getLeftLimit() {
        return leftLimit;
    }

    /**
     * @return Right x limit until which to draw the plot
     */
    public float getRightLimit() {
        return rightLimit;
    }

    /**
     * @return Length of the plot along the Ox axis
     */
    public float getLimitLength() {
        return rightLimit - leftLimit;
    }

    /**
     * @return Cyclic frequency of the plot
     */
    public float getCyclicFrequency() {
        return cyclicFrequency;
    }
}
