package com.project1.popularmovie.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Modal class of Movie
 */
// TODO: Extends Parceble class otherwise the state cannot be saved when rotating the apps etc!!!!!
public class Movie implements Parcelable {

    //public static final String posterBaseURL = "http://image.tmdb.org/t/p/w185/";
    public static final String posterBaseURL = "http://image.tmdb.org/t/p/";
    private String movieId; // movie id
    private String movieTitle; // movie title
    private String movieOriginalTitle; // movie's original title
    private String moviePlot; // overview
    private String userRating; // vote_average
    private String releaseYear; // year of the release date
    private String posterPath; // movie poster path

    // Need to add the CREATOR field
    public final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie> () {
        @Override
        public Movie createFromParcel(Parcel parcel) {
            return null;
        }

        @Override
        public Movie[] newArray(int i) {
            return new Movie[0];
        }
    };
    /**
     * Constructor
     * @param id
     * @param title
     * @param plot
     * @param rating
     * @param dateOfRelease
     * @param poster
     */
    public Movie(String id, String title, String plot, String rating, String dateOfRelease, String poster) {
        movieId = id;
        movieTitle = title;
        moviePlot = plot;
        userRating = rating;
        releaseYear = dateOfRelease;
        posterPath = poster;
    }

    /**
     * Constructor
     * @param in
     */
    private Movie(Parcel in){
        movieId = in.readString();
        movieTitle = in.readString();
        moviePlot = in.readString();
        userRating  = in.readString();
        releaseYear = in.readString();
        posterPath = in.readString();
        //image = in.readInt();
    }

    /**
     * getMovieId
     * @return
     */
    public String getMovieId() {
        return movieId;
    }

    /**
     * getMovieTitle
     * @return
     */
    public String getMovieTitle() {
        return movieTitle;
    }

    /**
     * getMovieOriginalTitle
     * @return
     */
    public String getMovieOriginalTitle() {
        return movieOriginalTitle;
    }

    public String getMoviePlot() {
        return moviePlot;
    }

    public String getUserRating() {
        return userRating;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public static String getPosterURL(String size) {

        return posterBaseURL + size + "/";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }

    /**
     * toString
     * @return
     */
    public String toString() {

        return  "--MovieID--" + movieId
                + "--Title--" + movieOriginalTitle
                + "--MoviePlot--" + moviePlot
                + "--ReleaseYear--" + releaseYear
                + "--userRating--" + userRating
                + "--Poster Path--" + posterPath;

    }
}
