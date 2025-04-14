package com.example.rentalps.utils;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

import com.example.rentalps.Billing;
import com.example.rentalps.database.BillingDatabaseHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class ExportUtils {

    public static void exportRiwayat(Context context) {
        BillingDatabaseHelper db = new BillingDatabaseHelper(context);
        List<Billing> dataList = db.getAllBilling();

        StringBuilder sb = new StringBuilder();
        sb.append("Tanggal | TV | Konsol | Durasi | Harga\n"); // Optional: header
        for (Billing data : dataList) {
            sb.append(data.getTanggal()).append(" | ")
                    .append(data.getTv()).append(" | ")
                    .append(data.getKonsol()).append(" | ")
                    .append(data.getDurasi()).append(" | Rp ")
                    .append(data.getHarga()).append("\n");
        }

        try {
            File file = new File(context.getExternalFilesDir(null), "riwayat_billing.txt");
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(sb.toString().getBytes());
            fos.close();
            Toast.makeText(context, "Data berhasil diexport ke: " + file.getAbsolutePath(), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Gagal export data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public static void shareRiwayat(Context context) {
        BillingDatabaseHelper db = new BillingDatabaseHelper(context);
        List<Billing> dataList = db.getAllBilling();

        StringBuilder sb = new StringBuilder();
        sb.append("Tanggal | TV | Konsol | Durasi | Harga\n"); // Optional: header
        for (Billing data : dataList) {
            sb.append(data.getTanggal()).append(" | ")
                    .append(data.getTv()).append(" | ")
                    .append(data.getKonsol()).append(" | ")
                    .append(data.getDurasi()).append(" | Rp ")
                    .append(data.getHarga()).append("\n");
        }

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, sb.toString());
        context.startActivity(Intent.createChooser(shareIntent, "Bagikan Riwayat Billing"));
    }
    public static void exportToCSV(Context context, List<Billing> billingList, String fileName) {
        StringBuilder sb = new StringBuilder();
        sb.append("Tanggal,TV,Konsol,Durasi,Harga\n"); // Header CSV

        for (Billing data : billingList) {
            sb.append(data.getTanggal()).append(",")
                    .append(data.getTv()).append(",")
                    .append(data.getKonsol()).append(",")
                    .append(data.getDurasi()).append(",")
                    .append(data.getHarga()).append("\n");
        }

        try {
            // Tentukan lokasi folder Download
            File downloadsFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            if (!downloadsFolder.exists()) {
                downloadsFolder.mkdirs(); // Buat folder jika belum ada
            }

            // Buat file CSV di folder Download
            File file = new File(downloadsFolder, fileName);

            // Tulis data ke file CSV
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(sb.toString().getBytes());
            fos.close();

            // Beri notifikasi ke pengguna
            Toast.makeText(context, "Data berhasil diexport ke: " + file.getAbsolutePath(), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Gagal export CSV: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
