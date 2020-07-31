package com.example.progettooop.ui.dashboard;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DashAdapter extends FragmentStateAdapter {
    public DashAdapter(Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Return a NEW fragment instance in createFragment(int)
        Fragment fragment = new ActiveAdvFragment();
        Bundle args = new Bundle();
        // Our object is just an integer :-P
        args.putInt(ActiveAdvFragment.ARG_OBJECT, position + 1);
        fragment.setArguments(args);
        return fragment;
    }

    public Fragment createFragment1(int position) {
        // Return a NEW fragment instance in createFragment(int)
        Fragment fragment = new FavouriteAdvFragment();
        Bundle args1 = new Bundle();
        // Our object is just an integer :-P
        args1.putInt(FavouriteAdvFragment.ARG_OBJECT, position + 2);
        fragment.setArguments(args1);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
