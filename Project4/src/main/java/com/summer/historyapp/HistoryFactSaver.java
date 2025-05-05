package com.summer.historyapp;

import com.mongodb.client.*;
import org.bson.Document;
import java.util.Scanner;

public class HistoryFactSaver {
    public static void main(String[] args) {

        String uri = "mongodb+srv://shubham4:0kMVSsyFW1rXr7Hb@cluster0.uv7b7.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";

        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase db = mongoClient.getDatabase("history_app");
            MongoCollection<Document> collection = db.getCollection("saved_facts");

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter a historical fact to save: ");
            String input = scanner.nextLine();

            Document doc = new Document("fact", input);
            collection.insertOne(doc);

            System.out.println("\n📜 All Saved Historical Facts:");
            for (Document d : collection.find()) {
                System.out.println("- " + d.getString("fact"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

