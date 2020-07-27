package com.vanhieu.studentapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

import static com.vanhieu.studentapp.R.layout.abc_list_menu_item_checkbox;
import static com.vanhieu.studentapp.R.layout.activity_addstudent;

public class AddStudent extends AppCompatActivity {

    private TextView txtname,txtphone,txtaddress,txtage;
    private RadioButton checknam,checknu;
    private Button btnchoice,btnsave;
    DatabaseReference databaseReference;
    Student student;
    long maxid=0;
    ImageView img;
    StorageReference storageReference;
    public Uri imguri;
    private StorageTask uploadTask;
    private String ava;
    HashMap <String, String> hashMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView(activity_addstudent);

        txtname  = (TextView) findViewById(R.id.textName);
        txtphone = (TextView) findViewById(R.id.textphone);
        txtaddress =(TextView) findViewById(R.id.textaddress);
        txtage =(TextView) findViewById(R.id.textage);
        checknam = (RadioButton)findViewById( R.id.radionam );
        checknu = (RadioButton) findViewById(R.id.radionu);
        btnsave = (Button) findViewById( R.id.btnadd );
        btnchoice = (Button) findViewById( R.id.buttonchoice );
        img = (ImageView) findViewById(R.id.img );
        storageReference = FirebaseStorage.getInstance().getReference("Images");

        //Thêm sv
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Student");
        databaseReference.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                    maxid = (dataSnapshot).getChildrenCount();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        } );
        btnsave.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Upload
                if(uploadTask != null && uploadTask.isInProgress()){
                    Toast.makeText( AddStudent.this ,"Dang up ",Toast.LENGTH_LONG).show();
                }else {
                    FileUpload();
                    Intent intent = new Intent(AddStudent.this,MainActivity.class);
                    startActivity( intent );
                }
            }
        } );

        //chọn ảnh
        btnchoice.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileChose();
            }
        } );
    }
    // method cho Uplooad
    private String getExtention(Uri uri){
            ContentResolver cr = getContentResolver();
            MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
            return mimeTypeMap.getExtensionFromMimeType( cr.getType(uri) );
    }
    private void FileUpload(){
         ava = System.currentTimeMillis()+"."+getExtention( imguri );
//        StorageReference ref = storageReference.child( System.currentTimeMillis()+"."+getExtention(imguri));
        final StorageReference ref = storageReference.child(ava);
        uploadTask = ref.putFile(imguri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        ref.getDownloadUrl().addOnSuccessListener( new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String link = String.valueOf( uri );
//                                hashMap.put("",String.valueOf( uri ));
                                // add Sinh vien
                                int agea = Integer.parseInt(txtage.getText().toString().trim());
                                String nam = (String) checknam.getText();
                                String nu = (String) checknu.getText();
                                String name = txtname.getText().toString();
                                String diachi = txtaddress.getText().toString();
                                String phone = txtphone.getText().toString();
//                                String avata = link.toString().trim();
                                if (checknam.isChecked() == true){
                                    student = new Student(name,agea,phone,diachi,link,nam);
                                }else{
                                    student = new Student(name,agea,phone,diachi,link,nu);
                                }
                                databaseReference.child(String.valueOf( maxid+1 )).setValue(student);
                                Toast.makeText(AddStudent.this,"Thêm thành công !",Toast.LENGTH_LONG ).show();
                            }
                        } );
                                Toast.makeText( AddStudent.this,"Update Thành công",Toast.LENGTH_LONG ).show();
                        }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                });
    }

    private void FileChose(){
        Intent inte = new Intent();
        inte.setType( "image/" );
        inte.setAction( Intent.ACTION_GET_CONTENT );
        startActivityForResult( inte,1 );
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData()!=null){
            imguri = data.getData();
            img.setImageURI( imguri );

        }
    }
}
