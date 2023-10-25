package com.example.ProductCatalog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.networkResponse.Catalogue.CatalogListResponse;
import com.example.retrofitandrxjavaapidemo.R;

import java.util.List;

public class ProductCatalogueCategoryAdapter extends RecyclerView.Adapter<ProductCatalogueCategoryAdapter.ProductCatalogueCategoryViewHolder> {

    Context context;
    List<CatalogListResponse.Category> categoryList;
    boolean hideShow = true;

    public ProductCatalogueCategoryAdapter(Context context, List<CatalogListResponse.Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public ProductCatalogueCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.product_catalogue_category_item, parent, false);
        return new ProductCatalogueCategoryViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ProductCatalogueCategoryViewHolder holder, int position) {

        holder.txtProductCatalogueCategoryName.setText(categoryList.get(position).getCategoryName() + " (" + categoryList.get(position).getSubCategoryList().size() + ")");
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        ProductCatalogueSubCategoryAdapter productCatalogueSubCategoryAdapter = new ProductCatalogueSubCategoryAdapter(context, categoryList.get(position).getSubCategoryList());
        holder.productCatalogueSubCategoryRecyclerView.setLayoutManager(layoutManager);
        holder.productCatalogueSubCategoryRecyclerView.setAdapter(productCatalogueSubCategoryAdapter);

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class ProductCatalogueCategoryViewHolder extends RecyclerView.ViewHolder{

        RecyclerView productCatalogueSubCategoryRecyclerView;
        TextView txtProductCatalogueCategoryName;
        ImageView imgUpDown;

        public ProductCatalogueCategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            txtProductCatalogueCategoryName = itemView.findViewById(R.id.txtProductCatalogueCategoryName);
            productCatalogueSubCategoryRecyclerView = itemView.findViewById(R.id.productCatalogueSubCategoryRecyclerView);
        }
    }
}
