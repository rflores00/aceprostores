package com.adv.aceprostores.interfaces;


/**
 * Created by Ruben Flores on 7/5/2016.
 */
public interface OnDownload_Listener {
    void onStart();
    void onComplete();
    void onProgress(int value, String message);
    void onError(String message);
}
