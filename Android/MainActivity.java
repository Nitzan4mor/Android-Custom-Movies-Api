package com.example.a05_09nitzanmorexercise3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {


    static final String url = "https://exercise-3-nitzan-mor.herokuapp.com/movies";
    static final OkHttpClient client = new OkHttpClient();
    static final ArrayList<MovieFromWebService> moviesList = new ArrayList<>();
    static AdapterForMovieView adapterForMovieView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //connecting the list view to the MovieFromWebService adapter
        ListView movies_LV = findViewById(R.id.movies_LV);
        adapterForMovieView = new AdapterForMovieView(this, R.layout.movies_list_view_layout, moviesList);
        movies_LV.setAdapter(adapterForMovieView);

        //sending get to the web service
        sendGetToWebService(adapterForMovieView);

        //refresh button - button that send get to the web service each time it's clicked
        FloatingActionButton getFloat_Btn = findViewById(R.id.getFloat_Btn);
        getFloat_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendGetToWebService(adapterForMovieView);
            }
        });

        //add new movie button - once clicked it direct you to add new movie activity
        FloatingActionButton addFloat_Btn = findViewById(R.id.addFloat_Btn);
        addFloat_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), AddNewMovieActivity.class);
                startActivityForResult(intent, 1);
            }
        });

    }

    // each time you return to main activity it sends get to web service
    @Override
    protected void onResume() {
        super.onResume();
        sendGetToWebService(adapterForMovieView);

    }

    // method that create new get request, send it to the web service
    // receive call back, checking the response, turning the response to gson array
    // creating to new Thread, sending the new data to the User Interface Thread
    // clearing the current movie list in the movie adapter
    // and adding the new list that was received from the web service
    public void sendGetToWebService(final AdapterForMovieView adapterForMovieView){
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("==================", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    Log.i("================", "response was successful");
                    final String myResponse = response.body().string();
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();
                    final MovieFromWebService[] moviesFromWebServices = gson.fromJson(myResponse, MovieFromWebService[].class);
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapterForMovieView.clear();
                            for (MovieFromWebService movieFromWebService : moviesFromWebServices) {
                                adapterForMovieView.add(movieFromWebService);
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            if (resultCode == RESULT_OK){
                quickToast("new movie added");
            }
            if (resultCode == RESULT_CANCELED){
                quickToast("failed to add new movie");
            }
        }
    }

    public void quickToast(String message){
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
    }
}