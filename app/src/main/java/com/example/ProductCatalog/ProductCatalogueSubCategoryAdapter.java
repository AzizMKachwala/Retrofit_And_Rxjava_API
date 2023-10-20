package com.example.ProductCatalog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.networkResponse.Catalogue.CatalogListResponse;
import com.example.retrofitandrxjavaapidemo.R;

import java.util.List;

public class ProductCatalogueSubCategoryAdapter extends RecyclerView.Adapter<ProductCatalogueSubCategoryAdapter.ProductCatalogueSubCategoryViewHolder> {

    Context context;
    List<CatalogListResponse.Category.SubCategory> subCategoryList;

    public ProductCatalogueSubCategoryAdapter(Context context, List<CatalogListResponse.Category.SubCategory> subCategoryList) {
        this.context = context;
        this.subCategoryList = subCategoryList;
    }

    @NonNull
    @Override
    public ProductCatalogueSubCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.product_catalogue_sub_category_item, parent, false);
        return new ProductCatalogueSubCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductCatalogueSubCategoryViewHolder holder, int position) {

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        ProductCatalogueProductAdapter productCatalogueProductAdapter = new ProductCatalogueProductAdapter(context,subCategoryList.get(position).getProductList());
        holder.productCatalogueProductRecyclerView.setLayoutManager(layoutManager);
        holder.productCatalogueProductRecyclerView.setAdapter(productCatalogueProductAdapter);
    }

    @Override
    public int getItemCount() {
        return subCategoryList.size();
    }

    public class ProductCatalogueSubCategoryViewHolder extends RecyclerView.ViewHolder{

        RecyclerView productCatalogueProductRecyclerView;
        public ProductCatalogueSubCategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            productCatalogueProductRecyclerView = itemView.findViewById(R.id.productCatalogueProductRecyclerView);
        }
    }
}
