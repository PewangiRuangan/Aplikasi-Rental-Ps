package com.example.rentalps.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.util.Log;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentalps.R;
import com.example.rentalps.models.PendapatanItem;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class PendapatanAdapter extends RecyclerView.Adapter<PendapatanAdapter.ViewHolder> {

    private final List<PendapatanItem> list;
    private final boolean isBulanan;

    public PendapatanAdapter(List<PendapatanItem> list, boolean isBulanan) {
        this.list = list;
        this.isBulanan = isBulanan;
    }
    private String formatTanggalLengkap(String tanggal) {
        try {

            SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

            Date date = sdfInput.parse(tanggal);
            SimpleDateFormat sdfOutput = new SimpleDateFormat("EEEE, dd MMMM yyyy", new Locale("id", "ID"));
            return sdfOutput.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return tanggal;
        }
    }

    private String formatRupiah(int amount) {
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        return format.format(amount).replace(",00", "");
    }


    @NonNull
    @Override
    public PendapatanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pendapatan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PendapatanAdapter.ViewHolder holder, int position) {
        PendapatanItem item = list.get(position);

        Log.d("PendapatanAdapter", "Tanggal: " + item.getTanggal() + ", Jumlah: " + item.getJumlah());

        holder.txtTanggal.setText(formatTanggalLengkap(item.getTanggal()));
        holder.txtJumlah.setText(formatRupiah(item.getJumlah()));


        if (isBulanan) {
            holder.textHari.setVisibility(View.GONE);
        } else {
            holder.textHari.setVisibility(View.VISIBLE);
            holder.textHari.setText(item.getHari()); // pastikan model PendapatanItem punya getHari()
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTanggal, txtJumlah, textHari;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTanggal = itemView.findViewById(R.id.textTanggal); // BENAR
            txtJumlah = itemView.findViewById(R.id.txtJumlah);
            textHari = itemView.findViewById(R.id.textHari);
        }
    }

    private String formatRupiah(String amountStr) {
        try {
            int amount = Integer.parseInt(amountStr);
            NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
            return format.format(amount).replace(",00", "");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "Rp0";
        }
    }

}
