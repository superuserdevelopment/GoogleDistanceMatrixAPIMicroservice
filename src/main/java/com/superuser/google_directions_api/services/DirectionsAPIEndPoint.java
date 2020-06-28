package com.superuser.google_directions_api.services;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
public class DirectionsAPIEndPoint {
    String distance;
    String duration;
    String startLoc;
    String endLoc;
    String error = "";

    private void updateData(final String point1, final String point2) {
        String response = getResponse("https://maps.googleapis.com/maps/api/directions/json?origin=" + point1
                + "&destination=" + point2 + "&key=AIzaSyDXlLjUgUAC6GgdUZD4rfmlHKsZrISplgs");
        JSONObject ob = null;
        error = "";
        // Incase a error is returned by the getResponse() function
        if (response.startsWith("Error:")) {
            error = response;
        }
        // Everything goes well and a JSON String is returned
        else {
            try {
                ob = (JSONObject) new JSONParser().parse(response);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            // If valid Places are entered
            if (ob.get("status").equals("OK")) {
                JSONArray ja = (JSONArray) ob.get("routes");
                JSONObject ja2 = (JSONObject) ja.get(0);
                JSONArray ja3 = (JSONArray) ja2.get("legs");
                JSONObject ja4 = (JSONObject) ja3.get(0);
                JSONObject ja5 = (JSONObject) ja4.get("distance");
                JSONObject ja6 = (JSONObject) ja4.get("duration");

                distance = ja5.get("text").toString();
                duration = ja6.get("text").toString();
                startLoc = ja4.get("start_address").toString();
                endLoc = ja4.get("end_address").toString();

            }
            // If invalid places are entered
            else if (ob.get("status").equals("NOT_FOUND") || ob.get("status").equals("ZERO_RESULTS")) {
                error = "Invalid Places Passed, Please Retry";
            }
            // In case of any other error
            else {
                error = response;
            }
        }

    }

    @RequestMapping("/distance/json/{point1}to{point2}")
    public String getDistance(@PathVariable("point1") final String point1,
            @PathVariable("point2") final String point2) {
        System.out.println("JSON Request recieved");
        updateData(point1, point2);
        if (error.isEmpty()) {
            JSONObject output = new JSONObject();
            output.put("Distance", distance);
            output.put("Duration", duration);
            output.put("Starting Location", startLoc);
            output.put("Ending Location", endLoc);
            return output.toJSONString();
        } else {
            return error;
        }
    }

    @RequestMapping("/distance/{point1}to{point2}")
    public String getDistanceString(@PathVariable("point1") final String point1,
            @PathVariable("point2") final String point2) {
        System.out.println("Plain Text Request recieved");
        updateData(point1, point2);
        if (error.isEmpty()) {
            return "Distance is: " + distance + "<br>Duration is: " + duration + "<br>Starting Location: " + startLoc
                    + "<br>Ending Location: " + endLoc;
        } else {
            return error;
        }
    }

    public String getResponse(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            return content.toString();
        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
            return "Error: " + e.toString();
        }
    }
}