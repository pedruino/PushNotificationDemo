package com.github.pedruino.pushnotificationdemo.api;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pedruino.pushnotificationdemo.api.services.FirebaseCallService;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "https://fcm.googleapis.com";
    private final Retrofit retrofit;
    private FirebaseCallService firebaseCallService;

    public ApiClient() {

        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        this.retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create(mapper))
                .build();

        this.firebaseCallService = retrofit.create(FirebaseCallService.class);
    }

    public FirebaseCallService getFirebaseCallService() {
        return firebaseCallService;
    }
}
