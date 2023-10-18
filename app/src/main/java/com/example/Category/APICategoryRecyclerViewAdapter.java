package com.example.Category;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.networkResponse.cate.CategoryListResponse;
import com.example.retrofitandrxjavaapidemo.R;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.ArrayList;
import java.util.List;

public class APICategoryRecyclerViewAdapter extends RecyclerView.Adapter<APICategoryRecyclerViewAdapter.ApiCategoryDataViewHolder> {

    List<CategoryListResponse.Category> categories,searchList;
    CategoryClick categoryClick;
    Context context;

    public APICategoryRecyclerViewAdapter(List<CategoryListResponse.Category> categories) {
        this.categories = categories;
        this.searchList = categories;
    }

    public interface CategoryClick{
        void EditClick(CategoryListResponse.Category category);
        void DeleteClick(CategoryListResponse.Category category);
        void onSwitchChanged(CategoryListResponse.Category category, boolean isChecked);
    }

    public void SetUpInterface(CategoryClick categoryClick){
        this.categoryClick = categoryClick;
    }

    @NonNull
    @Override
    public ApiCategoryDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.category_item, parent, false);
        return new ApiCategoryDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ApiCategoryDataViewHolder holder, @SuppressLint("RecyclerView") int position) {
        CategoryListResponse.Category category = searchList.get(position);
        holder.txtCategoryName.setText(category.getCategoryName());

        holder.imgEditCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryClick.EditClick(categories.get(position));
            }
        });

        holder.imgDeleteCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryClick.DeleteClick(category);
            }
        });

        holder.switchStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                categoryClick.onSwitchChanged(searchList.get(position), isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }

    public class ApiCategoryDataViewHolder extends RecyclerView.ViewHolder{

        TextView txtCategoryName;
        ImageView imgEditCategory,imgDeleteCategory;
        SwitchMaterial switchStatus;
        public ApiCategoryDataViewHolder(@NonNull View itemView) {
            super(itemView);

            txtCategoryName = itemView.findViewById(R.id.txtCategoryName);
            imgEditCategory = itemView.findViewById(R.id.imgEditCategory);
            imgDeleteCategory = itemView.findViewById(R.id.imgDeleteCategory);
            switchStatus = itemView.findViewById(R.id.switchStatus);
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    public void Search(CharSequence charSequence, RecyclerView categoryListRecyclerView) {
        try{
            String charString=charSequence.toString().toLowerCase().trim();
            if(charString.isEmpty()){
                searchList=categories;
                categoryListRecyclerView.setVisibility(View.VISIBLE);
            }else{
                int flag=0;
                List<CategoryListResponse.Category> filterList=new ArrayList<>();
                for(CategoryListResponse.Category Row:categories){
                    if(Row.getCategoryName().toLowerCase().contains(charString.toLowerCase())){
                        filterList.add(Row);
                        flag=1;
                    }
                }
                if (flag == 1) {
                    searchList=filterList;
                    categoryListRecyclerView.setVisibility(View.VISIBLE);
                }
                else{
                    categoryListRecyclerView.setVisibility(View.GONE);
                }
            }
            notifyDataSetChanged();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
