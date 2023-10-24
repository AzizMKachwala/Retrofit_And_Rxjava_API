package com.example.ProductCatalog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.AppUtils.Tools;
import com.example.AppUtils.VariableBag;
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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ProductCatalogueProductViewHolder holder, int position) {

        holder.txtProductCatalogueProductName.setText(productList.get(position).getProductName());
        holder.txtProductCatalogueProductPrice.setText(VariableBag.CURRENCY + productList.get(position).getProductPrice());
        holder.txtProductCatalogueProductDesc.setText(productList.get(position).getProductDesc());
//        holder.txtProductCatalogueProductIsVeg.setText(productList.get(position).getIsVeg());

        int txtIsVeg = Integer.parseInt(productList.get(position).getIsVeg());

        Log.d("TextIsVeg", String.valueOf(txtIsVeg));

        if (txtIsVeg == 0){
            holder.imgProductCatalogueIsVeg.setImageResource(R.drawable.veg);
        }
        else if(txtIsVeg == 1){
            holder.imgProductCatalogueIsVeg.setImageResource(R.drawable.nonveg);
        }

        Tools.DisplayImage(context, holder.imgProductCatalogueProductImage, productList.get(position).getProductImage());

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductCatalogueProductViewHolder extends RecyclerView.ViewHolder{

        TextView txtProductCatalogueProductName,txtProductCatalogueProductPrice,txtProductCatalogueProductDesc;
        ImageView imgProductCatalogueProductImage,imgProductCatalogueIsVeg;

        public ProductCatalogueProductViewHolder(@NonNull View itemView) {
            super(itemView);

            txtProductCatalogueProductName = itemView.findViewById(R.id.txtProductCatalogueProductName);
            txtProductCatalogueProductPrice = itemView.findViewById(R.id.txtProductCatalogueProductPrice);
            txtProductCatalogueProductDesc = itemView.findViewById(R.id.txtProductCatalogueProductDesc);
            imgProductCatalogueIsVeg = itemView.findViewById(R.id.imgProductCatalogueIsVeg);
            imgProductCatalogueProductImage = itemView.findViewById(R.id.imgProductCatalogueProductImage);

        }
    }
}
