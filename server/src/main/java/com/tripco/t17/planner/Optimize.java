package com.tripco.t17.planner;

import java.lang.reflect.Array;
import java.net.SocketPermission;
import java.util.ArrayList;

public class Optimize {

    /**
     * @param places a list of every place in the trip.
     * @return the places in the order which is the shortest travel distance from start.
     */

    public static ArrayList<Place> optimize(ArrayList<Place> places, double optLevel){
        boolean NNFlag = false;
        boolean twoOptFlag = false;

        if(optLevel > .33){
            NNFlag = true;
        }
        if(optLevel > .66){
            twoOptFlag = true;
        }

        int [] placesArray = buildPlacesArray(places.size() + 1);
        placesArray[placesArray.length - 1] = 0;
        int [][] distanceTable = buildDistanceTable(places);
        //Assume it's already in the best order (yeah right).
        int shortestDistance = startingTripDistance(distanceTable);

        //Initialize the array to return later.
        //We do a shallow copy here to avoid having shared references.
        int [] resultArray = new int [placesArray.length];

        if(NNFlag == true){
            //Initialize the two primary data structures.

            System.arraycopy(placesArray, 0, resultArray, 0, placesArray.length);

            //Compute nearestNeighbor for each place as the start.
            for (int start = 0 ; start < placesArray.length ; start++) {

                //Compute nearest neighbor for this starting point.
                int distance = nearestNeighborHelper((start)%placesArray.length-1, placesArray, distanceTable);
                if(twoOptFlag == true){
                    distance = twoOptRevised(placesArray, distanceTable);

                }


                //Is the best trip from this starting point BETTER than the last one?
                if(distance < shortestDistance){
                    //System.out.println(distance);
                    shortestDistance = distance; //Set new shortest distance
                    System.arraycopy(placesArray, 0, resultArray, 0, placesArray.length);

                }
            }
        }


        //Build new result before returning
        ArrayList<Place> result = new ArrayList<>();
        for (int i = 0 ; i < resultArray.length - 1; i++){
            result.add(places.get(resultArray[i]));
        }
        return result;

    }


    public static ArrayList<Place> nearestNeighbor(ArrayList<Place> places){
        //Initialize the two primary data structures.
        int [] placesArray = buildPlacesArray(places.size());
        int [][] distanceTable = buildDistanceTable(places);

        //Assume it's already in the best order (yeah right).
        int shortestDistance = startingTripDistance(distanceTable);

        //Initialize the array to return later.
        //We do a shallow copy here to avoid having shared references.
        int [] resultArray = new int [placesArray.length];
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
     * This method takes a starting point < n, an array of (n) indices,
     *  and a SQUARE distance table of size (nxn).
     * With this information it will reorder the indices in a ordering
     *  which is a more optimized trip.
     * @param start the current starting location.
     * @param placesArray the current ordering of places.
     * @param distanceTable the lookup table for distances.
     * @return the TOTAL DISTANCE of the best trip.
     */
    public static int nearestNeighborHelper(int start, int [] placesArray, int [][] distanceTable){
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
    private static int indexOf(int [] array, int value){
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


    public static int twoOptRevised(int[] placesArray, int[][] distanceTable){



        boolean improvement = true;
        while (improvement){
            improvement = false;
            for(int i = 0; i <= placesArray.length - 3; i++){
                for (int k = i + 2; k < placesArray.length -1; k++){
                    int delta = -dis(placesArray, distanceTable, i, i+1)-dis(placesArray, distanceTable,k,k+1)
                            +dis(placesArray,distanceTable,i,k)+dis(placesArray,distanceTable,i+1,k+1);
                    if(delta < 0){
                        twoOptReverse(placesArray, i+1, k);
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

//    public static ArrayList<Place> twoOpt(ArrayList<Place> places){
//        //Build places array once size bigger than total places
//        places = nearestNeighbor(places);
//        if(places.size() < 4){
//            System.out.println("You must have more than 4 places to use 2opt");
//            return places;
//        }
//        //Build the places Array
//        int [] placesArray = buildPlacesArray(places.size()+1);
//
//        //Make the last element in the places array the same as the first
//        placesArray[places.size()] = placesArray[0];
//
//        //build the distance table
//        int [][] distanceTable = buildDistanceTable(places);
//
//        //create the resultArray and set it equal to the places array
//        int [] resultArray = new int [placesArray.length];
//        System.arraycopy(placesArray, 0, resultArray, 0, placesArray.length);
//
//        //2opt bones
//        boolean improvement = true;
//        while (improvement){
//            improvement = false;
//            for(int i = 0; i <= placesArray.length - 3; i++){
//                for (int k = i + 2; k < placesArray.length -1; k++){
//                    int delta = -dis(placesArray, distanceTable, i, i+1)-dis(placesArray, distanceTable,k,k+1)
//                            +dis(placesArray,distanceTable,i,k)+dis(placesArray,distanceTable,i+1,k+1);
//                    if(delta < 0){
//                        twoOptReverse(placesArray, i+1, k);
//                        improvement = true;
//
//                    }
//                }
//            }
//
//        }
//
//
//
//        //Build result array!
//        ArrayList<Place> result = new ArrayList<>();
//        for (int i = 0 ; i < placesArray.length -1; i++){
//            result.add(places.get(placesArray[i]));
//        }
//        return result;
//
//    }

    public static int dis(int[] placesArray, int[][] distanceTable, int i, int k){
        return distanceTable[placesArray[i]][placesArray[k]];
    }

    public static void twoOptReverse(int[] placesArray, int i1, int k){
        while(i1 < k){
            int temp = placesArray[i1];
            placesArray[i1] = placesArray[k];
            placesArray[k] = temp;
            i1++;
            k--;
        }

    }





}
















