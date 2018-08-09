package com.project.podstreleny.pavol.baking.ui.recipieMaster;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.podstreleny.pavol.baking.R;
import com.project.podstreleny.pavol.baking.db.entities.RecipeIngredients;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterIngredients extends RecyclerView.Adapter<AdapterIngredients.IngredientsViewHolder> {


    private List<RecipeIngredients> mIngredients;

    @NonNull
    @Override
    public IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredients_list_item, parent, false);
        return new IngredientsViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull IngredientsViewHolder holder, int position) {
        holder.bind(position);
    }


    @Override
    public int getItemCount() {
        if (mIngredients == null) {
            return 0;
        }
        return mIngredients.size();
    }

    public void swapData(List<RecipeIngredients> ingredients) {
        mIngredients = ingredients;
        notifyDataSetChanged();
    }

    public class IngredientsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ingredients_summary_tv)
        TextView mIngredientsTextView;

        public IngredientsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(int position) {
            final RecipeIngredients ingredients = mIngredients.get(position);
            final String value = ingredients.getQuantity() + " " + ingredients.getMeasure() + " of " + ingredients.getIngredient();
            mIngredientsTextView.setText(value);
        }

    }

}
