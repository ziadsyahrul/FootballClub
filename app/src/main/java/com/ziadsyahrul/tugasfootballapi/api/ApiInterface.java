package com.ziadsyahrul.tugasfootballapi.api;

import com.ziadsyahrul.tugasfootballapi.model.TeamResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("/api/v1/json/1/search_all_teams.php")
    Call<TeamResponse> getTeamResponse(
            @Query("s") String s,
            @Query("c") String c
    );

    @GET("/api/v1/json/1/lookupteam.php")
    Call<TeamResponse> getDetailResponse(
            @Query("id") int id
    );
}
