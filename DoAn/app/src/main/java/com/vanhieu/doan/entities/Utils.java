package com.vanhieu.doan.entities;

import com.vanhieu.doan.network.RetrofiBuilder;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;

public class Utils {

    public static ApiError converErrors(ResponseBody response){
        Converter<ResponseBody, ApiError> converter = RetrofiBuilder.getGetRetrofit().responseBodyConverter( ApiError.class,new Annotation[0]);

        Object apiError = null;

        try {
            apiError = converter.convert(response);
        }catch (IOException e){
            e.printStackTrace();
        }
        return (ApiError) apiError;

    }
}
