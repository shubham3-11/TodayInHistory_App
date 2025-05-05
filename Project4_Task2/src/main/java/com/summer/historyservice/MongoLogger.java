//SHUBHAM SINGH
//AndrewID: shubham4
package com.summer.historyservice;

import com.mongodb.client.*;
import org.bson.Document;
import java.util.Date;

public class MongoLogger {
    public static void logRequest(String type, String fact, long start, long end, String device) {

        String uri = "mongodb+srv://shubham4:0kMVSsyFW1rXr7Hb@cluster0.uv7b7.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";

        try (MongoClient client = MongoClients.create(uri)) {
            MongoDatabase db = client.getDatabase("history_app");
            MongoCollection<Document> logs = db.getCollection("api_logs");

            Document doc = new Document("timestamp", new Date())
                    .append("type", type)
                    .append("fact", fact)
                    .append("latency", end - start)
                    .append("device", device)
                    .append("status", "success");

            logs.insertOne(doc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
