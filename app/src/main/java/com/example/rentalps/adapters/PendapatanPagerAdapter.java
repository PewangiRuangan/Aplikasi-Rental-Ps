package com.example.rentalps.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.rentalps.fragments.PendapatanHarianFragment;
import com.example.rentalps.fragments.PendapatanBulananFragment;

public class PendapatanPagerAdapter extends FragmentStateAdapter {

    public PendapatanPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new PendapatanHarianFragment();
        } else {
            return new PendapatanBulananFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2; // HARIAN dan BULANAN
    }
}
