package com.tripco.t17.planner;

import java.util.ArrayList;

/**
 * The Distance class is somewhat of a functor. Import planner package and call Distance.publicMethod
 */

public class Distance {

    /**
     * @param places arrayList of all places
     * @param unit either kilometers or miles
     * @return distance
     */
    public static ArrayList<Integer> legDistances(ArrayList<Place> places, String unit) {
        ArrayList<Integer> distance = new ArrayList<>();
        //If 0 Places
        if (places.size() < 1){
            return distance;
        }

        //If 1 or more places
        for (int i = 0 ; i < places.size() ; i++) {
            distance.add(gcd(places.get(i),
                    places.get((i+1)%places.size()),
                    unit));
        }
        return distance;
    }

    /**
     * @param source  starting point, contains lat/long
     * @param dest    ending point, contains lat/long
     * @param unit    calculation of radius, km or miles
     * @return result  the distance between source and dest represented in unit
     */
    public static int gcd(Place source, Place dest, String unit) {
        //0. Validate input
        if (invalidPlace(source)){
            return -1;
        }
        if (invalidPlace(dest)){
            return -2;
        }

        //1. Initialize coordinates
        double a1 = Math.toRadians(dmsToDegrees(source.latitude));
        double b1 = Math.toRadians(dmsToDegrees(source.longitude));
        double a2 = Math.toRadians(dmsToDegrees(dest.latitude));
        double b2 = Math.toRadians(dmsToDegrees(dest.longitude));

        //2. Compute X,Y,Z
        double xc = gcdX(a1, a2, b1, b2);
        double yc = gcdY(a1, a2, b1, b2);
        double zc = gcdZ(a1, a2);

        //3. Compute chord length
        double chordLength = chordLength(xc, yc, zc);

        //4. Compute central angle
        double centralAngle = centralAngle(chordLength);

        //5. Find greatest circle distance depending on unit
        double distance = gcdHelper(centralAngle, unit);

        //6. Round and return
        return Math.toIntExact(Math.round(distance));
    }

    /**
     * @param place has lat and long, we need to validate these!
     * @return whether the place's lat/long are valid or not
     */
    private static boolean invalidPlace(Place place){
        try {
            dmsToDegrees(place.latitude);
            dmsToDegrees(place.longitude);
        } catch (Exception e) {
            System.err.println(e);
            return true; //bad source
        }
        return false;
    }

    /**
     * @param a1 x coordinate of source
     * @param a2 x coordinate of dest
     * @param b1 y coordinate of source
     * @param b2 y coordinate of dest
     * @return xCoordinate
     */
    private static double gcdX(double a1, double a2, double b1, double b2){
        return Math.cos(a2) * Math.cos(b2) - Math.cos(a1) * Math.cos(b1);
    }

    /**
     * @param a1 x coordinate of source
     * @param a2 x coordinate of dest
     * @param b1 y coordinate of source
     * @param b2 y coordinate of dest
     * @return yCoordinate
     */
    private static double gcdY(double a1, double a2, double b1, double b2){
        return Math.cos(a2) * Math.sin(b2) - Math.cos(a1) * Math.sin(b1);
    }

    /**
     * @param a1 x coordinate of source
     * @param a2 x coordinate of dest
     * @return zCoordinate
     */
    private static double gcdZ(double a1, double a2){
        return Math.sin(a2) - Math.sin(a1);
    }

    /**
     * @param xc x coordinate
     * @param yc y coordinate
     * @param zc z coordinate
     * @return chordLength
     */
    private static double chordLength(double xc, double yc, double zc){
        return Math.sqrt(Math.pow(xc, 2) + Math.pow(yc, 2) + Math.pow(zc, 2));
    }

    /**
     * @param chordLength the length of the chord we are stretching to measure distance
     * @return centralAngle
     */
    private static double centralAngle(double chordLength){
        return 2 * (Math.asin(chordLength / 2));
    }

    /**
     * @param centralAngle the angle between the two destinations
     * @param unit either kilometers or miles
     * @return distance based on unit
     */
    private static double gcdHelper(double centralAngle, String unit){
        if (unit.equals("miles")) {
            return centralAngle * 3958.7613;
        } else { //Kilometers
            return centralAngle * 6371.0088;
        }
    }

    /**
     * @param dms  Something in one of the following forms:
     *             12.182
     *             49° 14' 46.6512" N
     *             174° 46' E
     *             69° W
     *             **Note: This takes lat OR long, not both
     * @return degrees
     */
    public static double dmsToDegrees(String dms) {
        double degrees;
        //Check for °, main validator
        if (dms.contains("°")) {
            String[] result = dms.split("°");
            degrees = Double.parseDouble(result[0].trim());
            //Check for '
            if (result[1].contains("'")) {
                result = result[1].split("'");
                double minutes = Double.parseDouble(result[0].trim());
                degrees += (minutes / 60);

                //Check for "
                if (result[1].contains("\"")) {
                    result = result[1].split("\"");
                    double seconds = Double.parseDouble(result[0].trim());
                    degrees += (seconds / 3600);
                }
            }

            //Set the sign at the very end
            if (result[1].trim().equals("S")) {
                degrees *= -1;
            } else if (result[1].trim().equals("W")) {
                degrees *= -1;
            }
        } else { //Already in degrees, or another invalid input like "klajsdf"
            try {
                degrees = Double.parseDouble(dms);
            } catch (Exception e) {
                //Perhaps we can make a new method for error handling which stops legDistances?
                System.err.println(e);
                throw e;
            }
        }

        return degrees;
    }
}
