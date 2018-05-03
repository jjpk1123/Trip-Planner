package com.tripco.t17.planner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

/**
 * The Query class supports TFFI so it can easily be converted to/from Json by Gson.
 */

public class Query{
    private final static String myDriver = "com.mysql.jdbc.Driver";
    private final static String myUrl = "jdbc:mysql://faure.cs.colostate.edu/cs314";

    // The variables in this class should reflect TFFI.
    public int version;
    public String type;
    public int limit;
    public String query;
    public Filter[] filters;
    public ArrayList<Place> places;

/*
SELECT count(*)
FROM continents
INNER JOIN country ON continents.id = country.continent
INNER JOIN region ON country.id = region.iso_country
INNER JOIN airports ON region.id = airports.iso_region
WHERE (country.name like '%Denver%')
OR (region.name like '%Denver%')
OR (airports.name like '%Denver%')
OR (airports.municipality like '%Denver%')
ORDER BY continents.name, country.name, region.name, airports.municipality, airports.name ASC;
*/

    /**
     * The top level method that does searching.
     */
    public void searchDatabase() {
        String count = "SELECT count(*) ";
        String searchName =
                "SELECT airports.id, airports.name, airports.municipality, airports.type, "
                + "airports.latitude, airports.longitude, region.name, country.name, continents.name ";
        String tableFormat =
                "FROM continents "
                + "INNER JOIN country ON continents.id = country.continent "
                + "INNER JOIN region ON country.id = region.iso_country "
                + "INNER JOIN airports ON region.id = airports.iso_region ";

        count      += tableFormat;
        searchName += tableFormat;

        if (this.filters.length != 0) {
            String where = retrieveWhere(filters[0].attribute, filters[0].values, 0);
            count += where;
            searchName += where;
        }
        String search =
                "WHERE (country.name like '%" + query + "%') "
                        + "OR (region.name like '%" + query + "%') "
                        + "OR (airports.name like '%" + query + "%') "
                        + "OR (airports.municipality like '%" + query + "%') "
                        + "OR (airports.id = '" + query + "')";

        count      += search + " ";
        searchName += search;

//        else {
//            String extra = "";
//            boolean countryName = false;
//            boolean regionName = false;
//            boolean airportsName = false;
//            boolean airportsMunic = false;
//            boolean airportsId = false;
//
//            for (int i = 0; i < this.filters.length; ++i) {
//                String where = "";
//                Filter f1 = filters[i];
//                String a1 = f1.attribute;
//                String[] v1 = f1.values;
//
//                if (a1.equals("country.name")){
//                    countryName = true;
//                }
//                if (a1.equals("region.name")){
//                    regionName = true;
//                }
//                if (a1.equals("airports.name")){
//                    airportsName = true;
//                }
//                if (a1.equals("airports.municipality")){
//                    airportsMunic = true;
//                }
//                if (a1.equals("airports.id")){
//                    airportsId = true;
//                }
//
//                where = retrieveWhere(a1, v1, i);
//
//                count      += where;
//                searchName += where;
//            }
//
//            if (countryName) {
//                extra += "AND (country.name like '%" + query + "%') ";
//            }
//            if (!regionName) {
//                extra += "AND (region.name like '%" + query + "%') ";
//            }
//            if (!airportsName) {
//                extra += "AND (airports.name like '%" + query + "%') ";
//            }
//            if (!airportsMunic) {
//                extra += "AND (airports.municipality like '%" + query + "%') ";
//            }
//            if (!airportsId) {
//                extra += "AND (airports.id = '" + query + "') ";
//            }
//
//            count += extra;
//            searchName += extra;
//        }

        String limit = "LIMIT 31;";

        count += limit;
        searchName += " ORDER BY continents.name, country.name, region.name,"
                + " airports.municipality, airports.name ASC " + limit;

        System.out.println("count: " + count);
        System.out.println("searN: " + searchName);

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

    private String retrieveWhere(String attrib, String[] values, int index) {
        String where = "";
//        if (index == 0)
//            where = "WHERE ";
//        else
//            where = "AND ";

        for (int i = 0; i < values.length; ++i) {
            String add = values[i];
//            if (i > 0) {
//                where += " AND ";
//            }
            where += "AND (" + attrib + " = '" + add + "')";
        }
        System.out.println(where);
        return " " + where;
    }

    private void printJSON(ResultSet count, ResultSet query1) throws SQLException {

        // determine the number of results that match the query
        count.next();
        int results = count.getInt(1);
        // iterate through query results and print out the airport codes
        while (query1.next()) {
            Place a = new Place();
            a.name = query1.getString("airports.name");
            a.id = query1.getString("airports.id");
            a.latitude = query1.getString("airports.latitude");
            a.longitude = query1.getString("airports.longitude");
            a.city = query1.getString("airports.municipality");
            a.state = query1.getString("region.name");
            a.country = query1.getString("country.name");
            a.continent = query1.getString("continents.name");

            places.add(a);


            System.out.printf(" \"%s\"", query1.getString("name"));
            if (--results == 0)
                System.out.printf("\n");
            else
                System.out.printf(",\n");
        }
    }



}
