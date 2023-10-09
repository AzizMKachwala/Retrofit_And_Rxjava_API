package com.example.SubCategory;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Category.APICategoryRecyclerViewAdapter;
import com.example.networkResponse.CategoryListResponse;
import com.example.networkResponse.SubCategoryListResponse;
import com.example.retrofitandrxjavaapidemo.R;

import java.util.ArrayList;
import java.util.List;

public class APISubCategoryRecyclerViewAdapter extends RecyclerView.Adapter<APISubCategoryRecyclerViewAdapter.ApiSubCategoryDataViewHolder> {

    List<SubCategoryListResponse.SubCategory> subCategories, subCategorySearchList;
    Context context;

    public APISubCategoryRecyclerViewAdapter(Context context, List<SubCategoryListResponse.SubCategory> subCategories) {
        this.context = context;
        this.subCategories = subCategories;
        this.subCategorySearchList = subCategories;
    }

    SubCategoryClick subCategoryClick;

    public interface SubCategoryClick{
        void SubEditClick(SubCategoryListResponse.SubCategory subCategory);
        void SubDeleteClick(SubCategoryListResponse.SubCategory subCategory);
    }

    public void SetUpInterface(APISubCategoryRecyclerViewAdapter.SubCategoryClick subCategoryClick){
        this.subCategoryClick = subCategoryClick;
    }

    @NonNull
    @Override
    public ApiSubCategoryDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.sub_category_item, parent, false);
        return new ApiSubCategoryDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ApiSubCategoryDataViewHolder holder, @SuppressLint("RecyclerView") int position) {
        SubCategoryListResponse.SubCategory subCategory = subCategorySearchList.get(position);
        holder.txtSubCategoryName.setText(subCategory.getSubcategoryName());
        holder.txtSubCategoryId.setText(subCategory.getSubCategoryId());

        holder.imgEditSubCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subCategoryClick.SubEditClick(subCategories.get(position));
            }
        });

        holder.imgDeleteSubCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subCategoryClick.SubDeleteClick(subCategory);
            }
        });
    }

    @Override
    public int getItemCount() {
        return subCategorySearchList.size();
    }

    public class ApiSubCategoryDataViewHolder extends RecyclerView.ViewHolder{

        TextView txtSubCategoryName,txtSubCategoryId;
        ImageView imgEditSubCategory,imgDeleteSubCategory;

        public ApiSubCategoryDataViewHolder(@NonNull View itemView) {
            super(itemView);

            txtSubCategoryName = itemView.findViewById(R.id.txtSubCategoryName);
            txtSubCategoryId = itemView.findViewById(R.id.txtSubCategoryId);
            imgEditSubCategory = itemView.findViewById(R.id.imgEditSubCategory);
            imgDeleteSubCategory = itemView.findViewById(R.id.imgDeleteSubCategory);
        }
    }

    public void Search(CharSequence charSequence, RecyclerView subCategoryListRecyclerView) {

        try{
            String charString=charSequence.toString().toLowerCase().trim();
            if(charString.isEmpty()){
                subCategorySearchList = subCategories;
                subCategoryListRecyclerView.setVisibility(View.VISIBLE);
            }else{
                int flag=0;
                List<SubCategoryListResponse.SubCategory> filterList=new ArrayList<>();
                for(SubCategoryListResponse.SubCategory Row:subCategories){
                    if(Row.getSubcategoryName().toString().toLowerCase().contains(charString.toLowerCase())){
                        filterList.add(Row);
                        flag=1;
                    }
                }
                if (flag == 1) {
                    subCategorySearchList = filterList;
                    subCategoryListRecyclerView.setVisibility(View.VISIBLE);
                }
                else{
                    subCategoryListRecyclerView.setVisibility(View.GONE);
                }
            }
            notifyDataSetChanged();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
