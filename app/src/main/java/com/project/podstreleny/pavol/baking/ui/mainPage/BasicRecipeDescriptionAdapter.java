package com.project.podstreleny.pavol.baking.ui.mainPage;

import android.content.Context;
import android.content.Intent;
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
import com.project.podstreleny.pavol.baking.ui.recipieMaster.RecipeMasterActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BasicRecipeDescriptionAdapter extends RecyclerView.Adapter<BasicRecipeDescriptionAdapter.RecipieViewHolder> {


    private final Context context;
    private List<Recipe> mRecipies;
    
    public BasicRecipeDescriptionAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public RecipieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.basic_recipie_list_item,parent,false);
        return new RecipieViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecipieViewHolder holder, int position) {
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

    public class RecipieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.main_layout_cv)
        CardView mMainLayout;

        @BindView(R.id.recipe_img)
        ImageView mRecipieImage;

        @BindView(R.id.recipe_name_tv)
        TextView mRecipeTextView;

        @BindView(R.id.recipe_servings_tv)
        TextView mRecipeServingsTextView;


        public RecipieViewHolder(View view){
            super(view);
            ButterKnife.bind(this,view);
            mMainLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context,RecipeMasterActivity.class);
            intent.putExtra(Intent.EXTRA_TEXT,mRecipies.get(getAdapterPosition()).getId());
            context.startActivity(intent);

        }

        public void bind(int position){
            Recipe recipe = mRecipies.get(position);
            if(recipe.hasImage()){
                Glide.with(context).load(recipe.getImage()).into(mRecipieImage);
            }

            mRecipeTextView.setText(recipe.getName());
            mRecipeServingsTextView.setText(String.valueOf(recipe.getServings()));

        }
    }




}
