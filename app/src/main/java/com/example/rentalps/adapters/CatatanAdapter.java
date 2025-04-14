package com.example.rentalps.adapters;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import android.widget.Button;


import com.example.rentalps.R;
import com.example.rentalps.models.Catatan;

public class CatatanAdapter extends RecyclerView.Adapter<CatatanAdapter.ViewHolder> {

    private List<Catatan> catatanList;
    private OnDeleteClickListener onDeleteClickListener;

    // Interface untuk callback tombol hapus
    public interface OnDeleteClickListener {
        void onDeleteClick(Catatan catatan);
    }

    // Constructor untuk menerima data dan listener
    public CatatanAdapter(List<Catatan> catatanList, OnDeleteClickListener onDeleteClickListener) {
        this.catatanList = catatanList;
        this.onDeleteClickListener = onDeleteClickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtIsi, txtTanggal, txtHari;
        Button btnHapus; // Tombol hapus

        public ViewHolder(View itemView) {
            super(itemView);
            txtIsi = itemView.findViewById(R.id.txtIsi);
            txtTanggal = itemView.findViewById(R.id.txtTanggal);
            txtHari = itemView.findViewById(R.id.txtHari);
            btnHapus = itemView.findViewById(R.id.btnHapus); // Inisialisasi tombol hapus
        }
    }

    @Override
    public CatatanAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_catatan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Catatan catatan = catatanList.get(position);
        holder.txtIsi.setText(catatan.getIsi());
        holder.txtTanggal.setText(catatan.getTanggal());
        holder.txtHari.setText(catatan.getHari());

        // Tambahkan listener untuk tombol hapus
        holder.btnHapus.setOnClickListener(v -> {
            if (onDeleteClickListener != null) {
                onDeleteClickListener.onDeleteClick(catatan);
            }
        });
    }

    @Override
    public int getItemCount() {
        return catatanList.size();
    }
}

