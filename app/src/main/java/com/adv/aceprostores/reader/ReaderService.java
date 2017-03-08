package com.adv.aceprostores.reader;


import com.adv.aceprostores.interfaces.IDataRepository;
import com.adv.aceprostores.interfaces.OnInsert_Listener;
import com.adv.aceprostores.interfaces.OnSelect_Listener;

/**
 * Created by Ruben Flores on 7/5/2016.
 */
public class ReaderService implements IDataRepository {
    private static ReaderService singleton;
    private IDataRepository _iDataRepository;

    private ReaderService() {

    }

    public static synchronized ReaderService getSingleton() {
        if (singleton == null) {
            singleton = new ReaderService();
        }

        return singleton;
    }

    public void set_iDataRepository(IDataRepository _iDataRepository) {
        this._iDataRepository = _iDataRepository;
    }

    @Override
    public void getProducts(OnSelect_Listener listener) {
        listener.onStart();
        _iDataRepository.getProducts(listener);
    }

    @Override
    public void insertStock(int id_product, int id_user, int stock, OnInsert_Listener listener) {
        listener.onStart();
        _iDataRepository.insertStock(id_product, id_user, stock, listener);

    }
}
