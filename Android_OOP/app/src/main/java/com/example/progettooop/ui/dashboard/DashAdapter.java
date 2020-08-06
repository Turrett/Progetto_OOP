package com.example.progettooop.ui.dashboard;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.annotation.NonNull;

import com.google.android.material.tabs.TabLayoutMediator;

//l'adapter cambia fragment di riferimento del tab in base alla posizione in cui scorro col dito

public class DashAdapter extends FragmentStateAdapter {
    public DashAdapter(Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = new ActiveAdvFragment();
                Bundle args = new Bundle();
                // Our object is just an integer :-P
                args.putInt(ActiveAdvFragment.ARG_OBJECT, position + 1);
                fragment.setArguments(args);
                return fragment;
            case 1:
                fragment = new FavouriteAdvFragment();
                Bundle args1 = new Bundle();
                // Our object is just an integer :-P
                args1.putInt(FavouriteAdvFragment.ARG_OBJECT, position + 1);
                fragment.setArguments(args1);
                return fragment;
            default:
                return new ActiveAdvFragment();
        }
    }


    @Override
    public int getItemCount() {
        return 2;
    }
}
