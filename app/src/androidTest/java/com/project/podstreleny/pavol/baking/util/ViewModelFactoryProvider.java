package com.project.podstreleny.pavol.baking.util;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

public class ViewModelFactoryProvider {

    public static   <T extends ViewModel> ViewModelProvider.Factory getViewModelFactory(final T model){
        return new ViewModelProvider.Factory(){
            /**
             * Creates a new instance of the given {@code Class}.
             * <p>
             *
             * @param modelClass a {@code Class} whose instance is requested
             * @return a newly created ViewModel
             */
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                if (modelClass.isAssignableFrom(model.getClass())) {
                    return (T) model;
                }
                throw new IllegalArgumentException("unexpected model class " + modelClass);
            }
        };
    }
}
