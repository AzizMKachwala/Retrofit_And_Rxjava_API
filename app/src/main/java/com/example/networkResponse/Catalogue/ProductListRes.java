package com.example.networkResponse.Catalogue;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ProductListRes implements Serializable, Parcelable {

    @SerializedName("categoryList")
    @Expose
    private List<Category> categoryList;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;

    public final static Creator<ProductListRes> CREATOR = new Creator<ProductListRes>() {


        public ProductListRes createFromParcel(android.os.Parcel in) {
            return new ProductListRes(in);
        }

        public ProductListRes[] newArray(int size) {
            return (new ProductListRes[size]);
        }

    }
            ;
    private final static long serialVersionUID = 4714491393165627319L;

    @SuppressWarnings({ "unchecked" })


    protected ProductListRes(android.os.Parcel in) {
        in.readList(this.categoryList, (com.example.networkResponse.Catalogue.Category.class.getClassLoader()));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeList(categoryList);
        dest.writeValue(message);
        dest.writeValue(status);
    }

    public int describeContents() {
        return 0;
    }
}
