package com.example.a05_09nitzanmorexercise3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.a05_09nitzanmorexercise3.MainActivity.client;
import static com.example.a05_09nitzanmorexercise3.MainActivity.url;

public class AddNewMovieActivity extends AppCompatActivity {

    static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_movie);

        // defining all the fields from the layout using their id
        final EditText newMovieName_ET = findViewById(R.id.newMovieName_ET);
        final EditText newMovieRate_ET = findViewById(R.id.newMovieRate_ET);
        final EditText newMovieActors_ET = findViewById(R.id.newMovieActors_ET);
        final EditText newMovieImageUrl_ET = findViewById(R.id.newMovieImageUrl_ET);
        Button newMovieAdd_Btn = findViewById(R.id.newMovieAdd_Btn);

        // add new movie button - once clicked it will make sure the field are not empty
        // and will send post request to the movie web service
        newMovieAdd_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //make sure that field inside edit text are not empty
                if (isEditTextEmpty(newMovieName_ET.getText().toString(), newMovieName_ET)) return;
                if (isEditTextEmpty(newMovieRate_ET.getText().toString(), newMovieRate_ET)) return;
                if (isEditTextEmpty(newMovieActors_ET.getText().toString(), newMovieActors_ET))
                    return;
                if (isEditTextEmpty(newMovieImageUrl_ET.getText().toString(), newMovieImageUrl_ET))
                    return;

                postNewMovie(
                        newMovieName_ET.getText().toString()
                        , newMovieRate_ET.getText().toString()
                        , newMovieActors_ET.getText().toString()
                        , newMovieImageUrl_ET.getText().toString());
            }
        });

        // lion king movie button - send post request to the web service to add new movie
        Button addLionKingMovie_btn = findViewById(R.id.addLionKingMovie_btn);
        addLionKingMovie_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postNewMovie(
                        "Lion King",
                        "85%",
                        "Rowan Atkinson , Matthew Broderick , Niketa Calame-Harris",
                        "https://m.media-amazon.com/images/M/MV5BYTYxNGMyZTYtMjE3MS00MzNjLWFjNmYtMDk3N2FmM2JiM2M1XkEyXkFqcGdeQXVyNjY5NDU4NzI@._V1_UX67_CR0,0,67,98_AL_.jpg"
                );
            }
        });

        // dumbo movie button - send post request to the web service to add new movie
        Button addDumboMovie_Btn = findViewById(R.id.addDumboMovie_Btn);
        addDumboMovie_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postNewMovie(
                        "Dumbo",
                        "72%",
                        "Herman Bing, Billy Bletcher, Edward Brophy, Hall Johnson Choir",
                        "https://m.media-amazon.com/images/M/MV5BNWVmNWQ2OTQtNzJlNC00ZmQwLTg4ZTktZTNmM2IxZTlkOGM3L2ltYWdlXkEyXkFqcGdeQXVyNTAyODkwOQ@@._V1_UX67_CR0,0,67,98_AL_.jpg"
                );
            }
        });

        // robin hood movie button - send post request to the web service to add new movie
        Button addRobinHoodMovie_Btn = findViewById(R.id.addRobinHoodMovie_Btn);
        addRobinHoodMovie_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postNewMovie(
                        "Robin Hood",
                        "76%",
                        "Roger Miller, Peter Ustinov, Terry-Thomas, Brian Bedford",
                        "https://m.media-amazon.com/images/M/MV5BYjUwMzhkM2ItMTU2OC00OTQ5LWJlMDUtMzRmYjc0NDUyNGVhL2ltYWdlL2ltYWdlXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_UX67_CR0,0,67,98_AL_.jpg"
                );
            }
        });


    }

    // method that create request body using the method's parameters,
    // sending post request to the movie web service, and receiving call back
    public static void postNewMovie(String name, String rate, String actors, String imageUrl) {
        RequestBody body = RequestBody.create(JSON, "{\n" +
                "\"name\": \"" + name + "\",\n" +
                "\"rate\": \"" + rate + "\",\n" +
                "\"actors\": \"" + actors + "\",\n" +
                "\"imageUrl\": \"" + imageUrl + "\"\n" +
                "}");

        Request postRequest = new Request.Builder().url(url).post(body).build();
        client.newCall(postRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("===================", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

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
