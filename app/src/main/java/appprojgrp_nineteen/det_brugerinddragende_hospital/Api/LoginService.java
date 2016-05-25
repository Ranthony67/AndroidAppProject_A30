package appprojgrp_nineteen.det_brugerinddragende_hospital.Api;

import java.util.ArrayList;
import java.util.List;

import appprojgrp_nineteen.det_brugerinddragende_hospital.Models.Child;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface LoginService {
    @POST("users/login")
    Call<UserInfo> login(@Body UserContainer user);

    @DELETE("users/logout")
    Call<Boolean> signout();

    @GET("users/info")
    Call<UserInfo> getInfo();

    public static class UserInfo {
        public final String email;
        public final String auth_token;
        public ArrayList<Child> children;

        public UserInfo(String email, String auth_token, ArrayList<Child> children) {
            this.email = email;
            this.auth_token = auth_token;
            this.children = children;
        }
    }

    class UserContainer {
        public final User user;

        public UserContainer(User user) {
            this.user = user;
        }
    }

    class User {
        public final String email;
        public final String password;

        public User(String email, String password) {
            this.email = email;
            this.password = password;
        }
    }
}
