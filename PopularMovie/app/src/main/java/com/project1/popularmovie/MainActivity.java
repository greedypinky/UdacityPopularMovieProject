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

import com.project1.popularmovie.data.Movie;
import com.project1.popularmovie.data.MovieAdapter;
import com.project1.popularmovie.data.MovieJSONUtility;
import com.project1.popularmovie.data.MovieNetworkUtility;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * MainActivity
 */
public class MainActivity extends AppCompatActivity {

    String TAG = MainActivity.class.getSimpleName();
    private String defaultSortingMethod = MovieNetworkUtility.POPULAR;
    private GridView mMovieGridView = null;
    private TextView mErrorMessage = null;
    private ProgressBar mLoadingIndicator = null;
    private MovieAdapter mMovieAdapter = null;
    private Movie[] mMovieArray = {};
    private static final String MOVIE_KEY = "movies";
    private static final String ERROR_KEY = "error";
    private static final String SORT_POPULAR_KEY = "sortPopular";
    private boolean isSortPopular = true;
    private boolean isErrorOccurs = false;
    ArrayList<Movie> mMovieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boolean hasSavedInstanceState = false;

        // Retrieve store state
        if(savedInstanceState!= null && savedInstanceState.containsKey(MOVIE_KEY)) {
            if(savedInstanceState.containsKey(MOVIE_KEY)) {
                // Retrieve the data from the saveInstanceState
                mMovieList = savedInstanceState.getParcelableArrayList(MOVIE_KEY);
                Log.d(TAG, "restore the movie list");
            }
            if(savedInstanceState.containsKey(ERROR_KEY)) {
               if( savedInstanceState.getBoolean(ERROR_KEY)) {
                   showErrorMessage();
                   Log.d(TAG, "restore the state of error!");
               }
            }
        }

        if(savedInstanceState!= null && savedInstanceState.containsKey(SORT_POPULAR_KEY)) {
            isSortPopular = savedInstanceState.getBoolean(SORT_POPULAR_KEY);
            if(isSortPopular) {
                getSupportActionBar().setTitle(R.string.app_pop_title);
            } else {
                getSupportActionBar().setTitle(R.string.app_top_rated_title);
            }
        }

        mMovieGridView = (GridView) findViewById(R.id.movie_grid);
        mErrorMessage = (TextView) findViewById(R.id.error_message);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        // add listener when clicking on the item
        mMovieGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Movie selectedMovie = mMovieAdapter.getItem(i) ;
                Log.d(TAG, "Selected movie to string:" + selectedMovie.toString());
                Intent intent = new Intent(getApplicationContext(), MovieDetailActivity.class);
                intent.putExtra(MOVIE_KEY, selectedMovie);
                startActivity(intent);

            }
        });

        // init the movie list only when it is null
        if(mMovieList == null) {
            mMovieList = new ArrayList<>();
        }
        mMovieAdapter = new MovieAdapter(this, mMovieList);
        mMovieGridView.setAdapter(mMovieAdapter);
       // If no savedInstanceState, we need to get data from the API
        if(savedInstanceState == null) {
            loadMoviesData(defaultSortingMethod);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if(isErrorOccurs) {
            outState.putBoolean(ERROR_KEY, isErrorOccurs);
        } else {
            ArrayList<Movie> movieList = new ArrayList<>(mMovieAdapter.getMovieList());
            outState.putParcelableArrayList(MOVIE_KEY, movieList);
            outState.putBoolean(SORT_POPULAR_KEY, isSortPopular);
            Log.d(TAG, "onSaveInstanceState");
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_sort_most_popular:
                loadMoviesData(MovieNetworkUtility.POPULAR);
                getSupportActionBar().setTitle(R.string.app_pop_title);
                isSortPopular = true;
                break;
            case R.id.action_sort_top_rated:
                loadMoviesData(MovieNetworkUtility.TOP_RATED);
                getSupportActionBar().setTitle((R.string.app_top_rated_title));
                isSortPopular = false;
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

    /**
     * AsyncTask to get back poster in the other thread
     */
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
                // Show Grid view
                showPosterGrid();
                // update the adapter's movie list
                mMovieAdapter.updateMovieList(Arrays.asList(movies));


            } else {
                // show error message when unable to get the result from the request
                showErrorMessage();
                isErrorOccurs = true;
                Log.e(TAG,"Unable to get Movies data, please check the API KEY!");
            }

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
                Movie[] theMovies = MovieJSONUtility.parseData(getApplicationContext(),response);
                return theMovies;
            }catch(Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * loadMoviesData
     * @param sortType sort by popular or top-rated
     */
    private void loadMoviesData(String sortType){
        showPosterGrid();
        new GetMovieAsyncTask().execute(sortType);
    }
}
