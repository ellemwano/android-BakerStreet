package com.mwano.lauren.baker_street.ui.detail;

import android.annotation.SuppressLint;
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
import com.mwano.lauren.baker_street.utils.Utils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A Fragment subclass that displays the video and description for the selected step.
 * Code help:
 * https://codelabs.developers.google.com/codelabs/exoplayer-intro/index.html?index=..%2F..%2Findex#0
 */
public class DetailStepPageFragment extends Fragment {

    @Nullable
    @BindView(R.id.tv_detail_description)
    TextView mDescriptionTextView;
    @BindView(R.id.exoplayer)
    PlayerView mPlayerView;
    @BindView(R.id.iv_thumbnail)
    ImageView mThumbnailView;
    @BindView(R.id.tv_step_error)
    TextView mErrorView;

    private SimpleExoPlayer mExoPlayer;

    private Step mStep;
    private int mStepId;
    private Uri mVideoUri;
    private int mCurrentWindow;
    private long mPlaybackPosition;
    private boolean mPlayWhenReady = true;
    private boolean mTwoPane;

    private static final String STEP = "step";
    private static final String STEP_ID = "step id";
    private static final String CURRENT_WINDOW = "current window";
    private static final String PLAYBACK_POSITION = "playback position";
    private static final String CURRENT_STEP = "current step";
    private static final String START_PLAY = "start play when ready";


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
        // Restore state
        if (savedInstanceState != null) {
            mStep = savedInstanceState.getParcelable(CURRENT_STEP);
            mCurrentWindow = savedInstanceState.getInt(CURRENT_WINDOW);
            mPlaybackPosition = savedInstanceState.getLong(PLAYBACK_POSITION);
            mPlayWhenReady = savedInstanceState.getBoolean(START_PLAY);
        } else {
            // Get selected Step and StepId from the intent
            if (getArguments() != null && getArguments().containsKey(STEP)) {
                mStep = getArguments().getParcelable(STEP);
                mStepId = getArguments().getInt(STEP_ID);
            }
        }

        // Set description content to view
        if(mStep != null && mDescriptionTextView != null) {
            mDescriptionTextView.setText(mStep.getDescription());
        }

        if(getActivity().findViewById(R.id.tablet_detail_step_container) != null) {
            mTwoPane = true;
        }

        return rootview;
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

    /**
     * Starting with API level 24 Android supports multiple windows. As our app can be visible
     * but not active in split window mode, we need to initialize the player in onStart.
     */
    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            if (!getUserVisibleHint()) return;
            // Hide system toolbar only if on phone
            if(!mTwoPane) hideSystemUi();
            initialisePlayer();
        }
    }

    /**
     * Before API 24, we wait as long as possible until we grab resources,
     * so we wait until onResume() before initialising the player.
     * hideSystemUi() called here is just an implementation detail to have a pure full screen experience
     */
    @Override
    public void onResume() {
        super.onResume();
        if (Util.SDK_INT <= 23 || mExoPlayer == null) {
            if (!getUserVisibleHint()) return;
            // Hide system toolbar only if on phone
            if(!mTwoPane)hideSystemUi();
            initialisePlayer();
        }
    }

    /**
     * Before API Level 24 there is no guarantee of onStop being called.
     * So we have to release the player as early as possible in onPause.
     * Although, starting with API Level 24 (which brought multi and split window mode)
     * onStop is guaranteed to be called and because in the paused mode our activity is eventually
     * still visible, we should wait until onStop to release the Player, we will release it here too,
     * in onPause. Otherwise, the state will not be saved, as onSavedInstanceState() is called before
     * onStop.
     */
    @Override
    public void onPause() {
        super.onPause();
            releasePlayer();
    }

    /**
     * Starting with API Level 24 (which brought multi and split window mode)onStop is guaranteed
     * to be called and in the paused mode our activity is eventually still visible.
     * Hence we need to wait releasing until onStop.
     */
    @Override
    public void onStop() {
        super.onStop();
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
        }
        mExoPlayer.setPlayWhenReady(mPlayWhenReady);
        mExoPlayer.seekTo(mCurrentWindow, mPlaybackPosition);
        // If there's a video, create a MediaSource
        if (mStep != null) {
            if (!TextUtils.isEmpty(mStep.getVideoURL())) {
                // If no internet connection, show mesage prompting user to connect
                if (!Utils.isNetworkConnected(getActivity())) {
                    showConnectionError();
                }
                mVideoUri = Uri.parse(mStep.getVideoURL());
                MediaSource mediaSource = buildMediaSource(mVideoUri);
                mExoPlayer.prepare(mediaSource, false, false);
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
     * (Source: https://stackoverflow.com/a/18375436/8691157)
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

    // ExoPlayer in full screen for phone (landscape)
    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        if(!mTwoPane) {
            mPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    }

    public void showExoPlayer() {
        mErrorView.setVisibility(View.GONE);
        mThumbnailView.setVisibility(View.GONE);
        mPlayerView.setVisibility(View.VISIBLE);
    }

    public void showImageView() {
        mErrorView.setVisibility(View.GONE);
        mPlayerView.setVisibility(View.GONE);
        mThumbnailView.setVisibility(View.VISIBLE);
    }

    public void showConnectionError() {
        mPlayerView.setVisibility(View.GONE);
        mThumbnailView.setVisibility(View.GONE);
        mErrorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(CURRENT_STEP, mStep);
            outState.putInt(CURRENT_WINDOW, mCurrentWindow);
            outState.putLong(PLAYBACK_POSITION, mPlaybackPosition);
            outState.putBoolean(START_PLAY, mPlayWhenReady);
    }
}




