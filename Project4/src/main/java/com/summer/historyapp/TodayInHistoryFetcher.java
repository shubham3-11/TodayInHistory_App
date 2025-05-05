package com.summer.historyapp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TodayInHistoryFetcher {
    public static void main(String[] args) {
        try {
            URL url = new URL("https://history.muffinlabs.com/date");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            System.out.println("📅 Today in History:");
            System.out.println(response.substring(0, 300) + "...");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

