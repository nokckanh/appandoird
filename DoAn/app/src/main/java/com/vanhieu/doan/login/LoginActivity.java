package com.vanhieu.doan.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.transition.TransitionManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.android.material.textfield.TextInputLayout;
import com.vanhieu.doan.MainActivity;
import com.vanhieu.doan.R;
import com.vanhieu.doan.TokenManager;
import com.vanhieu.doan.entities.AccessToken;
import com.vanhieu.doan.entities.ApiError;
import com.vanhieu.doan.entities.User;
import com.vanhieu.doan.entities.Utils;
import com.vanhieu.doan.network.ApiService;
import com.vanhieu.doan.network.RetrofiBuilder;
import com.vanhieu.doan.register.Register;
import com.vanhieu.doan.screen.CustomerActivity;


import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "loginActivity";
    // Ánh xạ
    @BindView( R.id.til_email )
    TextInputLayout tilEmail;
    @BindView( R.id.til_password )
    TextInputLayout tilPass;
    @BindView(R.id.container)
    RelativeLayout container;
    @BindView(R.id.form_container)
    LinearLayout formContainer;
    @BindView(R.id.loader)
    ProgressBar loader;

    // Service Retrofi
    ApiService service;

    ApiService servicemen;
    TokenManager tokenManager;
    AwesomeValidation awesomeValidation;
    Call<AccessToken> call;
    Call<List<User>> callused;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );

        ButterKnife.bind(this);

        service = RetrofiBuilder.createService( ApiService.class );
        tokenManager = TokenManager.getInstance( getSharedPreferences( "shareprefs" , MODE_PRIVATE ));

        awesomeValidation = new AwesomeValidation( ValidationStyle.TEXT_INPUT_LAYOUT );
        setupRules();
        servicemen = RetrofiBuilder.createserviceWithAuth( ApiService.class ,tokenManager );
        if (tokenManager.getToken().getAccessToken() != null ){
            ruleUser();
        }


    }

    @OnClick(R.id.btn_login)
    void login(){
        String email = tilEmail.getEditText().getText().toString();
        String pass = tilPass.getEditText().getText().toString();
        Boolean renember = true;

        tilEmail.setError( null );
        tilPass.setError( null );

        awesomeValidation.clear();

        if (awesomeValidation.validate()){
            // Call Serive retrofi
            showLoading();
            call = service.login( email,pass,renember);
            call.enqueue( new Callback<AccessToken>() {
                @Override
                public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                    Log.w( TAG,"onReponse: " + response );
                    if (response.isSuccessful()) {
                        tokenManager.saveToken( (AccessToken) response.body() );
                        ruleUser();
                    }else {
                        if (response.code() == 422){
                            handleErrors( response.errorBody());
                        }
                        if (response.code() == 401){
                            ApiError  apiError = Utils.converErrors( response.errorBody());
                            Toast.makeText( LoginActivity.this, apiError.getMessage(),Toast.LENGTH_LONG).show();
                        }
                        showForm();
                    }
                }
                @Override
                public void onFailure(Call<AccessToken> call, Throwable t) {
                    Log.w(TAG,"onFailure" + t.getMessage());
                    showForm();
                }
            } );
        }
    }

    @OnClick(R.id.go_to_register)
    void gotoRegister(){
        startActivity( new Intent( LoginActivity.this, Register.class ) );
    }

    public void handleErrors(ResponseBody reponse){

        ApiError apiError = Utils.converErrors( reponse );

        for(Map.Entry<String, List<String>> error :apiError.getErrors().entrySet()){
            if (error.getKey().equals( "email" )){
                tilEmail.setError(error.getValue().get( 0 ) );
            }
            if (error.getKey().equals( "pass" )){
                tilPass.setError(error.getValue().get( 0 ) );
            }

        }
    }

    private void showLoading(){
        TransitionManager.beginDelayedTransition(container);
        formContainer.setVisibility( View.GONE);
        loader.setVisibility(View.VISIBLE);
    }

    private void showForm(){
        TransitionManager.beginDelayedTransition(container);
        formContainer.setVisibility(View.VISIBLE);
        loader.setVisibility(View.GONE);
    }

    public void setupRules(){
        awesomeValidation.addValidation( this,R.id.til_email, Patterns.EMAIL_ADDRESS,R.string.erro_email );
        awesomeValidation.addValidation( this,R.id.til_password, RegexTemplate.NOT_EMPTY,R.string.erro_password );
    }

    private void ruleUser(){
        callused = servicemen.users();
        callused.enqueue( new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                Log.w( TAG,"onReponse: " + response );
                if (response.isSuccessful()){
                    String usertype = response.body().get(0).getUsertype();
                    Log.w( TAG,"Usertupe: " + usertype );
                    if (usertype.equals("customer")) {
                        startActivity( new Intent( LoginActivity.this, CustomerActivity.class ) );
                        finish();
                    }if (usertype.equals("actor")){
                        Intent i = new Intent(LoginActivity.this, MainActivity.class);

                        String xeid = response.body().get( 0 ).getXe_id();
                        i.putExtra("INT_xeid", xeid);
                        String email = response.body().get( 0 ).getEmail();
                        i.putExtra("STRING_EMAIL", email);
                        String phone = response.body().get( 0 ).getPhone();
//                        Integer sdt = Integer.parseInt(phone);
                        i.putExtra("STRING_PHONE", phone);
                        startActivity(i);
                        finish();
                    }
                }
            }
            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.w(TAG,"onFailure" + t.getMessage());
            }
        } );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (call !=null){
            call.cancel();
            call =null;
        }

    }
}