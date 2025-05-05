<%@ page import="com.mongodb.client.*, org.bson.Document" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>History Web Service Dashboard</title>
</head>
<body>
<h1>📊 Dashboard - Today in History Service</h1>

<h2>Analytics</h2>
<ul>
    <li>Total requests: <%= request.getAttribute("totalRequests") %></li>
    <li>Most common type: <%= request.getAttribute("mostCommonType") %></li>
    <li>Most frequent device: <%= request.getAttribute("topAgent") %></li>
</ul>

<h2>Request Logs</h2>
<table border="1">
    <tr>
        <th>Timestamp</th><th>Type</th><th>Device</th><th>Fact</th>
    </tr>
    <%
        MongoClient client = MongoClients.create("mongodb+srv://shubham4:0kMVSsyFW1rXr7Hb@cluster0.uv7b7.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0");
        MongoDatabase db = client.getDatabase("history_app");
        MongoCollection<Document> logs = db.getCollection("api_logs");

        for (Document d : logs.find()) {
            out.println("<tr>");
            out.println("<td>" + (d.get("timestamp") != null ? d.get("timestamp").toString() : "N/A") + "</td>");
            out.println("<td>" + d.getString("type") + "</td>");
            out.println("<td>" + d.getString("device") + "</td>");
            out.println("<td>" + d.getString("fact") + "</td>");
            out.println("</tr>");
        }

        client.close();
    %>
</table>
</body>
</html>
