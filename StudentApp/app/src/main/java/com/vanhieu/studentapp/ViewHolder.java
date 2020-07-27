package com.vanhieu.studentapp;

import android.content.Context;
import android.media.Image;
import android.provider.ContactsContract;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.firebase.ui.firestore.FirestoreRecyclerAdapter;

import java.util.List;

public class ViewHolder extends  RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{

        ImageView ava;
        TextView name;
        TextView sex;


        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            sex = (TextView) itemView.findViewById(R.id.textView_sex);
            ava = (ImageView) itemView.findViewById(R.id.image);
            itemView.setOnCreateContextMenuListener( this );

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Chọn ");
        menu.add(0, 121, 0, "Thêm thông tin");//groupId, itemId, order, title
        menu.add(0, 122, 1, "Sửa thông tin");
        menu.add(0, 123, 0, "Xóa thông tin");
    }




}
