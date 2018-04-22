package com.tripco.t17.planner;

import java.lang.reflect.Array;
import java.net.SocketPermission;
import java.util.ArrayList;
import java.util.Arrays;

public class Optimize {

    /**
     * This method optimizes a trip order based on the level of optimization passed
     * by the client. It calls either nearestNeighbor or 2opt. For 2opt, it calls
     * nearest neighbor to modify the the places order, then runs 2opt on that order.
     * @param places a list of every place in the trip.
     * @param optLevel the level of optimization passed from the client
     * @return An optimized trip, either Nearest Neighbor or 2opt, depending on optLevel
     */

    public static ArrayList<Place> optimize(ArrayList<Place> places, double optLevel){
        boolean nearestNeighborFlag = false;
        boolean twoOptFlag = false;
        boolean threeOptFlag = false;

        //If we do threeOpt, we don't redundantly do twoOpt
        if (optLevel > .75){
            threeOptFlag = true;
        }
        else if(optLevel > .50){
            twoOptFlag = true;
        }
        if(optLevel > .25){
            nearestNeighborFlag = true;
        }

        //Initialize places array, two opt array, distance table, and initial total distance.
        int [] placesArray = buildPlacesArray(places.size());
        int [] optArray = new int[placesArray.length + 1];
        int [][] distanceTable = buildDistanceTable(places);
        int shortestDistance = startingTripDistance(distanceTable);

        //Initialize the final array to return later.
        int [] resultArray = new int [placesArray.length];

        //If we are optimizing...
        if(nearestNeighborFlag){
            //Start by making resultArray == placesArray, but with different references
            System.arraycopy(placesArray, 0, resultArray, 0, placesArray.length);

            //make a new placesArray for 2opt
            for (int start = 0 ; start < placesArray.length ; start++) {
                System.out.println("start: " + start);

                //Compute nearest neighbor for this starting point.
                int distance = nearestNeighbor(start, placesArray, distanceTable);

                //If we are doing 2opt OR 3opt
                if(twoOptFlag || threeOptFlag){

                    //Make twoOptArray == resultArray, diff references
                    System.arraycopy(placesArray, 0, optArray, 0, placesArray.length);

                    //Add starting place to end of array
                    optArray[optArray.length - 1] = optArray[0];

                    //If we are only doing 2opt
                    if (twoOptFlag) {
                        //Get distance and reorder twoOptArray
                        distance = twoOpt(optArray, distanceTable);
                    }

                    //Optimizing up to 3opt
                    if (threeOptFlag){
                        distance = threeOpt(optArray, distanceTable);
                    }
                    //Copy all but the last location into placesArray
                    System.arraycopy(optArray, 0, placesArray, 0, placesArray.length);

                }

                //Is the best trip from this starting point BETTER than the last one?
                if(distance < shortestDistance){

                    //Set new shortest distance AND update resultArray with new trip ordering
                    shortestDistance = distance;
                    System.arraycopy(placesArray, 0, resultArray, 0, placesArray.length);
                }
            }
        }

        //Build new result before returning
        ArrayList<Place> result = new ArrayList<>();
        for (int i = 0 ; i < resultArray.length; i++){
            result.add(places.get(resultArray[i]));
        }
        return result;
    }

    /**
     * This method takes a starting point < n, an array of (n) indices,
     *  and a SQUARE distance table of size (nxn).
     * With this information it will reorder the indices in a ordering
     *  which is a more optimized trip.
     * @param start the current starting location.
     * @param placesArray the current ordering of places.
     * @param distanceTable the lookup table for distances.
     * @return the TOTAL DISTANCE of the best trip.
     */
    public static int nearestNeighbor(int start, int [] placesArray, int [][] distanceTable){
        //1. Swap the start value to the beginning of placesArray.
        int startIndex = indexOf(placesArray, start);
        swap(placesArray, 0, startIndex);
        int distance = 0;

        //2. Find the nearest in the subArray[start+1, length].
        for (int i = 0 ; i < placesArray.length ; i++){
            int nearest = 9999999;
            int swapper = i+1;

            //Find the nearest unvisited place.
            for (int j = i+1 ; j < placesArray.length ; j++){
                int temp = distanceTable[placesArray[i]][placesArray[j]];
                if (temp < nearest){
                    nearest = temp;
                    swapper = j;
                }
            }

            //If we are at the end of the line
            if (i == placesArray.length - 1) {
                nearest = distanceTable[placesArray[i]][start];
            } else if (swapper < placesArray.length){
                swap(placesArray, i+1, swapper);
            }
            distance += nearest;
        }

        return distance;
    }

    /**
     * This finds distances between two places.
     * @param placesArray the current ordering of places.
     * @param distanceTable the table of distances between places.
     * @return the total distance of a 2opt optimized trip.
     */
    public static int twoOpt(int[] placesArray, int[][] distanceTable){
        int delta;
        boolean improvement = true;
        while (improvement){
            improvement = false;
            for(int i = 0; i <= placesArray.length - 3; i++){
                for (int k = i + 2; k < placesArray.length - 1; k++){
                    delta = -dis(placesArray, distanceTable, i, i+1)-dis(placesArray, distanceTable,k,k+1)
                            +dis(placesArray,distanceTable,i,k)+dis(placesArray,distanceTable,i+1,k+1);
                    if(delta < 0){
                        reversePlaces(placesArray, i+1, k);
                        improvement = true;
                    }
                }
            }
        }
        int distance = 0;
        for (int i = 0; i < distanceTable.length ; i++){
            distance += distanceTable[placesArray[i]][placesArray[(i + 1) % distanceTable.length]];
        }
        return distance;
    }

    /**
     * @param placesArray the original ordering (after nearest neighbor usually) of places in the trip
     * @param distanceTable quick lookup for distances between places
     * @return the shortest distance via the 3opt algorithm
     */
    public static int threeOpt(int[] placesArray, int[][] distanceTable){
        boolean improvement = true;
        while (improvement) {
            System.out.println("--------------");
            improvement = false;
            for (int i = 0; i < placesArray.length - 3; i++) {
                for (int j = i + 1; j < placesArray.length - 2; j++) {
                    for (int k = j + 1; k < placesArray.length - 1 ; k++) {
                        //Current trip
                        int currentDistance = dis(placesArray, distanceTable, i, i+1) +
                          dis(placesArray, distanceTable, j, j+1) +
                          dis(placesArray, distanceTable, k, k+1);
                        //System.out.println("currentDistance: " + currentDistance);

                        //Case 1
                        int caseDistance = dis(placesArray, distanceTable, i, k) +
                          dis(placesArray, distanceTable, j+1, j) +
                          dis(placesArray, distanceTable, i+1, k+1);
                        //System.out.println("caseOneDistance: " + caseOneDistance);
                        if (caseDistance < currentDistance) {
                            reversePlaces(placesArray, i+1, k);
                            improvement = true;
                            //System.out.println("Case 1");
                            continue;
                        }

                        //Case 2
                        caseDistance = dis(placesArray, distanceTable, i, j) +
                          dis(placesArray, distanceTable, i+1, j+1) +
                          dis(placesArray, distanceTable, k, k+1);
                        //System.out.println("caseOneDistance: " + caseOneDistance);
                        if (caseDistance < currentDistance) {
                            reversePlaces(placesArray, i+1, j);
                            improvement = true;
                            //System.out.println("Case 2");
                            continue;
                        }

                        //Case 3
                        caseDistance = dis(placesArray, distanceTable, i, i+1) +
                          dis(placesArray, distanceTable, j, k) +
                          dis(placesArray, distanceTable, j+1, k+1);
                        //System.out.println("caseOneDistance: " + caseOneDistance);
                        if (caseDistance < currentDistance) {
                            reversePlaces(placesArray, j+1, k);
                            improvement = true;
                            //System.out.println("Case 3");
                            continue;
                        }

                        //Case 4
                        caseDistance = dis(placesArray, distanceTable, i, j) +
                          dis(placesArray, distanceTable, i+1, k) +
                          dis(placesArray, distanceTable, j+1, k+1);
                        //System.out.println("caseOneDistance: " + caseOneDistance);
                        if (caseDistance < currentDistance) {
                            reversePlaces(placesArray, i+1, j);
                            reversePlaces(placesArray, j+1, k);
                            improvement = true;
                            //System.out.println("Case 4");
                            continue;
                        }

                        //Case 5
                        caseDistance = dis(placesArray, distanceTable, i, k) +
                          dis(placesArray, distanceTable, j+1, i+1) +
                          dis(placesArray, distanceTable, j, k+1);
                        //System.out.println("caseOneDistance: " + caseOneDistance);
                        if (caseDistance < currentDistance) {
                            reversePlaces(placesArray, j+1, k);
                            swapBlocks(placesArray, i+1, j, j+1, k);
                            improvement = true;
                            //System.out.println("Case 5");
                            continue;
                        }

                        //Case 6
                        caseDistance = dis(placesArray, distanceTable, i, j+1) +
                          dis(placesArray, distanceTable, k, j) +
                          dis(placesArray, distanceTable, i+1, k+1);
                        //System.out.println("caseOneDistance: " + caseOneDistance);
                        if (caseDistance < currentDistance) {
                            reversePlaces(placesArray, i+1, j);
                            swapBlocks(placesArray, i+1, j, j+1, k);
                            improvement = true;
                            //System.out.println("Case 6");
                            continue;
                        }

                        //Case 7
                        caseDistance = dis(placesArray, distanceTable, i, j+1) +
                          dis(placesArray, distanceTable, k, i+1) +
                          dis(placesArray, distanceTable, j, k+1);
                        //System.out.println("caseOneDistance: " + caseOneDistance);
                        if (caseDistance < currentDistance) {
                            swapBlocks(placesArray, i+1, j, j+1, k);
                            improvement = true;
                            //System.out.println("Case 7");
                            continue;
                        }

                    }
                }
            }
        }
        int distance = 0;
        for (int i = 0; i < distanceTable.length ; i++){
            distance += distanceTable[placesArray[i]][placesArray[(i + 1) % distanceTable.length]];
        }
        System.out.println("3opt done");
        return distance;
    }

    /**
     * This reverses places array in place between two indexes.
     * @param placesArray the current ordering of places.
     * @param startIndex starting index for reversal.
     * @param endIndex ending index for reversal.
     */
    public static void reversePlaces(int[] placesArray, int startIndex, int endIndex){
        while(startIndex < endIndex){
            int temp = placesArray[startIndex];
            placesArray[startIndex] = placesArray[endIndex];
            placesArray[endIndex] = temp;
            startIndex++;
            endIndex--;
        }
    }

    /**
     *
     * @param placesArray The array where we will be swapping blocks.
     * @param startIndex1 Index of start of block 1.
     * @param endIndex1 Index of end of block 1.
     * @param startIndex2 Index of start of block 2.
     * @param endIndex2 Index of end of block 2.
     */
    public static void swapBlocks(int[] placesArray, int startIndex1, int endIndex1, int startIndex2, int endIndex2){
        int [] tempArray = new int [endIndex1 - startIndex1 + 1];

        //Hold the first block in a temp array
        for (int i = 0 ; i < (endIndex1 - startIndex1 + 1) ; i++){
            tempArray[i] = placesArray[startIndex1 + i];
        }

        //Overwrite first block with second block
        for (int i = 0 ; i < (endIndex2 - startIndex2 + 1) ; i++){
            placesArray[startIndex1 + i] = placesArray[startIndex2 + i];
        }

        //Overwrite second block with temp array
        for (int i = 0 ; i < tempArray.length ; i++){
            placesArray[endIndex2 - tempArray.length + 1 + i] = tempArray[i];
        }
    }

    /**
     * This finds distances between two places.
     * @param placesArray the current ordering of places.
     * @param distanceTable the table of distances between places.
     * @param place1 the index for the first place in placesArray.
     * @param place2 the index for the second place in placesArray.
     * @return the distance between two places.
     */
    public static int dis(int[] placesArray, int[][] distanceTable, int place1, int place2){
        return distanceTable[placesArray[place1]][placesArray[place2]];
    }

    /**
     * This method simply swaps two entries, given an array and two indices.
     * @param array the array we're swapping indices in.
     * @param swapper1 one index.
     * @param swapper2 the other index.
     */
    public static void swap(int [] array, int swapper1, int swapper2){
        int temp = array[swapper1];
        array[swapper1] = array[swapper2];
        array[swapper2] = temp;
    }

    /**
     * This method works much like an ArrayList finds indexOf, but only for integers.
     * Maybe we can put it elsewhere and turn it into a template method?
     * @param array an array to look for the value.
     * @param value a value to search for in the array.
     * @return the index where it finds the value, or -1 if it doesn't exist.
     */
    public static int indexOf(int [] array, int value){
        for (int i = 0 ; i < array.length ; i++){
            if (array[i] == value){
                return i;
            }
        }
        return -1;
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
     * This method takes an ArrayList type Place,
     *  and builds a table where the following properties hold.
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
                //Calculate the distance, from a to a is 0 along the diagonal.
                distanceTable[i][j] = Distance.gcd(places.get(i), places.get(j), "miles", "");

                //Table is symmetrical about the diagonal, table[i][j] always equals table[j][i].
                distanceTable [j][i] = distanceTable[i][j];
            }
        }

        return distanceTable;
    }

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
     * This calculates the default trip distance given.
     * @param distanceTable the table of distances.
     * @return the total distance in regular order.
     */
    private static int startingTripDistance(int [][] distanceTable){
        int distance = 0;
        for (int i = 0 ; i < distanceTable.length ; i++){
            distance += distanceTable[i][(i + 1) % distanceTable.length];
        }
        return distance;
    }

}
















