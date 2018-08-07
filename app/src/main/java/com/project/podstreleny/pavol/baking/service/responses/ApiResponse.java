package com.project.podstreleny.pavol.baking.service.responses;

import android.util.Log;

import java.io.IOException;

import retrofit2.Response;

public class ApiResponse<T> {

    private static final String LOG = ApiResponse.class.getSimpleName();
    private static final int EMPTY_RESPONSE = 204;

    public ApiResponse<T> create(Throwable error) {
        String errorMessage = "unknown error";
        if (error.getMessage() != null) {
            errorMessage = error.getMessage();
        }
        return new ApiErrorResponse<>(errorMessage);
    }

    public ApiResponse<T> create(Response<T> response) {

        if (response.isSuccessful()) {
            T body = response.body();
            if (body == null || response.code() == EMPTY_RESPONSE) {
                return new ApiEmptyResponse<>();
            } else {
                return new ApiSuccessResponse<>(body);
            }
        } else {
            String message = "";
            try {
                message = response.errorBody().string();
                if (message == null || message.isEmpty()) {
                    message = response.message();
                }

            } catch (IOException e) {
                e.printStackTrace();
                Log.e(LOG,"ApiRespons IOException e");
            } catch (NullPointerException e){
                e.printStackTrace();
                Log.e(LOG,"ApiResponse Null pointer exception!");
            }
            return new ApiErrorResponse<>(message);

        }
    }


}