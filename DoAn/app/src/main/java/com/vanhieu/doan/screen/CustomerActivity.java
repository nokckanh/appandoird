package com.vanhieu.doan.screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.vanhieu.doan.R;
import com.vanhieu.doan.Recyclerview.AdapterChithu;
import com.vanhieu.doan.Recyclerview.AdapterLichCustomer;
import com.vanhieu.doan.Recyclerview.AdapterLichtrinh;
import com.vanhieu.doan.TokenManager;
import com.vanhieu.doan.entities.Lichtrinh;
import com.vanhieu.doan.entities.Lichtrinhcustomer;
import com.vanhieu.doan.network.ApiService;
import com.vanhieu.doan.network.RetrofitBuilderRecyclerview;

import java.util.List;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerActivity extends AppCompatActivity {
    private static final String TAG = "LichtrinhFragment";

    ApiService service;
    private Call<List<Lichtrinhcustomer>> calllichtrinh;
    TokenManager tokenManager;
    private RecyclerView recyclerView;

    AdapterLichCustomer adapterLichCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_customer );

        SharedPreferences preferences = getSharedPreferences("shareprefs", Context.MODE_PRIVATE);
        tokenManager = TokenManager.getInstance( preferences);
        service = RetrofitBuilderRecyclerview.createService( ApiService.class );

        recyclerView = (RecyclerView) findViewById(R.id.customerrecycler);
        ButterKnife.bind(this);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager  = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);

        calllichtrinh = service.getLichtrinhcustomer();
        calllichtrinh.enqueue( new Callback<List<Lichtrinhcustomer>>() {
            @Override
            public void onResponse(Call<List<Lichtrinhcustomer>> call, Response<List<Lichtrinhcustomer>> response) {
                Log.w(TAG,"onReponne"  + response);

                if (response.isSuccessful()){
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setHasFixedSize( true );
                    adapterLichCustomer = new AdapterLichCustomer( CustomerActivity.this,response.body() );
                    recyclerView.setAdapter(adapterLichCustomer);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                }
            }

            @Override
            public void onFailure(Call<List<Lichtrinhcustomer>> call, Throwable t) {
                Log.w(TAG,"onFailure" + t.getMessage());
            }
        } );

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(calllichtrinh != null){
            calllichtrinh.cancel();
            calllichtrinh = null;
        }
    }
}