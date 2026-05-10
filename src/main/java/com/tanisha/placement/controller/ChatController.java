//package com.tanisha.placement.controller;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//import org.springframework.web.bind.annotation.*;
//
//import java.io.*;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.HashMap;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/chat")
//@CrossOrigin
//public class ChatController {
//
//    private final String API_KEY = "AIzaSyD3JFTO8VOG-g8s1othtHUbgryqVKH0_68";
//
//    @PostMapping
//    public Map<String, String> chat(@RequestBody Map<String, String> body) {
//
//        Map<String, String> result = new HashMap<>();
//
//        try {
//
//            String userMessage = body.get("message");
//
//            String endpoint = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + API_KEY;
//
//            URL url = new URL(endpoint);
//
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("POST");
//            conn.setRequestProperty("Content-Type", "application/json");
//            conn.setDoOutput(true);
//
//            // JSON Request
//            JSONObject request = new JSONObject();
//
//            JSONArray contents = new JSONArray();
//            JSONObject contentObj = new JSONObject();
//
//            JSONArray parts = new JSONArray();
//            parts.put(new JSONObject().put("text", userMessage));
//
//            contentObj.put("parts", parts);
//            contents.put(contentObj);
//
//            request.put("contents", contents);
//
//            // Send request
//            try (OutputStream os = conn.getOutputStream()) {
//                os.write(request.toString().getBytes("utf-8"));
//            }
//
//            BufferedReader br = new BufferedReader(
//                    new InputStreamReader(conn.getInputStream(), "utf-8")
//            );
//
//            StringBuilder response = new StringBuilder();
//            String line;
//
//            while ((line = br.readLine()) != null) {
//                response.append(line.trim());
//            }
//
//            // Parse response
//            JSONObject json = new JSONObject(response.toString());
//
//            String reply = json
//                    .getJSONArray("candidates")
//                    .getJSONObject(0)
//                    .getJSONObject("content")
//                    .getJSONArray("parts")
//                    .getJSONObject(0)
//                    .getString("text");
//
//            result.put("reply", reply);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            result.put("reply", "⚠️ AI service unavailable.");
//        }
//
//        return result;
//    }
//}