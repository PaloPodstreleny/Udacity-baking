package com.project.podstreleny.pavol.baking.ui.recipeStepDetail;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.Fragment;
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

public class RecipeStepDetailFragment extends Fragment {

    private static final String USER_AGENT = "recipe";
    private static final String RECIPE_VIDEO_WIND0W = "RECIPE_WINDOW";
    private static final String RECIPE_VIDEO_SEEK = "RECIPE_VIDEO_SEEK";
    private static final String ROTATED = "ROTATED";

    @BindView(R.id.playerView)
    PlayerView mPlayerView;

    @BindView(R.id.thumbnail_iv)
    ImageView mThumbnailImageView;

    @BindView(R.id.description_tv)
    TextView mDescriptionTextView;

    private ViewModelProvider.Factory factory;
    private SimpleExoPlayer mExoPlayer;
    private boolean isMobile;
    private int mRecipeWindow;
    private long mRecipeSeek;
    private RecipeStep mStep;

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

        final Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(Intent.EXTRA_TEXT)) {
            mStep = bundle.getParcelable(Intent.EXTRA_TEXT);
            isMobile = true;
        } else {
            isMobile = false;
        }


        if (savedInstanceState != null
                && savedInstanceState.containsKey(RECIPE_VIDEO_WIND0W)
                && savedInstanceState.containsKey(RECIPE_VIDEO_SEEK)
                ) {
            mRecipeWindow = savedInstanceState.getInt(RECIPE_VIDEO_WIND0W);
            mRecipeSeek = savedInstanceState.getLong(RECIPE_VIDEO_SEEK);

        }

        //Check if there is tablet version
        if (!isMobile) {
            final RecipeDetailViewModel viewModel =  ViewModelProviders.of(getActivity(),factory).get(RecipeDetailViewModel.class);
            viewModel.getActualRecipeStep().observe(this, new Observer<RecipeStep>() {
                @Override
                public void onChanged(@Nullable RecipeStep step) {
                    mStep = step;
                    showProperView();
                }
            });

            viewModel.getInitialSeek().observe(this, new Observer<Long>() {
                @Override
                public void onChanged(@Nullable Long value) {
                    mRecipeSeek = value;
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

            preparePlayerWithActualSource(videoUri);
        } else {
            preparePlayerWithActualSource(videoUri);
        }
    }

    private void preparePlayerWithActualSource(Uri videoUri) {
        mExoPlayer.seekTo(mRecipeWindow, mRecipeSeek);
        MediaSource mediaSource = new ExtractorMediaSource.Factory(new DefaultHttpDataSourceFactory(USER_AGENT)).createMediaSource(videoUri);
        mExoPlayer.prepare(mediaSource, false, false);
    }


    private void releasePlayer() {
        if (mExoPlayer != null) {
            mRecipeSeek = mExoPlayer.getCurrentPosition();
            mRecipeWindow = mExoPlayer.getCurrentWindowIndex();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    private void showProperView() {
        if (mStep != null) {
            setDescriptionView(mStep.getDescription());
            if (mStep.hasVideoURL()) {
                initializeExoPlayer(mStep.getVideoUri());
            } else if (mStep.hasImage()) {
                showThumbnail(mStep.getThumbnailURL());
            } else {
                hideImageAndPlayer();
            }
        }
    }


    private void setDescriptionView(String text) {
        mDescriptionTextView.setText(text);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            showProperView();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || mExoPlayer == null)) {
            showProperView();
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

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        releasePlayer();
        outState.putLong(RECIPE_VIDEO_SEEK, mRecipeSeek);
        outState.putInt(RECIPE_VIDEO_WIND0W, mRecipeWindow);
        outState.putBoolean(ROTATED,false);
    }

    @VisibleForTesting
    public void setFactory(ViewModelProvider.Factory factory){
        this.factory = factory;
    }
}
