package com.vanhieu.doan.network;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.vanhieu.doan.BuildConfig;
import com.vanhieu.doan.TokenManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class RetrofiBuilder {

    private static final String BASE_URL = " http://192.168.27.1:8888/TestAPI/public/api/auth/";

    OkHttpClient.Builder builder;
    private final static  OkHttpClient client = buildClient() ;
    private final static  Retrofit getRetrofit = buildRetrofit(client);

    private static OkHttpClient buildClient(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor( new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();

                        Request.Builder builder = request.newBuilder()
                                .addHeader( "Accept","application/json" )
                                .addHeader( "X-Requested-With" ,"XMLHttpRequest")
                                .addHeader( "Connection","close" );

                        request = builder.build();

                        return chain.proceed(request);
                    }
                } );
        if(BuildConfig.DEBUG){
            builder.addNetworkInterceptor( new StethoInterceptor());
        }

        return builder.build();
    }

    private static Retrofit buildRetrofit(OkHttpClient client){
        return new Retrofit.Builder()
                .baseUrl( BASE_URL )
                .client( client )
//                .addCallAdapterFactory( RxJavaCallAdapterFactory.create())
                .addConverterFactory( MoshiConverterFactory.create( ))
//                .addConverterFactory( GsonConverterFactory.create())
                .build();
    }

    public static <T> T createService(Class<T> service){
        return getRetrofit.create(service);
    }

    public  static <T> T createserviceWithAuth(Class<T> service , TokenManager tokenManager){

        OkHttpClient newClient = client.newBuilder().addInterceptor( new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                Request request = chain.request();
                Request.Builder builder = request.newBuilder();

                if (tokenManager.getToken().getAccessToken() != null){
                    builder.addHeader( "Authorization","Bearer "+ tokenManager.getToken().getAccessToken());
                }

                request = builder.build();
                return chain.proceed( request );
            }
        } ).authenticator( CustomAuthenticator.getInstance(tokenManager)).build();

        Retrofit newRetrofit = getRetrofit.newBuilder().client( newClient ).build();
        return newRetrofit.create( service );

    }

    public static Retrofit getGetRetrofit() {
        return getRetrofit;
    }
}
