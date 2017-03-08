package com.adv.aceprostores.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ruben Flores on 3/4/2017.
 */

public class User {
    @SerializedName("id")
    public int id;
    @SerializedName("name")
    public String name;
    @SerializedName("key")
    public String key;
    @SerializedName("level")
    public int level;
    @SerializedName("login")
    public boolean login;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }

    public int getLevel() {
        return level;
    }

    public boolean isLogin() {
        return login;
    }
}
