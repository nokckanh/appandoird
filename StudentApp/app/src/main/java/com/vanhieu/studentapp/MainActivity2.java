package com.vanhieu.studentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main2 );

        txt = findViewById(R.id.textView5);

        Intent  i = getIntent();

        txt.setText( i.getStringExtra( "Setlec" ) );
    }
}