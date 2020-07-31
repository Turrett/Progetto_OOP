package com.example.progettooop.ui.addadv;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.progettooop.R;
import com.example.progettooop.ui.dashboard.DashboardViewModel;
import com.example.progettooop.ui.home.HomeFragment;
import com.example.progettooop.ui.user.UserViewModel;

public class AddAdvFragment extends Fragment {
    private AddAdvViewModel addadvViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        addadvViewModel = new ViewModelProvider(this).get(AddAdvViewModel.class);;
        View root = inflater.inflate(R.layout.fragment_addadv, container, false);
        return root;
    }

   public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*devo lavorare sul tasto che si crea nella appbarconfigurator
        ma non so minimamente come si faccia
        google aiutami tu*/


        /*view.findViewById(R.id.floatingActionButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(AddAdvFragment.this)
                        .navigate(R.id.homeaddadv);
            }
        });*/
    }
}
