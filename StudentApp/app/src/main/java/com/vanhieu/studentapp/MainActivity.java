package com.vanhieu.studentapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.ReceiverCallNotAllowedException;
import android.icu.util.ULocale;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.Toolbar;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    FirebaseRecyclerOptions<Student> options;
    FirebaseRecyclerAdapter<Student,ViewHolder> adapter;
    Button btnadd;
    CardView cardView;

    String hello = "Hello word";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Button thêm
        btnadd = (Button) findViewById(R.id.buttonadd);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize( true );

        databaseReference =FirebaseDatabase.getInstance().getReference().child("Student");

        options = new FirebaseRecyclerOptions.Builder<Student>()
                .setQuery(databaseReference,Student.class ).build();
        adapter = new FirebaseRecyclerAdapter<Student, ViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Student model) {
                Picasso.get().load( model.getAva()).into( holder.ava, new Callback() {
                    @Override
                    public void onSuccess() {
                    }
                    @Override
                    public void onError(Exception e) {
                        Toast.makeText( getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT ).show();
                    }
                } );
                holder.name.setText( model.getName());
                holder.sex.setText( model.getSex() );
            }

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.row_adapter, parent, false);

                return new ViewHolder( view );
            }
        };

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter.startListening();
        recyclerView.setAdapter(adapter);
        registerForContextMenu(recyclerView);
    }

    // Sự kiên menu

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case 121:
                Toast.makeText(this,"Them Thong tin",Toast.LENGTH_SHORT ).show();
                Intent i = new Intent(this,MainActivity2.class);
                i.putExtra( "Setlec" ,hello );
                startActivity( i );
                return true;
            case 122:
                Toast.makeText(this,"Sua Thong tin",Toast.LENGTH_SHORT ).show();
                return true;
            case 123:
                Toast.makeText(this,"Xoa Thong tin",Toast.LENGTH_SHORT ).show();
                return true;
            default:
                return super.onContextItemSelected( item );
        }

    }

    public  void GoAddStudent(View view){
        Intent intent = new Intent (this, AddStudent.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (adapter!=null)
            adapter.startListening();

    }

    @Override
    protected void onStop() {
        if (adapter != null)
            adapter.stopListening();
            super.onStop();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter!=null)
            adapter.startListening();
    }


}
