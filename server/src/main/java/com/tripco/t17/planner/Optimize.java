package com.tripco.t17.planner;

import java.util.ArrayList;

public class Optimize {
    /**
     *
     * @param places a list of places to visit, in a specific order.
     * @param start the starting location index
     * @return same ordered ArrayList, but starting at a different location.
     */
    public static ArrayList<Place> changeStart(ArrayList<Place> places, int start){
        ArrayList<Place> result = new ArrayList<>();

        //Check bounds of start, right now if it's out of bounds return places
        if (start >= places.size() || start < 0){
            return places;
        }

        //Go through the ArrayList places, and add the places to result
        for (int i = 0 ; i < places.size() ; i++){
            result.add(places.get((i + start) % places.size()));
        }

        return result;
    }

    /**
     * @param places a list of every place in the trip.
     * @return the places in the order which is the shortest travel distance from start.
     */
    public static ArrayList<Place> nearestNeighbor(ArrayList<Place> places){
        //Initialize result
        int shortestDistance = 9999999;
        int placesArray [] = buildPlacesArray(places.size());
        int distanceTable [][] = buildDistanceTable(places);

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
                distance += Distance.gcd(temp.get(temp.size() - 1),
                        unvisited.get(nearestIndex) ,"miles", "");
                temp.add(unvisited.remove(nearestIndex));
            }
            //4. If new plan is shortest, keep it!
            if (distance < shortestDistance){
                //result = temp;
                shortestDistance = distance;
            }
        }

        //5. Return the arrayList! Client side takes care of round trip stuff
        return places;
    }

    /**
     * This method will make an array which has entries equal to its index.
     * Like this: [0, 1, 2, 3, 4, ... length-1].
     * @param length how long the array needs to be.
     * @return [0, 1, 2, 3, 4, ... length-1].
     */
    public static int [] buildPlacesArray(int length){
        int [] myArray = new int [length];
        for (int i = 0 ; i < length ; i++){
            myArray[i] = i;
        }
        return myArray;
    }

    /**
     * This method takes an ArrayList<Place> and builds a table where the following properties hold.
     * 1. Symmetrical: distanceTable[i][j] == [distanceTable[j][i].
     * 2. Diagonal is always 0.
     * 3. Every entry is distance from places.get(i) to places.get(j) where i is row, j is column.
     * @param places the array list from which we build the distance table.
     * @return the distance table.
     */
    public static int [][] buildDistanceTable(ArrayList<Place> places){
        int [][] distanceTable = new int [places.size()][places.size()];

        for (int i = 0 ; i < distanceTable.length ; i++){
            for (int j = i ; j < distanceTable[i].length ; j++){

                //The diagonal is all 0's
                if (i == j){
                    distanceTable[i][j] = 0;
                } else {
                    //Calculate distance from a to b
                    distanceTable [i][j] = Distance.gcd(places.get(i), places.get(j), "miles", "");

                    //Table is symmetrical about the diagonal, table[i][j] always equals table[j][i]
                    distanceTable [j][i] = distanceTable[i][j];
                }
            }
        }

        return distanceTable;
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
            int distance = Distance.gcd(start, places.get(i), "miles", "");
            if (distance < shortestDistance){   //If equal distance, favors previous nearest
                shortestDistance = distance;    //New shortest distance
                nearest = i;                    //New index to return
            }
        }
        return nearest;
    }
}
















