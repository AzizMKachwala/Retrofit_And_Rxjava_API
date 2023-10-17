package com.example.Product;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.example.SignInSignUp.PreferenceManager;
import com.example.Tools;
import com.example.VariableBag;
import com.example.network.RestCall;
import com.example.network.RestClient;
import com.example.networkResponse.ProductListResponse;
import com.example.networkResponse.cate.CategoryCommonResponse;
import com.example.networkResponse.cate.CategoryListResponse;
import com.example.networkResponse.subcate.SubCategoryListResponse;
import com.example.retrofitandrxjavaapidemo.R;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class AddProductActivity extends AppCompatActivity {

    ImageView imgEditProduct, imgProduct;
    TextInputEditText etvProductName, etvProductPrice, etvProductDescription;
    Button btnCancel, btnSubmit;
    SwitchMaterial IsVeg;
    AppCompatSpinner selectedCategorySpinnerProduct, selectedSubCategorySpinnerProduct;
    int selectedPos = 0;
    boolean isEdit = false;
    String selectedCategoryId, selectedCategoryName, selectedSubCategoryId, selectedSubCategoryName;
    String fetchedCategoryId, fetchedSubCategoryId, fetchedProductId, fetchedProductName, fetchedOldImage = "", fetchedProductPrice, fetchedProductDesc, fetchedIsVeg;
    ActivityResultLauncher<Intent> cameraLauncher;
    String currentPhotoPath = "";
    private File currentPhotoFile;
    private static final int REQUEST_CAMERA_PERMISSION = 101;
    RestCall restCall;
    Tools tools;
    PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        imgEditProduct = findViewById(R.id.imgEditProduct);
        imgProduct = findViewById(R.id.imgProduct);
        etvProductName = findViewById(R.id.etvProductName);
        etvProductPrice = findViewById(R.id.etvProductPrice);
        etvProductDescription = findViewById(R.id.etvProductDescription);
        selectedCategorySpinnerProduct = findViewById(R.id.selectedCategorySpinnerProduct);
        selectedSubCategorySpinnerProduct = findViewById(R.id.selectedSubCategorySpinnerProduct);
        btnCancel = findViewById(R.id.btnCancel);
        btnSubmit = findViewById(R.id.btnSubmit);
        IsVeg = findViewById(R.id.switchStatusProduct);

        restCall = RestClient.createService(RestCall.class, VariableBag.BASE_URL, VariableBag.API_KEY);

        preferenceManager = new PreferenceManager(this);
        tools = new Tools(this);

        getProductCateCall();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.getString("productId") != null) {
            isEdit = true;
            fetchedCategoryId = bundle.getString("category_id");
            fetchedSubCategoryId = bundle.getString("sub_category_id");
            fetchedProductId = bundle.getString("productId");
            fetchedProductName = bundle.getString("productName");
            fetchedOldImage = "" + bundle.getString("old_product_image");
            fetchedProductPrice = bundle.getString("product_price");
            fetchedProductDesc = bundle.getString("product_desc");
            fetchedIsVeg = bundle.getString("is_veg");
            btnSubmit.setText("Edit");
            EditProductCall();
        } else {
            isEdit = false;
            btnSubmit.setText("Submit");
        }

        cameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                if (currentPhotoFile != null && currentPhotoPath != null) {
                    Tools.DisplayImage(AddProductActivity.this, imgProduct, currentPhotoPath);
                }
            } else {
                Toast.makeText(AddProductActivity.this, "ERROR...", Toast.LENGTH_SHORT).show();
            }
        });

        imgEditProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    currentPhotoPath = "";
                    if (checkCameraPermission()) {
                        Toast.makeText(AddProductActivity.this, "Camera Starting", Toast.LENGTH_SHORT).show();
                        openCamera();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etvProductName.getText().toString().trim().isEmpty()) {
                    etvProductName.setError("Enter Product Name");
                    etvProductName.requestFocus();
                } else if (etvProductPrice.getText().toString().trim().isEmpty()) {
                    etvProductPrice.setError("Enter Product Price");
                    etvProductPrice.requestFocus();
                } else if (etvProductDescription.getText().toString().trim().isEmpty()) {
                    etvProductDescription.setError("Enter Product Description");
                    etvProductDescription.requestFocus();
                } else {
                    if (isEdit) {
                        EditProductCall();
                    } else {
                        AddProductCall();
                    }
                }
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
                                Toast.makeText(AddProductActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
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

                                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(AddProductActivity.this, android.R.layout.simple_spinner_dropdown_item, categoryNameArray);
                                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    selectedCategorySpinnerProduct.setAdapter(arrayAdapter);

                                    selectedCategorySpinnerProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                                            selectedPos = position;
                                            if (selectedPos >= 0 && selectedPos < categoryIdArray.length) {
                                                selectedCategoryId = categoryIdArray[selectedPos];
                                                selectedCategoryName = categoryNameArray[selectedPos];

                                                if (selectedCategoryId.equalsIgnoreCase("-1")) {
                                                    Toast.makeText(AddProductActivity.this, "Select Category", Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(AddProductActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
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

                                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(AddProductActivity.this, android.R.layout.simple_spinner_dropdown_item, subCategoryNameArray);
                                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    selectedSubCategorySpinnerProduct.setAdapter(arrayAdapter);

                                    selectedSubCategorySpinnerProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                                            selectedPos = position;
                                            if (selectedPos >= 0 && selectedPos < subCategoryIdArray.length) {
                                                selectedSubCategoryId = subCategoryIdArray[selectedPos];
                                                selectedSubCategoryName = subCategoryNameArray[selectedPos];

//                                                Toast.makeText(AddProductActivity.this, ""+selectedSubCategoryId, Toast.LENGTH_SHORT).show();

                                                if (selectedCategoryId.equalsIgnoreCase("-1") && selectedSubCategoryId.equalsIgnoreCase("-1")) {
                                                    Toast.makeText(AddProductActivity.this, "Select Sub Category", Toast.LENGTH_SHORT).show();
                                                } else {
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
    }

    private void AddProductCall() {
        tools.showLoading();
        RequestBody tag = RequestBody.create(MediaType.parse("text/plain"), "AddProduct");
        RequestBody rbCategoryId = RequestBody.create(MediaType.parse("text/plain"), selectedCategoryId);
        RequestBody rbSubCategoryId = RequestBody.create(MediaType.parse("text/plain"), selectedSubCategoryId);
        RequestBody rbProductName = RequestBody.create(MediaType.parse("text/plain"), etvProductName.getText().toString().trim());
        RequestBody rbProductPrice = RequestBody.create(MediaType.parse("text/plain"), etvProductPrice.getText().toString().trim());
        RequestBody rbProductDesc = RequestBody.create(MediaType.parse("text/plain"), etvProductDescription.getText().toString().trim());
        RequestBody rbIsVeg = RequestBody.create(MediaType.parse("text/plain"), IsVeg.isChecked() ? "1" : "0");
        RequestBody rbUserId = RequestBody.create(MediaType.parse("text/plain"), preferenceManager.getUserId());
        MultipartBody.Part fileToUpload = null;

        if (fileToUpload == null && currentPhotoPath != "") {
            try {
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
                File file = new File(currentPhotoPath);
                RequestBody rbPhoto = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                fileToUpload = MultipartBody.Part.createFormData("product_image", file.getName(), rbPhoto);
            } catch (Exception e) {
                Toast.makeText(this, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                e.printStackTrace();
            }
        }

        restCall.AddProduct(tag, rbCategoryId, rbSubCategoryId, rbProductName, rbProductPrice, rbProductDesc, rbIsVeg, rbUserId, fileToUpload)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<CategoryCommonResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tools.stopLoading();
//                                Toast.makeText(AddProductActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
                                Toast.makeText(AddProductActivity.this, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(CategoryCommonResponse categoryCommonResponse) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tools.stopLoading();
                                Toast.makeText(AddProductActivity.this, "" + categoryCommonResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                if (categoryCommonResponse.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_RESULT)) {
                                    if (currentPhotoFile != null && currentPhotoPath != null) {
                                        currentPhotoFile.delete();
                                        Toast.makeText(AddProductActivity.this, "Photo Deleted from the Package", Toast.LENGTH_SHORT).show();
                                    }
                                    startActivity(new Intent(AddProductActivity.this, SearchProductActivity.class));
                                    Toast.makeText(AddProductActivity.this, "" + categoryCommonResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }
                        });
                    }
                });
    }

    private void EditProductCall() {
        tools.showLoading();
        RequestBody tag = RequestBody.create(MediaType.parse("text/plain"), "AddProduct");
        RequestBody rbCategoryId = RequestBody.create(MediaType.parse("text/plain"), fetchedCategoryId);
        RequestBody rbSubCategoryId = RequestBody.create(MediaType.parse("text/plain"), fetchedSubCategoryId);
        RequestBody rbProductId = RequestBody.create(MediaType.parse("text/plain"), fetchedProductId);
        RequestBody rbOldImage = RequestBody.create(MediaType.parse("text/plain"), fetchedOldImage);
        RequestBody rbProductName = RequestBody.create(MediaType.parse("text/plain"), fetchedProductName);
        RequestBody rbProductPrice = RequestBody.create(MediaType.parse("text/plain"), fetchedProductPrice);
        RequestBody rbProductDesc = RequestBody.create(MediaType.parse("text/plain"), fetchedProductDesc);
        RequestBody rbIsVeg = RequestBody.create(MediaType.parse("text/plain"), fetchedIsVeg);
        RequestBody rbUserId = RequestBody.create(MediaType.parse("text/plain"), preferenceManager.getUserId());
        MultipartBody.Part UpdatedFileToUpload = null;

        if (UpdatedFileToUpload == null && currentPhotoPath != "") {
            try {
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
                File file = new File(currentPhotoPath);
                RequestBody rbPhoto = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                UpdatedFileToUpload = MultipartBody.Part.createFormData("product_image", file.getName(), rbPhoto);
            } catch (Exception e) {
                Toast.makeText(this, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }

//        etvProductName.setText(fetchedProductName);
//        etvProductPrice.setText(fetchedProductPrice);
//        etvProductDescription.setText(fetchedProductDesc);

        restCall.EditProduct(tag, rbCategoryId, rbSubCategoryId, rbProductId, rbProductName, rbProductPrice, rbProductDesc, rbOldImage, rbIsVeg, rbUserId, UpdatedFileToUpload)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<CategoryCommonResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(AddProductActivity.this, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onNext(CategoryCommonResponse categoryCommonResponse) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tools.stopLoading();
                                if (categoryCommonResponse.getStatus().equalsIgnoreCase(VariableBag.SUCCESS_RESULT)) {
                                    currentPhotoFile.delete();

                                    etvProductName.setText("");
                                    etvProductPrice.setText("");
                                    etvProductDescription.setText("");

                                    Toast.makeText(AddProductActivity.this, "Photo Updated and Deleted", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(AddProductActivity.this, SearchProductActivity.class));
                                    Toast.makeText(AddProductActivity.this, "" + categoryCommonResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }
                        });
                    }
                });
    }

    private boolean checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
            return false;
        }
        return true;
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, "com.example.Product", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                cameraLauncher.launch(takePictureIntent);
            }
        }
    }

    private File createImageFile() throws IOException {
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image  = File.createTempFile(imageFileName, ".jpg", storageDir);
        currentPhotoFile = image;
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }
}
