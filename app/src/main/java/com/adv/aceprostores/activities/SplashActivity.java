package com.adv.aceprostores.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adv.aceprostores.R;
import com.adv.aceprostores.helpers.Const;
import com.adv.aceprostores.interfaces.OnDownload_Listener;
import com.adv.aceprostores.interfaces.OnInsert_Listener;
import com.adv.aceprostores.interfaces.OnSelect_Listener;
import com.adv.aceprostores.models.Category;
import com.adv.aceprostores.models.Product;
import com.adv.aceprostores.models.Sync;
import com.adv.aceprostores.repositories.SQL;
import com.adv.aceprostores.repositories.WS;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.message;
import static com.adv.aceprostores.helpers.Const.TAG;

/**
 * Created by Ruben Flores on 2/23/2017.
 */

public class SplashActivity extends BaseActivity {
    private TextView textInfo1, textInfo2;
    private LinearLayout linearAnim;
    private Button buttonOffline, buttonOnline;
    private ImageView imageBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initUI();
        sync();
        linearAnim = (LinearLayout) findViewById(R.id.linearAnim);

    }

    private void initUI(){
        textInfo1 = (TextView) findViewById(R.id.textInfo1);
        textInfo2 = (TextView) findViewById(R.id.textInfo2);

        buttonOffline = (Button) findViewById(R.id.buttonOffline);
        buttonOnline = (Button)  findViewById(R.id.buttonOnline);

        imageBackground = (ImageView) findViewById(R.id.imageBackground);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        imageBackground.setImageBitmap(Const.setPic(metrics.widthPixels, metrics.heightPixels, getResources(), R.drawable.acepro_fondo_1920x1080));

        buttonOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                prefs.edit().putInt("conn_type", 0).apply();
                prefs.edit().putInt("id_user", 1).apply();

                Intent intent = new Intent(SplashActivity.this, ProductsActivity.class);
                startActivity(intent);
            }
        });

        buttonOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    private void sync(){
        WS.getInstance(this).Sync(new OnSelect_Listener() {
            @Override
            public void onStart() {}

            @Override
            public void onComplete(Object object) {
                Sync sync = (Sync) object;
                prefs.edit().putString("sync_date", sync.getSync_at()).apply();

                List<String> images = new ArrayList<>();

                if(sync.getProducts().size() > 0) {
                    textInfo1.setText(getString(R.string.label_sync));

                    for (Product product : sync.getProducts()) {
                        images.add(getString(R.string.conf_images_base_url) + product.getUrl_imagen());
                    }

                    downloadFiles(images, sync.getCategories(), sync.getProducts());
                }else{
                    textInfo1.setVisibility(View.INVISIBLE);
                    showButtons();
                }
            }

            @Override
            public void onComplete(List<Object> object) {

            }

            @Override
            public void onError() {
                textInfo2.setVisibility(View.VISIBLE);
                textInfo2.setText(getText(R.string.error_sync));
            }
        });
    }

    private void updateDB(final List<Category> categories, List<Product> products){
        SQL.getInstance(this).updateLocalCatPro(categories, products, new OnInsert_Listener() {
            @Override
            public void onStart() {
                textInfo1.setText(getString(R.string.label_update_local));
                textInfo2.setVisibility(View.GONE);
            }

            @Override
            public void onComplete() {
                textInfo1.setVisibility(View.GONE);
                showButtons();
            }

            @Override
            public void onError() {

            }
        });
    }

    private void showButtons(){
        Animation anim = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.down_to_top);

        linearAnim.startAnimation(anim);
        linearAnim.setVisibility(View.VISIBLE);
    }

    private void downloadFiles(final List<String> urls, final List<Category> categories, final List<Product> products){
        WS.getInstance(this).downloadFiles(urls, new OnDownload_Listener() {
            @Override
            public void onStart() {
                textInfo2.setVisibility(View.VISIBLE);
                textInfo2.setText(String.format(getString(R.string.label_downloading), 0, urls.size()));
            }

            @Override
            public void onComplete() {
                updateDB(categories, products);
            }

            @Override
            public void onProgress(int count, String message) {
                textInfo2.setText(String.format(getString(R.string.label_downloading), count, urls.size()));
            }

            @Override
            public void onError(String message) {
                Log.d(TAG, "onError: "+message);
            }
        });
    }
}
