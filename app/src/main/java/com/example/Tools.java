package com.example;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.retrofitandrxjavaapidemo.R;

import java.io.File;

public class Tools {

    Context context;
    private final Dialog dialog;

    public Tools(Context context) {
        this.context = context;
        dialog = new Dialog(context);
    }

    public static boolean isValidEmail(String str) {
        if (!TextUtils.isEmpty(str)) {
            return Patterns.EMAIL_ADDRESS.matcher(str.toLowerCase()).matches();
        }
        return false;
    }

    public void showLoading() {
        try {
            if (dialog != null) {
                dialog.setContentView(R.layout.loadingdialog);
                dialog.setCancelable(false);
                if (!dialog.isShowing()) {
                    dialog.show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopLoading() {
        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void DisplayImage(Context context, ImageView img, String urlImg) {
        try {
            File imgFile = new File(urlImg);
            if (imgFile.exists()) {
                Glide.with(context)
                        .load(imgFile)
                        .apply(new RequestOptions().placeholder(R.drawable.ic_launcher_foreground).error(R.drawable.bg))
                        .into(img);
            } else {
                img.setImageResource(R.drawable.bg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
