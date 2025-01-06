package com.example.emmanagement;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit;
    private static final String BASE_URL = "http://10.224.41.11/comp2000"; // Update this if needed

    // Singleton pattern to get a single Retrofit instance
    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL) // Set the API base URL
                    .addConverterFactory(GsonConverterFactory.create()) // Add Gson converter for JSON
                    .build();
        }
        return retrofit;
    }
}
