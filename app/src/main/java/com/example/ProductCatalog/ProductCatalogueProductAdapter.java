package com.example.ProductCatalog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.AppUtils.Tools;
import com.example.networkResponse.Catalogue.CatalogListResponse;
import com.example.retrofitandrxjavaapidemo.R;

import java.util.List;

public class ProductCatalogueProductAdapter extends RecyclerView.Adapter<ProductCatalogueProductAdapter.ProductCatalogueProductViewHolder> {

    Context context;
    List<CatalogListResponse.Category.SubCategory.Product> productList;

    public ProductCatalogueProductAdapter(Context context, List<CatalogListResponse.Category.SubCategory.Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductCatalogueProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.product_catalogue_product_item, parent, false);
        return new ProductCatalogueProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductCatalogueProductViewHolder holder, int position) {

        holder.txtProductCatalogueProductName.setText(productList.get(position).getProductName());
        holder.txtProductCatalogueProductPrice.setText(productList.get(position).getProductPrice());
        holder.txtProductCatalogueProductDesc.setText(productList.get(position).getProductDesc());
        holder.txtProductCatalogueProductIsVeg.setText(productList.get(position).getIsVeg());

        Tools.DisplayImage(context,holder.imgProductCatalogueProductImage,productList.get(position).getProductImage());

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductCatalogueProductViewHolder extends RecyclerView.ViewHolder{

        TextView txtProductCatalogueProductName,txtProductCatalogueProductPrice,txtProductCatalogueProductDesc,txtProductCatalogueProductIsVeg;
        ImageView imgProductCatalogueProductImage;

        public ProductCatalogueProductViewHolder(@NonNull View itemView) {
            super(itemView);

            txtProductCatalogueProductName = itemView.findViewById(R.id.txtProductCatalogueProductName);
            txtProductCatalogueProductPrice = itemView.findViewById(R.id.txtProductCatalogueProductPrice);
            txtProductCatalogueProductDesc = itemView.findViewById(R.id.txtProductCatalogueProductDesc);
            txtProductCatalogueProductIsVeg = itemView.findViewById(R.id.txtProductCatalogueProductIsVeg);
            imgProductCatalogueProductImage = itemView.findViewById(R.id.imgProductCatalogueProductImage);

        }
    }
}
