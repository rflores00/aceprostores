package com.adv.aceprostores.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ruben Flores on 3/4/2017.
 */

public class ResponseSimple {

    @SerializedName("status")
    public boolean status;
    @SerializedName("message")
    public String message;

    public String getMessage() {
        return message;
    }

    public boolean isStatus() {

        return status;
    }
}
