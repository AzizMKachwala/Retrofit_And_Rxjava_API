package com.example.network;

import android.app.DownloadManager;

import com.example.networkResponse.ProductListResponse;
import com.example.networkResponse.UserResponse;
import com.example.networkResponse.cate.CategoryListResponse;
import com.example.networkResponse.cate.CategoryCommonResponse;
import com.example.networkResponse.subcate.SubCategoryListResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Single;

public interface RestCall {

    @FormUrlEncoded
    @POST("CategoryController.php")
    Single<CategoryListResponse> getCategory(
            @Field("tag") String tag,
            @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("CategoryController.php")
    Single<CategoryCommonResponse> AddCategory(
            @Field("tag") String tag,
            @Field("user_id") String user_id,
            @Field("category_name") String category_name);

    @FormUrlEncoded
    @POST("CategoryController.php")
    Single<CategoryCommonResponse> EditCategory(
            @Field("tag") String tag,
            @Field("category_name") String category_name,
            @Field("category_id") String category_id,
            @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("CategoryController.php")
    Single<CategoryCommonResponse> DeleteCategory(
            @Field("tag") String tag,
            @Field("user_id") String user_id,
            @Field("category_id") String category_id);

    @FormUrlEncoded
    @POST("CategoryController.php")
    Single<CategoryCommonResponse> ActiveDeactiveCategory(
            @Field("tag") String tag,
            @Field("category_status") String category_status,
            @Field("category_id") String category_id);

    @FormUrlEncoded
    @POST("SubCategoryController.php")
    Single<SubCategoryListResponse> getSubCategory(
            @Field("tag") String tag,
            @Field("category_id") String category_id,
            @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("SubCategoryController.php")
    Single<SubCategoryListResponse> AddSubCategory(
            @Field("tag") String tag,
            @Field("category_id") String category_id,
            @Field("subcategory_name") String subcategory_name,
            @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("SubCategoryController.php")
    Single<SubCategoryListResponse> EditSubCategory(
            @Field("tag") String tag,
            @Field("category_id") String category_id,
            @Field("subcategory_name") String subcategory_name,
            @Field("sub_category_id") String sub_category_id,
            @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("SubCategoryController.php")
    Single<SubCategoryListResponse> DeleteSubCategory(
            @Field("tag") String tag,
            @Field("sub_category_id") String sub_category_id,
            @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("UserController.php")
    Single<UserResponse> AddUser(
            @Field("tag") String tag,
            @Field("first_name") String first_name,
            @Field("last_name") String last_name,
            @Field("email") String email,
            @Field("password") String password);

    @FormUrlEncoded
    @POST("UserController.php")
    Single<UserResponse> LoginUser(
            @Field("tag") String tag,
            @Field("email") String email,
            @Field("password") String password);

    @Multipart
    @POST("ProductController.php")
    Single<CategoryCommonResponse> AddProduct(
            @Part("tag") RequestBody tag,
            @Part("category_id") RequestBody category_id,
            @Part("sub_category_id") RequestBody sub_category_id,
            @Part("product_name") RequestBody product_name,
            @Part("product_price") RequestBody product_price,
            @Part("product_desc") RequestBody product_desc,
            @Part("is_veg") RequestBody is_veg,
            @Part("user_id") RequestBody user_id,
            @Part MultipartBody.Part product_image);

    @FormUrlEncoded
    @POST("ProductController.php")
    Single<ProductListResponse> getProduct(
            @Field("tag") String tag,
            @Field("category_id") String category_id,
            @Field("sub_category_id") String sub_category_id,
            @Field("user_id") String user_id);

}
