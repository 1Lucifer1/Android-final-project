package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class VideoActivity extends AppCompatActivity {

    public String TAG = "OnClickListener_Search_btn";
    public String KEY_CURRENT_POSITION = "current_position";
    public String KEY_TYPE_FROM = "type_from";

    public ImageView mAvatarView;
    public VideoView mVideoView;
    public TextView mSongView;
    public TextView mNickView;
    public TextView mDesView;
    private TextView mLikeNum;
    public ImageButton mHomeBtn;
    public ImageButton mSearchBtn;
    public ImageButton mAddBtn;
    public ImageButton mStoreBtn;
    public ImageButton mSelfBtn;
    private ViewPager2 viewPager2;
    private ViewPagerAdapter viewPagerAdapter;

    public Bundle tmpBundle;
//    public String nickname;
//    public String song;

//    private LoadVideoThread loadVideoThread = new LoadVideoThread();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_video);

//        Videos.curActivity = this;
        mVideoView = findViewById(R.id.video_view);
        mSongView = findViewById(R.id.text_song);
        //mSongView.setSelected(true);  //解决跑马灯失效
        mNickView = findViewById(R.id.text_nickname);
        mDesView = findViewById(R.id.description);
        mLikeNum = findViewById(R.id.number_liekcount);

        mHomeBtn = findViewById(R.id.btn_home);
        mSearchBtn = findViewById(R.id.btn_search);
        mAddBtn = findViewById(R.id.btn_add);
        mStoreBtn = findViewById(R.id.btn_store);
        mSelfBtn = findViewById(R.id.btn_me);
        viewPager2 = findViewById(R.id.viewpager2);

        viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager2.setAdapter(viewPagerAdapter);
//        viewPager2.setCurrentItem(Videos.curVideoId);

//        Log.d("asdf", String.valueOf(Videos.curVideoId));

//        loadVideoThread.start();

        //获取Intent 检查是主页的还是搜索得来的 0是主页 1是搜索
        Intent intent = getIntent();
        if (intent.getIntExtra(KEY_TYPE_FROM, 0) == 1) {
            // 搜索得来的
            Log.d(TAG, String.valueOf(Videos.curVideoId));
            viewPager2.setCurrentItem(Videos.curVideoId);
//            Message message = new Message();
//            message.what = 888;
//            videoHandler.sendMessage(message);
        }

        tmpBundle = new Bundle();

//        Glide.with(this).load(R.drawable.ic_avatar)
//                .bitmapTransform(new CropCircleTransformation(this))
//                .into(mAvatarView);

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mVideoView.pause();
                Log.d(TAG, "In OnClick");
                startActivity(new Intent(VideoActivity.this, SearchActivity.class));
                // 去除进场动画
                overridePendingTransition(0, 0);
                finish();
            }
        });

        mHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mVideoView.pause();
                Log.d(TAG, "In OnClick");
                Intent intent = new Intent(VideoActivity.this, VideoActivity.class);
                intent.putExtra(KEY_TYPE_FROM, 0);
                startActivity(intent);
                // 去除进场动画
                overridePendingTransition(0, 0);
                finish();
            }
        });

        mStoreBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
//                mVideoView.pause();
                Intent intent = new Intent(VideoActivity.this, StoreActivity.class);
                startActivity(intent);
                // 去除进场动画
                overridePendingTransition(0, 0);
                finish();
            }
        });

        mSelfBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
//                mVideoView.pause();
                Intent intent = new Intent(VideoActivity.this, SelfActivity.class);
                startActivity(intent);
                // 去除进场动画
                overridePendingTransition(0, 0);
                finish();
            }
        });

//        initNetVideo();
    }

//    @Override
//    protected void onPostResume() {
//        super.onPostResume();
//        viewPager2.forceLayout();
//    }

    //    private void initNetVideo() {
//        VideoData videoData = Videos.videos.get(Videos.curVideoId);
//        mVideoView.setVideoPath(Videos.getUrl(videoData.getFeedUrl()));
//        mLikeNum.setText(Integer.toString(videoData.getLikeCount()));
//        mNickView.setText(videoData.getNickname());
//        mDesView.setText(videoData.getDescription());
//        mVideoView.start();
//
//        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mp) {
//                mVideoView.start();
//            }
//        });
//
//        mVideoView.setOnTouchListener(new View.OnTouchListener() {
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (mVideoView.isPlaying()) {
//                    mVideoView.pause();
//                } else {
//                    mVideoView.start();
//                }
//                return false;
//            }
//        });
//    }


//    Handler videoHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what) {
//                case 777:
//                    viewPagerAdapter = new ViewPagerAdapter();
//                    viewPager2.setAdapter(viewPagerAdapter);
////                case 888:
//////                    initNetVideo();
////                    break;
//            }
//        }
//    };
//
//    class LoadVideoThread extends Thread {
//        @Override
//        public void run() {
//            super.run();
//            Videos.getVideos();
//            while (Videos.videos == null) {
//                try {
//                    sleep(10);
//                } catch (InterruptedException ex) {
//                    ex.printStackTrace();
//                }
//            }
//            Message message = new Message();
//            message.what = 777;
//            videoHandler.sendMessage(message);
//        }
//    }
}