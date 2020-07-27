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
import com.vanhieu.doan.network.ApiService;
import com.vanhieu.doan.network.RetrofitBuilderRecyclerview;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddchithuActivity extends AppCompatActivity {

    private static final String TAG = "AddchithuActivity";
    @BindView( R.id.namechithu )
    TextInputLayout til_namechithu;
    @BindView( R.id.ghichuct )
    TextInputLayout til_ghichu;
    @BindView( R.id.sotien )
    TextInputLayout til_sotien;

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
        setContentView( R.layout.activity_addchithu );


        ButterKnife.bind( this );


        awesomeValidation = new AwesomeValidation( ValidationStyle.TEXT_INPUT_LAYOUT );

        SharedPreferences preferences = getSharedPreferences("shareprefs", Context.MODE_PRIVATE);
        tokenManager = TokenManager.getInstance( preferences);
//        service = RetrofiBuilder.createserviceWithAuth( ApiService.class , tokenManager );
        service = RetrofitBuilderRecyclerview.createserviceWithAuth( ApiService.class ,tokenManager);
        setupRules();
    }

    @OnClick(R.id.btn_addchithu)
    void addchithu(){

        Intent i = getIntent();
        int idlichtrinh = i.getIntExtra( "idlichtrinh",0 );

        String name =til_namechithu.getEditText().getText().toString();
        String chithu = til_sotien.getEditText().getText().toString();
        String ghichu =til_ghichu.getEditText().getText().toString();
//        Date date = null;

        til_namechithu.setError( null );
        til_sotien.setError( null );
        til_ghichu.setError( null );
        awesomeValidation.clear();

        if (awesomeValidation.validate()) {
            call = service.addChithu( idlichtrinh,name,chithu,ghichu );
            call.enqueue( new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Log.w(TAG,"onReponne"  + response);

                    if(response.isSuccessful()){
                        Log.w( TAG,"onReponnse:"+response.body() );
                        startActivity(new Intent(AddchithuActivity.this, LichtrinhActivity.class));
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(call != null) {
            call.cancel();
            call = null;
        }
    }
}