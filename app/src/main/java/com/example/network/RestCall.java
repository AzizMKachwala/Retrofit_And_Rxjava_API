package com.example.network;

import com.example.networkResponse.CategoryListResponse;
import com.example.networkResponse.CommonResponse;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Single;

public interface RestCall {
    @FormUrlEncoded
    @POST("CategoryController.php")
    Single<CommonResponse> AddCategory(
            @Field("tag") String tag,
            @Field("category_name") String category_name);

    @FormUrlEncoded
    @POST("CategoryController.php")
    Single<CommonResponse> EditCategory(
            @Field("tag") String tag,
            @Field("category_name") String category_name,
            @Field("category_id") String category_id);

    @FormUrlEncoded
    @POST("CategoryController.php")
    Single<CommonResponse> DeleteCategory(
            @Field("tag") String tag,
            @Field("category_id") String category_id);

    @FormUrlEncoded
    @POST("CategoryController.php")
    Single<CommonResponse> ActiveDeactiveCategory(
            @Field("tag") String tag,
            @Field("category_status") String category_status,
            @Field("category_id") String category_id);

    @FormUrlEncoded
    @POST("CategoryController.php")
    Single<CategoryListResponse> getCategory(
            @Field("tag") String tag);


}
