package com.example.android.asynctaskloader;

import android.text.TextUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import androidx.lifecycle.ViewModel;

import com.example.android.asynctaskloader.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MyViewModel extends ViewModel {




    public MutableLiveData<String> text;


    public LiveData<String> getSearchResults(String gitHubQuery) {
        if (text == null) {
            text = new MutableLiveData<>();
            loadSearchResults(gitHubQuery);
        }

        loadSearchResults(gitHubQuery);
        return text;
    }

    public void loadSearchResults(String gitHubQuery) {
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.submit(() -> {

            if (TextUtils.isEmpty(gitHubQuery)) {
                text.postValue("fck");
            }
            try {
                URL searchUrl = new URL(gitHubQuery);
                String githubSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
                text.postValue(githubSearchResults);
            } catch (IOException e) {
                e.printStackTrace();
                text.postValue("fak");
            }


        });

    }
}
