package com.example.progettooop.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.progettooop.R;
import com.google.firebase.firestore.*;
import com.google.firebase.auth.*;

import java.util.Objects;


public class UserFragment extends Fragment implements View.OnClickListener {

    private TextView username;
    private UserViewModel userViewModel;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        View root = inflater.inflate(R.layout.fragment_user, container, false);
        root.findViewById(R.id.modify_button).setOnClickListener(this);
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        username.findViewById(R.id.user_username);


    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.modify_button);
        startActivity(new Intent(getContext(), ModifyUserInfo.class));
    }

    private void getInfo(){
        DocumentReference doc = db.collection("utenti")
                .document(Objects.requireNonNull(mAuth.getCurrentUser()).getUid())
                .
        username.setText();
    }
}

