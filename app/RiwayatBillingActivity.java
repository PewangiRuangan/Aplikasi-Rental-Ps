package com.example.rentalps;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class RiwayatBillingActivity extends AppCompatActivity {
    private RecyclerView recyclerBilling;
    private BillingAdapter billingAdapter;
    private List<Billing> billingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_billing);

        recyclerBilling = findViewById(R.id.recyclerBilling);
        recyclerBilling.setLayoutManager(new LinearLayoutManager(this));

        // Contoh data dummy (ganti dengan database/SharedPreferences nanti)
        billingList = new ArrayList<>();
        billingList.add(new Billing("TV1", "PS4", "1 Jam", "10k", "6 April 2025 - 10:00"));
        billingList.add(new Billing("TV2", "PS5", "2 Jam", "20k", "6 April 2025 - 11:00"));

        billingAdapter = new BillingAdapter(billingList);
        recyclerBilling.setAdapter(billingAdapter);
    }
}
