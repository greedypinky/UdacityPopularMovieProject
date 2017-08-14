package com.project1.popularmovie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.project1.popularmovie.data.Movie;
import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {

    private static final String TITLE_KEY = "title";
    private static final String RELEASE_DATE_KEY = "releaseDate";
    private static final String PLAYTIME_KEY = "playTime";
    private static final String USER_RATING_KEY = "userRating";
    private static final String POSTER_KEY = "poster";

    private final static String TAG = MovieDetailActivity.class.getSimpleName();
    Movie mSelectedMovie;
    // the Detail view items
    TextView mOriginalTitle = null;
    TextView mReleaseDate = null;
    //TextView mMoviePlayTime = null; // for eg. 120 mins
    ScrollView mScrollView = null;
    TextView mMoviePlot = null;
    TextView mUserRating = null;
    ImageView mMoviePoster= null;
    String posterBaseURL = Movie.getPosterURL("w185");
    String moviePosterURL = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        RelativeLayout relativeLayout =  (RelativeLayout) findViewById(R.id.relative_details_view);
        mSelectedMovie =  getIntent().getParcelableExtra("movies");
        Log.e(TAG, "mSelectedMovie" + mSelectedMovie.toString());
        mOriginalTitle = (TextView) findViewById(R.id.text_movie_title);
        mMoviePoster = (ImageView) findViewById(R.id.image_movie_poster);
        mReleaseDate = (TextView)findViewById(R.id.text_movie_release_year);
        mUserRating = (TextView) findViewById(R.id.text_movie_rating);
        mScrollView = (ScrollView) findViewById(R.id.scrollview_plot);
        mMoviePlot = (TextView)findViewById(R.id.text_movie_plot);
        //mMoviePlayTime = (TextView)findViewById(R.id.text_movie_playtime);

        if(mSelectedMovie !=null) {

            mOriginalTitle.setText(mSelectedMovie.getMovieTitle());
            mUserRating.setText(mSelectedMovie.getUserRating() + "/10");
            mReleaseDate.setText(mSelectedMovie.getReleaseYear());
            mMoviePlot.setText(mSelectedMovie.getMoviePlot());
            // compose Thumbnail movie poster's URL
            moviePosterURL = posterBaseURL + mSelectedMovie.getPosterPath();
            Log.d(TAG, "Poster URL:" + moviePosterURL);
            // Picasso will handle loading the images on a background thread, image decompression and caching the images.
            Picasso.with(this).load(moviePosterURL).into(mMoviePoster);

        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(TITLE_KEY, mOriginalTitle.getText().toString());
        outState.putString(RELEASE_DATE_KEY, mReleaseDate.getText().toString());
        outState.putString(USER_RATING_KEY, mUserRating.getText().toString());
        outState.putString(POSTER_KEY, moviePosterURL);
    }
}
