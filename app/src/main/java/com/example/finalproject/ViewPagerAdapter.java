package com.example.finalproject;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

/**
 * @author willy
 */
public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ViewPagerViewHolder> {

//    private GlideThread glideThread = new GlideThread();
//    private View view;
    private Context curActivity;
    public ViewPagerAdapter(Context context){
        curActivity = context;
    }
    @NonNull
    @Override
    public ViewPagerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_pager_video, parent,false);
//        glideThread.start();
        return new ViewPagerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_pager_video, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewPagerViewHolder holder, int position) {
        Videos.curVideoId = position;
//        Log.d("aaaaa", String.valueOf(position));
        VideoData videoData = Videos.videos.get(Videos.curVideoId);

        holder.videoView.setVideoPath(Videos.getUrl(videoData.getFeedUrl()));
        holder.mLikeNum.setText(Integer.toString(videoData.getLikeCount()));
        holder.mNickView.setText(videoData.getNickname());
        holder.mDesView.setText(videoData.getDescription());
        Glide.with(curActivity).load(R.drawable.ic_button_like_red).into(holder.imageView);


        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Videos.getHttpBitmap(videoData.getFeedUrl(), curActivity).compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes = baos.toByteArray();
        Glide.with(curActivity).load(bytes).into(holder.backgroundView);

        holder.backgroundView.setVisibility(View.VISIBLE);
        holder.backgroundView.bringToFront();
        holder.animationView.setVisibility(View.VISIBLE);
        holder.animationView.bringToFront();
        holder.animationView.playAnimation();
//        Log.d("111", "111");
//        Glide.with(curActivity).load(videoData.getAvatar()).into(holder.imageView);
//        holder.imageView.setVisibility(View.VISIBLE);
//        holder.imageView.requestFocus();
//        holder.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//            @Override
//            public void onPrepared(MediaPlayer mp) {
////                holder.videoView.setBackground();
////                Log.d("123", "123");
////                Glide.with(curActivity).load(videoData.getAvatar())
//                holder.imageView.setVisibility(View.VISIBLE);
//            }
//        });

        holder.videoView.start();
        Log.d("123", String.valueOf(holder.videoView.isPlaying()));
//        holder.backgroundView.setVisibility(View.INVISIBLE);
//        holder.backgroundView.setVisibility(View.INVISIBLE);
//        holder.imageView.setVisibility(View.INVISIBLE);
//        File file = Glide.with(curActivity).load().downloadOnly()

        holder.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                holder.backgroundView.setVisibility(View.INVISIBLE);
                holder.animationView.setVisibility(View.INVISIBLE);
            }
        });
        holder.videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                holder.videoView.start();
            }
        });


        holder.videoView.setOnTouchListener(new View.OnTouchListener() {
            GestureDetector mGesture;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mGesture == null) {
                    mGesture = new GestureDetector(curActivity, new GestureDetector.SimpleOnGestureListener() {
                        @Override
                        public boolean onDown(MotionEvent e) {
                            if (holder.videoView.isPlaying()) {
                                holder.videoView.pause();
                            } else {
                                holder.videoView.start();
                            }
                            return true;
                        }

                        @Override
                        public void onLongPress(MotionEvent e) {
                            super.onLongPress(e);
                        }

                        @Override
                        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                            return super.onScroll(e1, e2, distanceX, distanceY);
                        }
                    });
                    mGesture.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
                        @Override
                        public boolean onSingleTapConfirmed(MotionEvent e) {
                            return true;
                        }

                        @Override
                        public boolean onDoubleTap(MotionEvent e) {
                            holder.imageView.setVisibility(View.VISIBLE);
                            holder.imageView.bringToFront();
                            Glide.with(curActivity).load(R.drawable.ic_button_like_red).into(holder.likeButton);
                            videoData.setLikeCount(videoData.getLikeCount() + 1);
                            holder.mLikeNum.setText(Integer.toString(videoData.getLikeCount()));
                            v.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    // 这里会在 1s 后执行
                                    holder.imageView.setVisibility(View.INVISIBLE);
                                }
                            }, 1000);
                            return true;
                        }

                        @Override
                        public boolean onDoubleTapEvent(MotionEvent e) {
                            return false;
                        }
                    });
                }

                return mGesture.onTouchEvent(event);
//                return false;
            }


        });
    }

    @Override
    public void onViewAttachedToWindow(@NonNull ViewPagerViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        holder.backgroundView.setVisibility(View.VISIBLE);
        holder.backgroundView.bringToFront();
        holder.animationView.setVisibility(View.VISIBLE);
        holder.animationView.bringToFront();
        holder.animationView.playAnimation();
        holder.videoView.start();
    }


    @Override
    public int getItemCount() {
        return Videos.videos.size();
    }


    class ViewPagerViewHolder extends RecyclerView.ViewHolder {
        public VideoView videoView;
        public TextView mNickView;
        public TextView mDesView;
        public TextView mLikeNum;
        public ImageView imageView;
        public ImageButton likeButton;
        public ImageView backgroundView;
        public LottieAnimationView animationView;
        public ViewPagerViewHolder(@NonNull View itemView) {
            super(itemView);
            videoView = itemView.findViewById(R.id.video_view);
            mNickView = itemView.findViewById(R.id.text_nickname);
            mDesView = itemView.findViewById(R.id.description);
            mLikeNum = itemView.findViewById(R.id.number_liekcount);
            imageView = itemView.findViewById(R.id.image_view);
            likeButton = itemView.findViewById(R.id.like_button);
            backgroundView = itemView.findViewById(R.id.background_view);
            animationView = itemView.findViewById(R.id.animation_view);
        }
    }
//    Handler glideHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            ImageView imageView = view.findViewById(R.id.image_view);
//            switch (msg.what) {
//                case 777:
//                    Glide.with(Videos.curActivity).load(Videos.videos.get(Videos.curVideoId).getAvatar()).into(imageView);
//                    imageView.setVisibility(View.VISIBLE);
//                    break;
//                case 888:
//                    imageView.setVisibility(View.INVISIBLE);
//                    break;
//            }
//        }
//    };
//    class GlideThread extends Thread {
//        @Override
//        public void run() {
//            super.run();
//            VideoView videoView = view.findViewById(R.id.video_view);
//            Message message = new Message();
//            message.what = 777;
//            glideHandler.sendMessage(message);
//            while (!videoView.isPlaying()) {
//                try {
//                    sleep(10);
////                    Log.d("123", "123");
//                } catch (InterruptedException ex) {
//                    ex.printStackTrace();
//                }
//            }
//            Message message1 = new Message();
//            message1.what = 888;
//            glideHandler.sendMessage(message1);
//        }
//    }
}

