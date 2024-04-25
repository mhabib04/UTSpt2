package com.example.utspt2.api;

import com.example.utspt2.data.SearchUserResponse;
import com.example.utspt2.data.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("search/users")
    Call<SearchUserResponse> searchUsers(@Query("q") String query);

    //@Headers("Authorization: token <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>")
    @GET("users/{username}")
    Call<User> getUser(@Path("username") String username);
}