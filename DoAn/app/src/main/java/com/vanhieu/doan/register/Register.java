package com.vanhieu.doan.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.android.material.textfield.TextInputLayout;
import com.vanhieu.doan.R;
import com.vanhieu.doan.TokenManager;
import com.vanhieu.doan.entities.AccessToken;
import com.vanhieu.doan.entities.ApiError;
import com.vanhieu.doan.entities.Utils;
import com.vanhieu.doan.login.LoginActivity;
import com.vanhieu.doan.network.ApiService;
import com.vanhieu.doan.network.RetrofiBuilder;
import com.vanhieu.doan.screen.UserActivity;


import java.util.List;
import java.util.Map;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Register extends AppCompatActivity {

    private static final String TAG = "register";

    @BindView( R.id.til_name )
    TextInputLayout tilName;
    @BindView( R.id.til_email )
    TextInputLayout tilEmail;
    @BindView( R.id.til_password )
    TextInputLayout tilPass;
    @BindView( (R.id.til_phone) )
    TextInputLayout tilPhone;
    @BindView( (R.id.til_passwordconfirm) )
    TextInputLayout tilPasscofirm;

    // Service của retrofi
    ApiService service;
    Call<AccessToken> call;
    // Check lỗi
    AwesomeValidation awesomeValidation;
    // Token
    TokenManager tokenManager ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_register );

        ButterKnife.bind( this );

        service = RetrofiBuilder.createService( ApiService.class );

        awesomeValidation = new AwesomeValidation( ValidationStyle.TEXT_INPUT_LAYOUT );

        tokenManager = TokenManager.getInstance(getSharedPreferences("shareprefs", MODE_PRIVATE));
        setupRules();

        if (tokenManager.getToken().getAccessToken() != null ){
            startActivity( new Intent(Register.this,UserActivity.class ) );
            finish();
        }
    }

    @OnClick(R.id.btn_register)
    void register(){
        String name =tilName.getEditText().getText().toString();
        String email =tilEmail.getEditText().getText().toString();
        String password =tilPass.getEditText().getText().toString();
        String phone =tilPhone.getEditText().getText().toString();
        String passCofirm =tilPasscofirm.getEditText().getText().toString();


        tilName.setError( null );
        tilEmail.setError( null );
        tilPass.setError( null );
        tilPhone.setError( null );
        tilPasscofirm.setError( null );
        String type = "customer";

        awesomeValidation.clear();
        if (awesomeValidation.validate()){
            call = service.register( name,email,password,phone,type,passCofirm );
            call.enqueue( new Callback<AccessToken>() {
                @Override
                public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {

                    Log.w(TAG,"onReponne"  + response);

                    if(response.isSuccessful()){
                        Log.w( TAG,"onReponnse:"+response.body() );
                        tokenManager.saveToken( response.body() );
                        startActivity(new Intent(Register.this, LoginActivity.class));
                        finish();
                    }else {
                        handleErrors( response.errorBody() );
                    }
                }

                @Override
                public void onFailure(Call<AccessToken> call, Throwable t) {
                    Log.w(TAG,"onFailue: "+ t.getMessage());
                }
            } );
        }

    }

    @OnClick(R.id.go_to_login)
    void gotoRegister(){
        startActivity( new Intent( Register.this, LoginActivity.class ) );
    }

    public void setupRules(){
        awesomeValidation.addValidation( this,R.id.til_name, RegexTemplate.NOT_EMPTY,R.string.erro_name );
        awesomeValidation.addValidation( this,R.id.til_phone, RegexTemplate.NOT_EMPTY,R.string.erro_phone );
        awesomeValidation.addValidation( this,R.id.til_email, Patterns.EMAIL_ADDRESS,R.string.erro_email );
        awesomeValidation.addValidation( this,R.id.til_password, "[a-zA-Z0-9]{6,}",R.string.erro_password );
        awesomeValidation.addValidation( this,R.id.til_passwordconfirm, RegexTemplate.NOT_EMPTY,R.string.erro_passwordcofirm );
    }

    public void handleErrors(ResponseBody reponse){

        ApiError apiError = Utils.converErrors( reponse );

        for(Map.Entry<String, List<String>> error :apiError.getErrors().entrySet()){
            if (error.getKey().equals( "name" )){
                tilName.setError(error.getValue().get( 0 ) );
            }
            if (error.getKey().equals( "email" )){
                tilEmail.setError(error.getValue().get( 0 ) );
            }
            if (error.getKey().equals( "pass" )){
                tilPass.setError(error.getValue().get( 0 ) );
            }
            if (error.getKey().equals( "phone" )){
                tilPhone.setError(error.getValue().get( 0 ) );
            }
            if (error.getKey().equals( "passwordConfirm" )){
                tilPasscofirm.setError(error.getValue().get( 0 ) );
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