package com.wtho.booklisting;


import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public final class QueryUtils {
   public static final String LOG_TAG = MainActivity.class.getName();

   public QueryUtils() {
   }

   /**
    * Returns new URL object from the given string URL
    */
   private static URL createUrl(String stringUrl) {
      URL url = null;
      try {
         url = new URL(stringUrl);
      } catch (MalformedURLException e) {
         Log.e(LOG_TAG, "Problem building the URL.", e);
      }
      return url;
   }

   private static String makeHttpRequest(URL url) throws IOException {
      String jsonResponse = "";
      if (url == null) {
         return jsonResponse;
      }
      HttpURLConnection urlConnection = null;
      InputStream inputStream = null;
      try {
         urlConnection = (HttpURLConnection) url.openConnection();
         urlConnection.setReadTimeout(10000);
         urlConnection.setConnectTimeout(15000);
         urlConnection.setRequestMethod("GET");
         urlConnection.connect();
         if (urlConnection.getResponseCode() == 200) {
            inputStream = urlConnection.getInputStream();
            jsonResponse = readFromStream(inputStream);
         } else {
            Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
         }
      } catch (IOException e) {
         Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
      } finally {
         if (urlConnection != null) {
            urlConnection.disconnect();
         }
         if (inputStream != null) {
            inputStream.close();
         }
      }
      return jsonResponse;
   }

   private static String readFromStream(InputStream inputStream) throws IOException {
      StringBuilder output = new StringBuilder();
      if (inputStream != null) {
         InputStreamReader reader = new InputStreamReader(inputStream);
         BufferedReader bufferedReader = new BufferedReader(reader);
         String line = bufferedReader.readLine();
         while (line != null) {
            output.append(line);
            line = bufferedReader.readLine();
         }
      }
      return output.toString();
   }

   public static List<Books> extractItemsFromJson(String bookJSON) {
      if (TextUtils.isEmpty(bookJSON)) {
         return null;
      }
      List<Books> books = new ArrayList<>();
      try {
         JSONObject jsonResponse = new JSONObject(bookJSON);
         JSONArray earthquakeArray = jsonResponse.getJSONArray("items");
         for (int i = 0; i < earthquakeArray.length(); i++) {
            JSONObject currentEarthquake = earthquakeArray.getJSONObject(i);
            JSONObject properties = currentEarthquake.getJSONObject("volumeInfo");
            String title = properties.getString("title");
            String author = properties.getString("authors");
            String date = properties.getString("publishedDate");

            Books book = new Books(title, author, date);
            books.add(book);
         }
      } catch (JSONException e) {
         Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
      }
      return books;
   }

   public static List<Books> fetchBooksData(String requestUrl) {
      URL url = createUrl(requestUrl);
      String jsonResponse = null;
      try {
         Thread.sleep(2000);
      } catch (InterruptedException e) {
         e.printStackTrace();
      }
      try {
         jsonResponse = makeHttpRequest(url);
      } catch (IOException e) {
         Log.e(LOG_TAG, "Problem making the HTTP request.", e);
      }
      List<Books> books = extractItemsFromJson(jsonResponse);
      return books;
   }
}
