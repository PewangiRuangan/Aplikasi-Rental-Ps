package com.example.rentalps.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.rentalps.Billing;
import com.example.rentalps.R;
import java.util.List;



public class BillingAdapter extends RecyclerView.Adapter<BillingAdapter.ViewHolder> {

    private List<Billing> billingList;

    public BillingAdapter(List<Billing> billingList) {
        this.billingList = billingList;
    }

    @Override
    public BillingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_billing, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(BillingAdapter.ViewHolder holder, int position) {
        Billing billing = billingList.get(position);
        String hargaText = "Rp " + billing.getHarga(); // Sudah int, jadi langsung pakai

        holder.tvInfo.setText(
                "TV: " + billing.getTv() + "\n" +
                        "Konsol: " + billing.getKonsol() + "\n" +
                        "Durasi: " + billing.getDurasi() + " menit\n" +
                        "Harga: " + hargaText + "\n" +
                        "Waktu: " + billing.getWaktu()
        );
    }


    @Override
    public int getItemCount() {
        return billingList.size();
    }
    public List<Billing> getBillingList() {
        return billingList;
    }

    public void setData(List<Billing> newData) {
        this.billingList = newData;
        notifyDataSetChanged();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvInfo;

        public ViewHolder(View itemView) {
            super(itemView);
            tvInfo = itemView.findViewById(R.id.tvInfo);
        }
    }
}
