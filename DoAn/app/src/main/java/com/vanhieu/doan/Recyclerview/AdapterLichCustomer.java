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
import com.vanhieu.doan.entities.Lichtrinhcustomer;

import java.text.SimpleDateFormat;
import java.util.List;

public class AdapterLichCustomer extends RecyclerView.Adapter<AdapterLichCustomer.ViewHoloderLT> {

    Context context;
    private List<Lichtrinhcustomer> lichtrinhcustomers;

    public AdapterLichCustomer(Context context, List<Lichtrinhcustomer> lichtrinhcustomers) {
        this.context = context;
        this.lichtrinhcustomers = lichtrinhcustomers;
    }

    @NonNull
    @Override
    public ViewHoloderLT onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate( R.layout.row_lichtrinhcustomer, parent, false);

        return new ViewHoloderLT( view );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoloderLT holder, int position) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        holder.txtnhaxe.setText( lichtrinhcustomers.get( position ).getName() );
        holder.txttuyen.setText(lichtrinhcustomers.get( position ).getTuyen_id() );
        holder.txtbiensoxe.setText(lichtrinhcustomers.get( position ).getBSX() );
        holder.txtgioxuatben.setText(lichtrinhcustomers.get( position ).getXuatben() );
        holder.txtngaydi.setText( sdf.format(lichtrinhcustomers.get( position ).getNgaydi()));

    }

    @Override
    public int getItemCount() {
        return lichtrinhcustomers.size();
    }

    public class ViewHoloderLT extends RecyclerView.ViewHolder {
        TextView txtnhaxe;
        TextView txttuyen;
        TextView txtngaydi;
        TextView txtbiensoxe;
        TextView txtgioxuatben;


        public ViewHoloderLT(@NonNull View itemView) {
            super( itemView );
            txtnhaxe = (TextView) itemView.findViewById( R.id.namexe );
            txttuyen = (TextView) itemView.findViewById( R.id.tuyenid );
            txtngaydi = (TextView) itemView.findViewById( R.id.ngaykhoihanh );
            txtbiensoxe = (TextView) itemView.findViewById( R.id.BSX );
            txtgioxuatben = (TextView) itemView.findViewById( R.id.gioxuatben );
        }
    }
}
