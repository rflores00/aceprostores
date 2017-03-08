package com.adv.aceprostores.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ruben Flores on 2/23/2017.
 */

public class Sync {
    @SerializedName("sync_at")
    private String sync_at;
    @SerializedName("categories")
    private List<Category> categories;
    @SerializedName("products")
    private List<Product> products;

    public String getSync_at() {
        return sync_at;
    }

    public void setSync_at(String sync_at) {
        this.sync_at = sync_at;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
