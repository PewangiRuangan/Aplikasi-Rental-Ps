package com.example.rentalps.adapters;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.text.NumberFormat;
import android.media.MediaPlayer;
import com.example.rentalps.Billing;
import com.example.rentalps.SharedPrefHelper;
import com.example.rentalps.R;
import com.example.rentalps.database.BillingDatabaseHelper;
import com.example.rentalps.models.TV;
import com.example.rentalps.adapters.TVAdapter;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TVAdapter extends RecyclerView.Adapter<TVAdapter.TVViewHolder> {

    public interface OnTVClickListener {
        void onTVClick(TV tv, int position);
        void onPauseClick(int position);
        void onResumeClick(int position);
        void onResetClick(int position);

        void onTVLongClick(TV tv, int position);
        void onDeleteClick(int position);
    }

    private  List<TV> tvList;
    private final OnTVClickListener listener;
    private final LayoutInflater inflater;
    private final Handler handler = new Handler();
    private final Context context;

    public void setTVList(List<TV> tvList) {
        this.tvList = tvList;
        notifyDataSetChanged(); // agar RecyclerView refresh datanya
    }

    public TVAdapter(Context context, List<TV> tvList, OnTVClickListener listener) {
        this.context = context;
        this.tvList = tvList;
        this.listener = listener;
        this.inflater = LayoutInflater.from(context);
        startPeriodicUpdate();
    }

    private void startPeriodicUpdate() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
                handler.postDelayed(this, 1000);
            }
        }, 1000);
    }


    @NonNull
    @Override
    public TVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TVViewHolder(inflater.inflate(R.layout.item_tv, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TVViewHolder holder, int position) {
        TV tv = tvList.get(position);
        holder.tvNomor.setText("TV " + tv.getNomor());
        //holder.tvStatus.setText(tv.isSedangDigunakan() ? "Berjalan" : "Siap");
        holder.tvTimer.setText(formatMillis(tv.getWaktuSisaMillis()));

        // Update timer secara real-time
        tv.setOnTimerTickListener(millisUntilFinished -> {
            holder.tvTimer.setText(formatMillis(millisUntilFinished));
        });

        // Konsol
        holder.tvKonsol.setText("" + tv.getKonsol());

        // HARGA
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));
        formatRupiah.setMaximumFractionDigits(0);
        holder.tvHarga.setText("" + formatRupiah.format(tv.getHarga())); // Tambahkan ini
        // Pastikan holder.tvHarga terhubung dengan TextView yang sesuai di item_tv.xml

        // Klik item
        holder.itemView.setOnClickListener(v -> listener.onTVClick(tv, position));

        // Tombol Pause
        holder.btnPause.setOnClickListener(v -> {
            tv.pauseTimer();
            notifyItemChanged(position);
        });

        // Tombol Resume
        holder.btnResume.setOnClickListener(v -> {
            tv.resumeTimer(context, () -> {
                tv.setSedangDigunakan(false);
                notifyItemChanged(position);
            });
        });

        // Tombol Reset
        holder.btnReset.setOnClickListener(v -> {
            tv.resetTimer(() -> {
                tv.setSedangDigunakan(false);
                notifyItemChanged(position);

                long waktuTransaksi = System.currentTimeMillis();

                BillingDatabaseHelper dbHelper = new BillingDatabaseHelper(context);
                dbHelper.insertBillingData("TV " + tv.getNomor(), tv.getKonsol(), tv.getDurasiAwal(), tv.getHarga(), waktuTransaksi);
            });
        });

        // Waktu berakhir
        if (tv.isSedangDigunakan()) {
            long waktuBerakhir = tv.getWaktuBerakhirMillis();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
            String waktuBerakhirStr = sdf.format(new Date(waktuBerakhir));
            holder.tvBerakhir.setText("Berakhir pada: " + waktuBerakhirStr);
        } else {
            holder.tvBerakhir.setText("Berakhir pada: -");
        }

        // Tombol Hapus
        holder.btnDelete.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDeleteClick(position);
            }
        });
    }


    public void startTimer(TV tv, int position) {
        tv.startTimer(context, () -> {
            notifyItemChanged(position); // kalau mau refresh UI setelah timer habis
        });
    }


    private String formatMillis(long millis) {
        long seconds = millis / 1000;
        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;
        long secs = seconds % 60;

        return String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, secs);
    }



    @Override
    public int getItemCount() {
        return tvList.size();
    }

    public void removeTV(int position) {
        if (position >= 0 && position < tvList.size()) {
            tvList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, tvList.size());
        }
    }

    static class TVViewHolder extends RecyclerView.ViewHolder {
        TextView tvNomor, tvTimer, tvKonsol, tvBerakhir, tvHarga;
        Button btnPause, btnResume, btnReset, btnDelete;

        TVViewHolder(View itemView) {
            super(itemView);
            tvNomor = itemView.findViewById(R.id.tvNomor);
            //tvStatus = itemView.findViewById(R.id.tvStatus);
            tvTimer = itemView.findViewById(R.id.tvTimer);
            tvKonsol = itemView.findViewById(R.id.tvKonsol);
            tvBerakhir = itemView.findViewById(R.id.tvBerakhir);
            tvHarga = itemView.findViewById(R.id.tvHarga);
            btnPause = itemView.findViewById(R.id.btnPause);
            btnResume = itemView.findViewById(R.id.btnResume);
            btnReset = itemView.findViewById(R.id.btnReset);
            btnDelete = itemView.findViewById(R.id.btnDelete); // Tambahan
        }
    }


}
