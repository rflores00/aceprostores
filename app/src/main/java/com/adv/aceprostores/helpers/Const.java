package com.adv.aceprostores.helpers;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.adv.aceprostores.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ruben Flores on 2/1/2017.
 */

public class Const {
    public static String APP_NAME = "aceprostores";
    public static String PACKAGE_NAME = "com.adv.aceprostores";
    public static String DB_NAME = "db.db";
    public static String TAG = "LOG_TAG";

    public static int SERVICE_CONNETION = 1;
    public static int LOCAL_CONNETION = 2;


    public static String DB_PATH(Context context){
        String DB_PATH;
        if(android.os.Build.VERSION.SDK_INT >= 17){
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        }
        else
        {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }
        return DB_PATH;
    }

    public static String formatDate(String date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        Date convertedDate;
        try {
            convertedDate = dateFormat.parse(date);
            SimpleDateFormat sdfmonth = new SimpleDateFormat("MM/yyyy");
            return sdfmonth.format(convertedDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static Bitmap setPic(int w, int h, Resources res, int resId) {
        // Get the dimensions of the View
        int targetW = w;
        int targetH = h;

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res,R.drawable.acepro_fondo_1920x1080, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeResource(res, resId, bmOptions);

        return bitmap;
    }
}
