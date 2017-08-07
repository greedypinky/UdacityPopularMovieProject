package com.project1.popularmovie;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.project1.popularmovie.data.Movie;
import com.project1.popularmovie.data.MovieAdapter;
import com.project1.popularmovie.data.MovieJSONUtility;
import com.project1.popularmovie.data.MovieNetworkUtility;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String TAG = MainActivity.class.getSimpleName();
    private String defaultSortingMethod = MovieNetworkUtility.POPULAR;
    private GridView mMovieGridView = null;
    private TextView mErrorMessage = null;
    private ProgressBar mLoadingIndicator = null;
    private MovieAdapter mMovieAdapter = null;
    private Movie[] mMovieArray = {};
    private static final String MOVIE_KEY = "movies";
    ArrayList<Movie> mMovieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState!= null && savedInstanceState.containsKey(MOVIE_KEY)) {
            // Retrieve the data from the saveInstanceState
            mMovieList = savedInstanceState.getParcelableArrayList(MOVIE_KEY);
        }

        mMovieGridView = (GridView) findViewById(R.id.movie_grid);
        mErrorMessage = (TextView) findViewById(R.id.error_message);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        // TODO: link the click option to open the Detail View - need to study the Indent chapters this week
        mMovieGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //Movie selectedMovie = (Movie) adapterView.getSelectedItem();
                Movie selectedMovie = mMovieAdapter.getItem(i) ;
                // TODO: create Indent and pass the movie information to the Details View
                Log.d(TAG, "Selected movie to string:" + selectedMovie.toString());
                Intent intent = new Intent(getApplicationContext(), MovieDetailActivity.class);
                intent.putExtra(MOVIE_KEY, selectedMovie);
                startActivity(intent);

            }
        });

        mMovieList = new ArrayList<>();
        mMovieAdapter = new MovieAdapter(this, mMovieList);// list cannot be null
        mMovieGridView.setAdapter(mMovieAdapter);
        loadMoviesData(defaultSortingMethod);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(MOVIE_KEY, mMovieList);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_sort_most_popular:
                // TODO add back how to sort by most popular?
                Toast.makeText(this,"sort by popular" , Toast.LENGTH_LONG).show();
                loadMoviesData(MovieNetworkUtility.POPULAR);
                getSupportActionBar().setTitle(R.string.app_pop_title);
                break;
            case R.id.action_sort_top_rated:
                // TODO add back how to sort by top rated?
                Toast.makeText(this,"sort by top-rated" , Toast.LENGTH_LONG).show();
                loadMoviesData(MovieNetworkUtility.TOP_RATED);
                getSupportActionBar().setTitle((R.string.app_top_rated_title));
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showPosterGrid(){
        mMovieGridView.setVisibility(GridView.VISIBLE);
        mErrorMessage.setVisibility(TextView.INVISIBLE);
    }

    private void showErrorMessage(){
        mMovieGridView.setVisibility(GridView.INVISIBLE);
        mErrorMessage.setVisibility(TextView.VISIBLE);
    }

    // TODO: use networkUtility class to get the movies back
    private class GetMovieAsyncTask extends AsyncTask<String , Void , Movie[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(ProgressBar.VISIBLE);

        }

        @Override
        protected void onPostExecute(Movie[] movies) {
            // dismiss the progressbar
            mLoadingIndicator.setVisibility(ProgressBar.INVISIBLE);
            if(movies != null) {
                showPosterGrid();
                mMovieAdapter.updateMovieList(Arrays.asList(movies));

            } else {
                // show error message when unable to get the result from the request
                showErrorMessage();
                Log.e(TAG,"Unable to get Movies data");
            }

            //super.onPostExecute(s);
        }

        @Override
        protected Movie[] doInBackground(String...params) {
            if(params.length == 0) {
                return null;
            }

            String sortMethod = params[0];
            URL sortMovieURL = MovieNetworkUtility.getMovieURL(sortMethod);
            Log.d(TAG,"Movie URL is " + sortMovieURL);
            try {
                String response = MovieNetworkUtility.getResponseFromHttp(sortMovieURL);
                Movie[] mMovies = MovieJSONUtility.parseData(getApplicationContext(),response);
                return mMovies;
            }catch(Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * loadMoviesData
     * @param sortType
     */
    private void loadMoviesData(String sortType){
        showPosterGrid();
        new GetMovieAsyncTask().execute(sortType);
    }
}
