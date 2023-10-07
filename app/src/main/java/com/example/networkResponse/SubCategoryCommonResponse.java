package com.example.networkResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SubCategoryCommonResponse implements Serializable {

    @SerializedName("sub_category_id")
    @Expose
    private String subCategoryId;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;

    public SubCategoryCommonResponse(String subCategoryId, String message, String status) {
        this.subCategoryId = subCategoryId;
        this.message = message;
        this.status = status;
    }

    public String getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
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
}
