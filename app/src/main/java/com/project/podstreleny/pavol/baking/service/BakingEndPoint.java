package com.project.podstreleny.pavol.baking.service;

import android.arch.lifecycle.LiveData;

import com.project.podstreleny.pavol.baking.db.entities.Recipe;
import com.project.podstreleny.pavol.baking.service.responses.ApiResponse;

import java.util.List;

import retrofit2.http.GET;

public interface BakingEndPoint {

    @GET("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json")
    LiveData<ApiResponse<List<Recipe>>> getAllRecipies();

}
