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
/*
   You will need to append a base path ahead of this relative path to build the complete url you will need to fetch the image using Picasso.
   It’s constructed using 3 parts:
   The base URL will look like: http://image.tmdb.org/t/p/.
   Then you will need a ‘size’, which will be one of the following: "w92", "w154", "w185", "w342", "w500", "w780", or "original". For most phones we recommend using “w185”.
   And finally the poster path returned by the query, in this case “/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg”
   Combining these three parts gives us a final url of http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg
   */

/*
API Key (v3 auth)

a232d2749d97ef5370730e47205b19cf

API Read Access Token (v4 auth)

eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJhMjMyZDI3NDlkOTdlZjUzNzA3MzBlNDcyMDViMTljZiIsInN1YiI6IjU5N2Q1ZmY5YzNhMzY4NTQyZTAwZWM0ZCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.bUdVQ-xa3T6E7iLK6UsFtO11_1UFfh51iZNbshJNZjo

Example API Request
https://api.themoviedb.org/3/movie/550?api_key=a232d2749d97ef5370730e47205b19cf
 */

   // How to configure :- http://docs.themoviedb.apiary.io/#reference
   // themoviedb 's API:- https://developers.themoviedb.org/3/getting-started
   // TODO: Please replace with your API KEY before running the app
   /// http://api.themoviedb.org/3/movie/popular?api_key=a232d2749d97ef5370730e47205b19cf
   /// http://api.themoviedb.org/3/movie/top_rated?api_key=a232d2749d97ef5370730e47205b19cf

   private final static String V3_API_KEY="a232d2749d97ef5370730e47205b19cf";
   private final static String TAG = MovieNetworkUtility.class.getSimpleName();

   // http://image.tmdb.org/t/p/w185/tWqifoYuwLETmmasnGHO7xBjEtt.jpg
   // private final static String IMAGE_POSTER_BASE_URL="http://image.tmdb.org/t/p/";
   // private final static String IMAGE_POSTER_DEFAULT_SIZE="w185";
   private final static String POPULAR_MOVIE_BASE_URI = "http://api.themoviedb.org/3/movie/popular";
   private final static String TOP_RATED_MOVIE_BASE_URI = "http://api.themoviedb.org/3/movie/top_rated";
   //private final static String MOVIE_THUMBNAIL_DEFAULT_SIZE = "w185";
   private final static String API_KEY_PARAM = "api_key"; // API_KEY parameter
   private final static String SORT_BY_PARAM = "sort_by"; // SORT_BY parameter
   private final static String OVERVIEW_PARAM = "overview"; // A plot synopsis (called overview in the api)
   private final static String VOTE_RATING_PARAM = "vote_average"; // user rating (called vote_average in the api)
   private final static HashMap<String,String> sizesMap = new HashMap<>();
   private final static String DELIMITER = "//A";
   public final static String POPULAR = "popular";
   public final static String TOP_RATED = "top_rated";



   /*
   public enum MOVIE
   {
      POPULAR,TOP_RATED
   }*/

   /**
    * build the URL by URI
    * @return
    */
   public static URL getMovieURL(String sortType) {
      Uri.Builder builder = null;
      Uri movieUri = null;
      URL movieURL = null;

         switch(sortType) {
            case POPULAR:
               builder = Uri.parse(POPULAR_MOVIE_BASE_URI).buildUpon();
               Log.d(TAG,"Popular URL");
               break;

            case TOP_RATED:
               builder = Uri.parse(TOP_RATED_MOVIE_BASE_URI).buildUpon();
               Log.d(TAG,"Top-Rated URL");
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
    * @return
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
