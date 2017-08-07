package com.project1.popularmovie;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.project1.popularmovie.data.Movie;

public class MovieDetailActivity extends AppCompatActivity {

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
        Toast.makeText(this,"on Create in Detail Activity", Toast.LENGTH_LONG).show();

        // TODO: get movie object from Intent and display the details on the details view
        // unable to pass  the data
       // mSelectedMovie =  getIntent().getParcelableExtra("movies");

        mOriginalTitle = (TextView) findViewById(R.id.text_movie_title);
        mUserRating = (TextView) findViewById(R.id.text_movie_rating);
        mMoviePlot = (TextView)findViewById(R.id.text_movie_plot);
        mReleaseDate = (TextView)findViewById(R.id.text_movie_release_year); // year or date ??

        //TODO: get detail view item by finditembyid method

        //TODO: set data into detail view
        if(mSelectedMovie !=null) {
            mOriginalTitle.setText(mSelectedMovie.getMovieTitle());
            mUserRating.setText(mSelectedMovie.getUserRating());
            mReleaseDate.setText(mSelectedMovie.getReleaseYear());
            mMoviePlot.setText(mSelectedMovie.getMoviePlot());
        }


    }
}
