package com.example.brainfatigueapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FitBitAPIHandler {
    String access_token = "going to have to check our git isn't publicly viewable before putting this here";

    public String getUserProfileInfo(){
        try {
            URL userDataURL = new URL("https://api.fitbit.com/1/user/-/profile.json");
            HttpURLConnection conn = (HttpURLConnection) userDataURL.openConnection();
            conn.setRequestProperty("Authorization", "Bearer " + access_token);
            conn.setRequestMethod("GET");
            int response = conn.getResponseCode();
            System.out.println("Reponse code = " + response);
            if (response == HttpURLConnection.HTTP_OK){ // we have connected
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


}
