package com.example.rentalps;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentalps.adapters.BillingAdapter;

import java.text.SimpleDateFormat;
import java.util.*;

public class BillingActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TextView tvTotalPendapatan, tvTanggal;
    private BillingAdapter billingAdapter;
    private List<Billing> billingList = new ArrayList<>();
    private SharedPrefHelper sharedPrefHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing);

        recyclerView = findViewById(R.id.recyclerViewBilling);
        tvTotalPendapatan = findViewById(R.id.tvTotalPendapatan);
        tvTanggal = findViewById(R.id.tvTanggal);

        sharedPrefHelper = new SharedPrefHelper(this);
        billingList = sharedPrefHelper.getBillingList();

        String today = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        tvTanggal.setText("Tanggal: " + today);

        List<Billing> hariIniList = new ArrayList<>();
        int totalPendapatan = 0;
        for (Billing data : billingList) {
            if (data.getWaktu().equals(today)) {
                hariIniList.add(data);
                try {
                    totalPendapatan += data.getHarga();
                } catch (NumberFormatException e) {
                    // Handle jika harga bukan angka valid
                }
            }
        }

        billingAdapter = new BillingAdapter(hariIniList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(billingAdapter);

        tvTotalPendapatan.setText("Total Pendapatan: Rp " + totalPendapatan);
    }
}
