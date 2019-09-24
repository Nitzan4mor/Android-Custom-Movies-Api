package com.Nitzan_Mor.a05_09nitzanmorexercise3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddNewMovieActivity extends AppCompatActivity {

    private String url;
    private final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private final OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_movie);

        url = getString(R.string.URL);

        // defining all the fields from the layout using their id
        final EditText newMovieName_ET = findViewById(R.id.newMovieName_ET);
        final EditText newMovieRate_ET = findViewById(R.id.newMovieRate_ET);
        final EditText newMovieActors_ET = findViewById(R.id.newMovieActors_ET);
        final EditText newMovieImageUrl_ET = findViewById(R.id.newMovieImageUrl_ET);
        Button newMovieAdd_Btn = findViewById(R.id.newMovieAdd_Btn);

        // add new movie button - once clicked it will make sure the field are not empty
        // and will send post request to the movie web service, and return to Main Activity
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

        // lion king movie button - send post request to the web service to add new movie and return to Main Activity
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

        // dumbo movie button - send post request to the web service to add new movie and return to Main Activity
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

        // robin hood movie button - send post request to the web service to add new movie and return to Main Activity
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

        // jack reacher movie button - send post request to the web service to add new movie and return to Main Activity
        Button addJackReacherMovie_btn = findViewById(R.id.addJackReacherMovie_btn);
        addJackReacherMovie_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postNewMovie(
                        "Jack Reacher",
                        "70%",
                        "Tom Cruise, Rosamund Pike, Richard Jenkins, David Oyelowo",
                        "https://m.media-amazon.com/images/M/MV5BMTM1NjUxMDI3OV5BMl5BanBnXkFtZTcwNjg1ODM3OA@@._V1_UY98_CR0,0,67,98_AL_.jpg"
                );
            }
        });

        // Armageddon movie button - send post request to the web service to add new movie and return to Main Activity
        Button addArmageddonMovie_Btn = findViewById(R.id.addArmageddonMovie_Btn);
        addArmageddonMovie_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postNewMovie(
                        "Armageddon",
                        "67%",
                        "Bruce Willis, Billy Bob Thornton, Ben Affleck , Liv Tyler",
                        "https://m.media-amazon.com/images/M/MV5BMGM0NzE2YjgtZGQ4YS00MmY3LTg4ZDMtYjUwNTNiNTJhNTQ5XkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_UY98_CR0,0,67,98_AL_.jpg"
                );
            }
        });

        // Aladdin movie button - send post request to the web service to add new movie and return to Main Activity
        Button addAladdinMovie_Btn = findViewById(R.id.addAladdinMovie_Btn);
        addAladdinMovie_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postNewMovie(
                        "Aladdin",
                        "80%",
                        "Scott Weinger, Robin Williams, Linda Larkin, Jonathan Freeman",
                        "https://m.media-amazon.com/images/M/MV5BY2Q2NDI1MjUtM2Q5ZS00MTFlLWJiYWEtNTZmNjQ3OGJkZDgxXkEyXkFqcGdeQXVyNTI4MjkwNjA@._V1_UX67_CR0,0,67,98_AL_.jpg"
                );
            }
        });

    }

    // method that create request body using the method's parameters,
    // sending post request to the movie web service, and receiving call back
    public void postNewMovie(String name, String rate, String actors, String imageUrl) {
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
                Log.e("AddNewMovieActivity", e.getMessage());
                startIntentWithResult(RESULT_CANCELED);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    Log.i("AddNewMovieActivity", "response was successful");
                    startIntentWithResult(RESULT_OK);
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

    public void startIntentToMainActivity(){
        Intent intent = new Intent(AddNewMovieActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void startIntentWithResult(int resultCode){
        Intent intent = new Intent();
        setResult(resultCode, intent);
        finish();
    }
}