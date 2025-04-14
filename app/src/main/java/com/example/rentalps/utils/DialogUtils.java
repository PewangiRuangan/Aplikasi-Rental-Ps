package com.example.rentalps.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rentalps.database.BillingDatabaseHelper;
import com.example.rentalps.R;
import com.example.rentalps.adapters.TVAdapter;
import com.example.rentalps.models.TV;

public class DialogUtils {

    public interface OnCustomInputListener {
        void onInput(String input);
    }

    public interface OnTVSelectedListener {
        void onTVSelected(TV tv, int position);
    }

    public static void showDialogPilihKonsolDurasi(Activity activity, TV tv, int position, TVAdapter adapter, OnTVSelectedListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View dialogView = LayoutInflater.from(activity).inflate(R.layout.dialog_pilih_konsol_durasi, null);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        Context context = activity;

        // Konsol
        TextView btnPs3 = dialogView.findViewById(R.id.btnPs3);
        TextView btnPs4 = dialogView.findViewById(R.id.btnPs4);
        TextView btnPs5 = dialogView.findViewById(R.id.btnPs5);

        // Durasi
        TextView cardDurasi30m = dialogView.findViewById(R.id.cardDurasi30m);
        TextView cardDurasi1 = dialogView.findViewById(R.id.cardDurasi1);
        TextView cardDurasi2 = dialogView.findViewById(R.id.cardDurasi2);
        TextView cardDurasi3 = dialogView.findViewById(R.id.cardDurasi3);
        TextView cardDurasi4 = dialogView.findViewById(R.id.cardDurasi4);
        TextView cardDurasi5 = dialogView.findViewById(R.id.cardDurasi5);
        TextView cardCustomDurasi = dialogView.findViewById(R.id.cardCustomDurasi);

        // Harga
        TextView cardHarga3k = dialogView.findViewById(R.id.cardHarga3k);
        TextView cardHarga5k = dialogView.findViewById(R.id.cardHarga5k);
        TextView cardHarga10k = dialogView.findViewById(R.id.cardHarga10k);
        TextView cardHarga15k = dialogView.findViewById(R.id.cardHarga15k);
        TextView cardHarga20k = dialogView.findViewById(R.id.cardHarga20k);
        TextView cardHarga25k = dialogView.findViewById(R.id.cardHarga25k);
        TextView cardHargaCustom = dialogView.findViewById(R.id.cardHargaCustom);

        // Ringkasan & Tombol
        TextView tvRingkasan = dialogView.findViewById(R.id.tvRingkasan);
        TextView btnKonfirmasi = dialogView.findViewById(R.id.btnKonfirmasi);
        //TextView btnCetakResi = dialogView.findViewById(R.id.btnCetakResi);

        addClickEffect(cardDurasi30m);
        addClickEffect(cardDurasi1);
        addClickEffect(cardDurasi2);
        addClickEffect(cardDurasi3);
        addClickEffect(cardDurasi4);
        addClickEffect(cardDurasi5);
        addClickEffect(cardCustomDurasi);

        addClickEffect(cardHarga3k);
        addClickEffect(cardHarga5k);
        addClickEffect(cardHarga10k);
        addClickEffect(cardHarga15k);
        addClickEffect(cardHarga20k);
        addClickEffect(cardHarga25k);
        addClickEffect(cardHargaCustom);

        addClickEffect(btnKonfirmasi);
        //addClickEffect(btnCetakResi);

        final String[] selectedKonsol = {null};
        final int[] selectedDurasi = {0};
        final int[] selectedHarga = {0};

        // Konsol listener
        View.OnClickListener konsolListener = v -> {
            resetCardBackground(btnPs3, btnPs4, btnPs5);
            v.setBackgroundResource(R.drawable.card_selected);
            selectedKonsol[0] = ((TextView) v).getText().toString();
            updateRingkasan(tvRingkasan, selectedKonsol[0], selectedDurasi[0], selectedHarga[0]);
        };
        btnPs3.setOnClickListener(konsolListener);
        btnPs4.setOnClickListener(konsolListener);
        btnPs5.setOnClickListener(konsolListener);

        // Durasi
        setDurasiListener(cardDurasi30m, 30, selectedDurasi, tvRingkasan, selectedKonsol, selectedHarga);
        setDurasiListener(cardDurasi1, 60, selectedDurasi, tvRingkasan, selectedKonsol, selectedHarga);
        setDurasiListener(cardDurasi2, 120, selectedDurasi, tvRingkasan, selectedKonsol, selectedHarga);
        setDurasiListener(cardDurasi3, 180, selectedDurasi, tvRingkasan, selectedKonsol, selectedHarga);
        setDurasiListener(cardDurasi4, 240, selectedDurasi, tvRingkasan, selectedKonsol, selectedHarga);
        setDurasiListener(cardDurasi5, 300, selectedDurasi, tvRingkasan, selectedKonsol, selectedHarga);

        cardCustomDurasi.setOnClickListener(v -> showCustomInputDialog(activity, "Masukkan durasi (menit)", input -> {
            try {
                selectedDurasi[0] = Integer.parseInt(input);
                updateRingkasan(tvRingkasan, selectedKonsol[0], selectedDurasi[0], selectedHarga[0]);
                resetCardBackground(cardDurasi30m, cardDurasi1, cardDurasi2, cardDurasi3, cardDurasi4, cardDurasi5, cardCustomDurasi);
                v.setBackgroundResource(R.drawable.card_selected);
            } catch (Exception e) {
                Toast.makeText(context, "Input tidak valid", Toast.LENGTH_SHORT).show();
            }
        }));

        // Harga
        setHargaListener(cardHarga3k, 3000, selectedHarga, tvRingkasan, selectedKonsol, selectedDurasi);
        setHargaListener(cardHarga5k, 5000, selectedHarga, tvRingkasan, selectedKonsol, selectedDurasi);
        setHargaListener(cardHarga10k, 10000, selectedHarga, tvRingkasan, selectedKonsol, selectedDurasi);
        setHargaListener(cardHarga15k, 15000, selectedHarga, tvRingkasan, selectedKonsol, selectedDurasi);
        setHargaListener(cardHarga20k, 20000, selectedHarga, tvRingkasan, selectedKonsol, selectedDurasi);
        setHargaListener(cardHarga25k, 25000, selectedHarga, tvRingkasan, selectedKonsol, selectedDurasi);

        cardHargaCustom.setOnClickListener(v -> showCustomInputDialog(activity, "Masukkan harga (Rp)", input -> {
            try {
                selectedHarga[0] = Integer.parseInt(input);
                updateRingkasan(tvRingkasan, selectedKonsol[0], selectedDurasi[0], selectedHarga[0]);
                resetCardBackground(cardHarga3k, cardHarga5k, cardHarga10k, cardHarga15k, cardHarga20k, cardHarga25k, cardHargaCustom);
                v.setBackgroundResource(R.drawable.card_selected);
            } catch (Exception e) {
                Toast.makeText(context, "Input tidak valid", Toast.LENGTH_SHORT).show();
            }
        }));



        btnKonfirmasi.setOnClickListener(view -> {
            if (selectedKonsol[0] == null || selectedDurasi[0] <= 0 || selectedHarga[0] <= 0) {
                Toast.makeText(context, "Lengkapi pilihan konsol, durasi, dan harga!", Toast.LENGTH_SHORT).show();
                return;
            }
            long waktuMulai = System.currentTimeMillis();

            tv.setKonsol(selectedKonsol[0]);
            tv.setHarga(selectedHarga[0]);
            tv.setDurasiAwal(selectedDurasi[0]);
            tv.setWaktuMulaiMillis(System.currentTimeMillis());
            tv.setWaktuSisaMillis((long) selectedDurasi[0] * 60 * 1000);
            tv.setSedangDigunakan(true);

            tv.startTimer(context, () -> {
                tv.setSedangDigunakan(false);
                long waktuTransaksi = System.currentTimeMillis();

                BillingDatabaseHelper dbHelper = new BillingDatabaseHelper(context);
                dbHelper.insertBillingData("TV " + tv.getNomor(), tv.getKonsol(), selectedDurasi[0], selectedHarga[0], waktuTransaksi);
            });

            adapter.notifyItemChanged(position);
            dialog.dismiss();

            if (listener != null) {
                listener.onTVSelected(tv, position);
            }

            Toast.makeText(context, "TV " + tv.getNomor() + " dimulai!", Toast.LENGTH_SHORT).show();
        });

        dialog.show();
    }
    public static void addClickEffect(View view) {
        view.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    v.animate().scaleX(0.95f).scaleY(0.95f).setDuration(100).start();
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    v.animate().scaleX(1f).scaleY(1f).setDuration(100).start();
                    break;
            }
            return false;
        });
    }

    private static void resetCardBackground(TextView... cards) {
        for (TextView card : cards) {
            card.setBackgroundResource(R.drawable.card_default);
        }
    }

    private static void setDurasiListener(TextView view, int durasi, int[] selectedDurasi,
                                          TextView tvRingkasan, String[] selectedKonsol, int[] selectedHarga) {
        view.setOnClickListener(v -> {
            resetCardBackground(
                    view.getRootView().findViewById(R.id.cardDurasi30m),
                    view.getRootView().findViewById(R.id.cardDurasi1),
                    view.getRootView().findViewById(R.id.cardDurasi2),
                    view.getRootView().findViewById(R.id.cardDurasi3),
                    view.getRootView().findViewById(R.id.cardDurasi4),
                    view.getRootView().findViewById(R.id.cardDurasi5),
                    view.getRootView().findViewById(R.id.cardCustomDurasi));
            v.setBackgroundResource(R.drawable.card_selected);
            selectedDurasi[0] = durasi;
            updateRingkasan(tvRingkasan, selectedKonsol[0], durasi, selectedHarga[0]);
        });
    }

    private static void setHargaListener(TextView view, int harga, int[] selectedHarga,
                                         TextView tvRingkasan, String[] selectedKonsol, int[] selectedDurasi) {
        view.setOnClickListener(v -> {
            resetCardBackground(
                    view.getRootView().findViewById(R.id.cardHarga3k),
                    view.getRootView().findViewById(R.id.cardHarga5k),
                    view.getRootView().findViewById(R.id.cardHarga10k),
                    view.getRootView().findViewById(R.id.cardHarga15k),
                    view.getRootView().findViewById(R.id.cardHarga20k),
                    view.getRootView().findViewById(R.id.cardHarga25k),
                    view.getRootView().findViewById(R.id.cardHargaCustom));
            v.setBackgroundResource(R.drawable.card_selected);
            selectedHarga[0] = harga;
            updateRingkasan(tvRingkasan, selectedKonsol[0], selectedDurasi[0], harga);
        });
    }

    private static void updateRingkasan(TextView ringkasan, String konsol, int durasi, int harga) {
        if (konsol != null && durasi > 0 && harga > 0) {
            ringkasan.setText("Konsol: " + konsol + "\nDurasi: " + durasi + " menit\nHarga: Rp " + harga);
        } else {
            ringkasan.setText("Silakan pilih konsol, durasi, dan harga.");
        }
    }

    private static void showCustomInputDialog(Activity activity, String title, OnCustomInputListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(title);

        final EditText input = new EditText(activity);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        builder.setPositiveButton("OK", (dialog, which) -> {
            String text = input.getText().toString();
            if (!text.isEmpty()) {
                listener.onInput(text);
            }
        });

        builder.setNegativeButton("Batal", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private static void showDialogResi(Context context, String tv, String konsol, int durasi, int harga) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Resi Sewa");
        builder.setMessage("TV: " + tv + "\nKonsol: " + konsol + "\nDurasi: " + durasi + " menit\nHarga: Rp " + harga);
        builder.setPositiveButton("Tutup", (dialog, which) -> dialog.dismiss());
        builder.show();
    }
}
