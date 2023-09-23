package com.example.home.model.api;

import com.example.home.model.bean.HomeResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiStore {
    @GET("{page}/json")
    Call<HomeResult> getProjectResult(@Path("page") int page, @Query("cid") int cid);
}
