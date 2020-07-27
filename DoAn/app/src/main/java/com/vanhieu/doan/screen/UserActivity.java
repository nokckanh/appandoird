package com.vanhieu.doan.screen;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.vanhieu.doan.R;
import com.vanhieu.doan.TokenManager;
import com.vanhieu.doan.entities.User;
import com.vanhieu.doan.login.LoginActivity;
import com.vanhieu.doan.network.ApiService;
import com.vanhieu.doan.network.RetrofiBuilder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserActivity extends AppCompatActivity {

    private static final String TAG = "UserActivity";

    @BindView( R.id.post_title )
    TextView title;

    ApiService service;
    TokenManager tokenManager;
    Call<List<User>> call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_user );

        ButterKnife.bind(this);

        tokenManager = TokenManager.getInstance( getSharedPreferences( "shareprefs" , MODE_PRIVATE ));

        if(tokenManager.getToken() == null){
            startActivity(new Intent(UserActivity.this, LoginActivity.class));
            finish();
        }
        service = RetrofiBuilder.createserviceWithAuth( ApiService.class ,tokenManager );

    }

    @OnClick(R.id.btn_user)
    void getUser(){
        call = service.users();
        call.enqueue( new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                Log.w( TAG,"onReponse: " + response );

                String url = response.raw().request().url().toString();
                if (response.isSuccessful()){
                    title.setText( response.body().get( 0 ).getUsertype() );
//                    JsonObjectRequest request = new JsonObjectRequest( Request.Method.GET, url, null, new com.android.volley.Response.Listener<JSONObject>() {
//                        @Override
//                        public void onResponse(JSONObject response) {
//                            try {
//                               JSONArray json =  response.getJSONArray("data");
//                               JSONObject hit = json.getJSONObject(0);
//                               String usertype = hit.getString( "usertype" );
//                               title.setText( usertype );
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }, new com.android.volley.Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            error.printStackTrace();
//                        }
//                    } );
                }else {
                    tokenManager.deleteToken();
                        startActivity( new Intent( UserActivity.this,LoginActivity.class ) );
                        finish();
                }
            }
            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.w(TAG,"onFailure" + t.getMessage());
            }
        } );

    }
    @OnClick(R.id.btn_logout)
    void logout(){
        call = service.logout();
        call.enqueue( new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                Log.w( TAG,"onReponse: " + response );
                if (response.isSuccessful()){
                    tokenManager.deleteToken();
                    startActivity( new Intent( UserActivity.this,LoginActivity.class ) );
                    finish();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.w(TAG,"onFailure" + t.getMessage());
            }
        } );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(call != null){
            call.cancel();
            call = null;
        }
    }
}