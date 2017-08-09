package com.project1.popularmovie.data;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

/**
 * NetworkUtility
 */
public class MovieNetworkUtility {

   // TODO: Please replace your own MOVIEDB's API KEY
   private final static String V3_API_KEY="";
   private final static String TAG = MovieNetworkUtility.class.getSimpleName();
   private final static String POPULAR_MOVIE_BASE_URI = "http://api.themoviedb.org/3/movie/popular";
   private final static String TOP_RATED_MOVIE_BASE_URI = "http://api.themoviedb.org/3/movie/top_rated";
   private final static String API_KEY_PARAM = "api_key"; // API_KEY parameter
   private final static String DELIMITER = "//A";
   public final static String POPULAR = "popular";
   public final static String TOP_RATED = "top_rated";

   /**
    * getMovieURL - build the URL by Uri.Builder
    * @param sortType
    * @return
    */
   public static URL getMovieURL(String sortType) {
      Uri.Builder builder = null;
      Uri movieUri = null;
      URL movieURL = null;

         switch(sortType) {
            case POPULAR:
               builder = Uri.parse(POPULAR_MOVIE_BASE_URI).buildUpon();
               Log.d(TAG,"Popular URL:" + builder.toString());
               break;

            case TOP_RATED:
               builder = Uri.parse(TOP_RATED_MOVIE_BASE_URI).buildUpon();
               Log.d(TAG,"Top-Rated URL:" + builder.toString());
               break;

            default:
               break;
         }

         movieUri = builder.appendQueryParameter(API_KEY_PARAM, V3_API_KEY).build();

      try {
         movieURL = new URL(movieUri.toString());
      }catch (MalformedURLException e) {
            e.printStackTrace();
         Log.e(TAG, e.toString());
      }
      return movieURL;
   }

   /**
    * gerRespsonseFromHttp
    * @param url
    * @return response
    * @throws IOException
    */
   public static String getResponseFromHttp(URL url) throws IOException{

      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      InputStream responseInputStream = conn.getInputStream();
      String response = null;

      try {

         Scanner scanner = new Scanner(responseInputStream);
         scanner.useDelimiter(DELIMITER);
         if(scanner.hasNext()) {
            response = scanner.next();
            return response;
         } else {
            return null;
         }

      } finally {
         conn.disconnect();
      }

   }


}
