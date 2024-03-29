package com.example.progettooop.ui.dashboard;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.annotation.NonNull;

import com.example.progettooop.ui.dashboard.watchlist.FavouriteAdvertisement;
import com.example.progettooop.ui.dashboard.yourproducts.ActiveAdvertisement;

public class TabLayoutAdapter extends FragmentStateAdapter {
    public TabLayoutAdapter(Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = new ActiveAdvertisement();
                Bundle args = new Bundle();
                // Our object is just an integer :-P
                args.putInt(ActiveAdvertisement.ARG_OBJECT, position + 1);
                fragment.setArguments(args);
                return fragment;
            case 1:
                fragment = new FavouriteAdvertisement();
                Bundle args1 = new Bundle();
                // Our object is just an integer :-P
                args1.putInt(FavouriteAdvertisement.ARG_OBJECT, position + 1);
                fragment.setArguments(args1);
                return fragment;
            default:
                return new ActiveAdvertisement();
        }
    }


    @Override
    public int getItemCount() {
        return 2;
    }
}
