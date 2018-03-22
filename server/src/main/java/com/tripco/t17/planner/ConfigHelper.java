package com.tripco.t17.planner;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.tripco.t17.server.HTTP;
import spark.Request;

/**
 * A Helper class that calls Config.java to retrieve server_version/optimization_levels
 * and send the info to the client.
 */
public class ConfigHelper {

    private Config config;

    /**
     * Preps the config fields with  before sending the Json to the client.
     * @param request = the client's json
     */
    public ConfigHelper(Request request) {
        // first print the request
        //System.out.println(HTTP.echoRequest(request));

        // extract the information from the body of the request.
        JsonParser jsonParser = new JsonParser();
        JsonElement requestBody = jsonParser.parse(request.body());

        // convert the body of the request to a Java class.
        Gson gson = new Gson();
        config = gson.fromJson(requestBody, Config.class);

        // retrieve version and optimization
        config.retrieveValues();

        // log something
        System.out.println("[Config] responded with " +
                "{v" + config.version + ", opt:" + config.optimization + "}");
    }

    /**
     * Handles the response for the Config object.
     * Does the conversion from a Java class to a Json string.
     * @return server_response Json
     */
    public String getConfig() {
        Gson gson = new Gson();
        return gson.toJson(config);
    }
}
