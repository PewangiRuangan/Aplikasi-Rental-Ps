package com.example.rentalps;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import com.example.rentalps.adapters.BillingAdapter;
import com.example.rentalps.database.BillingDatabaseHelper;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.rentalps.utils.ExportUtils;
import com.example.rentalps.utils.ExportUtils;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class HarianFragment extends Fragment {

    private RecyclerView recyclerView;
    private BillingAdapter billingAdapter;
    private BillingDatabaseHelper databaseHelper;
    private TextView tvTotal;
    private List<Billing> billingList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_harian, container, false);

        recyclerView = view.findViewById(R.id.rvHarian);
        Button btnPilihTanggal = view.findViewById(R.id.btnPilihTanggal);
        Button btnExport = view.findViewById(R.id.btnExportCSV);
        tvTotal = view.findViewById(R.id.tvTotalHarian);

        databaseHelper = new BillingDatabaseHelper(getContext());
        // Ambil tanggal hari ini
        String today = new java.text.SimpleDateFormat("dd-MM-yyyy", java.util.Locale.getDefault()).format(new java.util.Date());
        android.util.Log.d("TanggalHariIni", "Hari ini: " + today); // Debug log

        billingList = databaseHelper.getBillingByDate(today); // Ganti ke data hari ini

        setupRecyclerView();

        hitungTotalPendapatan();

        btnPilihTanggal.setOnClickListener(v -> showDatePicker());

        btnExport.setOnClickListener(v -> {
            ExportUtils.exportToCSV(getContext(), billingAdapter.getBillingList(), "billing_harian.csv");
        });


        return view;
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        billingAdapter = new BillingAdapter(billingList);
        recyclerView.setAdapter(billingAdapter);
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(getContext(),
                (view, year, month, dayOfMonth) -> {
                    // ✅ Format disamakan dengan format simpan di Billing
                    String tanggalDipilih = String.format(Locale.getDefault(), "%02d-%02d-%04d", dayOfMonth, month + 1, year);

                    billingList = databaseHelper.getBillingByDate(tanggalDipilih);
                    setupRecyclerView();
                    hitungTotalPendapatan();
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        ).show();
    }


    private void hitungTotalPendapatan() {
        int total = 0;
        for (Billing billing : billingList) {
            try {
                total += billing.getHarga();

            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        //NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        java.text.DecimalFormat formatRupiah = new java.text.DecimalFormat("Rp#,###");
        tvTotal.setText("Pendapatan Hari Ini: " + formatRupiah.format(total));

    }
}
