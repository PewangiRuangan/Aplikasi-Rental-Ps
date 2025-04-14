package com.example.rentalps;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.rentalps.fragments.CatatanFragment;
import com.example.rentalps.fragments.PendapatanFragment;

import com.example.rentalps.fragments.HomeFragment;
import com.example.rentalps.fragments.PengaturanFragment;
import com.example.rentalps.models.TV;
import com.example.rentalps.adapters.TVAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements HomeFragment.TVStartListener {

    BottomNavigationView bottomNavigationView;
    private TVAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // Tampilkan HomeFragment pertama kali
        loadFragment(new HomeFragment(this)); // kirim listener TVStartListener

        // Listener navigasi bawah
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.navigation_home) {
                loadFragment(new HomeFragment(this));
                return true;
            } else if (itemId == R.id.navigation_riwayat) {
                startActivity(new Intent(MainActivity.this, RiwayatBillingActivity.class));
                return true;

            } else if (itemId == R.id.navigation_pendapatan) {
                    loadFragment(new PendapatanFragment());
                    return true;



            }  else if (itemId == R.id.navigation_catatan) {
                    loadFragment(new CatatanFragment());
                    return true;


            } else if (itemId == R.id.navigation_pengaturan) {
                loadFragment(new PengaturanFragment());
                return true;
            }

            return false;
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }

    // ✅ Dipanggil dari HomeFragment ketika user klik tombol Konfirmasi
    @Override
    public void onStartTVTimer(TV tv, int posisi, TVAdapter adapter) {
        this.adapter = adapter;
        if (adapter != null) {
            adapter.startTimer(tv, posisi);
        }
    }
}
