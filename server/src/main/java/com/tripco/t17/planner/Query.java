package com.tripco.t17.planner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;

/**
 * The Query class supports TFFI so it can easily be converted to/from Json by Gson.
 */

public class Query{
    private final static String myDriver = "com.mysql.jdbc.Driver";
    private final static String myUrl = "jdbc:mysql://faure.cs.colostate.edu/cs314";

    // The variables in this class should reflect TFFI.
    public String type;
    public String query;
    public ArrayList<Place> places;

    public String count = "";
    public String searchName = "";



    /**
     * The top level method that does searching.
     */
    public void searchDatabase() {
        //This will do something one day :)
        count = "select count(*) from airports where name like '%" + query + "%' or municipality like '%" + query + "%';";
        searchName = "select id,name,latitude,longitude from airports where name like '%" + query + "%' or municipality like '%" + query + "%' ;";
        try {
            Class.forName(myDriver);
            // connect to the database and query
            try (Connection conn = DriverManager.getConnection(myUrl, "ehuston", "830624075");
                 Statement stCount = conn.createStatement();
                 Statement stQuery = conn.createStatement();
                 ResultSet rsCount = stCount.executeQuery(count);

                 ResultSet rsQuery = stQuery.executeQuery(searchName)
            ) {
                printJSON(rsCount, rsQuery);
            }
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    private void printJSON(ResultSet count, ResultSet query1) throws SQLException {
        System.out.printf("\n{\n");
        System.out.printf("\"type\": \"find\",\n");
        System.out.printf("\"title\": \"%s\",\n",query1);
        System.out.printf("\"places\": [\n");
        Place a = new Place();
// determine the number of results that match the query
        count.next();
        int results = count.getInt(1);
// iterate through query results and print out the airport codes
        while (query1.next()) {
            a.name = query1.getString("name");
            a.id = query1.getString("id");
            a.latitude = query1.getString("latitude");
            a.longitude = query1.getString("longitude");
            places.add(a);


            System.out.printf(" \"%s\"", query1.getString("name"));
            if (--results == 0)
                System.out.printf("\n");
            else
                System.out.printf(",\n");
        }
        System.out.printf(" ]\n}\n");
    }


}
