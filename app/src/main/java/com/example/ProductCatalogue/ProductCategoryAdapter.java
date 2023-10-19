package com.example.ProductCatalogue;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.networkResponse.Catalogue.Category;
import com.example.networkResponse.cate.CategoryListResponse;
import com.example.retrofitandrxjavaapidemo.R;

import java.util.List;

public class ProductCategoryAdapter extends RecyclerView.Adapter<ProductCategoryAdapter.ProductCategoryViewHolder> {

    Context context;
    List<CategoryListResponse.Category> categoryList;

    public ProductCategoryAdapter(Context context, List<CategoryListResponse.Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public ProductCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.product_category_item, parent, false);
        return new ProductCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductCategoryViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class ProductCategoryViewHolder extends RecyclerView.ViewHolder{

        RecyclerView productSubCategory;

        public ProductCategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            productSubCategory = itemView.findViewById(R.id.productSubCategory);
        }
    }
}
