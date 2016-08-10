package com.android.ming.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.VideoView;

import com.android.ming.R;
import com.android.ming.app.Consts;
import com.android.ming.utils.SPUtil;

public class VideoPlayActivity extends BaseActivity implements MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener {
    private static String VIDEO_URL = "url";
    private static String IS_PORTRAIT = "portrait";
    private static String IS_FREE = "free";
    private VideoView videoView;
    private ProgressBar pb;
    private boolean isPortrait;// 是否竖屏
    private boolean isFree;// 是否免费

    public static Intent createIntent(Context context, String url, boolean isPortrait) {
        return createIntent(context, url, isPortrait, false);
    }

    public static Intent createIntent(Context context, String url, boolean isPortrait, boolean isFree) {
        Intent intent = new Intent(context, VideoPlayActivity.class);
        intent.putExtra(VIDEO_URL, url);
        intent.putExtra(IS_PORTRAIT, isPortrait);
        intent.putExtra(IS_FREE, isFree);
        return intent;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);

        videoView = (VideoView) this.findViewById(R.id.videoView);
        pb = (ProgressBar) findViewById(R.id.pb);
        String url = getIntent().getStringExtra(VIDEO_URL);
        isPortrait = getIntent().getBooleanExtra(IS_PORTRAIT, false);
        isFree = getIntent().getBooleanExtra(IS_FREE, false);
        VideoView videoView = (VideoView) this.findViewById(R.id.videoView);
        android.widget.MediaController mc = new android.widget.MediaController(this);
        mc.setVisibility(View.INVISIBLE);

        videoView.setMediaController(mc);
        videoView.setVideoURI(Uri.parse(url));
        videoView.requestFocus();
        videoView.setOnPreparedListener(this);
        videoView.setOnCompletionListener(this);
        videoView.setOnErrorListener(this);
        videoView.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isPortrait && getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        finish();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        pb.setVisibility(View.INVISIBLE);
        mp.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                int currentPosition = videoView.getCurrentPosition() / 1000;

                if (currentPosition >= 20) {
                    if (isFree && SPUtil.getInt(VideoPlayActivity.this, Consts.SP.VIP) == 0) {
                        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        }
                        videoView.stopPlayback();
                        pay1Dialog.show();
                    }
                }
            }
        });
    }
}