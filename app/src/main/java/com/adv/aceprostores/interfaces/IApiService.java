package com.adv.aceprostores.interfaces;

import com.adv.aceprostores.models.ResponseLogin;
import com.adv.aceprostores.models.ResponseProduct;
import com.adv.aceprostores.models.ResponseSimple;
import com.adv.aceprostores.models.Sync;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import retrofit2.http.Url;

import static android.R.attr.type;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

/**
 * Created by Ruben Flores on 2/1/2017.
 */

public interface IApiService {

    @GET("sync")
    Call<Sync> Sync(@Query("date") String date);

    @GET
    Call<ResponseBody> downloadFile(@Url String fileUrl);

    @GET("login")
    Call<ResponseLogin> login(@Query("email")String user, @Query("password") String password);

    @GET("product")
    Call<List<ResponseProduct>> getProducts(@Query("id_user")int user);

    @FormUrlEncoded
    @PUT("product/stock")
    Call<ResponseSimple> update_stock(@Field("id_user") int id_user, @Field("id_product") int id_product, @Field("quantity") int quantity);

}
