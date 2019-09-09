package com.example.a05_09nitzanmorexercise3;

import android.view.View;
import android.widget.Toast;

import java.io.IOException;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DeleteOnLongClickListener implements View.OnLongClickListener {

    private OkHttpClient _client = MainActivity.client;
    private String _url = MainActivity.url;
    private int _position;
    private AdapterForMovieView _AdapterForMovieView;

    // C'tor
    public DeleteOnLongClickListener(AdapterForMovieView adapterForMovieView, int position) {
        _AdapterForMovieView = adapterForMovieView;
        _position = position;
    }


    // overriding the parent method so in case of long click on the item it will
    // set new sweet alert dialog that will make sure we want to delete this movie from the web service
    // if clicked on confirmation button it will create a delete request and
    // will send it to the movie web service
    @Override
    public boolean onLongClick(final View view) {
        new SweetAlertDialog(_AdapterForMovieView.get_context(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("You will delete this movie :'(")
                .setConfirmText("Delete!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        Request request = new Request.Builder()
                                .url(_url + "/" + MainActivity.moviesList.get(_position).getName())
                                .delete()
                                .build();
                        _client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                quickToast("failed");
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                if (response.isSuccessful()){

                                }

                            }
                        });

                        sDialog.dismissWithAnimation();

                    }
                })
                .setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        quickToast("movie not deleted");
                        sDialog.dismissWithAnimation();
                    }
                })
                .show();
        return true;
    }

    public void quickToast(String message) {
        Toast.makeText(_AdapterForMovieView.get_context(), message, Toast.LENGTH_SHORT).show();
    }

}