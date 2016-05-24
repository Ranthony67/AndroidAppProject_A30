package appprojgrp_nineteen.det_brugerinddragende_hospital.Api;


import java.io.IOException;

import appprojgrp_nineteen.det_brugerinddragende_hospital.Constants.Constants;
import appprojgrp_nineteen.det_brugerinddragende_hospital.MainApplication;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Inspired by https://futurestud.io/blog
 * And https://futurestud.io/blog/retrofit-add-custom-request-header
 */
public class ServiceGenerator {
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL())
                    .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass) {
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header("Accept", "application/vnd.a30backend.v" + Constants.API_VERSION)
                        .method(original.method(), original.body())
                        .build();
                return chain.proceed(request);
            }
        });

        if (MainApplication.getAuthToken() != "") {

            final String auth_token = MainApplication.getAuthToken();

            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    Request request = original.newBuilder()
                            .header("Authorization", auth_token)
                            .method(original.method(), original.body())
                            .build();
                    return chain.proceed(request);
                }
            });
        }

        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }

    public static String API_BASE_URL() {
        if (Constants.DEVELOPMENT_MODE) {
            return Constants.API_BASE_URL_DEVELOPMENT;
        } else {
            return Constants.API_BASE_URL_PRODUCTION;
        }
    }
}
