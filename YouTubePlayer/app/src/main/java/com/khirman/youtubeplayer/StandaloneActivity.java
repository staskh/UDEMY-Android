package com.khirman.youtubeplayer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.google.android.youtube.player.YouTubeStandalonePlayer;

public class StandaloneActivity extends AppCompatActivity implements View.OnClickListener {

    private String GOOGLE_API_KEY = "AIzaSyBagAw7h8cJl6vP_qq_JEo_zHFiUOZqUOo";
    private String YOUTUBE_VIDEO_ID = "vBafhBcIE2M";
    private String YOUTUBE_VIDEO_LIST = "yzTuBuRdAyA&list=PLEpfh9jiEpYQJWMW2EF2PgCBhz2SQu6Ld";

    private Button btnPlayVideo;
    private Button btnPlayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standalone);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnPlayVideo = (Button) findViewById(R.id.btnPlayVideo);
        btnPlayVideo.setOnClickListener(this);

        btnPlayList = (Button) findViewById(R.id.btnPlayList);
        btnPlayList.setOnClickListener(this);

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        Intent intent=null;

        switch (v.getId()){
            case R.id.btnPlayVideo:
                intent = YouTubeStandalonePlayer.createVideoIntent(this,GOOGLE_API_KEY,YOUTUBE_VIDEO_ID);
                break;
            case R.id.btnPlayList:
                intent = YouTubeStandalonePlayer.createPlaylistIntent(this,GOOGLE_API_KEY,YOUTUBE_VIDEO_LIST);
                break;
            default:
                break;
        }

        if(intent!=null){
            startActivity(intent);
        }
    }
}
