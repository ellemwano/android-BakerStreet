package com.mwano.lauren.baker_street.ui.detail;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.mwano.lauren.baker_street.R;
import com.mwano.lauren.baker_street.model.Step;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A Fragment subclass that displays the video and description for the selected step.
 * Code help:
 * https://codelabs.developers.google.com/codelabs/exoplayer-intro/index.html?index=..%2F..%2Findex#0
 */
public class DetailStepPageFragment extends Fragment {

    @BindView(R.id.tv_detail_description)
    TextView mDescriptionTextView;
    @BindView(R.id.exoplayer)
    PlayerView mPlayerView;
    @BindView(R.id.iv_thumbnail)
    ImageView mThumbnailView;

    private SimpleExoPlayer mExoPlayer;

    public Step mStep;
    public int mStepId;
    public ArrayList<Step> mStepList;
    private Uri mVideoUri;
    private int mCurrentWindow;
    private long mPlaybackPosition;
    private boolean mPlayWhenReady = true;

    public static final String STEP = "step";
    public static final String STEP_ID = "step id";
    private static final String CURRENT_WINDOW = "current window";
    private static final String PLAYBACK_POSITION = "playback position";
    private static final String CURRENT_STEP = "current step";
    private static final String START_PLAY = "start play when ready";
    private static final String TAG = DetailStepPageFragment.class.getSimpleName();

    // Constructor
    public DetailStepPageFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_detail_step_page, container, false);
        ButterKnife.bind(this, rootview);
        if (savedInstanceState != null) {
            mStep = savedInstanceState.getParcelable(CURRENT_STEP);
//            mCurrentWindow = savedInstanceState.getInt(CURRENT_WINDOW, 0);
//            mPlaybackPosition = savedInstanceState.getLong(PLAYBACK_POSITION, 0);
            mPlayWhenReady = savedInstanceState.getBoolean(START_PLAY);
        } else {
            // Get selected Step and StepId from the intent
            if (getArguments() != null && getArguments().containsKey(STEP)) {
                mStep = getArguments().getParcelable(STEP);
                mStepId = getArguments().getInt(STEP_ID);
            }
        }
        // Set content to views
        mDescriptionTextView.setText(mStep.getDescription());
        // If phone in landscape, hide description
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mDescriptionTextView.setVisibility(View.GONE);
            hideSystemUi();
        }
        return rootview;
    }

    /**
     * Initialise ExoPlayer
     * //* @param  The URI of the video to play
     */
    private void initialisePlayer() {

        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(
                    new DefaultRenderersFactory(getContext()),
                    new DefaultTrackSelector(), new DefaultLoadControl());
            showExoPlayer();
            mPlayerView.setPlayer(mExoPlayer);

            mExoPlayer.setPlayWhenReady(mPlayWhenReady);
            mExoPlayer.seekTo(mCurrentWindow, mPlaybackPosition);
        }
        // If there's a video, create a MediaSource
        if (!TextUtils.isEmpty(mStep.getVideoURL())) {
            mVideoUri = Uri.parse(mStep.getVideoURL());
            MediaSource mediaSource = buildMediaSource(mVideoUri);
            mExoPlayer.prepare(mediaSource, true, false);
        } else {
            // If no video, check if there's a thumbnail, and if not display placeholder
            // Instantiate Picasso to handle the thumbnail image
            Picasso mPicasso = Picasso.get();
            // Remove Player view and show Image view
            showImageView();
            if (!TextUtils.isEmpty(mStep.getThumbnailURL())) {
                mPicasso.load(mStep.getThumbnailURL())
                        .placeholder(R.drawable.donut_169)
                        .error(R.drawable.donut_169)
                        .into(mThumbnailView);
            }
            // If no thumbnail either, show placeholder
            showImageView();
            mThumbnailView.setImageResource(R.drawable.donut_169);
        }
    }

    /**
     * Media Source builder
     *
     * @param uri The Video URO
     * @return A Media Source for the given video
     */
    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("Baker Street")).
                createMediaSource(uri);
    }

    /**
     * Starting with API level 24 Android supports multiple windows. As our app can be visible
     * but not active in split window mode, we need to initialize the player in onStart.
     */
    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initialisePlayer();
        }
        Log.d(TAG, "This is onStart for: " + mStepId);
    }

    /**
     * Before API 24, we wait as long as possible until we grab resources,
     * so we wait until onResume() before initialising the player.
     * hideSystemUi() called here is just an implementation detail to have a pure full screen experience
     */
    @Override
    public void onResume() {
        super.onResume();
        hideSystemUi();
        if (!getUserVisibleHint()) {
            return;
        }
        if ((Util.SDK_INT <= 23 || mExoPlayer == null)) {
            initialisePlayer();
        }
        Log.d(TAG, "This is onResume for: " + mStepId);
    }

    /**
     * Before API Level 24 there is no guarantee of onStop being called.
     * So we have to release the player as early as possible in onPause.
     */
    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
        Log.d(TAG, "This is onPause for :" + mStepId);

    }

    /**
     * Starting with API Level 24 (which brought multi and split window mode)onStop is guaranteed
     * to be called and in the paused mode our activity is eventually still visible.
     * Hence we need to wait releasing until onStop.
     */
    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
        Log.d(TAG, "This is onStop for: " + mStepId);
    }

    /**
     * Release the player when the activity is destroyed
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
        Log.d(TAG, "This is onDestroy for: " + mStepId);
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            mPlaybackPosition = mExoPlayer.getCurrentPosition();
            mCurrentWindow = mExoPlayer.getCurrentWindowIndex();
            mPlayWhenReady = mExoPlayer.getPlayWhenReady();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    /**
     * Set a hint to the system about whether this fragment's UI is currently visible to the user.
     *
     * @param visible The fragment's UI is visible to the user
     */
    @Override
    public void setUserVisibleHint(boolean visible) {
        super.setUserVisibleHint(visible);
        if (visible && isResumed()) {
            onResume();
        } else if (!visible && isResumed()) {
            onPause();
        }
    }

    // TODO Implement in land
    // ExoPlayer in full screen
    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        mPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    /**
     * This method sets up a bundle for the arguments to pass
     * to a new instance of this fragment.
     *
     * @param step   String description of selected step in step list
     * @param stepId int id of the selected step in step list
     * @return fragment
     */
    public static DetailStepPageFragment newStepInstance(Step step, int stepId) {
        DetailStepPageFragment stepFragment = new DetailStepPageFragment();
        // Set the bundle arguments for the fragment.
        Bundle arguments = new Bundle();
        arguments.putParcelable(STEP, step);
        arguments.putInt(STEP_ID, stepId);
        stepFragment.setArguments(arguments);
        return stepFragment;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mDescriptionTextView.setVisibility(View.GONE);
            hideSystemUi();
        } else {
            mDescriptionTextView.setVisibility(View.VISIBLE);
        }
    }

    public void showExoPlayer() {
        mThumbnailView.setVisibility(View.GONE);
        mPlayerView.setVisibility(View.VISIBLE);
    }

    public void showImageView() {
        mPlayerView.setVisibility(View.GONE);
        mThumbnailView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(CURRENT_STEP, mStep);
//        outState.putInt(CURRENT_WINDOW, mCurrentWindow);
//        outState.putLong(PLAYBACK_POSITION, mPlaybackPosition);
        outState.putBoolean(START_PLAY, mPlayWhenReady);
    }
}



