package com.project.podstreleny.pavol.baking.service;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

//a generic class that describes a data with a status
public class Resource<T> {

    @NonNull
    private final Status status;

    @Nullable
    private final T data;

    @Nullable public final String message;

    private Resource(@NonNull Status status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> Resource<T> success(@NonNull T data) {
        return new Resource<>(Status.SUCCESS, data, null);
    }

    public static <T> Resource<T> error(String msg, @Nullable T data) {
        return new Resource<>(Status.ERROR, data, msg);
    }

    public static <T> Resource<T> loading(Object data){
        return new Resource<>(Status.LOADING,null,null);
    }

    @NonNull
    public Status getStatus() {
        return status;
    }

    @Nullable
    public T getData() {
        return data;
    }
}