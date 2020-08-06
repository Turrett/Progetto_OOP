package com.example.progettooop.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.example.progettooop.ui.dashboard.*;

import com.example.progettooop.R;
import com.example.progettooop.ui.home.HomeViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class DashboardFragment extends Fragment {

    // When requested, this adapter returns a DemoObjectFragment,
    // representing an object in the collection.
    DashAdapter dashadapter;
    ViewPager2 viewPager;
    DashboardViewModel dashviewodel;

    @Nullable
    @Override
    //crea la view
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dashviewodel = new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        dashadapter = new DashAdapter(this);
        viewPager = view.findViewById(R.id.pager);
        viewPager.setAdapter(dashadapter);

       TabLayout tabLayout = view.findViewById(R.id.tab_layout);
       /* tabs.setupWithViewPager(viewPager);
        tabs.getTabAt(0).setIcon(R.drawable.icon1);
        tabs.getTabAt(1).setIcon(R.drawable.icon2);
        tabs.getTabAt(0).setText(getResources().getText(R.string.tab1));
        tabs.getTabAt(1).setText(getResources().getText(R.string.tab2));*/


        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> tab.setText( "Object "+ (position + 1))).attach();
    }
}



