package com.mwano.lauren.baker_street.ui.detail;

import android.annotation.SuppressLint;
import android.graphics.BitmapFactory;
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
 * A Fragment subclass that displays the video and description for the selected step
 * ExoPlayer, help for code: ClassicalMusicQuiz app - Udacity, and Google codelab
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
    private boolean playWhenReady = true;

    public static final String STEP = "step";
    public static final String STEP_ID = "step id";
    private static final String TAG = DetailStepPageFragment.class.getSimpleName();

    // Constructor
    public DetailStepPageFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey(STEP)) {
            mStep = getArguments().getParcelable(STEP);
            mStepId = getArguments().getInt(STEP_ID);
        }
        // TODO Default image?
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_detail_step_page, container, false);
        ButterKnife.bind(this, rootview);
        // Set content to views
        mDescriptionTextView.setText(mStep.getDescription());
//        // Initialise the player
//        mVideoUri = mStep.getVideoURL();
//        initialisePlayer(Uri.parse(mVideoUri));

        // Load the donut as the background image until the video loads.
//        mPlayerView.setDefaultArtwork(BitmapFactory.decodeResource
//                (getResources(), R.drawable.donut_169));

        return rootview;
    }

    /**
     * Initialise ExopLayer
     * @param videoUri The URI of the video to play
     */
    private void initialisePlayer(Uri videoUri) {

        // Initialise player
        if(mExoPlayer == null) {
            // Create an instance of the ExoPlayer
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(
                    new DefaultRenderersFactory(getContext()),
                    new DefaultTrackSelector(), new DefaultLoadControl());

            mPlayerView.setPlayer(mExoPlayer);

            mExoPlayer.setPlayWhenReady(playWhenReady);
            mExoPlayer.seekTo(mCurrentWindow, mPlaybackPosition);
        }

        // If there's a video, create a MediaSource
        if(!TextUtils.isEmpty(mStep.getVideoURL())) {
            mVideoUri = Uri.parse(mStep.getVideoURL());
            MediaSource mediaSource = buildMediaSource(mVideoUri);
            mExoPlayer.prepare(mediaSource, true, false);
        } else {
            // If no video, check if there's a thumbnail, and if not display placeholder
            // Instantiate Picasso to handle the thumbnail image
            Picasso mPicasso = Picasso.get();
            // Set ExoPlayer view to gone and Image view to visible
            mPlayerView.setVisibility(View.GONE);
            mThumbnailView.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(mStep.getThumbnailURL())) {
                mPicasso.load(mStep.getThumbnailURL())
                        .placeholder(R.drawable.donut_169)
                        .error(R.drawable.donut_169)
                        .into(mThumbnailView);
            }
            mPicasso.load(R.drawable.donut_169)
                    .placeholder(R.drawable.donut_169)
                    .error(R.drawable.donut_169)
                    .into(mThumbnailView);
        }
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("Baker Street")).
                createMediaSource(uri);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initialisePlayer(mVideoUri);
        }
        Log.d(TAG, "This is onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!getUserVisibleHint()) {
            return;
        }
        hideSystemUi();
        if ((Util.SDK_INT <= 23 || mExoPlayer == null)) {
            initialisePlayer(mVideoUri);
        }
        Log.d(TAG, "This is onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
        Log.d(TAG, "This is onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
        Log.d(TAG, "This is onStop");
    }

    @Override
    public void setUserVisibleHint(boolean visible) {
        super.setUserVisibleHint(visible);
        if (visible && isResumed()) {
            onResume();
        }
    }

    /**
     * Release the player when the activity is destroyed
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            mPlaybackPosition = mExoPlayer.getCurrentPosition();
            mCurrentWindow = mExoPlayer.getCurrentWindowIndex();
            playWhenReady = mExoPlayer.getPlayWhenReady();
            mExoPlayer.release();
            mExoPlayer = null;
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
     * @param step String description of selected step in step list
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
}



