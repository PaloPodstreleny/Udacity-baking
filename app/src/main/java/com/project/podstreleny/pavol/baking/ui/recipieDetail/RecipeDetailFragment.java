package com.project.podstreleny.pavol.baking.ui.recipieDetail;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.project.podstreleny.pavol.baking.R;
import com.project.podstreleny.pavol.baking.db.entities.RecipeStep;
import com.project.podstreleny.pavol.baking.viewModels.RecipeDetailViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailFragment extends Fragment {

    private static final String USER_AGENT = "recipe";
    private static final String LOG = RecipeDetailFragment.class.getSimpleName();

    @BindView(R.id.playerView)
    PlayerView mPlayerView;

    @BindView(R.id.thumbnail_iv)
    ImageView mThumbnailImageView;

    @BindView(R.id.description_tv)
    TextView mDescriptionTextView;


    private SimpleExoPlayer mExoPlayer;
    private boolean isMobile = true;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Check if Fragment contains some arguments -- Arguments are sut just when we are using mobile version of app
        if (!isMobile) {
            // Table version
            final RecipeDetailViewModel viewModel = ViewModelProviders.of(this).get(RecipeDetailViewModel.class);
            viewModel.getActualRecipeStep().observe(this, new Observer<RecipeStep>() {
                @Override
                public void onChanged(@Nullable RecipeStep recipeStep) {
                    if (recipeStep != null) {
                        Log.v(RecipeDetailFragment.class.getSimpleName(), "Everything is good +" + recipeStep.getShortDescription());
                    }
                }
            });
        }


    }

    private void hideImageAndPlayer() {
        mPlayerView.setVisibility(View.GONE);
        mThumbnailImageView.setVisibility(View.GONE);
    }

    private void showThumbnail(String thumbnailURL) {
        mPlayerView.setVisibility(View.INVISIBLE);
        mThumbnailImageView.setVisibility(View.VISIBLE);
        Context context = getContext();
        if (context != null) {
            Glide.with(context).load(thumbnailURL).into(mThumbnailImageView);
        }
    }

    private void initializeExoPlayer(Uri videoUri) {
        mThumbnailImageView.setVisibility(View.INVISIBLE);
        mPlayerView.setVisibility(View.VISIBLE);
        if (mExoPlayer == null) {
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(
                    new DefaultRenderersFactory(getContext()),
                    new DefaultTrackSelector(),
                    new DefaultLoadControl());
            mPlayerView.setPlayer(mExoPlayer);

            mExoPlayer.setPlayWhenReady(false);
            mExoPlayer.seekTo(0, 0);

            MediaSource mediaSource = new ExtractorMediaSource.Factory(new DefaultHttpDataSourceFactory(USER_AGENT)).createMediaSource(videoUri);
            mExoPlayer.prepare(mediaSource, true, false);

        }
    }


    private void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            getDataAndInitPlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || mExoPlayer == null)) {
            getDataAndInitPlayer();
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();

        }
    }


    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }

    }


    private void getDataAndInitPlayer() {

        if (getArguments() != null) {
            final Bundle bundle = getArguments();
            if (bundle.containsKey(Intent.EXTRA_TEXT)) {
                isMobile = true;
                final RecipeStep step = bundle.getParcelable(Intent.EXTRA_TEXT);
                if (step != null) {
                    setDescriptionView(step.getDescription());
                    if (step.hasVideoURL()) {
                        initializeExoPlayer(step.getVideoUri());
                    } else if (step.hasImage()) {
                        showThumbnail(step.getThumbnailURL());
                    } else {
                        hideImageAndPlayer();
                    }

                }
            } else {
                isMobile = false;
            }
        }
    }


    private void setDescriptionView(String text) {
        mDescriptionTextView.setText(text);
    }
}
