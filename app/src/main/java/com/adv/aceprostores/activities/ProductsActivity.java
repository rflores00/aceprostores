package com.adv.aceprostores.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Window;

import com.adv.aceprostores.R;
import com.adv.aceprostores.adapters.SectionedGridRecyclerViewAdapter;
import com.adv.aceprostores.adapters.SimpleAdapter;
import com.adv.aceprostores.helpers.Const;
import com.adv.aceprostores.interfaces.OnSelect_Listener;
import com.adv.aceprostores.models.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Ruben Flores on 2/24/2017.
 */

public class ProductsActivity extends BaseActivity{
    RecyclerView mRecyclerView;
    SimpleAdapter mAdapter;

    private boolean loading = true;
    private LinearLayoutManager mLayoutManager;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mRecyclerView.setHasFixedSize(true);


        mLayoutManager = new GridLayoutManager(this, 4);
        mRecyclerView.setLayoutManager(mLayoutManager);


        //Sections
        reader.getSingleton().getProducts(new OnSelect_Listener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onComplete(Object object) {

            }

            @Override
            public void onComplete(List<Object> object) {
                List<Product> products = (List<Product>)(Object)object;

                mAdapter = new SimpleAdapter(ProductsActivity.this, products);

                //This is the code to provide a sectioned grid
                final List<SectionedGridRecyclerViewAdapter.Section> sections =
                        new ArrayList<SectionedGridRecyclerViewAdapter.Section>();

                int idCategory = 0, count = 0;

                for(Product product : products){
                    if(idCategory != product.getId_category()){
                        sections.add(new SectionedGridRecyclerViewAdapter.Section(count, product.getCategory_name()));
                        idCategory = product.getId_category();

                    }
                    count++;
                }

                //Add your adapter to the sectionAdapter
                SectionedGridRecyclerViewAdapter.Section[] dummy = new SectionedGridRecyclerViewAdapter.Section[sections.size()];
                SectionedGridRecyclerViewAdapter mSectionedAdapter = new
                        SectionedGridRecyclerViewAdapter(ProductsActivity.this,R.layout.section_product,R.id.section_text,mRecyclerView,mAdapter);
                mSectionedAdapter.setSections(sections.toArray(dummy));

                //Apply this adapter to the RecyclerView
                mRecyclerView.setAdapter(mSectionedAdapter);

            }

            @Override
            public void onError() {

            }
        });

    }




}
