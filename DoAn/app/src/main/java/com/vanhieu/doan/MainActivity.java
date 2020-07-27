package com.vanhieu.doan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.vanhieu.doan.Fragment.PaperAdapter;
import com.vanhieu.doan.entities.User;
import com.vanhieu.doan.login.LoginActivity;
import com.vanhieu.doan.network.ApiService;
import com.vanhieu.doan.network.RetrofiBuilder;
import com.vanhieu.doan.screen.PageMainActivity;
import com.vanhieu.doan.screen.PageTwo;

import java.util.List;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;

    NavigationView navigationView;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    ViewPager viewPager;
    TabLayout tabLayout;
    PaperAdapter paperAdapter;

    TabItem ltrinhitem,hhoaitem,khangitem;
    TextView txtemail,txtphone;
    // Api
    private static final String TAG = "MainActivity";

    ApiService service;
    TokenManager tokenManager;
    Call<List<User>> call;

    private String xeid ;
    int idxe;
    User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        ButterKnife.bind(this);
        // Ánh xạ
        anhXa();

        setSupportActionBar( toolbar );
        // Api
        tokenManager = TokenManager.getInstance( getSharedPreferences( "shareprefs" , MODE_PRIVATE ));
        if(tokenManager.getToken() == null){
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
        service = RetrofiBuilder.createserviceWithAuth( ApiService.class ,tokenManager );
        // END API
        // Push Dữ liệu
        putData();
        // END Push
        navigationView.setNavigationItemSelectedListener( this );
        actionBarDrawerToggle = new ActionBarDrawerToggle( this,drawerLayout,toolbar,
                R.string.open, R.string.close );
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled( true );
        actionBarDrawerToggle.syncState();
        // method Adapter Tab
        AdapterTab();

        /*
        // tai deflaut fragmet
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.content_main,new PageMainActivity());
        fragmentTransaction.commit();
        */

    }

    public void anhXa(){
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById( R.id.drawer );
        navigationView = findViewById( R.id.navigationView );
        viewPager = findViewById( R.id.viewpaper );
        ltrinhitem = findViewById( R.id.itemlt );
        hhoaitem = findViewById( R.id.itemhh );
        khangitem = findViewById( R.id.itemkh );
        tabLayout = findViewById( R.id.tabllayoutlt );
    }
    public void AdapterTab(){
        paperAdapter = new PaperAdapter( getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,
                tabLayout.getTabCount());
        viewPager.setAdapter(paperAdapter);

        tabLayout.addOnTabSelectedListener( new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem( tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        } );
    }

    public void putData(){
        View headerview = navigationView.getHeaderView( 0 );
        txtemail = headerview.findViewById( R.id.getEmail );
        txtphone = headerview.findViewById( R.id.getPhone );
        Intent intent = getIntent();
        txtemail.setText( intent.getStringExtra("STRING_EMAIL") );
        txtphone.setText( intent.getStringExtra( "STRING_PHONE" ) );

        xeid = intent.getStringExtra( "INT_xeid" );
        idxe = Integer.parseInt( xeid );
        Log.w( TAG,"Usertupe: " + xeid );

    }

    public int getXeid(){
        return idxe;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer( GravityCompat.START );
        if (item.getItemId() == R.id.home){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content_main,new PageTwo());
            fragmentTransaction.commit();
        }
        if (item.getItemId() == R.id.Contact){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content_main,new PageMainActivity());
            fragmentTransaction.commit();
        }
        if (item.getItemId() == R.id.logout){
            call = service.logout();
            call.enqueue( new Callback<List<User>>() {
                @Override
                public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                    Log.w( TAG,"onReponse: " + response );
                    if (response.isSuccessful()){
                        tokenManager.deleteToken();
                        startActivity( new Intent( MainActivity.this,LoginActivity.class ) );
                        finish();
                    }
                }
                @Override
                public void onFailure(Call<List<User>> call, Throwable t) {
                    Log.w(TAG,"onFailure" + t.getMessage());
                }
            } );
        }
        return false;
    }
/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
            if (data.hasExtra("returnKey1")) {
                Toast.makeText(this, data.getExtras().getString("STRING_EMAIL"),
                        Toast.LENGTH_SHORT).show();

        }
    }
*/
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(call != null){
            call.cancel();
            call = null;
        }
    }

}


