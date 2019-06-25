package com.github.pedruino.pushnotificationdemo.api.services;

import com.github.pedruino.pushnotificationdemo.api.responses.FirebasePushResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface FirebaseCallService {
    static final String API_KEY = "AAAAjgw3q54:APA91bGowyi4EqKfGMpfOvByQfUm2oBD-4KBB7vIljzllcSEZsjaQO2TUw4qYr1e6gO7v1WNEkG1All_HdaKTV7M5pSAZK-se8l4-okmrNcKnUOgLLBZP0ozbPBoCEWAJ1rsfWRGik8u";
    static final String PATH = "fcm/send";

    @POST(PATH)
    @Headers({"Authorization:key=" + API_KEY, "Content-Type:application/json"})
    public Call<FirebasePushResponse> sendPush(@Body Object object);

    @POST(PATH)
    @Headers({"Authorization:key=" + API_KEY, "Content-Type:application/json"})
    public Call<FirebasePushResponse> sendPush(@Body Map<String, Object> object);
}
