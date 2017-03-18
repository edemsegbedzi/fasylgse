package com.fasylgh.fasylgse.controller;

import com.fasylgh.fasylgse.model.Stork;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by edem on 18/03/17.
 */

public interface StorksApiInterface {

//    @GET("users/{user}/repos")
//    Call<List<Repo>> listRepos(@Path("user") String user);

    @GET("live")
     Call<List<Stork>> getStorks() ;
}

