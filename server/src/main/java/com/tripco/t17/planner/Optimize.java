package com.tripco.t17.planner;

import java.util.ArrayList;

public class Optimize {

    /**
     * @param places a list of every place in the trip.
     * @return the places in the order which is the shortest travel distance from start.
     */
    public static ArrayList<Place> nearestNeighbor(ArrayList<Place> places){
        //Initialize result
        ArrayList<Place> result = new ArrayList<>();
        int shortestDistance = 9999999;

        //1. Choose starting location. We do this for each place in places hi
        //This is removed, then added, so that we will not find it again :)
        for (int i = 0 ; i < places.size() ; i++) {
            ArrayList<Place> unvisited = new ArrayList<>();
            unvisited.addAll(places);
            ArrayList<Place> temp = new ArrayList<>();
            int distance = 0;

            if (!unvisited.isEmpty()) {
                temp.add(unvisited.remove(i));
            }

            //2. Find nearest city, add it to the result!
            //3. If unvisited is not empty, go to step 2
            while (!unvisited.isEmpty()) {
                int nearestIndex = findNearest(temp.get(temp.size() - 1), unvisited);
                distance += Distance.gcd(temp.get(temp.size() - 1), unvisited.get(nearestIndex) ,"miles");
                temp.add(unvisited.remove(nearestIndex));
            }
            //4. If new plan is shortest, keep it!
            if (distance < shortestDistance){
                result = temp;
                shortestDistance = distance;
            }
        }

        //5. Return the arrayList! Client side takes care of round trip stuff
        return result;
    }

    /**
     * @param start the starting city
     * @param places the arrayList of comparing cities
     * @return nearest: the INDEX OF the nearest city to the one you are at
     */
    public static int findNearest(Place start, ArrayList<Place> places){
        int nearest = 0;
        int shortestDistance = 9999999;

        //Go through places, get GCD of each, return index of shortest
        for (int i = 0 ; i < places.size() ; i++){
            int distance = Distance.gcd(start, places.get(i), "miles");
            if (distance < shortestDistance){   //If equal distance, favors previous nearest
                shortestDistance = distance;    //New shortest distance
                nearest = i;                    //New index to return
            }
        }
        return nearest;
    }
}

















