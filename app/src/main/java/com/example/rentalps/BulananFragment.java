package com.example.rentalps;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.rentalps.adapters.BillingAdapter;
import com.example.rentalps.database.BillingDatabaseHelper;
import com.example.rentalps.utils.ExportUtils;
import android.widget.NumberPicker;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class BulananFragment extends Fragment {

    private RecyclerView recyclerView;
    private BillingAdapter billingAdapter;
    private BillingDatabaseHelper databaseHelper;
    private TextView tvTotal;
    private List<Billing> billingList = new ArrayList<>();
    private int selectedMonth;
    private int selectedYear;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bulanan, container, false);

        recyclerView = view.findViewById(R.id.rvBulanan);
        Button btnPilihTanggal = view.findViewById(R.id.btnPilihBulan);
        Button btnExport = view.findViewById(R.id.btnExportCSV);
        tvTotal = view.findViewById(R.id.tvTotalBulanan);

        databaseHelper = new BillingDatabaseHelper(getContext());
        Calendar calendar = Calendar.getInstance();
        selectedMonth = calendar.get(Calendar.MONTH) + 1;
        selectedYear = calendar.get(Calendar.YEAR);

        // Load default bulan ini
        loadDataBulanan(selectedMonth, selectedYear);

        btnPilihTanggal.setOnClickListener(v -> showMonthYearPicker());
        btnExport.setOnClickListener(v -> ExportUtils.exportToCSV(getContext(), billingList, "billing_bulanan.csv"));

        return view;
    }

    private void loadDataBulanan(int month, int year) {
        billingList = databaseHelper.getBillingByMonth(month, year);
        billingAdapter = new BillingAdapter(billingList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(billingAdapter);
        hitungTotalPendapatan();
    }

    private void showMonthYearPicker() {
        final Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);

        // Layout untuk dialog custom
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_month_year_picker, null);
        final NumberPicker monthPicker = dialogView.findViewById(R.id.pickerMonth);
        final NumberPicker yearPicker = dialogView.findViewById(R.id.pickerYear);

        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);
        monthPicker.setValue(currentMonth + 1);

        yearPicker.setMinValue(2022);
        yearPicker.setMaxValue(currentYear + 10);
        yearPicker.setValue(currentYear);

        new androidx.appcompat.app.AlertDialog.Builder(getContext())
                .setTitle("Pilih Bulan & Tahun")
                .setView(dialogView)
                .setPositiveButton("OK", (dialog, which) -> {
                    selectedMonth = monthPicker.getValue();
                    selectedYear = yearPicker.getValue();
                    loadDataBulanan(selectedMonth, selectedYear);
                })
                .setNegativeButton("Batal", null)
                .show();
    }


    private void hitungTotalPendapatan() {
        int total = 0;
        for (Billing billing : billingList) {
            total += billing.getHarga();
        }

        java.text.DecimalFormat formatRupiah = new java.text.DecimalFormat("Rp#,###");
        tvTotal.setText("Pendapatan Hari Ini: " + formatRupiah.format(total));
    }
}
