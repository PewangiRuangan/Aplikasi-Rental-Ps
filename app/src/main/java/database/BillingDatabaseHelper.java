package com.example.rentalps.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.List;
import java.util.ArrayList;
import com.example.rentalps.models.PendapatanItem;

import com.example.rentalps.Billing;
import java.util.Calendar;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class BillingDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "billing.db";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_NAME = "billing";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TV = "tv";
    private static final String COLUMN_KONSOL = "konsol";
    private static final String COLUMN_DURASI = "durasi";
    private static final String COLUMN_HARGA = "harga";
    private static final String COLUMN_TIMESTAMP = "timestamp"; // disimpan dalam milidetik

    public BillingDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_TV + " TEXT," +
                COLUMN_KONSOL + " TEXT," +
                COLUMN_DURASI + " INTEGER," +
                COLUMN_HARGA + " INTEGER," +
                COLUMN_TIMESTAMP + " INTEGER," +
                "tanggal TEXT" + ")";
        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertBillingData(String tv, String konsol, int durasi, int harga, long waktuTransaksi) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TV, tv);
        values.put(COLUMN_KONSOL, konsol);
        values.put(COLUMN_DURASI, durasi);
        values.put(COLUMN_HARGA, harga);
        values.put(COLUMN_TIMESTAMP, waktuTransaksi);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    // ✅ Digunakan untuk ambil semua data billing
    public ArrayList<Billing> getAllBilling() {
        ArrayList<Billing> billingList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM billing", null);

        if (cursor.moveToFirst()) {
            do {
                String tv = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TV));
                String konsol = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_KONSOL));
                int durasi = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_DURASI));
                int harga = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_HARGA));
                long timestamp = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_TIMESTAMP));

                String tanggal = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date(timestamp));
                String waktu = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date(timestamp));

                Billing billing = new Billing(tv, konsol, durasi, harga, tanggal, waktu);
                billingList.add(billing);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return billingList;
    }

    public ArrayList<Billing> getBillingByDate(String targetDate) {
        ArrayList<Billing> filteredList = new ArrayList<>();
        ArrayList<Billing> allData = getAllBilling();

        for (Billing data : allData) {
            Log.d("CekTanggal", "Data: " + data.getTanggal() + " == Target: " + targetDate);
            if (data.getTanggal().equals(targetDate)) {
                filteredList.add(data);
            }
        }

        return filteredList;
    }


    public ArrayList<Billing> getBillingByMonth(int month, int year) {
        ArrayList<Billing> filteredList = new ArrayList<>();
        ArrayList<Billing> allData = getAllBilling();

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

        for (Billing data : allData) {
            try {
                Date date = sdf.parse(data.getTanggal());
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                int dataMonth = cal.get(Calendar.MONTH) + 1; // bulan dimulai dari 0
                int dataYear = cal.get(Calendar.YEAR);

                if (dataMonth == month && dataYear == year) {
                    filteredList.add(data);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return filteredList;
    }


    public ArrayList<Billing> getBillingByDateRange(String startDateStr, String endDateStr) {
        ArrayList<Billing> filteredList = new ArrayList<>();
        ArrayList<Billing> allData = getAllBilling();

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            Date startDate = sdf.parse(startDateStr);
            Date endDate = sdf.parse(endDateStr);

            for (Billing data : allData) {
                Date billingDate = sdf.parse(data.getTanggal());
                if ((billingDate.equals(startDate) || billingDate.after(startDate)) &&
                        (billingDate.equals(endDate) || billingDate.before(endDate))) {
                    filteredList.add(data);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return filteredList;
    }

    public boolean exportBillingToCSV(String filePath, ArrayList<Billing> billingList) {
        try {
            FileWriter writer = new FileWriter(filePath);
            writer.append("TV,Konsol,Durasi,Harga,Tanggal,Waktu\n");

            for (Billing billing : billingList) {
                writer.append(billing.getTv()).append(",")
                        .append(billing.getKonsol()).append(",")
                        .append(String.valueOf(billing.getDurasi())).append(",")
                        .append(String.valueOf(billing.getHarga())).append(",")
                        .append(billing.getTanggal()).append(",")
                        .append(billing.getWaktu()).append("\n");
            }

            writer.flush();
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public List<PendapatanItem> getPendapatanHarian() {
        List<PendapatanItem> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT strftime('%Y-%m-%d', datetime(timestamp / 1000, 'unixepoch')) AS tanggal, SUM(harga) as total " +
                "FROM billing GROUP BY tanggal ORDER BY tanggal DESC";

        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            String tanggal = cursor.getString(0); // contoh: 2025-04-11
            String total = cursor.getString(1);   // total harga
            list.add(new PendapatanItem(tanggal, "", total)); // kolom hari bisa dikosongkan atau diolah nanti
        }
        cursor.close();
        return list;
    }





    public List<PendapatanItem> getPendapatanBulanan() {
        List<PendapatanItem> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT strftime('%m-%Y', datetime(timestamp / 1000, 'unixepoch')) AS bulan, SUM(harga) as total " +
                "FROM billing GROUP BY bulan ORDER BY bulan DESC";

        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            String bulan = cursor.getString(0); // misalnya: 04-2025
            String total = cursor.getString(1); // total harga
            list.add(new PendapatanItem(bulan, "", total)); // kolom hari dikosongkan
        }
        cursor.close();
        return list;
    }


    private String convertHari(String kodeHari) {
        switch (kodeHari) {
            case "0": return "Minggu";
            case "1": return "Senin";
            case "2": return "Selasa";
            case "3": return "Rabu";
            case "4": return "Kamis";
            case "5": return "Jumat";
            case "6": return "Sabtu";
            default: return "";
        }
    }

}
