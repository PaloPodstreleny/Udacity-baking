package com.project.podstreleny.pavol.baking.service.responses;


public class ApiErrorResponse<T> extends ApiResponse<T> {

    private final String errorMessage;

    public ApiErrorResponse(String errorMessage){
        this.errorMessage = errorMessage;

    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
