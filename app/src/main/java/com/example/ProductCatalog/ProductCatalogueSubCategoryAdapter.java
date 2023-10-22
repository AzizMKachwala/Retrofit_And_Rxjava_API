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

public class ProductCatalogueSubCategoryAdapter extends RecyclerView.Adapter<ProductCatalogueSubCategoryAdapter.ProductCatalogueSubCategoryViewHolder> {

    Context context;
    List<CatalogListResponse.Category.SubCategory> subCategoryList;
    boolean hideShow = true;

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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ProductCatalogueSubCategoryViewHolder holder, int position) {

        holder.txtProductCatalogueSubCategoryName.setText(subCategoryList.get(position).getSubcategoryName() + " (" + subCategoryList.get(position).getProductList().size() + ")");
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        ProductCatalogueProductAdapter productCatalogueProductAdapter = new ProductCatalogueProductAdapter(context,subCategoryList.get(position).getProductList());
        holder.productCatalogueProductRecyclerView.setLayoutManager(layoutManager);
        holder.productCatalogueProductRecyclerView.setAdapter(productCatalogueProductAdapter);

        holder.txtProductCatalogueSubCategoryName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(hideShow == true){
                    holder.productCatalogueProductRecyclerView.setVisibility(View.VISIBLE);
                    holder.imgUpDown.setImageResource(R.drawable.up);
                    hideShow = false;
                }
                else {
                    holder.productCatalogueProductRecyclerView.setVisibility(View.GONE);
                    holder.imgUpDown.setImageResource(R.drawable.down);
                    hideShow = true;
                }
            }
        });
        holder.imgUpDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(hideShow == true){
                    holder.productCatalogueProductRecyclerView.setVisibility(View.VISIBLE);
                    holder.imgUpDown.setImageResource(R.drawable.up);
                    hideShow = false;
                }
                else {
                    holder.productCatalogueProductRecyclerView.setVisibility(View.GONE);
                    holder.imgUpDown.setImageResource(R.drawable.down);
                    hideShow = true;
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return subCategoryList.size();
    }

    public class ProductCatalogueSubCategoryViewHolder extends RecyclerView.ViewHolder{

        RecyclerView productCatalogueProductRecyclerView;
        TextView txtProductCatalogueSubCategoryName;
        ImageView imgUpDown;

        public ProductCatalogueSubCategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            txtProductCatalogueSubCategoryName = itemView.findViewById(R.id.txtProductCatalogueSubCategoryName);
            productCatalogueProductRecyclerView = itemView.findViewById(R.id.productCatalogueProductRecyclerView);
            imgUpDown = itemView.findViewById(R.id.imgUpDown);
        }
    }
}
