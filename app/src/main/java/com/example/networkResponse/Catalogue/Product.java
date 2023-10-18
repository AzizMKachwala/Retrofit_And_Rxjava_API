package com.example.networkResponse.Catalogue;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Product implements Serializable, Parcelable {

    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("product_image")
    @Expose
    private String productImage;
    @SerializedName("old_product_image")
    @Expose
    private String oldProductImage;
    @SerializedName("product_price")
    @Expose
    private String productPrice;
    @SerializedName("product_desc")
    @Expose
    private String productDesc;
    @SerializedName("is_veg")
    @Expose
    private String isVeg;
    @SerializedName("product_total_rating")
    @Expose
    private String productTotalRating;
    @SerializedName("product_average_rating")
    @Expose
    private String productAverageRating;
    public final static Creator<Product> CREATOR = new Creator<Product>() {


        public Product createFromParcel(android.os.Parcel in) {
            return new Product(in);
        }

        public Product[] newArray(int size) {
            return (new Product[size]);
        }

    }
            ;
    private final static long serialVersionUID = 4790569971101266857L;

    @SuppressWarnings({
            "unchecked"
    })

    protected Product(android.os.Parcel in) {
        this.productId = ((String) in.readValue((String.class.getClassLoader())));
        this.productName = ((String) in.readValue((String.class.getClassLoader())));
        this.productImage = ((String) in.readValue((String.class.getClassLoader())));
        this.oldProductImage = ((String) in.readValue((String.class.getClassLoader())));
        this.productPrice = ((String) in.readValue((String.class.getClassLoader())));
        this.productDesc = ((String) in.readValue((String.class.getClassLoader())));
        this.isVeg = ((String) in.readValue((String.class.getClassLoader())));
        this.productTotalRating = ((String) in.readValue((String.class.getClassLoader())));
        this.productAverageRating = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Product() {
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getOldProductImage() {
        return oldProductImage;
    }

    public void setOldProductImage(String oldProductImage) {
        this.oldProductImage = oldProductImage;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getIsVeg() {
        return isVeg;
    }

    public void setIsVeg(String isVeg) {
        this.isVeg = isVeg;
    }

    public String getProductTotalRating() {
        return productTotalRating;
    }

    public void setProductTotalRating(String productTotalRating) {
        this.productTotalRating = productTotalRating;
    }

    public String getProductAverageRating() {
        return productAverageRating;
    }

    public void setProductAverageRating(String productAverageRating) {
        this.productAverageRating = productAverageRating;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(productId);
        dest.writeValue(productName);
        dest.writeValue(productImage);
        dest.writeValue(oldProductImage);
        dest.writeValue(productPrice);
        dest.writeValue(productDesc);
        dest.writeValue(isVeg);
        dest.writeValue(productTotalRating);
        dest.writeValue(productAverageRating);
    }

    public int describeContents() {
        return 0;
    }

}
