package com.project.podstreleny.pavol.baking.ui.recipieMaster;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.podstreleny.pavol.baking.R;
import com.project.podstreleny.pavol.baking.db.entities.RecipeStep;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.AdapterViewHolder> {

    private static final int OFFSET = 1;
    private List<RecipeStep> mSteps;
    private OnRecipeStepClickListener mListener;

    public interface OnRecipeStepClickListener {
        void onClick(RecipeStep recipeStep, int position);
    }

    public StepsAdapter(OnRecipeStepClickListener listener) {
        mListener = listener;
    }

    public ArrayList<RecipeStep> getSteps() {
        return (ArrayList) mSteps;
    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.steps_list_item, parent, false);
        return new AdapterViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, int position) {
        holder.bind(position);
    }


    @Override
    public int getItemCount() {
        if (mSteps == null) {
            return 0;
        }
        return mSteps.size();
    }

    public void swapData(List<RecipeStep> steps) {
        mSteps = steps;
        notifyDataSetChanged();
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.parent_cl)
        ConstraintLayout parent;

        @BindView(R.id.step_tv)
        TextView mStepsTextView;

        @BindView(R.id.number_step_tv)
        TextView numberStepTextView;

        public AdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            parent.setOnClickListener(this);

        }

        public void bind(int position) {
            final RecipeStep step = mSteps.get(position);
            mStepsTextView.setText(step.getShortDescription());
            numberStepTextView.setText(String.valueOf(getAdapterPosition() + OFFSET));
        }

        @Override
        public void onClick(View view) {
            mListener.onClick(mSteps.get(getAdapterPosition()), getAdapterPosition());
        }

    }

}
