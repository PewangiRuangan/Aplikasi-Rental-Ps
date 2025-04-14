package com.example.rentalps;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BillingAdapter extends RecyclerView.Adapter<BillingAdapter.BillingViewHolder> {

    private List<Billing> billingList;

    public BillingAdapter(List<Billing> billingList) {
        this.billingList = billingList;
    }

    @NonNull
    @Override
    public BillingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_billing, parent, false);
        return new BillingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillingViewHolder holder, int position) {
        Billing billing = billingList.get(position);
        holder.tvTV.setText("TV: " + billing.getTv());
        holder.tvKonsol.setText("Konsol: " + billing.getKonsol());
        holder.tvDurasi.setText("Durasi: " + billing.getDurasi());
        holder.tvHarga.setText("Harga: " + billing.getHarga());
        holder.tvWaktu.setText("Waktu: " + billing.getWaktu());
    }

    @Override
    public int getItemCount() {
        return billingList.size();
    }

    public static class BillingViewHolder extends RecyclerView.ViewHolder {
        TextView tvTV, tvKonsol, tvDurasi, tvHarga, tvWaktu;

        public BillingViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTV = itemView.findViewById(R.id.tvTV);
            tvKonsol = itemView.findViewById(R.id.tvKonsol);
            tvDurasi = itemView.findViewById(R.id.tvDurasi);
            tvHarga = itemView.findViewById(R.id.tvHarga);
            tvWaktu = itemView.findViewById(R.id.tvWaktu);
        }
    }
}
