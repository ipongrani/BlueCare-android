package com.ipong.rani.bluecare.apolloClient;

import com.apollographql.apollo.ApolloClient;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class BlueCareApolloClient {

    private static final String BASE_URL = "https://of9bca1n83.execute-api.us-east-1.amazonaws.com/dev/OpenBlueCare";
    public static ApolloClient blueCareApolloClient;


    public static ApolloClient getBlueCareApolloClient(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                                        .addInterceptor(loggingInterceptor)
                                        .build();

        blueCareApolloClient = ApolloClient.builder()
                                           .serverUrl(BASE_URL)
                                           .okHttpClient(okHttpClient)
                                           .build();

        return blueCareApolloClient;
    }

}
