package com.example.ProductCatalog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;

import com.example.AppUtils.Tools;
import com.example.AppUtils.VariableBag;
import com.example.SignInSignUp.PreferenceManager;
import com.example.network.RestCall;
import com.example.network.RestClient;
import com.example.networkResponse.Catalogue.CatalogListResponse;
import com.example.retrofitandrxjavaapidemo.R;

import rx.Subscriber;
import rx.schedulers.Schedulers;

public class ProductCatalogActivity extends AppCompatActivity {

    RestCall restCall;
    Tools tools;
    PreferenceManager preferenceManager;
    ProductCatalogueCategoryAdapter productCatalogueCategoryAdapter;
    RecyclerView productCatalogueCategoryRecyclerView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_catalog);

        restCall = RestClient.createService(RestCall.class, VariableBag.BASE_URL, VariableBag.API_KEY);
        tools = new Tools(this);
        preferenceManager = new PreferenceManager(this);
        productCatalogueCategoryRecyclerView = findViewById(R.id.productCatalogueCategoryRecyclerView);

        getCatalog();
    }

    private void getCatalog() {
        tools.showLoading();
        restCall.GetCatalog("GetCatalog", preferenceManager.getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<CatalogListResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        runOnUiThread(() -> {
                            tools.stopLoading();
                            Toast.makeText(ProductCatalogActivity.this, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        });
                    }

                    @Override
                    public void onNext(CatalogListResponse catalogListResponse) {
                        runOnUiThread(() -> {
                            tools.stopLoading();
                            if (catalogListResponse.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_RESULT)
                                    && catalogListResponse.getCategoryList() != null
                                    && catalogListResponse.getCategoryList().size() > 0)
                            {
//                                    Toast.makeText(ProductCatalogActivity.this, "CATALOGUE", Toast.LENGTH_SHORT).show();

                                RecyclerView.LayoutManager layoutManager =new LinearLayoutManager(ProductCatalogActivity.this);
                                productCatalogueCategoryAdapter = new ProductCatalogueCategoryAdapter(ProductCatalogActivity.this,catalogListResponse.getCategoryList());
                                productCatalogueCategoryRecyclerView.setLayoutManager(layoutManager);
                                productCatalogueCategoryRecyclerView.setAdapter(productCatalogueCategoryAdapter);
                            }
                        });
                    }
                });
    }
}