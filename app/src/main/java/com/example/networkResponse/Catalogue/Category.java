package com.example.networkResponse.Catalogue;

import android.os.Parcelable;

import com.example.networkResponse.subcate.SubCategoryListResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Category implements Serializable,Parcelable {

    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("category_name")
    @Expose
    private String categoryName;
    @SerializedName("category_status")
    @Expose
    private String categoryStatus;
    @SerializedName("sub_category_list")
    @Expose
    private List<SubCategory> subCategoryList;

    public final static Parcelable.Creator<Category> CREATOR = new Parcelable.Creator<Category>() {


        public Category createFromParcel(android.os.Parcel in) {
            return new Category(in);
        }

        public Category[] newArray(int size) {
            return (new Category[size]);
        }

    }
            ;
    private final static long serialVersionUID = 4350388223797411397L;

    @SuppressWarnings({
            "unchecked"
    })

    protected Category(android.os.Parcel in) {
        this.categoryId = ((String) in.readValue((String.class.getClassLoader())));
        this.categoryName = ((String) in.readValue((String.class.getClassLoader())));
        this.categoryStatus = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.subCategoryList, (SubCategory.class.getClassLoader()));
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryStatus() {
        return categoryStatus;
    }

    public void setCategoryStatus(String categoryStatus) {
        this.categoryStatus = categoryStatus;
    }

    public List<SubCategory> getSubCategoryList() {
        return subCategoryList;
    }

    public void setSubCategoryList(List<SubCategory> subCategoryList) {
        this.subCategoryList = subCategoryList;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(categoryId);
        dest.writeValue(categoryName);
        dest.writeValue(categoryStatus);
        dest.writeList(subCategoryList);
    }

    public int describeContents() {
        return 0;
    }

}
