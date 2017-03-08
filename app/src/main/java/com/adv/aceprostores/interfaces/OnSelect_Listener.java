package com.adv.aceprostores.interfaces;

import java.util.List;

/**
 * Created by Ruben Flores on 7/5/2016.
 */
public interface OnSelect_Listener {
    void onStart();
    void onComplete(Object object);
    void onComplete(List<Object> object);
    void onError();
}
