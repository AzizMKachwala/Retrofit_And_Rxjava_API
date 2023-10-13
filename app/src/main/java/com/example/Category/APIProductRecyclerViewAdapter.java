package com.example.Category;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.networkResponse.ProductListResponse;
import com.example.retrofitandrxjavaapidemo.R;

import java.util.List;

public class APIProductRecyclerViewAdapter extends RecyclerView.Adapter<APIProductRecyclerViewAdapter.APIProductDataViewHolder> {

    List<ProductListResponse.Product> products, productSearchList;
    Context context;

    public APIProductRecyclerViewAdapter(Context context, List<ProductListResponse.Product> products) {
        this.context = context;
        this.products = products;
        this.productSearchList = products;

    }

    @NonNull
    @Override
    public APIProductDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.product_item, parent, false);
        return new APIProductDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull APIProductDataViewHolder holder, int position) {
        ProductListResponse.Product product = productSearchList.get(position);

        try {
            Glide
                    .with(context)
                    .load(product.getProductImage())
                    .apply(new RequestOptions().placeholder(R.drawable.ic_launcher_foreground))
                    .error(R.drawable.bg)
                    .into(holder.imgProdList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.txtProductName.setText(product.getProductName());
        holder.txtProductPrice.setText(product.getProductPrice());
        holder.txtProductDescription.setText(product.getProductDesc());
        holder.txtProductVegNonVeg.setText(product.getIsVeg());

        holder.imgEditProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Edit Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        holder.imgDeleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Delete Clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return productSearchList.size();
    }

    public class APIProductDataViewHolder extends RecyclerView.ViewHolder {

        ImageView imgProdList, imgEditProduct, imgDeleteProduct;
        TextView txtProductName, txtProductPrice, txtProductDescription, txtProductVegNonVeg;

        public APIProductDataViewHolder(@NonNull View itemView) {
            super(itemView);

            imgProdList = itemView.findViewById(R.id.imgProdList);
            imgEditProduct = itemView.findViewById(R.id.imgEditProduct);
            imgDeleteProduct = itemView.findViewById(R.id.imgDeleteProduct);
            txtProductName = itemView.findViewById(R.id.txtProductName);
            txtProductPrice = itemView.findViewById(R.id.txtProductPrice);
            txtProductDescription = itemView.findViewById(R.id.txtProductDescription);
            txtProductVegNonVeg = itemView.findViewById(R.id.txtProductVegNonVeg);
        }
    }
}
