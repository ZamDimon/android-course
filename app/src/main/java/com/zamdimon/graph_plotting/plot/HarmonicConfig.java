package com.zamdimon.graph_plotting.plot;

public class HarmonicConfig {
    /** Represents the minimal left value the plot can start drawing from. */
    public static final float MIN_LEFT_LIMIT = -5.0f;

    /** Represents the maximal right value the plot can end drawing up. */
    public static final float MAX_RIGHT_LIMIT = 5.0f;

    /** Represents the left x limit from which to draw the plot */
    private float leftLimit;
    /** Represents the right x limit until which to draw the plot */
    private float rightLimit;
    /** Represents the cyclic frequency of the function */
    private float cyclicFrequency;

    public HarmonicConfig() {
        this.leftLimit = MIN_LEFT_LIMIT;
        this.rightLimit = MAX_RIGHT_LIMIT;
        this.cyclicFrequency = 1.0f;
    }

    /**
     * Constructor for the HarmonicPlot class.
     * @param newLeftLimit the left x limit from which to draw the plot
     * @param newRightLimit the right x limit until which to draw the plot
     * @param newCyclicFrequency the cyclic frequency of the function
     */
    public HarmonicConfig(float newLeftLimit, float newRightLimit, float newCyclicFrequency) {
        this.leftLimit = newLeftLimit;
        this.rightLimit = newRightLimit;
        this.cyclicFrequency = newCyclicFrequency;
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
