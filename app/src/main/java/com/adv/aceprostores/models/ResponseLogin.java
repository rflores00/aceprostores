package com.adv.aceprostores.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ruben Flores on 3/4/2017.
 */

public class ResponseLogin {

    @SerializedName("status")
    public boolean status;
    @SerializedName("message")
    public String message;
    @SerializedName("user")
    public User user;

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }
}
