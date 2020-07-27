package com.vanhieu.doan.network;

import com.vanhieu.doan.entities.AccessToken;
import com.vanhieu.doan.entities.Chithu;
import com.vanhieu.doan.entities.Lichtrinh;
import com.vanhieu.doan.entities.Lichtrinhcustomer;
import com.vanhieu.doan.entities.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    @POST("addlichtrinh")
    @FormUrlEncoded
    Call<ResponseBody> addlichtrinh(@Field( "xe_id" ) int idxe,
                                  @Field( "tuyen_id" ) String tuyen,
                                  @Field( "xuatben" ) String xuatben ,
                                  @Field( "ngaydi" ) String ngaydi);
//    @FormUrlEncoded
//    @POST("lichtrinh/add")
//
//    Call<Lichtrinh> addlichtrinh(@Body Lichtrinh body);



    @POST("signup")
    @FormUrlEncoded
    Call<AccessToken> register(@Field( "name" ) String name,
                               @Field( "email" ) String email ,
                               @Field( "password" ) String password,
                               @Field( "phone" ) String phone,
                               @Field( "usertype" ) String type,
                               @Field( "password_confirmation") String passCofirm );

    @POST("login")
    @FormUrlEncoded
    Call<AccessToken> login(@Field( "email" ) String email,
                            @Field( "password" )String password,
                            @Field( "renember_me" )Boolean renember);

    @POST("refresh")
    @FormUrlEncoded
    Call<AccessToken> refresh(@Field( "refresh_token" ) String refreshToken);

    @GET("user")
    Call<List<User>> users();

    @GET("logout")
    Call<List<User>> logout();

    @GET("lichtrinh/idxe={id}")
    Call<List<Lichtrinh>> getLichtrinh(@Path( "id" ) int idxe);

    @POST("chithu/idlichtrinh={id}")
    Call<List<Chithu>> getChithu(@Path( "id" ) int idlichtrinh);

    @POST("addChithu")
    @FormUrlEncoded
    Call<ResponseBody> addChithu(@Field( "lichtrinh_id" ) int lichtrinh_id,
                                 @Field( "name" ) String name,
                                 @Field( "chithu" ) String chithu ,
                                 @Field( "ghichu" ) String ghichu);

    @GET("lichtrinhxe")
    Call<List<Lichtrinhcustomer>> getLichtrinhcustomer();

}
