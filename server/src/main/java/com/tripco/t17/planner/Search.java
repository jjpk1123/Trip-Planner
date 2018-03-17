package com.tripco.t17.planner;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.tripco.t17.server.HTTP;
import spark.Request;

/**
 * This class handles to the conversions of the query request/response,
 * converting from the Json string in the request body to a Query object,
 * searching the DB using the Query, and
 * converting the resulting Query object back to a Json string
 * so it may returned as the response.
 */
public class Search {

  private Query query;

  /** Handles query searching request, creating a new query object from the query request.
   * Does the conversion from Json to a Java class before searching the database.
   * @param request to search
   */
  public Search(Request request) {
    // first print the request
    System.out.println(HTTP.echoRequest(request));

    // extract the information from the body of the request.
    JsonParser jsonParser = new JsonParser();
    JsonElement requestBody = jsonParser.parse(request.body());

    // convert the body of the request to a Java class.
    Gson gson = new Gson();
    query = gson.fromJson(requestBody, Query.class);

    // search the database.
    query.searchDatabase();

    // log something.
    System.out.println(query.query);
  }

  /** Handles the response for a Trip object.
   * Does the conversion from a Java class to a Json string.*
   */
  public String getQuery() {
    Gson gson = new Gson();
    return gson.toJson(query);
  }
}