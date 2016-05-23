package appprojgrp_nineteen.det_brugerinddragende_hospital.Api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface LoginService {
    @POST("users/login")
    Call<UserInfo> login(@Body UserContainer user);

    public static class UserInfo {
        public final String email;
        public final String auth_token;

        public UserInfo(String email, String auth_token) {
            this.email = email;
            this.auth_token = auth_token;
        }
    }

    class UserContainer {
        public final User user;

        public UserContainer(User user) {
            this.user = user;
        }
    }

    public static class User {
        public final String email;
        public final String password;

        public User(String email, String password) {
            this.email = email;
            this.password = password;
        }
    }
}