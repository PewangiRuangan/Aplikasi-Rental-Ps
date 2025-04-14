package com.example.rentalps.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.rentalps.R;
import com.example.rentalps.adapters.PendapatanPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class PendapatanFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;

    public PendapatanFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pendapatan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        tabLayout = view.findViewById(R.id.tabPendapatan);
        viewPager = view.findViewById(R.id.viewPagerPendapatan);

        // Pasang adapter ViewPager
        PendapatanPagerAdapter adapter = new PendapatanPagerAdapter(requireActivity());
        viewPager.setAdapter(adapter);

        // Hubungkan TabLayout dengan ViewPager2
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            if (position == 0) {
                tab.setText("HARIAN");
            } else {
                tab.setText("BULANAN");
            }
        }).attach();
    }
}
