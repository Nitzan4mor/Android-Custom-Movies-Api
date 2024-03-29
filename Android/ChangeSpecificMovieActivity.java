package com.Nitzan_Mor.a05_09nitzanmorexercise3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChangeSpecificMovieActivity extends AppCompatActivity {
    private String url;
    private final OkHttpClient client = new OkHttpClient();
    private final MediaType JSON = MediaType.parse("application/json; charset=utf-8");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_specific_movie);

        url = getString(R.string.URL);

        // pulling data using intent extra for the original movie name
        final String movieOriginalName = getIntent().getExtras()
                .getString(getString(R.string.MOVIE_NAME_INTENT_KEY));

        // defining all the text view and image view fields from the layout using their id
        // and setting their text/image using intent extra
        TextView specificMovieName_TV = findViewById(R.id.specificMovieName_TV);
        specificMovieName_TV.setText(movieOriginalName);
        TextView specificMovieRate_TV = findViewById(R.id.specificMovieRate_TV);
        specificMovieRate_TV.setText(getIntent().getExtras()
                .getString(getString(R.string.MOVIE_RATE_INTENT_KEY)));
        TextView specificMovieActors_TV = findViewById(R.id.specificMovieActors_TV);
        specificMovieActors_TV.setText(getIntent().getExtras()
                .getString(getString(R.string.MOVIE_ACTORS_INTENT_KEY)));
        ImageView specificMovieImageUrl_TV = findViewById(R.id.specificMovieImageUrl_TV);
        Picasso.get().load(getIntent().getExtras()
                .getString(getString(R.string.MOVIE_IMAGE_URL_INTENT_KEY))).into(specificMovieImageUrl_TV);

        // defining all the edit text fields from the layout using their id
        final EditText changeSpecificMovieName_ET = findViewById(R.id.changeSpecificMovieName_ET);
        final EditText changeSpecificMovieRate_ET = findViewById(R.id.changeSpecificMovieRate_ET);
        final EditText changeSpecificMovieActors_ET = findViewById(R.id.changeSpecificMovieActors_ET);
        final EditText changeSpecificMovieImageUrl_ET = findViewById(R.id.changeSpecificMovieImageUrl_ET);

        // change specific movie button - once clicked it will make sure the field are not empty
        // and will send put request to the movie web service, and return to Main Activity
        Button changeSpecificMovie_Btn = findViewById(R.id.changeSpecificMovie_Btn);
        changeSpecificMovie_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //make sure that field inside edit text are not empty
                if (isEditTextEmpty(changeSpecificMovieName_ET.getText().toString(), changeSpecificMovieName_ET)) return;
                if (isEditTextEmpty(changeSpecificMovieRate_ET.getText().toString(), changeSpecificMovieRate_ET)) return;
                if (isEditTextEmpty(changeSpecificMovieActors_ET.getText().toString(), changeSpecificMovieActors_ET))return;
                if (isEditTextEmpty(changeSpecificMovieImageUrl_ET.getText().toString(), changeSpecificMovieImageUrl_ET))return;

                updateSpecificMovie(
                        changeSpecificMovieName_ET.getText().toString(),
                        changeSpecificMovieRate_ET.getText().toString(),
                        changeSpecificMovieActors_ET.getText().toString(),
                        changeSpecificMovieImageUrl_ET.getText().toString(),
                        movieOriginalName
                );
                finish();

            }
        });
    }

    // method that create request body using the method's parameters,
    // sending put request to the movie web service, and receiving call back
    public  void updateSpecificMovie(String name, String rate, String actors, String imageUrl, String movieOriginalName){
        RequestBody body = RequestBody.create(JSON, "{\n" +
                "\"name\": \"" + name + "\",\n" +
                "\"rate\": \"" + rate + "\",\n" +
                "\"actors\": \"" + actors + "\",\n" +
                "\"imageUrl\": \"" + imageUrl + "\"\n" +
                "}");
        Request postRequest = new Request.Builder().url(url+"/"+ movieOriginalName).put(body).build();
        client.newCall(postRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("ChangeSpecificMovieActi", e.getMessage());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    Log.i("ChangeSpecificMovieActi", "response was successful");
                }
            }
        });
    }

    // method that return if the edit text given is empty or not
    // in case it's empty, the method will set error massage to the edit text
    public static boolean isEditTextEmpty(String editTextValue, EditText editText) {
        if (TextUtils.isEmpty(editTextValue)) {
            editText.setError("this field cannot be empty");
            return true;
        }
        return false;
    }

}