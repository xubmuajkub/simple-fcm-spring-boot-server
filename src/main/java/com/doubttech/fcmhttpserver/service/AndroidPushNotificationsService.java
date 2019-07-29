package com.doubttech.fcmhttpserver.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AndroidPushNotificationsService {

    @Value("${app.firebase_api_url}")
    private String FIREBASE_API_URL;
    @Value("${app.firebase.server_key}")
    private String FIREBASE_SERVER_KEY;

    public String sendPushNotification() throws IOException {

        String result = "";
        URL url = new URL(FIREBASE_API_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setUseCaches(false);
        conn.setDoInput(true);
        conn.setDoOutput(true);

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "key=" + FIREBASE_SERVER_KEY);
        conn.setRequestProperty("Content-Type", "application/json");

        JSONObject body = new JSONObject();
        String TOPIC = "WEEKLY_TOPIC";
        body.put("to", "/topics/" + TOPIC);
        body.put("priority", "high");

        JSONObject notification = new JSONObject();
        notification.put("title", "Kagura-sama: Love is War");
        notification.put("body", "This is one of my best anime ever");
        notification.put("image", "https://s3.amazonaws.com/filepicker-images-rapgenius/wM7WBX0hQLyfJQ7RdxFx_Anime.jpg");
        notification.put("icon", "https://s3.amazonaws.com/filepicker-images-rapgenius/wM7WBX0hQLyfJQ7RdxFx_Anime.jpg");
        notification.put("badge", 2);

        JSONObject data = new JSONObject();
        data.put("image", "https://s3.amazonaws.com/filepicker-images-rapgenius/wM7WBX0hQLyfJQ7RdxFx_Anime.jpg");
        data.put("subtitle", "Just a best Anime");

        body.put("notification", notification);
        body.put("data", data);

        try {
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(body.toString());
            wr.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }
            result = "succcess";
        } catch (Exception e) {
            e.printStackTrace();
            result = "failure";
        }
        System.out.println("GCM Notification is sent successfully");

        return result;

    }
}