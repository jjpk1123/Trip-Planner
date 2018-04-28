package com.tripco.t17.planner;

import java.util.ArrayList;

public class Optimize {
    private static int [] placesArray;
    private static int [] optArray;
    private static int [][] distanceTable;
    /**
     * This method optimizes a trip order based on the level of optimization passed
     * by the client. It calls either nearestNeighbor or 2opt. For 2opt, it calls
     * nearest neighbor to modify the the places order, then runs 2opt on that order.
     * @param places a list of every place in the trip.
     * @param optLevel the level of optimization passed from the client
     * @return An optimized trip, either Nearest Neighbor or 2opt, depending on optLevel
     */

    public static ArrayList<Place> optimize(ArrayList<Place> places, double optLevel){
        //Initialize places array, two opt array, distance table, and initial total distance.
        Optimize.placesArray = buildPlacesArray(places.size());
        Optimize.optArray = new int[placesArray.length + 1];
        Optimize.distanceTable = buildDistanceTable(places);
        int shortestDistance = startingTripDistance(distanceTable);

        //Initialize the final array to return later.
        int [] resultArray = new int [placesArray.length];

        //If we are optimizing...
        if(optLevel >= .25){
            //Start by making resultArray == placesArray, but with different references
            System.arraycopy(placesArray, 0, resultArray, 0, placesArray.length);

            //make a new placesArray for 2opt
            for (int start = 0 ; start < placesArray.length ; start++) {

                //Compute nearest neighbor for this starting point.
                int distance = nearestNeighbor(start);

                //If we are doing 2opt OR 3opt
                if(optLevel >= .50){

                    //Make twoOptArray == placesArray, diff references
                    System.arraycopy(placesArray, 0, optArray, 0, placesArray.length);

                    //Add starting place to end of array
                    optArray[optArray.length - 1] = optArray[0];

                    //3opt
                    if (optLevel >= .75){
                      distance = threeOpt();
                    } else { //2opt
                      distance = twoOpt();
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
     * @return the TOTAL DISTANCE of the best trip.
     */
    private static int nearestNeighbor(int start){
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
     * @return the total distance of a 2opt optimized trip.
     */
    private static int twoOpt(){
        boolean improvement = true;
        while (improvement){
            improvement = false;
            for(int i = 0; i <= optArray.length - 3; i++){
                for (int k = i + 2; k < optArray.length - 1; k++){
                    if(deltaDistance(i, k) < 0){
                        reversePlaces(optArray, i+1, k);
                        improvement = true;
                    }
                }
            }
        }

        return totalDistance(optArray, distanceTable);
    }

    private static int deltaDistance(int i, int k){
        return -dis(optArray, i, i+1) - dis(optArray, k,k+1)
          + dis(optArray, i,k) + dis(optArray,i+1,k+1);
    }

    /**
     * @return the shortest distance via the 3opt algorithm
     */
    private static int threeOpt(){
        boolean improvement = true;
        while (improvement) {
            improvement = false;
            for (int i = 0; i < optArray.length - 3; i++) {
                for (int j = i + 1; j < optArray.length - 2; j++) {
                    for (int k = j + 1; k < optArray.length - 1 ; k++) {
                        //Current trip
                        int currentDistance = currentDistance(optArray, i, j, k);

                        //Case 1
                        if (caseOneDistance(optArray, i, j, k) < currentDistance) {
                            reversePlaces(optArray, i+1, k);
                            improvement = true;
                        }

                        //Case 2
                        else if (caseTwoDistance(optArray, i, j, k) < currentDistance) {
                            reversePlaces(optArray, i+1, j);
                            improvement = true;
                        }

                        //Case 3
                        else if (caseThreeDistance(optArray, i, j, k) < currentDistance) {
                            reversePlaces(optArray, j+1, k);
                            improvement = true;
                        }

                        //Case 4
                        else if (caseFourDistance(optArray, i, j, k) < currentDistance) {
                            reversePlaces(optArray, i+1, j);
                            reversePlaces(optArray, j+1, k);
                            improvement = true;
                        }

                        //Case 5
                        else if (caseFiveDistance(optArray, i, j, k) < currentDistance) {
                            reversePlaces(optArray, j+1, k);
                            swapBlocks(optArray, i+1, j, j+1, k);
                            improvement = true;
                        }

                        //Case 6
                        else if (caseSixDistance(optArray, i, j, k) < currentDistance) {
                            reversePlaces(optArray, i+1, j);
                            swapBlocks(optArray, i+1, j, j+1, k);
                            improvement = true;
                        }

                        //Case 7
                        else if (caseSevenDistance(optArray, i, j, k) < currentDistance) {
                            swapBlocks(optArray, i+1, j, j+1, k);
                            improvement = true;
                        }

                    }
                }
            }
        }

        return totalDistance(optArray, distanceTable);
    }

    /**
     * ThreeOpt Helpers
     * @param optArray the array of places to be optimized.
     * @param i leg one.
     * @param j leg two.
     * @param k leg three.
     * @return distance of the legs added.
     */
    private static int currentDistance(int [] optArray, int i, int j, int k) {
        return dis(optArray, i, i+1)
          + dis(optArray, j, j+1)
          + dis(optArray, k, k+1);
    }

    private static int caseOneDistance(int [] optArray, int i, int j, int k){
        return dis(optArray, i, k)
          + dis(optArray, j+1, j)
          + dis(optArray, i+1, k+1);
    }

    private static int caseTwoDistance(int [] optArray, int i, int j, int k){
        return dis(optArray, i, j)
          + dis(optArray, i+1, j+1)
          + dis(optArray, k, k+1);
    }

    private static int caseThreeDistance(int [] optArray, int i, int j, int k){
        return dis(optArray, i, i+1)
          + dis(optArray, j, k)
          + dis(optArray, j+1, k+1);
    }

    private static int caseFourDistance(int [] optArray, int i, int j, int k){
        return dis(optArray, i, j)
          + dis(optArray, i+1, k)
          + dis(optArray, j+1, k+1);
    }

    private static int caseFiveDistance(int [] optArray, int i, int j, int k){
        return dis(optArray, i, k)
          + dis(optArray, j+1, i+1)
          + dis(optArray, j, k+1);
    }

    private static int caseSixDistance(int [] optArray, int i, int j, int k){
        return dis(optArray, i, j+1)
          + dis(optArray, k, j)
          + dis(optArray, i+1, k+1);
    }

    private static int caseSevenDistance(int [] optArray, int i, int j, int k){
        return dis(optArray, i, j+1)
          + dis(optArray, k, i+1)
          + dis(optArray, j, k+1);
    }

    /**
     * Returns the total distance of your trip in the order prescribed by placesArray.
     * @param placesArray array of places.
     * @param distanceTable table of distances.
     * @return the total distance of your trip in the order prescribed by placesArray.
     */
    private static int totalDistance(int [] placesArray, int [][] distanceTable){
        int distance = 0;
        for (int i = 0; i < distanceTable.length ; i++){
            distance += distanceTable[placesArray[i]][placesArray[(i + 1) % distanceTable.length]];
        }
        return distance;
    }

    /**
     * This reverses places array in place between two indexes.
     * @param optArray the current ordering of places.
     * @param startIndex starting index for reversal.
     * @param endIndex ending index for reversal.
     */
    private static void reversePlaces(int[] optArray, int startIndex, int endIndex){
        while(startIndex < endIndex){
            int temp = optArray[startIndex];
            optArray[startIndex] = optArray[endIndex];
            optArray[endIndex] = temp;
            startIndex++;
            endIndex--;
        }
    }

    /**
     *
     * @param startIndex1 Index of start of block 1.
     * @param endIndex1 Index of end of block 1.
     * @param startIndex2 Index of start of block 2.
     * @param endIndex2 Index of end of block 2.
     */
    private static void swapBlocks(int [] array, int startIndex1, int endIndex1,
                                  int startIndex2, int endIndex2){
        int [] tempArray = new int [endIndex1 - startIndex1 + 1];

        //Hold the first block in a temp array
        System.arraycopy(array, startIndex1, tempArray, 0, (endIndex1 - startIndex1 + 1));

        //Overwrite first block with second block
        System.arraycopy(array, startIndex2, array, startIndex1, (endIndex2 - startIndex2 + 1));

        //Overwrite second block with temp array
        System.arraycopy(tempArray, 0, array, endIndex2 - tempArray.length + 1, tempArray.length);
    }

    /**
     * This finds distances between two places.
     * @param optArray the current ordering of places.
     * @param place1 the index for the first place in placesArray.
     * @param place2 the index for the second place in placesArray.
     * @return the distance between two places.
     */
    private static int dis(int[] optArray, int place1, int place2){
        return distanceTable[optArray[place1]][optArray[place2]];
    }

    /**
     * This method simply swaps two entries, given an array and two indices.
     * @param array the array we're swapping indices in.
     * @param swapper1 one index.
     * @param swapper2 the other index.
     */
    private static void swap(int [] array, int swapper1, int swapper2){
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
    private static int indexOf(int [] array, int value){
      int index = 0;
      for (; index < array.length ; index++){
            if (array[index] == value){
              break;
            }
      }
      return index;
    }

    /**
     * This method will make an array which has entries equal to its index.
     * Like this: [0, 1, 2, 3, 4, ... length-1].
     * @param length how long the array needs to be.
     * @return [0, 1, 2, 3, 4, ... length-1].
     */
    private static int [] buildPlacesArray(int length){
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
    private static int [][] buildDistanceTable(ArrayList<Place> places){
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
















