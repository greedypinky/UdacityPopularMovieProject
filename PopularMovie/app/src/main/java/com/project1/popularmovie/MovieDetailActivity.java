package com.project1.popularmovie;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.project1.popularmovie.data.Movie;
import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {

    private final static String TAG = MovieDetailActivity.class.getSimpleName();
    Movie mSelectedMovie;
    // TODO: Define the Detail view items here
    TextView mOriginalTitle;
    TextView mReleaseDate; // or year only ?
    TextView mMoviePlayTime; // for eg. 120 mins
    TextView mMoviePlot;
    TextView mUserRating;
    ImageView mMoviePoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        Toast.makeText(this,"on Create in Detail Activity", Toast.LENGTH_LONG).show();

        //LayoutInflater mInflater = LayoutInflater.from(this);
        RelativeLayout relativeLayout =  (RelativeLayout) findViewById(R.id.relative_details_view);

        // TODO: get movie object from Intent and display the details on the details view
        // unable to pass  the data
        mSelectedMovie =  getIntent().getParcelableExtra("movies");
        Log.e(TAG, "mSelectedMovie" + mSelectedMovie);

        mOriginalTitle = (TextView) findViewById(R.id.text_movie_title);
        mMoviePoster = (ImageView) findViewById(R.id.image_movie_poster);
        mReleaseDate = (TextView)findViewById(R.id.text_movie_release_year); // year or date ??
        mUserRating = (TextView) findViewById(R.id.text_movie_rating);
        mMoviePlot = (TextView)findViewById(R.id.text_movie_plot);
        mMoviePlayTime = (TextView)findViewById(R.id.text_movie_playtime);

        //TODO: get detail view item by finditembyid method

        //TODO: set data into detail view
        if(mSelectedMovie !=null) {

            mOriginalTitle.setText(mSelectedMovie.getMovieTitle());
            Log.d(TAG, "movie title:" + mSelectedMovie.getMovieTitle());
           // mOriginalTitle.setText("DUMMY".toString());
            mUserRating.setText(mSelectedMovie.getUserRating() + "/10");
            mReleaseDate.setText(mSelectedMovie.getReleaseYear());
            mMoviePlot.setText(mSelectedMovie.getMoviePlot());
            mMoviePlayTime.setText("120 mins".toString());
            // compose Thumbnail movie poster's URL
            String posterBaseURL = Movie.getPosterURL("w185");
            String moviePosterURL = posterBaseURL + mSelectedMovie.getPosterPath();
            Log.d(TAG, "Poster URL:" + moviePosterURL);
            // TODO: Use Picasso library to load the image into the imageview
            // Picasso will handle loading the images on a background thread, image decompression and caching the images.
            Picasso.with(this).load(moviePosterURL).into(mMoviePoster);

        } else {
            mOriginalTitle.setText("spiderman");
            mUserRating.setText("8/10");
            mReleaseDate.setText("1980");
            mMoviePlot.setText("it is talk about spiderman");

        }


    }
}
