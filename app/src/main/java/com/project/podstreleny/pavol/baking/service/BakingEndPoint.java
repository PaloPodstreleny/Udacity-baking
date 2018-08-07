package com.project.podstreleny.pavol.baking.service;

import android.arch.lifecycle.LiveData;

import com.project.podstreleny.pavol.baking.db.entities.Recipe;
import com.project.podstreleny.pavol.baking.service.responses.ApiResponse;

import java.util.List;

import retrofit2.http.GET;

public interface BakingEndPoint {

    @GET()
    LiveData<ApiResponse<List<Recipe>>> getAllRecipies();

}
