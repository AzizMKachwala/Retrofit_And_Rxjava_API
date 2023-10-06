package com.example.Category;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.networkResponse.CategoryListResponse;
import com.example.retrofitandrxjavaapidemo.R;

import java.util.ArrayList;
import java.util.List;

public class APIRecyclerViewAdapter extends RecyclerView.Adapter<APIRecyclerViewAdapter.ApiDataViewHolder> {

    List<CategoryListResponse.Category> categories,searchList;
    CategoryClick categoryClick;

    public APIRecyclerViewAdapter(List<CategoryListResponse.Category> categories) {
        this.categories = categories;
        this.searchList = categories;
    }

    public interface CategoryClick{
        void EditClick(CategoryListResponse.Category category);
        void DeleteClick(CategoryListResponse.Category category);
    }

    public void SetUpInterface(CategoryClick categoryClick){
        this.categoryClick = categoryClick;
    }

    @NonNull
    @Override
    public ApiDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.category_item, parent, false);
        return new ApiDataViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull ApiDataViewHolder holder, int position) {
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
    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }

    public class ApiDataViewHolder extends RecyclerView.ViewHolder{

        TextView txtCategoryName;
        ImageView imgEditCategory,imgDeleteCategory;
        public ApiDataViewHolder(@NonNull View itemView) {
            super(itemView);

            txtCategoryName = itemView.findViewById(R.id.txtCategoryName);
            imgEditCategory = itemView.findViewById(R.id.imgEditCategory);
            imgDeleteCategory = itemView.findViewById(R.id.imgDeleteCategory);

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
                    if(Row.getCategoryName().toString().toLowerCase().contains(charString.toLowerCase())){
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
