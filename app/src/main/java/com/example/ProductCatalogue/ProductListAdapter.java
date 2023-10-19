package com.example.ProductCatalogue;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.networkResponse.Catalogue.Product;
import com.example.retrofitandrxjavaapidemo.R;

import org.w3c.dom.Text;

import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductListViewHolder> {

    Context context;
    List<Product> productList;

    public ProductListAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.product_product_item, parent, false);
        return new ProductListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductListViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductListViewHolder extends RecyclerView.ViewHolder{

        TextView txtCatalogPName,txtCatalogPPrice,txtCatalogPDesc,txtCatalogPIsVeg;
        ImageView imgCatalogProduct;
        public ProductListViewHolder(@NonNull View itemView) {
            super(itemView);

            txtCatalogPName = itemView.findViewById(R.id.txtCatalogPName);
            txtCatalogPPrice = itemView.findViewById(R.id.txtCatalogPPrice);
            txtCatalogPDesc = itemView.findViewById(R.id.txtCatalogPDesc);
            txtCatalogPIsVeg = itemView.findViewById(R.id.txtCatalogPIsVeg);
            imgCatalogProduct = itemView.findViewById(R.id.imgCatalogProduct);

        }
    }
}
