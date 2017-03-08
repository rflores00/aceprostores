package com.adv.aceprostores.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.adv.aceprostores.R;
import com.adv.aceprostores.helpers.Const;
import com.adv.aceprostores.interfaces.OnInsert_Listener;
import com.adv.aceprostores.models.Product;
import com.adv.aceprostores.reader.ReaderService;
import com.adv.aceprostores.repositories.SQL;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.os.Build.VERSION_CODES.M;
import static com.adv.aceprostores.R.id.textStock;
import static java.security.AccessController.getContext;

/**
 * Created by Ruben Flores on 2/24/2017.
 */

public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.SimpleViewHolder> {
    private static final int COUNT = 100;

    private final Context mContext;
    public final List<Product> mItems;
    private int mCurrentItemId = 0;


    public static class SimpleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView textStock;
        public final ImageView image, buttonAdd, buttonDeduct;
        private final ReaderService reader;
        private final Context context;
        private Product mItem;
        private SharedPreferences prefs;
        private int id_user = 1;

        public SimpleViewHolder(View view) {
            super(view);

            context = view.getContext();

            prefs = context.getSharedPreferences(
                    Const.PACKAGE_NAME, Context.MODE_PRIVATE);

            textStock = (TextView) view.findViewById(R.id.textStock);

            buttonAdd = (ImageView) view.findViewById(R.id.buttonAdd);
            buttonDeduct = (ImageView) view.findViewById(R.id.buttonDeduct);

            image = (ImageView) view.findViewById(R.id.image);

            buttonAdd.setOnClickListener(this);
            buttonDeduct.setOnClickListener(this);

            reader = ReaderService.getSingleton();

            id_user = prefs.getInt("id_user", 1);

                //reader.set_iDataRepository(new SQL(context));
        }

        @Override
        public void onClick(View view) {
            int stock = Integer.parseInt(textStock.getText().toString());
            if(view.getId() == buttonAdd.getId()) {
                stock = stock + 1;

            }else{
                stock = stock - 1;
            }

            textStock.setText(""+stock);

            reader.insertStock(mItem.getId(), id_user, stock, new OnInsert_Listener() {
                @Override
                public void onStart() {

                }

                @Override
                public void onComplete() {

                }

                @Override
                public void onError() {

                }
            });

        }

        public void setItem(Product item) {
            mItem = item;
        }
    }

    public SimpleAdapter(Context context, List<Product> products) {
        mContext = context;
        mItems = products;
        /*for (int i = 0; i < COUNT; i++) {
            addItem(i);
        }*/
    }

    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.item_product, parent, false);

        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, final int position) {
        //holder.title.setText(mItems.get(position).toString());
        File file = new File(mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES) + File.separator + mItems.get(position).getUrl_imagen());
        holder.textStock.setText(""+mItems.get(position).getStock());
        holder.setItem(mItems.get(position));
        Picasso.with(mContext).load(file).into(holder.image);
    }

    public void addItem(int position) {
        final int id = mCurrentItemId++;
        //mItems.add(position, id);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        mItems.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
