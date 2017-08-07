package com.project1.popularmovie.data;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.project1.popularmovie.R;
import com.squareup.picasso.Picasso;
import java.util.List;

import static com.project1.popularmovie.R.layout.abc_action_bar_up_container;
import static com.project1.popularmovie.R.layout.movie_item;

//https://www.raywenderlich.com/127544/android-gridview-getting-started
public class MovieAdapter extends ArrayAdapter<Movie> {

    private Context mContext;
    private List<Movie> mMovies;
    private String currentPosterSize ="w185";

    public static final String TAG = MovieAdapter.class.getSimpleName();


    /**
     * Current Context
     * @param context
     * Objects to display in the gridview
     * @param movies
     */
    public MovieAdapter(Activity context, List<Movie> movies) {
        super(context,0,movies);
        mContext = context;
        mMovies = movies;
        Log.d(TAG,"movies " + mMovies );
    }



    @Override
    public Movie getItem(int position) {
        if(mMovies != null) {
            return mMovies.get(position);
        } else {
            return null;
        }
    }

    @Override
    public int getCount() {

        if(mMovies != null) {
            return mMovies.size();
        } else {
            return 0;
        }
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView,  ViewGroup parent) {

        Log.d(TAG,"getView is called at position " + position);
        // super.getView(position, convertView, parent);

        // Get the movie by position
        Movie movieItem = getItem(position);
        //ImageView posterImageView = null;
        // Inflat the view that hold the image view
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.movie_item, parent, false);
        }

        ImageView posterImageView = (ImageView) convertView.findViewById(R.id.movie_thumbnail_image);
        TextView debugText = (TextView)convertView.findViewById(R.id.debugtext);
        debugText.setText(movieItem.getMovieTitle());

        //posterImageView.setLayoutParams(new GridView.LayoutParams(85,85));
        //posterImageView.setPadding(8, 8, 8, 8);
        //TODO: Set the image by this position
        String posterSize = currentPosterSize;
        String posterBaseURL = Movie.getPosterURL(posterSize);

        String moviePosterURL = posterBaseURL + movieItem.getPosterPath();
        Log.d(TAG,"Load image from poster URL : " + moviePosterURL);

        // TODO: Use Picasso library to load the image into the imageview
        // Picasso will handle loading the images on a background thread, image decompression and caching the images.
       Picasso.with(getContext()).load(moviePosterURL).into(posterImageView);
       //Picasso.with(getContext()).load( "https://lh6.googleusercontent.com/-55osAWw3x0Q/URquUtcFr5I/AAAAAAAAAbs/rWlj1RUKrYI/s1024/A%252520Photographer.jpg").into(posterImageView);

        //posterImageView.setImageResource(mMovies.get(position).);
        String[] mThumbIds  =
                {   "https://lh6.googleusercontent.com/-55osAWw3x0Q/URquUtcFr5I/AAAAAAAAAbs/rWlj1RUKrYI/s1024/A%252520Photographer.jpg",
                        "https://lh4.googleusercontent.com/--dq8niRp7W4/URquVgmXvgI/AAAAAAAAAbs/-gnuLQfNnBA/s1024/A%252520Song%252520of%252520Ice%252520and%252520Fire.jpg",
                        "https://lh5.googleusercontent.com/-7qZeDtRKFKc/URquWZT1gOI/AAAAAAAAAbs/hqWgteyNXsg/s1024/Another%252520Rockaway%252520Sunset.jpg",
                };
        //posterImageView.setImageResource(R.drawable.cupcake);
        Log.d(TAG,"SetImageResource");

       /*
        You can use Picasso to easily load album art thumbnails into your views using:
        Picasso.with(context).load("http://i.imgur.com/DvpvklR.png").into(imageView);
        Picasso will handle loading the images on a background thread, image decompression and caching the images.
        */

        // populate the view item in the AdapterView
        return convertView;
    }

    // update the movie list
    // TODO: call from the Async Task to set the latest movie list
    public void updateMovieList(List<Movie> theMovies) {
        mMovies = theMovies;
        Log.e(TAG,"is the list empty?" + mMovies.size());
        // otherwise Gridview will not update itself!!
        notifyDataSetChanged();
    }

    // update poster size
    public void updatePosterSize(String size) {

        currentPosterSize = size;

    }
}
