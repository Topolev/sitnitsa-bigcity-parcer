package com.mycompany.myapp.service.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Vladimir on 02.01.2017.
 */
public class ExternalPageUtil {

    private static final Logger LOG = LoggerFactory.getLogger(ExternalPageUtil.class);

    private static String getEncodingPage(HttpURLConnection connection){
        if (connection.getContentType() == null) return "UTF8";
        return connection.getContentType().replaceFirst(".+charset\\s*=\\s*", "");
    }

    public static String getHtmlPage(String urlString) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(urlString).openConnection();
        String encoding = getEncodingPage(connection);
        BufferedReader in;
        try{
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(),encoding));
        } catch (UnsupportedEncodingException e){
            LOG.debug("Problem with extract encoding in page '{}'. Applying the UTF8.", urlString, e);
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        }
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        return response.toString();
    }

    public static Document getPageDOM(String urlPage) throws IOException {
        return Jsoup.parse(ExternalPageUtil.getHtmlPage(urlPage));
    }
}
