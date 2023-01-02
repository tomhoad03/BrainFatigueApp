package com.example.brainfatigueapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

public class FitBitAPIHandler {
    String access_token;
    Integer averageDailySteps;
    Integer userAge;
    HashMap<String, DaysActivity> activitySummaries;

    public FitBitAPIHandler(String new_access_token){
        access_token = new_access_token;
    }

    public String getUserProfile(){
        try {
            URL userDataURL = new URL("https://api.fitbit.com/1/user/-/profile.json");
            HttpURLConnection conn = (HttpURLConnection) userDataURL.openConnection();
            conn.setRequestProperty("Authorization", "Bearer " + access_token);
            conn.setRequestMethod("GET");
            int response = conn.getResponseCode();
            System.out.println("Reponse code = " + response);
            if (response == HttpURLConnection.HTTP_OK){ // success
                BufferedReader buffered_reader_in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuffer responseString = new StringBuffer();
                //works through all the lines one by one getting them
                while ((inputLine = buffered_reader_in.readLine()) != null) {
                    responseString.append(inputLine);
                }
                buffered_reader_in.close();

                // print result just to check

                System.out.println(responseString.toString());
                return responseString.toString();

            }
            else{
                System.out.println("failed GET request");
                return null;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getUserLifetimeActivities(){
        try {
            URL userDataURL = new URL("https://api.fitbit.com/1/user/-/activities.json");
            HttpURLConnection conn = (HttpURLConnection) userDataURL.openConnection();
            conn.setRequestProperty("Authorization", "Bearer " + access_token);
            conn.setRequestMethod("GET");
            int response = conn.getResponseCode();
            System.out.println("Reponse code = " + response);
            if (response == HttpURLConnection.HTTP_OK){ // success
                BufferedReader buffered_reader_in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuffer responseString = new StringBuffer();
                //works through all the lines one by one getting them
                while ((inputLine = buffered_reader_in.readLine()) != null) {
                    responseString.append(inputLine);
                }
                buffered_reader_in.close();

                // print result just to check
                System.out.println(responseString.toString());
                return responseString.toString();
            }
            else{
                System.out.println("failed GET request");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getUserActivities(){
        try {
            URL userDataURL = new URL("https://api.fitbit.com/1/user/-/activities/list.json?afterDate=2015-01-01&sort=asc&offset=0&limit=100");
            HttpURLConnection conn = (HttpURLConnection) userDataURL.openConnection();
            conn.setRequestProperty("Authorization", "Bearer " + access_token);
            conn.setRequestMethod("GET");
            int response = conn.getResponseCode();
            System.out.println("Reponse code = " + response);
            if (response == HttpURLConnection.HTTP_OK){ // success
                BufferedReader buffered_reader_in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuffer responseString = new StringBuffer();
                //works through all the lines one by one getting them
                while ((inputLine = buffered_reader_in.readLine()) != null) {
                    responseString.append(inputLine);
                }
                buffered_reader_in.close();

                // print result just to check
                System.out.println(responseString.toString());
                return responseString.toString();
            }
            else{
                System.out.println("failed GET request");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void parseProfile(String userProfile){
        /*
        int locationOfAverageSteps = userProfile.indexOf("averageDailySteps") + "averageDailySteps".length() + 2;
        System.out.println(locationOfAverageSteps);
        String averageDailySteps = "";
        while (userProfile.charAt(locationOfAverageSteps) != ','){
            averageDailySteps = averageDailySteps +userProfile.charAt(locationOfAverageSteps);
            locationOfAverageSteps++;
        }
        System.out.println(averageDailySteps);
         */
        JSONParser parser = new JSONParser();
        try{
            Object user = parser.parse(userProfile);
            //JSONArray array = (JSONArray)user;
            JSONObject fullJSON = (JSONObject) user;
            JSONObject userJSON = (JSONObject) fullJSON.get("user");
            System.out.println(userJSON);
            userAge = (int) (long) userJSON.get("age");
            averageDailySteps = (int) (long) userJSON.get("averageDailySteps");
            System.out.println(userAge);
            System.out.println(averageDailySteps);


        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public void parseUserActivities(String activities){
        JSONParser parser = new JSONParser();
        try{
            Object user = parser.parse(activities);
            //JSONArray array = (JSONArray)user;
            JSONObject fullJSON = (JSONObject) user;
            JSONArray activityArray = (JSONArray) fullJSON.get("activities");
            System.out.println(activityArray);
            System.out.println(activityArray.size());
            activitySummaries = new HashMap<>();
            HashMap<String, ArrayList<JSONObject>> jsonHashmap = new HashMap<>();
            for(Object i: activityArray){
                JSONObject activity = (JSONObject) i;
                System.out.println(activity);
                String startTime = (String) activity.get("originalStartTime");
                String startDate = startTime.split("T")[0];
                System.out.println(startDate);
                if (jsonHashmap.containsKey(startDate)){
                    jsonHashmap.get(startDate).add(activity);
                }
                else{
                    jsonHashmap.put(startDate, new ArrayList<>());
                    jsonHashmap.get(startDate).add(activity);
                }
            }
            for (String s: jsonHashmap.keySet()){
                String date = s;
                ArrayList<JSONObject> arrayListOfActivities = jsonHashmap.get(s);
                JSONArray jsonActivityArray = new JSONArray();
                for(JSONObject j: arrayListOfActivities){
                    jsonActivityArray.add(j);
                }
                DaysActivity daysActivity = new DaysActivity(date, jsonActivityArray);
                activitySummaries.put(date, daysActivity);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public HashMap<String, DaysActivity> getActivitySummaries() {
        return activitySummaries;
    }

    public Integer getAverageDailySteps() {
        return averageDailySteps;
    }

}


