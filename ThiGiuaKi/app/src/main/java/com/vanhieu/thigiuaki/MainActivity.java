package com.vanhieu.thigiuaki;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        btn = (Button) findViewById( R.id.button3 );


    }

    public void GoAddTodo(View view) {
        Intent intent = new Intent( this, ListMucsic.class );
        startActivity( intent );
    }
}