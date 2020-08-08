package com.example.progettooop.ui.user;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.progettooop.R;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.firestore.*;
import com.google.firebase.auth.*;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.Objects;


public class UserFragment extends Fragment implements View.OnClickListener {

    private TextView username,numero,indirizzo,email;
    private ProgressBar user_progressbar;
    private UserViewModel userViewModel;
    private ImageView userImage;
    public String imageUrl;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    StorageReference storageReference;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        View root = inflater.inflate(R.layout.fragment_user, container, false);
        root.findViewById(R.id.modify_button).setOnClickListener(this);

        username = root.findViewById(R.id.user_username);
        numero =root.findViewById(R.id.User_Phone_Number);
        indirizzo = root.findViewById(R.id.User_address);
        email = root.findViewById(R.id.User_email);
        userImage = root.findViewById(R.id.User_iv);
        user_progressbar =root.findViewById(R.id.progressbar_user);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        getInfo();
        return root;
    }




    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.modify_button);
        startActivity(new Intent(getContext(), ModifyUserInfo.class));
    }

    private void getInfo(){
        user_progressbar.setVisibility(View.VISIBLE);
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

                    imageUrl = documentSnapshot.getString("PhotoID");
                    /*Glide.with(UserFragment.this)
                            .load(imageUrl)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(userImage);*/

                    assert imageUrl != null;
                    storageReference.child(imageUrl).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Glide.with(UserFragment.this).load(uri.toString()).diskCacheStrategy(DiskCacheStrategy.ALL).into(userImage);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(getContext(),"non ci sono riuscito",Toast.LENGTH_SHORT).show();
                        }
                    });


                }
                else {
                    Toast.makeText(getContext(),"empty document",Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @SuppressLint("ShowToast")
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(),"Fail Loading Data",Toast.LENGTH_SHORT);

            }

        });



        user_progressbar.setVisibility(View.GONE);
    }
}

