package com.mwano.lauren.baker_street.ui.detail;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.mwano.lauren.baker_street.R;
import com.mwano.lauren.baker_street.model.Step;

import java.util.ArrayList;
import java.util.SimpleTimeZone;

import javax.sql.DataSource;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A Fragment subclass that displays the video and description for the selected step
 * ExoPlayer, help for code: ClassicalMusicQuiz app - Udacity
 */
public class DetailStepPageFragment extends Fragment {

    @BindView(R.id.tv_detail_description)
    TextView mDescriptionTextView;
    @BindView(R.id.exoplayer)
    PlayerView mPlayerView;

    private SimpleExoPlayer mExoPLayer;

    public Step mStep;
    public int mStepId;
    public ArrayList<Step> mStepList;
    private String mVideoUri;

    public static final String STEP = "step";
    public static final String STEP_ID = "step id";

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
        // Initialise the player
        mVideoUri = mStep.getVideoURL();
        initialisePlayer(Uri.parse(mVideoUri));
        return rootview;
    }

    /**
     * Initialise ExopLayer
     * @param videoUri The URI of the video to play
     */
    private void initialisePlayer(Uri videoUri) {
        // Initialise player
        if (mExoPLayer == null) {
            // Create an instance of the ExoPlayer
            RenderersFactory renderersFactory = new DefaultRenderersFactory(getContext());
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPLayer = ExoPlayerFactory.newSimpleInstance(renderersFactory, trackSelector, loadControl);
            // Bind the player to the PlayerView
            mPlayerView.setPlayer(mExoPLayer);
            // Prepare the MediaSource
            String userAgent = Util.getUserAgent(getContext(), "Baker Street");
            DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(getContext(), userAgent);
            // The media to be played
            MediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(videoUri);
            // Prepare the ExoPlayer
            mExoPLayer.prepare(mediaSource);
            // Play the video when ready
            mExoPLayer.setPlayWhenReady(true);
        }
    }

        /**
         * Release ExoPlayer
         */
        private void releasePlayer(){
            mExoPLayer.stop();
            mExoPLayer.release();
            mExoPLayer = null;
        }

    /**
     * Release the player when the activity is destroyed
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
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
