package com.example.ProductCatalogue;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.networkResponse.Catalogue.Category;
import com.example.networkResponse.Catalogue.SubCategory;
import com.example.networkResponse.subcate.SubCategoryListResponse;
import com.example.retrofitandrxjavaapidemo.R;

import java.util.List;

public class ProductSubCategoryAdapter extends RecyclerView.Adapter<ProductSubCategoryAdapter.ProductSubCategoryViewHolder> {

    Context context;
    List<SubCategoryListResponse.SubCategory> subCategoryList;

    public ProductSubCategoryAdapter(Context context, List<SubCategoryListResponse.SubCategory> subCategoryList) {
        this.context = context;
        this.subCategoryList = subCategoryList;
    }

    @NonNull
    @Override
    public ProductSubCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.product_sub_category_item, parent, false);
        return new ProductSubCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductSubCategoryViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return subCategoryList.size();
    }

    public class ProductSubCategoryViewHolder extends RecyclerView.ViewHolder{

        RecyclerView ProductProduct;

        public ProductSubCategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            ProductProduct = itemView.findViewById(R.id.ProductProduct);

        }
    }
}
