package com.project.podstreleny.pavol.baking.ui.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.project.podstreleny.pavol.baking.R;
import com.project.podstreleny.pavol.baking.db.entities.Recipe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {


    private final Context context;
    private List<Recipe> mRecipies;
    private OnRecipeClickListener listener;
    
    public RecipeAdapter(Context context, OnRecipeClickListener listener){
        this.context = context;
        this.listener = listener;
    }

    public interface OnRecipeClickListener{
        void onClick(Recipe recipe);
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.basic_recipie_list_item,parent,false);
        return new RecipeViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        holder.bind(position);
    }


    @Override
    public int getItemCount() {
        if(mRecipies == null){
            return 0;
        }
        return mRecipies.size();
    }

    public void swapData(List<Recipe> recipes){
        this.mRecipies = recipes;
        notifyDataSetChanged();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.main_layout_cv)
        CardView mMainLayout;

        @BindView(R.id.recipe_img)
        ImageView mRecipeImage;

        @BindView(R.id.recipe_name_tv)
        TextView mRecipeTextView;

        @BindView(R.id.recipe_servings_tv)
        TextView mRecipeServingsTextView;


        public RecipeViewHolder(View view){
            super(view);
            ButterKnife.bind(this,view);
            mMainLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(mRecipies.get(getAdapterPosition()));
        }

        public void bind(int position){
            Recipe recipe = mRecipies.get(position);
            if(recipe.hasImage()){
                Glide.with(context).load(recipe.getImage()).into(mRecipeImage);
            }

            mRecipeTextView.setText(recipe.getName());
            mRecipeServingsTextView.setText(context.getString(R.string.ui_recipe_main_servings, String.valueOf(recipe.getServings())));

        }
    }




}
