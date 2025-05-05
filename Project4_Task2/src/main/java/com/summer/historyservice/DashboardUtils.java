//SHUBHAM SINGH
//AndrewID: shubham4
package com.summer.historyservice;

import com.mongodb.client.*;
import org.bson.Document;
import java.util.*;
import java.util.stream.Collectors;

public class DashboardUtils {

    public static List<Document> getAllLogs() {
        MongoClient client = MongoClients.create("mongodb+srv://shubham4:0kMVSsyFW1rXr7Hb@cluster0.uv7b7.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0"
        );
        MongoCollection<Document> logs = client.getDatabase("history_app").getCollection("api_logs");
        return logs.find().into(new ArrayList<>());
    }

    public static Map<String, Long> getTypeCounts(List<Document> logs) {
        return logs.stream().collect(Collectors.groupingBy(
                doc -> doc.getString("type"), Collectors.counting()
        ));
    }

    public static double getAverageLatency(List<Document> logs) {
        return logs.stream()
                .mapToLong(doc -> doc.getLong("latency"))
                .average()
                .orElse(0.0);
    }

    public static Map<String, Long> getTopDevices(List<Document> logs) {
        return logs.stream().collect(Collectors.groupingBy(
                doc -> doc.getString("device"), Collectors.counting()
        ));
    }
}

