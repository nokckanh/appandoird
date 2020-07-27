package com.vanhieu.doan.Recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vanhieu.doan.R;
import com.vanhieu.doan.entities.Chithu;

import java.util.List;

public class AdapterChithu extends RecyclerView.Adapter<AdapterChithu.ChithuViewholder> {

    Context context;
    private List<Chithu> chithus;

    public AdapterChithu(Context context, List<Chithu> chithus) {
        this.context = context;
        this.chithus = chithus;
    }

    @NonNull
    @Override
    public ChithuViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context =parent.getContext();
        View v = LayoutInflater.from( context ).inflate( R.layout.row_chitieu,parent ,false);
        ChithuViewholder myViewHolder = new ChithuViewholder(  v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChithuViewholder holder, int position) {
        holder.txtchitieuname.setText( chithus.get( position ).getName() );


        holder.txtchitieughichu.setText("Ghi chú : "+ chithus.get( position ).getGhichu() );


        holder.txtchithu.setText( chithus.get( position ).getChithu()+"VNĐ" );
    }

    @Override
    public int getItemCount() {
        return chithus.size();
    }

    public class ChithuViewholder extends RecyclerView.ViewHolder {
        TextView txtchitieuname;
        TextView txtchitieughichu;
        TextView txtchithu;

        public ChithuViewholder(@NonNull View itemView) {
            super( itemView );
            txtchitieuname = (TextView) itemView.findViewById( R.id.tuyenid );
            txtchitieughichu = (TextView) itemView.findViewById( R.id.ghichuchithu );
            txtchithu = (TextView) itemView.findViewById( R.id.gioxuatben );
        }
    }
}
