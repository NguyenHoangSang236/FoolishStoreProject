package com.backend.core.usecase.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class GoogleTranslateService {
    public static String translate(String langFrom, String langTo, String text) throws IOException {
        // INSERT YOUR URL HERE
        String urlStr = "https://script.google.com/macros/s/AKfycbzTHvQ4X9u0ROHPE_Q_VtYlki3wFrwyKLsu3gahDhesPA3ESnVNrT6IsZ-1iCdFVBq7/exec" +
                "?q=" + URLEncoder.encode(text, StandardCharsets.UTF_8) +
                "&target=" + langTo +
                "&source=" + langFrom;

        URL url = new URL(urlStr);
        StringBuilder response = new StringBuilder();

        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }
}
