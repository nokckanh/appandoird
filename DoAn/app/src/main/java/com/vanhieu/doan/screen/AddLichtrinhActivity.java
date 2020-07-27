package com.vanhieu.doan.screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.android.material.textfield.TextInputLayout;
import com.vanhieu.doan.MainActivity;
import com.vanhieu.doan.R;
import com.vanhieu.doan.TokenManager;
import com.vanhieu.doan.entities.ApiError;
import com.vanhieu.doan.entities.Utils;
import com.vanhieu.doan.network.ApiService;
import com.vanhieu.doan.network.RetrofitBuilderRecyclerview;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddLichtrinhActivity extends AppCompatActivity {

    private static final String TAG = "AddLichtrinhActivity";
    @BindView( R.id.tuyen )
    TextInputLayout tiltruyen;
    @BindView( R.id.gioxuatben )
    TextInputLayout tilgioxuatben;
    @BindView( R.id.ngaydi )
    TextInputLayout tilngaydi;

    // Service của retrofi
    ApiService service;

    Call<ResponseBody> call;
    // Check lỗi
    AwesomeValidation awesomeValidation;
    // Token
    TokenManager tokenManager ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_add_lichtrinh );

        ButterKnife.bind( this );

        service = RetrofitBuilderRecyclerview.createService( ApiService.class );
        awesomeValidation = new AwesomeValidation( ValidationStyle.TEXT_INPUT_LAYOUT );

        SharedPreferences preferences = getSharedPreferences("shareprefs", Context.MODE_PRIVATE);
        tokenManager = TokenManager.getInstance( preferences);
//        service = RetrofiBuilder.createserviceWithAuth( ApiService.class , tokenManager );

        setupRules();
    }

    @OnClick(R.id.btn_addlichtrinh)
    void addlichtrinh(){
        Intent i = getIntent();
        int idxe = i.getIntExtra( "id",0 );
        Log.w(TAG,"idxeeeee"  + idxe);

        String tuyen =tiltruyen.getEditText().getText().toString();
        String gio = tilgioxuatben.getEditText().getText().toString();
        String ngay =tilngaydi.getEditText().getText().toString();
//        Date date = null;

        tiltruyen.setError( null );
        tilgioxuatben.setError( null );
        tilngaydi.setError( null );

        awesomeValidation.clear();

        if (awesomeValidation.validate()){
            call = service.addlichtrinh(idxe,tuyen,gio,ngay);
            call.enqueue( new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Log.w(TAG,"onReponne"  + response);

                    if(response.isSuccessful()){
                        Log.w( TAG,"onReponnse:"+response.body() );
                        startActivity(new Intent(AddLichtrinhActivity.this, MainActivity.class));
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.w(TAG,"onFailue: "+ t.getMessage());
                }
            } );
        }


    }

    public void setupRules(){
        awesomeValidation.addValidation( this,R.id.tuyen, RegexTemplate.NOT_EMPTY,R.string.erro_tuyen );
        awesomeValidation.addValidation( this,R.id.gioxuatben, RegexTemplate.NOT_EMPTY,R.string.erro_gioxuatben );
        awesomeValidation.addValidation( this,R.id.ngaydi, RegexTemplate.NOT_EMPTY,R.string.erro_ngaydi );

    }
    public void handleErrors(ResponseBody reponse){

        ApiError apiError = Utils.converErrors( reponse );

        for(Map.Entry<String, List<String>> error :apiError.getErrors().entrySet()){
            if (error.getKey().equals( "tuyen_id" )){
                tiltruyen.setError(error.getValue().get( 0 ) );
            }
            if (error.getKey().equals( "ngaydi" )){
                tilngaydi.setError(error.getValue().get( 0 ) );
            }
            if (error.getKey().equals( "xuatben" )){
                tilgioxuatben.setError(error.getValue().get( 0 ) );
            }

        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(call != null) {
            call.cancel();
            call = null;
        }
    }
}