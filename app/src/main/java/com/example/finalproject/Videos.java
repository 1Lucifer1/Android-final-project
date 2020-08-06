package com.example.finalproject;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author willy
 */
public class Videos {

    public static ArrayList<VideoData> videos;

    public static int curVideoId;

    //图片资源缓存
    private static Map<String, Bitmap> bitmapCache = new HashMap<String,Bitmap>();

//    public static ArrayList<File> images;

//    public static Activity curActivity;

    public static void getVideos(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://beiyou.bytedance.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        apiService.getVideos().enqueue(new Callback<List<VideoData>>() {
            @Override
            public void onResponse(Call<List<VideoData>> call, Response<List<VideoData>> response) {
                if (response.body() != null) {
                    videos = (ArrayList<VideoData>) response.body();
                    curVideoId = 0;
                }
            }

            @Override
            public void onFailure(Call<List<VideoData>> call, Throwable t) {
                Log.d("retrofit", t.getMessage());
            }
        });
    }

    public static String getUrl(String s) {
        if(s.substring(0,5).equals("https")){
            return s;
        }else {
            return "https" + s.substring(4);
        }
    }

    public static Bitmap getHttpBitmap(String url, Context context){
        //首先先从缓存中取数据
        Bitmap bitmap = bitmapCache.get(url);
        if(bitmap != null){
            //如果取到就直接返回
            return bitmap;
        }

        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            //根据url获取缩略图
            retriever.setDataSource(getUrl(url), new HashMap());
            //获得第一帧图片
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } finally {
            retriever.release();
        }

//        try{
//            bitmap = Glide.with(context)
//                    .load(getUrl(url))
//                    .asBitmap()
//                    .centerCrop()
//                    .into(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL)
//                    .get();
//        }catch(Exception e){
//            e.printStackTrace();
//        }

        if(bitmap != null){
            //将获取到的图片缓存起来
            bitmapCache.put(url, bitmap);
        }
        Log.d("123", String.valueOf(bitmapCache.size()));
        return bitmap;
    }
}
