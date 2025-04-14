package com.example.rentalps;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class RiwayatPagerAdapter extends FragmentStateAdapter {

    public RiwayatPagerAdapter(@NonNull FragmentActivity fa) {
        super(fa);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new HarianFragment();
            case 1:
                return new BulananFragment();

            default:
                return new HarianFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
