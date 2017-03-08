package com.adv.aceprostores.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ruben Flores on 2/23/2017.
 */

public class Product {

    @SerializedName("id")
    private int id;
    @SerializedName("id_category")
    private int id_category;
    @SerializedName("code")
    private String code;
    @SerializedName("url_imagen")
    private String url_imagen;
    @SerializedName("changes_at")
    private String changes_at;
    @SerializedName("stock")
    private int stock;
    @SerializedName("category_name")
    private String category_name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_category() {
        return id_category;
    }

    public void setId_category(int id_category) {
        this.id_category = id_category;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUrl_imagen() {
        return url_imagen;
    }

    public void setUrl_imagen(String url_imagen) {
        this.url_imagen = url_imagen;
    }

    public String getChanges_at() {
        return changes_at;
    }

    public void setChanges_at(String changes_at) {
        this.changes_at = changes_at;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }
}
