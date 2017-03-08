package com.adv.aceprostores.repositories;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQuery;
import android.os.Build;
import android.util.Log;

import com.adv.aceprostores.helpers.Const;
import com.adv.aceprostores.interfaces.IDataRepository;
import com.adv.aceprostores.interfaces.OnInsert_Listener;
import com.adv.aceprostores.interfaces.OnSelect_Listener;
import com.adv.aceprostores.models.Category;
import com.adv.aceprostores.models.Product;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.category;
import static com.adv.aceprostores.helpers.Const.DB_NAME;
import static com.adv.aceprostores.helpers.Const.DB_PATH;


/**
 * Created by Ruben Flores on 7/5/2016.
 */
public class SQL implements IDataRepository {
    private static SQL  _instance;
    private static SQLiteDatabase db;

    public SQL(Context context) {

        db = SQLiteDatabase.openDatabase(DB_PATH(context) + DB_NAME, new SQLiteDatabase.CursorFactory() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public Cursor newCursor(SQLiteDatabase db, SQLiteCursorDriver masterQuery, String editTable, SQLiteQuery
                    query) {
                return new SQLiteCursor(masterQuery, editTable, query);
            }
        }, SQLiteDatabase.OPEN_READWRITE);
    }

    public synchronized static SQL getInstance(Context context)
    {
        if (_instance == null || db == null)
        {
            _instance = new SQL(context.getApplicationContext());
        }

        return _instance;
    }

    public void updateLocalCatPro(List<Category> categories, List<Product> products, OnInsert_Listener listener){
        listener.onStart();

        ContentValues cv;
        for(Category category : categories){
            cv = new ContentValues();

            cv.put("id", category.getId());
            cv.put("name", category.getName());
            cv.put("`order`", category.getOrder());
            cv.put("changes_at", category.getChanges_at());

            db.delete("categories", "id = "+category.getId(), null);
            db.insert("categories", null, cv);
        }

        for(Product product : products){
            cv = new ContentValues();

            cv.put("id", product.getId());
            cv.put("id_category", product.getId_category());
            cv.put("code", product.getCode());
            cv.put("url_imagen", product.getUrl_imagen());
            cv.put("changes_at", product.getChanges_at());

            db.delete("products", "id = "+product.getId(), null);
            db.insert("products", null, cv);
        }

        listener.onComplete();
    }

    @Override
    public void getProducts(OnSelect_Listener listener) {
        List<Product> products = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT p.*,c.name, IFNULL((select up.stock from users_products up where up.id_product = p.id and up.id_user = 1), 0) AS stock FROM products p INNER JOIN categories c ON c.id = p.id_category ORDER BY c.`order`";

        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Product product = new Product();
                    // Adding contact to list
                    product.setId(Integer.parseInt(cursor.getString(0)));
                    product.setId_category(Integer.parseInt(cursor.getString(1)));
                    product.setCode(cursor.getString(2));
                    product.setUrl_imagen(cursor.getString(3));
                    product.setChanges_at(cursor.getString(4));
                    product.setCategory_name(cursor.getString(5));
                    product.setStock(Integer.parseInt(cursor.getString(6)));

                    products.add(product);
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }


        List<Object> list = new ArrayList<Object>(products);
        listener.onComplete(list);
    }

    @Override
    public void insertStock(int id_product, int id_user, int stock, OnInsert_Listener listener) {
        ContentValues cv;
        cv = new ContentValues();

        cv.put("id_user", id_user);
        cv.put("id_product", id_product);
        cv.put("stock", stock);

        db.delete("users_products","id_product =" + id_product +" AND id_user = "+id_user, null);

        db.insert("users_products", null, cv);

        listener.onComplete();
    }

}
