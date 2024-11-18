package pl.news.demo.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OpenAiService {

    private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";

    @Value("${openai.api.key}")
    private String apiKey;

    public String classifyNews(String title, String content) {
        if (apiKey == null || apiKey.isEmpty()) {
            throw new IllegalStateException("API Key is not configured");
        }

        try {
            // Construct JSON payload
            JSONObject userMessage = new JSONObject();
            userMessage.put("role", "user");
            userMessage.put("content", 
                "Classify the following news article as 'Local' or 'Global'. \n" +
                "If it's local, specify the **city and state** it pertains to, and use the format **City, State** (e.g., \"Dallas, Texas\").\n\n" +
                "Please ensure that the city is mentioned by its full name and the state is fully spelled out. \n\n" +
                "If you know which state it regards but you are not sure which city, choose the biggest city in state. \n\n" +
                "News Title: " + title + "\nNews Content: " + content + "\n\n" +
                "Output Format:\n- Classification: Local/Global\n- Location (if Local): <City, State>"
            );

            JSONArray messages = new JSONArray();
            messages.put(userMessage);

            JSONObject payload = new JSONObject();
            payload.put("model", "gpt-4o-mini");
            payload.put("messages", messages);

            // Create the HTTP request
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(OPENAI_API_URL))
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(payload.toString()))
                .build();

            // Send the HTTP request
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println(response.body());

            // Handle the response
            if (response.statusCode() == 200) {
                JSONObject responseBody = new JSONObject(response.body());
                JSONArray choices = responseBody.getJSONArray("choices");
                return choices.getJSONObject(0).getJSONObject("message").getString("content");
            } else {
                throw new RuntimeException("OpenAI API request failed with status: " + response.statusCode() + " and body: " + response.body());
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error during OpenAI API call", e);
        }
    }
}
