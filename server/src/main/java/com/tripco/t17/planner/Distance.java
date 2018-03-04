package com.tripco.t17.planner;

import java.util.ArrayList;

public class Distance {


    /**
     * @param places: arrayList of places for trip
     * @param unit: kilometers or miles
     * @return dist: the distances between consecutive places
     */
    public static ArrayList<Integer> legDistances(ArrayList<Place> places, String unit) {
        ArrayList<Integer> dist = new ArrayList<>();
        //If 0 Places
        if (places.size() < 1){
            return dist;
        }

        //If 1 or more places
        for (int i = 0 ; i < places.size() ; i++) {
            dist.add(GCD(places.get(i),
                    places.get((i+1)%places.size()),
                    unit));
        }
        return dist;
    }

    /**
     * @param DMS: Something in one of the following forms:
     *             12.182
     *             49° 14' 46.6512" N
     *             174° 46' E
     *             69° W
     *             **Note: This takes lat OR long, not both
     * @return degrees: double version of DMS
     */
    public static double DmsToDegrees(String DMS) {
        double degrees = 0.0;
        //5° 30' N
        //Check for °, main validator
        if (DMS.contains("°")) {
            String[] result = DMS.split("°");
            //[5, 30' N]
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
                degrees = Double.parseDouble(DMS);
            } catch (Exception e) {
                //Perhaps we can make a new method for error handling which stops legDistances?
                System.err.println(e);
                throw e;
            }
        }

        return degrees;
    }

    /**
     * @param source: starting point, contains lat/long
     * @param dest:   ending point, contains lat/long
     * @param unit:   calculation of radius, km or miles
     * @return result: the distance between source and dest represented in unit
     */
    public static int GCD(Place source, Place dest, String unit) {
        //Source (a1,b1)
        double a1, a2, b1, b2 = 0;
        try {
            a1 = Math.toRadians(DmsToDegrees(source.latitude));
            b1 = Math.toRadians(DmsToDegrees(source.longitude));
        } catch (Exception e){
            System.err.println(e);
            return -1; //bad source
        }

        //Dest (a2,b2)
        try{
            a2 = Math.toRadians(DmsToDegrees(dest.latitude));
            b2 = Math.toRadians(DmsToDegrees(dest.longitude));
        } catch (Exception e){
            System.err.println(e);
            return -2; //Bad dest
        }
        //Compute X,Y,Z
        double x = Math.cos(a2) * Math.cos(b2) - Math.cos(a1) * Math.cos(b1);
        double y = Math.cos(a2) * Math.sin(b2) - Math.cos(a1) * Math.sin(b1);
        double z = Math.sin(a2) - Math.sin(a1);

        //Compute chord length
        double c = Math.sqrt((x * x) + (y * y) + (z * z));

        //Compute central angle
        double o = 2 * (Math.asin(c / 2));

        //Find greatest circle distance depending on unit
        double d;
        if (unit.equals("miles")) {
            d = o * 3958.7613;
        } else { //Kilometers
            d = o * 6371.0088;
        }

        //Do some Math magic
        int result = Math.toIntExact(Math.round(d));

        return result;
    }
}
