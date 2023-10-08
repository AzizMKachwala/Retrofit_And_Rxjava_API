package com.example.network;

import com.example.networkResponse.CategoryListResponse;
import com.example.networkResponse.CategoryCommonResponse;
import com.example.networkResponse.SubCategoryCommonResponse;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;
import rx.Single;

public interface RestCall {
    @FormUrlEncoded
    @POST("CategoryController.php")
    Single<CategoryCommonResponse> AddCategory(
            @Field("tag") String tag,
            @Field("category_name") String category_name);

    @FormUrlEncoded
    @POST("CategoryController.php")
    Single<CategoryCommonResponse> EditCategory(
            @Field("tag") String tag,
            @Field("category_name") String category_name,
            @Field("category_id") String category_id);

    @FormUrlEncoded
    @POST("CategoryController.php")
    Single<CategoryCommonResponse> DeleteCategory(
            @Field("tag") String tag,
            @Field("category_id") String category_id);

    @FormUrlEncoded
    @POST("CategoryController.php")
    Single<CategoryCommonResponse> ActiveDeactiveCategory(
            @Field("tag") String tag,
            @Field("category_status") String category_status,
            @Field("category_id") String category_id);

    @FormUrlEncoded
    @POST("CategoryController.php")
    Single<CategoryListResponse> getCategory(
            @Field("tag") String tag);

    @FormUrlEncoded
    @POST("SubCategoryController.php")
    Single<SubCategoryCommonResponse> getSubCategory(
            @Field("tag") String tag,
            @Field("category_id") String category_id);

    @FormUrlEncoded
    @POST("SubCategoryController.php")
    Single<SubCategoryCommonResponse> AddSubCategory(
            @Field("tag") String tag,
            @Field("category_id") String category_id,
            @Field("subcategory_name") String subcategory_name);

    @FormUrlEncoded
    @POST("SubCategoryController.php")
    Single<SubCategoryCommonResponse> EditSubCategory(
            @Field("tag") String tag,
            @Field("category_id") String category_id,
            @Field("subcategory_name") String subcategory_name,
            @Field("sub_category_id") String sub_category_id);

    @FormUrlEncoded
    @POST("SubCategoryController.php")
    Single<SubCategoryCommonResponse> DeleteSubCategory(
            @Field("tag") String tag,
            @Field("sub_category_id") String sub_category_id);

}
