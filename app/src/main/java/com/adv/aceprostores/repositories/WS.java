package com.adv.aceprostores.repositories;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.util.Log;

import com.adv.aceprostores.helpers.Const;
import com.adv.aceprostores.helpers.ServiceGenerator;
import com.adv.aceprostores.interfaces.IDataRepository;
import com.adv.aceprostores.interfaces.IApiService;
import com.adv.aceprostores.interfaces.OnDownload_Listener;
import com.adv.aceprostores.interfaces.OnInsert_Listener;
import com.adv.aceprostores.interfaces.OnSelect_Listener;
import com.adv.aceprostores.models.Product;
import com.adv.aceprostores.models.ResponseLogin;
import com.adv.aceprostores.models.ResponseProduct;
import com.adv.aceprostores.models.ResponseSimple;
import com.adv.aceprostores.models.Sync;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

import static android.R.id.list;
import static android.content.ContentValues.TAG;


/**
 * Created by Ruben Flores on 7/5/2016.
 */
public class WS implements IDataRepository {
    private IApiService client;
    private static WS _instance;

    //Variables para el proceso de sincronizacion
    private Call<ResponseBody> call;
    private OnDownload_Listener OnDownload_listener;
    private List<String> _urls;
    private int count = 0;
    private Context context;
    private SharedPreferences prefs;
    //

    public WS(Context context) {
        this.client = ServiceGenerator.createService(IApiService.class, context);
    }

    public synchronized static WS getInstance(Context context)
    {
        if (_instance == null)
        {
            _instance = new WS(context);
        }

        _instance.setContext(context);

        return _instance;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        prefs = context.getSharedPreferences(
                Const.PACKAGE_NAME, Context.MODE_PRIVATE);
        this.context = context;
    }

    public void Sync(final OnSelect_Listener listener) {
        listener.onStart();
        Call<Sync> call = client.Sync(prefs.getString("sync_date", null));
        call.enqueue(new Callback<Sync>() {
            @Override
            public void onResponse(Call<Sync> call, Response<Sync> response) {
                listener.onComplete(response.body());
            }

            @Override
            public void onFailure(Call<Sync> call, Throwable t) {
                listener.onError();
            }
        });
    }

    public void downloadFiles(List<String> urls, OnDownload_Listener listener){
        this._urls = urls;
        this.OnDownload_listener = listener;
        this.count = 0;

        OnDownload_listener.onStart();

        download(count);
    }

    private void download(int pos){
        call = client.downloadFile(_urls.get(pos));
        call.enqueue(callback);

    }

    public void login(String user, String pass, final OnSelect_Listener listener){
        Call<ResponseLogin> call = client.login(user, pass);
        call.enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                if(response.isSuccessful()){
                    listener.onComplete(response.body());
                }else{
                    Converter<ResponseBody, ResponseLogin> converter =
                            ServiceGenerator.retrofit.responseBodyConverter(ResponseLogin.class, new Annotation[0]);

                    ResponseLogin error;

                    try {
                        error = converter.convert(response.errorBody());
                        listener.onComplete(error);
                    } catch (IOException e) {
                        listener.onError();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                listener.onError();
            }
        });
    }

    private Callback<ResponseBody> callback = new Callback<ResponseBody>() {
        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            if (response.isSuccessful()) {

                String filename = _urls.get(count).substring(_urls.get(count).lastIndexOf("/")+1);

                Boolean writed = writeResponseBodyToDisk(response.body(), filename);
                OnDownload_listener.onProgress(count+1, ""+writed);
                count++;
                if(count < _urls.size()) {
                    download(count);
                }else{
                    OnDownload_listener.onComplete();
                }

            } else {

                OnDownload_listener.onError("Error "+ _urls.get(count));
            }
        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {
            OnDownload_listener.onError("Error "+ _urls.get(count));
        }
    };

    private boolean writeResponseBodyToDisk(ResponseBody body, String imageFileName) {
        try {


            File futureStudioIconFile = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES) + File.separator + imageFileName);
            // todo change the file location/name according to your needs

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    //Log.d(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public void getProducts(final OnSelect_Listener listener) {
        Call<List<ResponseProduct>> call = client.getProducts(prefs.getInt("id_user", -1));
        call.enqueue(new Callback<List<ResponseProduct>>() {
            @Override
            public void onResponse(Call<List<ResponseProduct>> call, Response<List<ResponseProduct>> response) {
                if(response.isSuccessful()){
                    List<Product> products = new ArrayList<>();
                    for(ResponseProduct responseProduct : response.body()){
                        products.addAll(responseProduct.getProducts());
                    }

                    List<Object> list = new ArrayList<Object>(products);
                    listener.onComplete(list);
                }
                else{
                    listener.onError();
                }
            }

            @Override
            public void onFailure(Call<List<ResponseProduct>> call, Throwable t) {
                listener.onError();
            }
        });
    }

    @Override
    public void insertStock(int id_product, int id_user, int stock, final OnInsert_Listener listener) {

        Call<ResponseSimple> call = client.update_stock(id_user, id_product, stock);
        call.enqueue(new Callback<ResponseSimple>() {
            @Override
            public void onResponse(Call<ResponseSimple> call, Response<ResponseSimple> response) {
                if(response.isSuccessful()){

                    listener.onComplete();
                }else{
                    listener.onError();
                }
            }

            @Override
            public void onFailure(Call<ResponseSimple> call, Throwable t) {
                listener.onError();
            }
        });
    }
}
