package com.example.ProductCatalogue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.AppUtils.Tools;
import com.example.AppUtils.VariableBag;
import com.example.SignInSignUp.PreferenceManager;
import com.example.network.RestCall;
import com.example.network.RestClient;
import com.example.networkResponse.Catalogue.ProductListRes;
import com.example.retrofitandrxjavaapidemo.R;

import rx.Subscriber;
import rx.schedulers.Schedulers;

public class ProductCatalogueActivity extends AppCompatActivity {

    RestCall restCall;
    PreferenceManager preferenceManager;
    Tools tools;
    ProductCategoryAdapter productCategoryAdapter;
    RecyclerView productCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_catalogue);

        restCall = RestClient.createService(RestCall.class, VariableBag.BASE_URL, VariableBag.API_KEY);
        preferenceManager = new PreferenceManager(this);
        tools = new Tools(this);
        productCategory = findViewById(R.id.productCategory);

        GetCatalog();
    }

    public void GetCatalog(){
        tools.showLoading();
        restCall.GetCatalog("GetCatalog",preferenceManager.getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<ProductListRes>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ProductCatalogueActivity.this, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(ProductListRes productListRes) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tools.stopLoading();

                                if(productListRes.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_RESULT) &&
                                        productListRes.getCategoryList() != null &&
                                        productListRes.getCategoryList().size() > 0 ){

                                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ProductCatalogueActivity.this,RecyclerView.HORIZONTAL,false);
                                    productCategory.setLayoutManager(layoutManager);

//                                    productCategoryAdapter = new ProductCategoryAdapter(ProductCatalogueActivity.this,productListRes.getCategoryList());

                                }

                            }
                        });
                    }
                });
    }
}
