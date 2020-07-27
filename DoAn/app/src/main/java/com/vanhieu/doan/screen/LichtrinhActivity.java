package com.vanhieu.doan.screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.vanhieu.doan.Fragment.PaperLichtrinhAdapter;
import com.vanhieu.doan.MainActivity;
import com.vanhieu.doan.R;

import butterknife.ButterKnife;

public class LichtrinhActivity extends AppCompatActivity {

    private static final String TAG = "LichtrinhActivity";
    TabLayout tabLayout;
    ViewPager viewPager;

    TabItem tabItem1,tabItem2,tabItem3;
    PaperLichtrinhAdapter paperAdapter;
    Toolbar toolbar;
    int idlichtrinh;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_lichtrinh );
        ButterKnife.bind(this);
        anhXa();
        setSupportActionBar( toolbar );
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent i = getIntent();
        String ngay = i.getStringExtra( "STRING_hello" );

        idlichtrinh = i.getIntExtra( "STRING_idlt" ,0);

        Log.w(TAG,"LTacti"  + idlichtrinh);
        getSupportActionBar().setTitle(ngay);

        AdapterTab();

    }

    public int getidLichtrinh(){
        return idlichtrinh;
    }

    public void anhXa(){
        toolbar = (Toolbar) findViewById(R.id.toobarlt);
        viewPager = findViewById( R.id.paperviewlichtrinh );
        tabItem1 = findViewById( R.id.itemchitieu );
        tabItem2 = findViewById( R.id.itemhh );
        tabItem3 = findViewById( R.id.itemkh );
        tabLayout = findViewById( R.id.tabllayoutlt );
    }

    public void AdapterTab(){
        paperAdapter = new PaperLichtrinhAdapter( getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,
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
}