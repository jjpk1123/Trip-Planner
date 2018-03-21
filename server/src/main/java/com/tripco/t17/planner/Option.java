package com.tripco.t17.planner;

/**
 * Describes the options to apply when planning a trip in TFFI format.
 * At this point we are only using the values provided.
 */
public class Option {

    public String distance;
    public String optimization;

    /**
     * Returns true if the slider is greater than "longest"
     * @param optStr = trip.options.optimization
     * @return false = do not optimize path
     */
    public static boolean optimizeCheck(String optStr) {
        if (optStr.equals("none")) {
            return false; //longest path
        }

        double optDbl = Double.parseDouble(optStr);
        double optBreak = (1.0 / 2); // @TODO: ******Config Change******

        if (optDbl < optBreak) {
            return false; //longest path
        } else { //if (optDbl >= optBreak) {
            return true; //shortest path
        }
    }
}
