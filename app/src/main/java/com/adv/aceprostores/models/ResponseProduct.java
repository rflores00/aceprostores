package com.adv.aceprostores.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ruben Flores on 3/4/2017.
 */

public class ResponseProduct {
    @SerializedName("id")
    public int id;
    @SerializedName("name")
    public String name;
    @SerializedName("order")
    public int order;
    @SerializedName("changes_at")
    public String changes_at;
    @SerializedName("Products")
    public List<Product> products;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getOrder() {
        return order;
    }

    public String getChanges_at() {
        return changes_at;
    }

    public List<Product> getProducts() {
        return products;
    }
}
