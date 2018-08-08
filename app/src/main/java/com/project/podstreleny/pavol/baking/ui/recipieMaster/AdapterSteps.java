package com.project.podstreleny.pavol.baking.ui.recipieMaster;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.podstreleny.pavol.baking.R;
import com.project.podstreleny.pavol.baking.db.entities.RecipeIngredients;
import com.project.podstreleny.pavol.baking.db.entities.RecipeStep;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterSteps extends RecyclerView.Adapter<AdapterSteps.AdapterViewHolder> {

    private static final int OFFSET = 1;
    private List<RecipeStep> mSteps;

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.steps_list_item,parent,false);
        return new AdapterViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, int position) {
        holder.bind(position);
    }


    @Override
    public int getItemCount() {
        if(mSteps == null){
            return 0;
        }
        return mSteps.size();
    }

    public void swapData(List<RecipeStep> steps){
        mSteps = steps;
        notifyDataSetChanged();
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.step_tv)
        TextView mStepsTextView;

        public AdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void bind(int position){
            final RecipeStep step = mSteps.get(position);
            final String value = getAdapterPosition() + OFFSET +" "+ step.getShortDescription();
            mStepsTextView.setText(value);
        }

    }

}
