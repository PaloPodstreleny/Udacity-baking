package com.project.podstreleny.pavol.baking.db;

import android.app.Application;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

import com.project.podstreleny.pavol.baking.db.dao.RecipeDao;
import com.project.podstreleny.pavol.baking.db.entities.Recipe;
import com.project.podstreleny.pavol.baking.db.entities.RecipeIngredients;
import com.project.podstreleny.pavol.baking.db.entities.RecipeStep;


@Database(entities = {Recipe.class, RecipeIngredients.class, RecipeStep.class}, version = 1, exportSchema = false)
public abstract class BakingDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "baking_database";
    private static BakingDatabase INSTANCE;

    public abstract RecipeDao movieDao();

    public static BakingDatabase getDatabaseInstance(Application context) {
        if (INSTANCE == null) {
            synchronized (BakingDatabase.class) {
                INSTANCE = Room.databaseBuilder(context, BakingDatabase.class, DATABASE_NAME)
                        .build();
            }
        }
        return INSTANCE;
    }

}