package com.vanhieu.doan.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vanhieu.doan.MainActivity;
import com.vanhieu.doan.R;
import com.vanhieu.doan.Recyclerview.AdapterChithu;
import com.vanhieu.doan.Recyclerview.AdapterLichtrinh;
import com.vanhieu.doan.TokenManager;
import com.vanhieu.doan.entities.Chithu;
import com.vanhieu.doan.entities.Lichtrinh;
import com.vanhieu.doan.network.ApiService;
import com.vanhieu.doan.network.RetrofitBuilderRecyclerview;
import com.vanhieu.doan.screen.AddLichtrinhActivity;
import com.vanhieu.doan.screen.AddchithuActivity;
import com.vanhieu.doan.screen.LichtrinhActivity;

import java.util.List;

import butterknife.ButterKnife;
import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChitieuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChitieuFragment extends Fragment {

    private static final String TAG = "ChitieuFragment";

    ApiService service;
    private Call<List<Chithu>> callListchithu;
    TokenManager tokenManager;
    private RecyclerView recyclerView;

    AdapterChithu adapterChithu;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChitieuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChitieuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChitieuFragment newInstance(String param1, String param2) {
        ChitieuFragment fragment = new ChitieuFragment();
        Bundle args = new Bundle();
        args.putString( ARG_PARAM1, param1 );
        args.putString( ARG_PARAM2, param2 );
        fragment.setArguments( args );
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        if (getArguments() != null) {
            mParam1 = getArguments().getString( ARG_PARAM1 );
            mParam2 = getArguments().getString( ARG_PARAM2 );
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        SharedPreferences preferences = this.getActivity().getSharedPreferences("shareprefs", Context.MODE_PRIVATE);
        tokenManager = TokenManager.getInstance( preferences);
        service = RetrofitBuilderRecyclerview.createserviceWithAuth( ApiService.class , tokenManager );

        View rootView = inflater.inflate(R.layout.fragment_chitieu, container,
                false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerviewchithu);
        ButterKnife.bind(this, rootView);

        LichtrinhActivity lichtrinhActivity = (LichtrinhActivity) getActivity();
        int idlt = lichtrinhActivity.getidLichtrinh();

        callListchithu = service.getChithu( idlt );
        callListchithu.enqueue( new Callback<List<Chithu>>() {
            @Override
            public void onResponse(Call<List<Chithu>> call, Response<List<Chithu>> response) {
                Log.w(TAG,"onReponne"  + response);

                if (response.isSuccessful()){
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyclerView.setHasFixedSize( true );
                    adapterChithu = new AdapterChithu(getActivity(),response.body() );
                    recyclerView.setAdapter(adapterChithu);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                }
            }

            @Override
            public void onFailure(Call<List<Chithu>> call, Throwable t) {
                Log.w(TAG,"onFailure" + t.getMessage());
            }
        } );

        FabSpeedDial fabSpeedDial = (FabSpeedDial) rootView.findViewById( R.id.fabv);
        fabSpeedDial.setMenuListener(new SimpleMenuListenerAdapter() {
            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {
                //TODO: Start some activity

                switch (menuItem.getItemId()){
                    case R.id.callthunhap:
                        Intent i = new Intent(getActivity(), AddchithuActivity.class);
                        i.putExtra( "idlichtrinh" ,idlt );
                        startActivity( i );
                        return true;
                    default:
                        return super.onMenuItemSelected( menuItem );
                }
            }
        });

//        Log.w(TAG,"CHitieu "  + idlt);

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(callListchithu != null){
            callListchithu.cancel();
            callListchithu = null;
        }
    }
}