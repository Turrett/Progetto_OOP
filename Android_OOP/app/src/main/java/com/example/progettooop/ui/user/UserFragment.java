package com.example.progettooop.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.progettooop.R;


public class UserFragment extends Fragment implements View.OnClickListener {

    private UserViewModel userViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        View root = inflater.inflate(R.layout.fragment_user, container, false);
        root.findViewById(R.id.modify_button).setOnClickListener(this);
        return root;
    }



    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.modify_button);
        startActivity(new Intent(getContext(), modify_user_information.class));
    }
}

