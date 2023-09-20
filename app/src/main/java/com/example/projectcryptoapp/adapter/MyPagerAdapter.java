package com.example.projectcryptoapp.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.projectcryptoapp.R;
import com.example.projectcryptoapp.gragment.TopGainFragment;
import com.example.projectcryptoapp.gragment.TopLossGainFragment;

public class MyPagerAdapter extends FragmentPagerAdapter {
    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new TopGainFragment();
            case 1:
                return new TopLossGainFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2; // Number of fragments
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Top Gain";
            case 1:
                return " Top Loss";
            default:
                return null;
        }

    }

}
