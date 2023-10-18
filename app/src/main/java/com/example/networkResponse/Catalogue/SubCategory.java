package com.example.networkResponse.Catalogue;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SubCategory implements Serializable, Parcelable {

    @SerializedName("sub_category_id")
    @Expose
    private String subCategoryId;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("subcategory_name")
    @Expose
    private String subcategoryName;
    @SerializedName("subcategory_status")
    @Expose
    private String subcategoryStatus;
    @SerializedName("product_list")
    @Expose
    private List<Product> productList;
    public final static Creator<SubCategory> CREATOR = new Creator<SubCategory>() {


        public SubCategory createFromParcel(android.os.Parcel in) {
            return new SubCategory(in);
        }

        public SubCategory[] newArray(int size) {
            return (new SubCategory[size]);
        }

    }
            ;
    private final static long serialVersionUID = -9034874071317418708L;

    @SuppressWarnings({
            "unchecked"
    })
    protected SubCategory(android.os.Parcel in) {
        this.subCategoryId = ((String) in.readValue((String.class.getClassLoader())));
        this.categoryId = ((String) in.readValue((String.class.getClassLoader())));
        this.subcategoryName = ((String) in.readValue((String.class.getClassLoader())));
        this.subcategoryStatus = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.productList, (com.example.networkResponse.Catalogue.Product.class.getClassLoader()));
    }

    public SubCategory() {
    }

    public String getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getSubcategoryName() {
        return subcategoryName;
    }

    public void setSubcategoryName(String subcategoryName) {
        this.subcategoryName = subcategoryName;
    }

    public String getSubcategoryStatus() {
        return subcategoryStatus;
    }

    public void setSubcategoryStatus(String subcategoryStatus) {
        this.subcategoryStatus = subcategoryStatus;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(subCategoryId);
        dest.writeValue(categoryId);
        dest.writeValue(subcategoryName);
        dest.writeValue(subcategoryStatus);
        dest.writeList(productList);
    }

    public int describeContents() {
        return 0;
    }

}
