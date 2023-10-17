package com.example.Product;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.SignInSignUp.PreferenceManager;
import com.example.Tools;
import com.example.VariableBag;
import com.example.network.RestCall;
import com.example.network.RestClient;
import com.example.networkResponse.ProductListResponse;
import com.example.networkResponse.cate.CategoryListResponse;
import com.example.networkResponse.subcate.SubCategoryListResponse;
import com.example.retrofitandrxjavaapidemo.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.schedulers.Schedulers;

public class SearchProductActivity extends AppCompatActivity {

    AppCompatSpinner categorySpinnerProduct, subCategorySpinnerProduct;
    EditText etvProductSearch;
    RecyclerView productRecyclerView;
    FloatingActionButton btnAddProduct;
    RestCall restCall;
    Tools tools;
    PreferenceManager preferenceManager;
    APIProductRecyclerViewAdapter apiProductRecyclerViewAdapter;
    int selectedPos = 0;
    String selectedCategoryId, selectedCategoryName, selectedSubCategoryId, selectedSubCategoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_product);

        categorySpinnerProduct = findViewById(R.id.categorySpinnerProduct);
        subCategorySpinnerProduct = findViewById(R.id.subCategorySpinnerProduct);
        etvProductSearch = findViewById(R.id.etvProductSearch);
        productRecyclerView = findViewById(R.id.productRecyclerView);
        btnAddProduct = findViewById(R.id.btnAddProduct);

        restCall = RestClient.createService(RestCall.class, VariableBag.BASE_URL, VariableBag.API_KEY);

        preferenceManager = new PreferenceManager(this);
        tools = new Tools(this);

        getProductCateCall();

        etvProductSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (apiProductRecyclerViewAdapter != null) {
                    apiProductRecyclerViewAdapter.Search(charSequence, productRecyclerView);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SearchProductActivity.this, AddProductActivity.class));
            }
        });
    }

    private void getProductCateCall() {
        tools.showLoading();
        restCall.getCategory("getCategory", preferenceManager.getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<CategoryListResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(SearchProductActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(CategoryListResponse categoryListResponse) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tools.stopLoading();
                                if (categoryListResponse.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_RESULT) && categoryListResponse.getCategoryList() != null
                                        && categoryListResponse.getCategoryList().size() > 0) {

                                    List<CategoryListResponse.Category> categories = categoryListResponse.getCategoryList();
                                    String[] categoryNameArray = new String[categories.size() + 1];
                                    String[] categoryIdArray = new String[categories.size() + 1];

                                    categoryNameArray[0] = "Select Category";
                                    categoryIdArray[0] = "-1";

                                    for (int i = 0; i < categories.size(); i++) {
                                        categoryNameArray[i + 1] = categories.get(i).getCategoryName();
                                        categoryIdArray[i + 1] = categories.get(i).getCategoryId();
                                    }

                                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(SearchProductActivity.this, android.R.layout.simple_spinner_dropdown_item, categoryNameArray);
                                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    categorySpinnerProduct.setAdapter(arrayAdapter);

                                    categorySpinnerProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                                            selectedPos = position;
                                            if (selectedPos >= 0 && selectedPos < categoryIdArray.length) {
                                                selectedCategoryId = categoryIdArray[selectedPos];
                                                selectedCategoryName = categoryNameArray[selectedPos];

                                                if (selectedCategoryId.equalsIgnoreCase("-1")) {
                                                    Toast.makeText(SearchProductActivity.this, "Select Category", Toast.LENGTH_SHORT).show();
                                                } else {
//                                                    Toast.makeText(AddProductActivity.this, "SubCategory Loading", Toast.LENGTH_SHORT).show();
                                                    getProductSubCateCall();
                                                }
                                            }
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> adapterView) {

                                        }
                                    });
                                }
                            }
                        });
                    }
                });
        apiProductRecyclerViewAdapter = new APIProductRecyclerViewAdapter(SearchProductActivity.this, new ArrayList<>());
    }

    private void getProductSubCateCall() {
        tools.showLoading();
        restCall.getSubCategory("getSubCategory", selectedCategoryId, preferenceManager.getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<SubCategoryListResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(SearchProductActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(SubCategoryListResponse subCategoryListResponse) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tools.stopLoading();
                                if (subCategoryListResponse.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_RESULT) && subCategoryListResponse.getSubCategoryList() != null
                                        && subCategoryListResponse.getSubCategoryList().size() > 0) {

                                    List<SubCategoryListResponse.SubCategory> subCategories = subCategoryListResponse.getSubCategoryList();
                                    String[] subCategoryNameArray = new String[subCategories.size() + 1];
                                    String[] subCategoryIdArray = new String[subCategories.size() + 1];

                                    subCategoryNameArray[0] = "Select Sub Category";
                                    subCategoryIdArray[0] = "-1";

                                    for (int i = 0; i < subCategories.size(); i++) {
                                        subCategoryNameArray[i + 1] = subCategories.get(i).getSubcategoryName();
                                        subCategoryIdArray[i + 1] = subCategories.get(i).getSubCategoryId();
                                    }

                                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(SearchProductActivity.this, android.R.layout.simple_spinner_dropdown_item, subCategoryNameArray);
                                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    subCategorySpinnerProduct.setAdapter(arrayAdapter);

                                    subCategorySpinnerProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                                            selectedPos = position;
                                            if (selectedPos >= 0 && selectedPos < subCategoryIdArray.length) {
                                                selectedSubCategoryId = subCategoryIdArray[selectedPos];
                                                selectedSubCategoryName = subCategoryNameArray[selectedPos];

//                                                Toast.makeText(AddProductActivity.this, ""+selectedSubCategoryId, Toast.LENGTH_SHORT).show();

                                                if (selectedCategoryId.equalsIgnoreCase("-1") && selectedSubCategoryId.equalsIgnoreCase("-1")) {
                                                    Toast.makeText(SearchProductActivity.this, "Select Sub Category", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    getProduct();
//                                                    Toast.makeText(AddProductActivity.this, "SubCategory Loading", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> adapterView) {

                                        }
                                    });
                                }

                            }
                        });
                    }
                });
        apiProductRecyclerViewAdapter = new APIProductRecyclerViewAdapter(SearchProductActivity.this, new ArrayList<>());
    }

    private void getProduct() {
        tools.showLoading();
        restCall.getProduct("getProduct", selectedCategoryId, selectedSubCategoryId, preferenceManager.getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<ProductListResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tools.stopLoading();
                                Toast.makeText(SearchProductActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(ProductListResponse productListResponse) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tools.stopLoading();
                                if (productListResponse.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_RESULT)
                                        && productListResponse.getProductList() != null
                                        && productListResponse.getProductList().size() > 0) {

                                    LinearLayoutManager layoutManager = new LinearLayoutManager(SearchProductActivity.this);
                                    productRecyclerView.setLayoutManager(layoutManager);

                                    apiProductRecyclerViewAdapter = new APIProductRecyclerViewAdapter(SearchProductActivity.this, productListResponse.getProductList());
                                    productRecyclerView.setAdapter(apiProductRecyclerViewAdapter);

                                    apiProductRecyclerViewAdapter.SetUpInterface(new APIProductRecyclerViewAdapter.ProductClick() {
                                        @Override
                                        public void productEditClick(ProductListResponse.Product product) {
                                            String cateId = selectedCategoryId;
                                            String subCateId = selectedSubCategoryId;
                                            String productId = product.getProductId();
                                            String productName = product.getProductName();
                                            String old_product_image = product.getOldProductImage();
                                            String productPrice = product.getProductPrice();
                                            String productDesc = product.getProductDesc();
                                            String isVeg = product.getIsVeg();

                                            Intent intent = new Intent(SearchProductActivity.this, AddProductActivity.class);
                                            Bundle bundle = new Bundle();
                                            bundle.putString("category_id", cateId);
                                            bundle.putString("sub_category_id", subCateId);
                                            bundle.putString("productId", productId);
                                            bundle.putString("productName", productName);
                                            bundle.putString("old_product_image", old_product_image);
                                            bundle.putString("product_price", productPrice);
                                            bundle.putString("product_desc", productDesc);
                                            bundle.putString("is_veg", isVeg);
                                            intent.putExtras(bundle);
                                            startActivity(intent);
                                        }

                                        @Override
                                        public void productDeleteClick(ProductListResponse.Product product) {
                                            String productId = product.getProductId();

                                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(SearchProductActivity.this);
                                            alertDialog.setTitle("Alert!!");
                                            alertDialog.setMessage("Are you sure, you want to delete Product " + product.getProductName());

                                            alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    deleteProductCall(productId);
                                                    dialogInterface.dismiss();
                                                }
                                            });

                                            alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    dialogInterface.dismiss();
                                                }
                                            });
                                            alertDialog.show();
                                        }
                                    });

                                }
                            }
                        });
                    }
                });
    }

    private void deleteProductCall(String productId) {
        tools.showLoading();
        restCall.DeleteProduct("DeleteProduct", productId, preferenceManager.getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<ProductListResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tools.stopLoading();
                                Toast.makeText(SearchProductActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(ProductListResponse productListResponse) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tools.stopLoading();
                                if (productListResponse.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_RESULT)) {
                                    getProduct();
                                }
                                Toast.makeText(SearchProductActivity.this, productListResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
    }
}