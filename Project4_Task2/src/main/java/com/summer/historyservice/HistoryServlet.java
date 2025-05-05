//SHUBHAM SINGH
//AndrewID: shubham4
package com.summer.historyservice;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;

public class HistoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String path = req.getServletPath();

        //  Handle dashboard
        if ("/dashboard".equals(path)) {
            try {
                List<Document> logs = DashboardUtils.getAllLogs();

                req.setAttribute("totalRequests", logs.size());

                Map<String, Long> typeCounts = DashboardUtils.getTypeCounts(logs);
                String mostCommonType = typeCounts.entrySet().stream()
                        .max(Map.Entry.comparingByValue())
                        .map(Map.Entry::getKey)
                        .orElse("N/A");
                req.setAttribute("mostCommonType", mostCommonType);

                Map<String, Long> deviceCounts = DashboardUtils.getTopDevices(logs);
                String topAgent = deviceCounts.entrySet().stream()
                        .max(Map.Entry.comparingByValue())
                        .map(Map.Entry::getKey)
                        .orElse("N/A");
                req.setAttribute("topAgent", topAgent);

                req.getRequestDispatcher("/dashboard.jsp").forward(req, resp);
                return;

            } catch (Exception e) {
                resp.setStatus(500);
                resp.setContentType("text/plain");
                resp.getWriter().write("Dashboard error: " + e.getMessage());
                return;
            }
        }

        //  Handle /history?type=...
        String type = req.getParameter("type");
        Map<String, String> typeMap = Map.of(
                "birth", "Births",
                "event", "Events",
                "death", "Deaths"
        );

        String mappedType = (type != null) ? typeMap.get(type.toLowerCase()) : null;

        if (mappedType == null) {
            resp.setStatus(400);
            resp.setContentType("application/json");
            resp.getWriter().write("{\"error\":\"Missing or invalid 'type' parameter (use 'birth', 'event', or 'death')\"}");
            return;
        }

        long start = System.currentTimeMillis();

        try {
            URL api = new URL("https://history.muffinlabs.com/date");
            HttpURLConnection conn = (HttpURLConnection) api.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();

            JSONObject data = new JSONObject(sb.toString());
            JSONArray facts = data.getJSONObject("data").getJSONArray(mappedType);
            JSONObject first = facts.getJSONObject(0);

            String factText = first.getString("text");
            String year = first.getString("year");

            long end = System.currentTimeMillis();
            MongoLogger.logRequest(type, factText, start, end, "Pixel 8 Pro");

            JSONObject result = new JSONObject();
            result.put("type", type);
            result.put("text", factText);
            result.put("year", year);

            resp.setContentType("application/json");
            resp.getWriter().write(result.toString());

        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500);
            resp.setContentType("application/json");
            resp.getWriter().write("{\"error\": \"Server error: " + e.getMessage() + "\"}");
        }
    }
}
