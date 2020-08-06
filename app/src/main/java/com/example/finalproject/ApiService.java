package com.example.finalproject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * @author willy
 */
public interface ApiService {

    @GET("api/invoke/video/invoke/video")
    Call<List<VideoData>> getVideos();

}
