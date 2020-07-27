package com.vanhieu.doan.Recyclerview;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vanhieu.doan.R;
import com.vanhieu.doan.entities.Lichtrinh;
import com.vanhieu.doan.screen.LichtrinhActivity;

import java.text.SimpleDateFormat;
import java.util.List;

public class AdapterLichtrinh extends RecyclerView.Adapter<AdapterLichtrinh.MyViewHolder> {
    Context mContext;
    private   List<Lichtrinh> lichtrinhs;
    Lichtrinh lt;

    private static final String TAG = "AdapterLichtrinh";

    public AdapterLichtrinh(List<Lichtrinh> lichtrinhs, Context context) {
        this.mContext = context;
        this.lichtrinhs = lichtrinhs;
//        this.onLTListener = onLTListener;
    }

    @NonNull
    @Override
    public AdapterLichtrinh.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext =parent.getContext();
        View v = LayoutInflater.from( mContext ).inflate( R.layout.row_lichtrinh,parent ,false);
        MyViewHolder myViewHolder = new MyViewHolder( v );

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        holder.txtngay.setText( sdf.format(lichtrinhs.get( position ).getNgaydi()) );
        holder.txttuyen.setText( lichtrinhs.get( position ).getTuyen_id() );
        holder.txtgio.setText( lichtrinhs.get( position ).getXuatben());
        holder.txtidlichtrinh.setText( String.valueOf( lichtrinhs.get( position ).getId() ) );

    }

    @Override
    public int getItemCount() {
        return lichtrinhs.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView txtngay;
        TextView txttuyen;
        TextView txtgio;
        TextView txtidlichtrinh;

        public MyViewHolder(@NonNull View itemView) {
            super( itemView );
            txtngay = (TextView) itemView.findViewById(R.id.title);
            txttuyen = (TextView) itemView.findViewById(R.id.noidung);
            txtgio = (TextView) itemView.findViewById(R.id.hourgo);
            txtidlichtrinh = (TextView) itemView.findViewById(R.id.idlichtrinh);

            itemView.setOnClickListener( this );
        }

        @Override
        public void onClick(View v) {
            Context context = v.getContext();
            Intent intent = new Intent(context, LichtrinhActivity.class);
            intent.putExtra("STRING_hello",txtngay.getText().toString());
            intent.putExtra("STRING_idlt",Integer.parseInt( txtidlichtrinh.getText().toString() ));
            context.startActivity(intent);
        }
    }

}
