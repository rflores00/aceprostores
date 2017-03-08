package com.adv.aceprostores.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.widget.Toast;

import com.adv.aceprostores.helpers.Const;
import com.adv.aceprostores.reader.ReaderService;
import com.adv.aceprostores.repositories.SQL;
import com.adv.aceprostores.repositories.WS;
import com.google.gson.Gson;

/**
 * Created by Ruben Flores on 2/1/2017.
 */

public class BaseActivity extends AppCompatActivity {
    public ProgressDialog progress;
    public Gson gson = new Gson();
    public String token = "";
    public SharedPreferences prefs;
    public ReaderService reader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = this.getSharedPreferences(
                Const.PACKAGE_NAME, Context.MODE_PRIVATE);

        progress = new ProgressDialog(this);
        progress.setTitle("");
        progress.setCancelable(false);
        progress.setMessage("Espere ...");

        reader = ReaderService.getSingleton();
        if(prefs.getInt("conn_type",0) == 0){
            reader.set_iDataRepository(SQL.getInstance(this));
        }else{
            reader.set_iDataRepository(WS.getInstance(this));
        }

    }

    public void showToast(String text){
        Toast.makeText(this,""+text,Toast.LENGTH_SHORT).show();
    }
}
