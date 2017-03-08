package com.adv.aceprostores.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ruben Flores on 2/23/2017.
 */

public class Category {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("order")
    private int order;
    @SerializedName("changes_at")
    private String changes_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getChanges_at() {
        return changes_at;
    }

    public void setChanges_at(String changes_at) {
        this.changes_at = changes_at;
    }
}
