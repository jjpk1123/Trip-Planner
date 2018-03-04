package com.tripco.t17.planner;

import java.util.ArrayList;

public class Optimize {
    //public String optimization; //Level of optimization. 1 = Nearest Neighbor, 0 = none


    //Returns the arraylist that is handed to it for sanity testing
    public static ArrayList<Place> nearestNeighbor(ArrayList<Place> unvisited){
        //Initialize result
        ArrayList<Place> result = new ArrayList<>();

        //1. Choose starting location. For now this will be the first place in places!
        //This is removed, then added, so that we will not find it again :)
        result.add(unvisited.remove(0));

        //2. Find nearest city, add it to the result!
        //Think we will use a helper here
        do {
            int nearest = findNearest(result.get(result.size() - 1), unvisited);
            result.add(unvisited.remove(nearest));
        } while (!unvisited.isEmpty());
        //3. We will add until unvisited is empty :)

        //4. Return the arrayList! Client side takes care of round trip stuff
        return result;
    }

    /**
     * @param start: the starting city
     * @param places: the arrayList of comparing cities
     * @return nearest: the INDEX OF the nearest city to the one you are at
     */
    public static int findNearest(Place start, ArrayList<Place> places){
        int nearest = 0;
        int shortestDistance = 9999999;

        //Go through places, get GCD of each, return index of shortest
        for (int i = 0 ; i < places.size() ; i++){
            int distance = Trip.GCD(start, places.get(i), "miles");
            System.out.println("i = " + i + ", distance: " + distance + ", shortestDistance: " + shortestDistance);
            if (distance < shortestDistance){   //If equal distance, favors previous nearest
                shortestDistance = distance;    //New shortest distance
                nearest = i;                    //New index to return
            }
        }
        return nearest;
    }
}

















