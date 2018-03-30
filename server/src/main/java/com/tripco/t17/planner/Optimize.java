package com.tripco.t17.planner;

import java.net.SocketPermission;
import java.util.ArrayList;

public class Optimize {

    /**
     * @param places a list of every place in the trip.
     * @return the places in the order which is the shortest travel distance from start.
     */
    public static ArrayList<Place> nearestNeighbor(ArrayList<Place> places){
        //Initialize some good stuff
        int placesArray [] = buildPlacesArray(places.size());
        int distanceTable [][] = buildDistanceTable(places);
        int shortestDistance = startingTripDistance(distanceTable);
        int resultArray [] = new int [placesArray.length]; //Assume it's already in the best order (yeah right).
        System.arraycopy(placesArray, 0, resultArray, 0, placesArray.length);


        //Compute nearestNeighbor for each place as the start.
        for (int start = 0 ; start < placesArray.length ; start++) {
            //Compute nearest neighbor for this starting point.
            int distance = nearestNeighborHelper(start, placesArray, distanceTable);
            //Is the best trip from this starting point BETTER than the last one?
            if (distance < shortestDistance){
                shortestDistance = distance; //Set new shortest distance
                System.arraycopy(placesArray, 0, resultArray, 0, placesArray.length);
            }
        }


        //Build new result before returning
        ArrayList<Place> result = new ArrayList<>();
        for (int i = 0 ; i < resultArray.length ; i++){
            result.add(places.get(resultArray[i]));
        }
        return result;
    }

    /**
     * This method takes a starting point < n, an array of (n) indices, and a SQUARE distance table of size (nxn).
     * With this information it will reorder the indices in a ordering which is a more optimized trip.
     * @param start the current starting location.
     * @param placesArray the current ordering of places.
     * @param distanceTable the lookup table for distances.
     * @return the TOTAL DISTANCE of the best trip.
     */
    public static int nearestNeighborHelper(int start, int [] placesArray, int [][] distanceTable){
        //1. Swap the start position to the beginning of placesArray.
        int startIndex = indexOf(placesArray, start);
        swap(placesArray, 0, startIndex);
        int distance = 0;

        //2. Find the nearest in the subArray[start+1, length].
        for (int i = 0 ; i < placesArray.length ; i++){
            int smallest = 9999999;
            int swapper = i+1;
            //Find the nearest unvisited place.
            for (int j = i+1 ; j < placesArray.length ; j++){
                int temp = distanceTable[placesArray[i]][placesArray[j]];
                if (temp < smallest){
                    smallest = temp;
                    swapper = j;
                }
            }


            //If we are at the end of the line
            if (i == placesArray.length - 1) {
                smallest = distanceTable[placesArray[i]][start];
            } else if (swapper < placesArray.length){
                swap(placesArray, i+1, swapper);
            }
            distance += smallest;
        }

        return distance;
    }

    /**
     * This method simply swaps two entries, given an array and two indices.
     * @param array the array we're swapping indices in.
     * @param x one index.
     * @param y the other index.
     */
    public static void swap (int [] array, int x, int y){
        int temp = array[x];
        array[x] = array[y];
        array[y] = temp;
    }

    /**
     * I made a method for arrays to find indexOf, much like an ArrayList finds indexOf, but only for integers.
     * @param array an array to look for the value.
     * @param value a value to search for in the array.
     * @return the index where it finds the value, or -1 if it doesn't exist.
     */
    private static int indexOf (int [] array, int value){
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
      for (int i = 0 ; i < distanceTable.length - 1 ; i++){
        distance += distanceTable[i][i+1];
      }
      return distance;
    }





}
















