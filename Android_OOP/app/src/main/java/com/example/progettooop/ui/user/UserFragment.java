package com.example.progettooop.ui.user;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.progettooop.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.*;
import com.google.firebase.auth.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class UserFragment extends Fragment implements View.OnClickListener {

    private TextView username,numero,indirizzo,email;
    private UserViewModel userViewModel;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        View root = inflater.inflate(R.layout.fragment_user, container, false);
        root.findViewById(R.id.modify_button).setOnClickListener(this);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        username = root.findViewById(R.id.user_username);
        numero =root.findViewById(R.id.User_Phone_Number);
        indirizzo = root.findViewById(R.id.User_address);
        email = root.findViewById(R.id.User_email);
        getInfo();
        return root;
    }




    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.modify_button);
        startActivity(new Intent(getContext(), ModifyUserInfo.class));
    }

    private void getInfo(){
        DocumentReference doc = db.collection("utenti")
                .document(Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
        doc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    username.setText(documentSnapshot.getString("username"));
                    email.setText(documentSnapshot.getString("email"));
                    indirizzo.setText(documentSnapshot.getString("address"));
                    numero.setText(documentSnapshot.getString("phone"));
                }
                else {
                    Toast.makeText(getContext(),"empty document",Toast.LENGTH_SHORT);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @SuppressLint("ShowToast")
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(),"Fail Loading Data",Toast.LENGTH_SHORT);

            }
        });
    }
}

