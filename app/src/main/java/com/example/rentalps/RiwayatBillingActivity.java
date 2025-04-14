package com.example.rentalps;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class RiwayatBillingActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private RiwayatPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_billing);

        tabLayout = findViewById(R.id.tabLayoutBilling);
        viewPager = findViewById(R.id.viewPagerBilling);
        pagerAdapter = new RiwayatPagerAdapter(this);

        viewPager.setAdapter(pagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    switch (position) {
                        case 0:
                            tab.setText("Harian");
                            break;
                        case 1:
                            tab.setText("Bulanan");
                            break;

                    }
                }).attach();
    }
}
