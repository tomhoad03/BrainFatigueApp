package com.example.brainfatigueapp;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

public class DaysActivity {
    String date;
    Integer totalActivityMinutes;
    Integer averageActivityHeartrate;
    Integer caloriesBurned;
    String activityName;
    Integer[] heartRateZones; //{Minutes of Resting/ "out of range", Minutes of "fat burn", Minutes of "cardio", minutes of "peak"}

    public DaysActivity(String newDate, JSONArray newActivityArray){
        date = newDate;
        caloriesBurned = 0;
        Integer[] totalHeartRateZones = {0, 0, 0, 0};
        String activityType = "";
        Float totalActivityMilliseconds = Float.parseFloat("0");
        Float activityMinutesTimesHeartrate = Float.parseFloat("0");
        //if we total up avg heartrate * length of acivity for all activities for the day then divide it by total activity time we can get
        // the overall average heartrate

        for(Object i: newActivityArray) {
            JSONObject activity = (JSONObject) i;
            totalActivityMilliseconds = totalActivityMilliseconds + Float.valueOf((Long) activity.get("duration"));
            activityMinutesTimesHeartrate = activityMinutesTimesHeartrate + ((Float.valueOf((Long) activity.get("duration"))) * (Float.valueOf((Long) activity.get("averageHeartRate"))));
            caloriesBurned = caloriesBurned + Integer.parseInt(String.valueOf(activity.get("calories")));
            Integer[] currentActivityZones = {0, 0, 0, 0};
            JSONArray heartRateZoneArray = (JSONArray) activity.get("heartRateZones");
            //getting the number of minutes in each heart rate Zone
            for (Object zone: heartRateZoneArray){
                JSONObject JSONZone = (JSONObject) zone;
                if("Out of Range".equals(String.valueOf(JSONZone.get("name")))){
                    currentActivityZones[0] = currentActivityZones[0] + Integer.parseInt(String.valueOf(JSONZone.get("minutes")));
                }
                if("Fat Burn".equals(String.valueOf(JSONZone.get("name")))){
                    currentActivityZones[1] = currentActivityZones[1] + Integer.parseInt(String.valueOf(JSONZone.get("minutes")));
                }
                else if("Cardio".equals(String.valueOf(JSONZone.get("name")))){
                    currentActivityZones[2] = currentActivityZones[2] + Integer.parseInt(String.valueOf(JSONZone.get("minutes")));
                }
                else if("Peak".equals(String.valueOf(JSONZone.get("name")))){
                    currentActivityZones[3] = currentActivityZones[3] + Integer.parseInt(String.valueOf(JSONZone.get("minutes")));
                }
            }

            for (int j = 0; j<4; j++){
                totalHeartRateZones[j] = totalHeartRateZones[j] + currentActivityZones[j];
            }

            //going to get the types of activites done that day as maybe there is some correlation between type of activity and fatigue?
            if (activityType.equals("")){
                activityType = String.valueOf(activity.get("activityName"));
                // first activity of the day, that days activity type will be set to that activities type for now
            }
            else if (activityType.equals(String.valueOf(activity.get("activityName")))){
                //the activities are the same so the activity for "that day" can remain being that one activity
            }
            else{
                activityType = "Mixed";
                //they have done multiple activities that day so will set the activity type as mixed
            }
        }
        heartRateZones = totalHeartRateZones;
        activityName = activityType;
        averageActivityHeartrate = Math.round(activityMinutesTimesHeartrate / totalActivityMilliseconds);

    }

    public Integer getAverageActivityHeartrate() {
        return averageActivityHeartrate;
    }

    public Integer getCaloriesBurned() {
        return caloriesBurned;
    }

    public Integer getTotalActivityMinutes() {
        return totalActivityMinutes;
    }

    public Integer[] getHeartRateZones() {
        return heartRateZones;
    }

    public String getActivityName() {
        return activityName;
    }

    public String getDate() {
        return date;
    }
}
