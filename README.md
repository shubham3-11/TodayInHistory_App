# TodayInHistory_App
Today in History – Mobile to Cloud Project
This project is a distributed mobile-to-cloud application that delivers historical facts based on the current date using the MuffinLabs API. The system consists of three key components: a native Android application, a RESTful Java web service hosted on GitHub Codespaces, and a MongoDB-backed analytics dashboard.

👤 Author
Name: Shubham Singh

🌐 API Used
API Name: Today in History – MuffinLabs
API Documentation: http://history.muffinlabs.com/

📱 Mobile Application (Android – Kotlin with Jetpack Compose)
Fetches a historical event, birth, or death for today’s date.
Prompts the user to enter a fact type (birth, death, or event).
Sends an HTTP request to the deployed web service.
Parses the JSON response and displays the fact on the UI.
Allows users to mark facts as "favourites" to be saved to the backend.
Designed using three Compose views: TextView, EditText, and ImageView.
All network calls are performed on background threads using OkHttp.

🖥️ Java Web Service (Servlet-based API)
Deployed on GitHub Codespaces via a Dockerized ROOT.war application.
Receives HTTP GET requests from the Android app.
Connects to the MuffinLabs API, retrieves JSON data, and filters it by requested fact type.
Sends a clean JSON response back to the app containing only the relevant fact.
Saves the query logs and any marked favourites to MongoDB Atlas.

🧾 MongoDB Atlas Integration
Logs 6+ fields per user request:
Fact type (event, birth, death)
Device model
Timestamp
Latency to 3rd-party API
Response status
Whether the fact was marked as favourite
Logs are stored persistently across service restarts.
MongoDB collections support both real-time user queries and dashboard analytics.

📊 Web Dashboard (JSP-based)
Accessible via a browser on port 8080 of the deployed Codespace.
Displays formatted logs stored in MongoDB using HTML tables (not raw JSON).
Provides at least 3 analytics:
Most frequently requested fact types
Top Android devices by number of requests
Average API latency grouped by fact type

🐳 Deployment
Uses a GitHub Codespace with .devcontainer.json and Dockerfile to build and deploy the ROOT.war file.
Service is exposed on port 8080 and made public for access by the Android app.
URL tested in incognito browsers and Android clients to confirm public accessibility.

📂 Project Structure
HistoryServlet.java – Handles incoming API requests and communication with MuffinLabs.
MongoLogger.java – Inserts logs into MongoDB Atlas.
dashboard.jsp – Displays analytics and logs in tabular form.
MainActivity.kt – Kotlin code for UI logic and network calls in the Android app.
HistoryFactSaver.java – CLI app for Task 1 testing MongoDB read/write.

🧠 Key Technologies
Java, Kotlin, Jetpack Compose, OkHttp, MongoDB Atlas, GitHub Codespaces, Docker, JSP, Servlet API

✅ Highlights
Fully distributed mobile-to-cloud app with REST API integration
Persistent logging and dashboard analytics using MongoDB
Background-threaded networking in Android with Compose UI
Dockerized deployment pipeline with servlet-based backend

