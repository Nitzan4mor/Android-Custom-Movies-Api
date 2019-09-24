package com.Nitzan_Mor.a05_09nitzanmorexercise3;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterForMovieView extends ArrayAdapter {

    private Context _context;
    private int _layout;
    private ArrayList<MovieFromWebService> _movies;

    //C'tor
    public AdapterForMovieView(Context context, int layout, ArrayList<MovieFromWebService> movies) {
        super(context, layout, movies);
        _context = context;
        _layout = layout;
        _movies = movies;
    }

    // overriding parent method so we can connect between the movie list
    // and the current view in the movie list view
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        //inflating the current view in case its null
        View result = convertView;
        if (result == null)
        {
            result = LayoutInflater.from(_context).inflate(_layout,
                    parent, false);
        }

        //defining how to paint each item in the current view whether it's image or text
        ImageView movieImage_IV = result.findViewById(R.id.movieImage_IV);
        Picasso.get().load(_movies.get(position).getImageUrl()).into(movieImage_IV);
        TextView movieName_TV = result.findViewById(R.id.movieName_TV);
        movieName_TV.setText(_movies.get(position).getName());
        TextView movieRate_TV = result.findViewById(R.id.movieRate_TV);
        movieRate_TV.setText(_movies.get(position).getRate());
        TextView movieActors_TV = result.findViewById(R.id.movieActors_TV);
        movieActors_TV.setText(_movies.get(position).getActors());

        //defining that long click on the current view will allow us to delete it
        result.setOnLongClickListener(new DeleteOnLongClickListener(this, position));

        //defining that regular clear on the current view will open our -
        // Change Specific Movie Activity and using intent extra to forward data to the activity
        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(_context, ChangeSpecificMovieActivity.class);
                intent.putExtra(_context.getString(R.string.MOVIE_NAME_INTENT_KEY), _movies.get(position).getName());
                intent.putExtra(_context.getString(R.string.MOVIE_RATE_INTENT_KEY), _movies.get(position).getRate());
                intent.putExtra(_context.getString(R.string.MOVIE_ACTORS_INTENT_KEY), String.valueOf(_movies.get(position).getActors()));
                intent.putExtra(_context.getString(R.string.MOVIE_IMAGE_URL_INTENT_KEY), String.valueOf(_movies.get(position).getImageUrl()));
                intent.putExtra(_context.getString(R.string.MOVIE_INDEX_INTENT_KEY), position);
                _context.startActivity(intent);
            }
        });

        return result;
    }

    public Context get_context() {
        return _context;
    }

    public ArrayList<MovieFromWebService> get_movies() {
        return _movies;
    }


}