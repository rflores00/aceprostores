package com.adv.aceprostores.interfaces;


/**
 * Created by Ruben Flores on 7/5/2016.
 */
public interface IDataRepository {
    void getProducts(OnSelect_Listener listener);
    void insertStock(int id_product, int id_user, int stock, OnInsert_Listener listener);
}
