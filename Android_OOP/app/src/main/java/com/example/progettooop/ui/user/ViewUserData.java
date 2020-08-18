package com.example.progettooop.ui.user;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.progettooop.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ViewUserData extends AppCompatActivity {
    private TextView username,numero,indirizzo,email;
    private ProgressBar user_progressbar;
    private UserViewModel userViewModel;
    private ImageView userImage;
    public String imageUrl;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user_data);

        username = findViewById(R.id.user_username_alt);
        numero =findViewById(R.id.User_Phone_Number_alt);
        indirizzo = findViewById(R.id.User_address_alt);
        email = findViewById(R.id.User_email_alt);
        userImage = findViewById(R.id.User_iv_alt);
        user_progressbar =findViewById(R.id.progressbar_user_alt);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        getInfo();

    }

    private void getInfo(){
        Bundle extras = getIntent().getExtras();
        user_progressbar.setVisibility(View.VISIBLE);
        DocumentReference doc = db.
                collection("utenti")
                .document(extras.getString("UserId"));
        doc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    username.setText(documentSnapshot.getString("username"));
                    email.setText(documentSnapshot.getString("email"));
                    indirizzo.setText(documentSnapshot.getString("address"));
                    numero.setText(documentSnapshot.getString("phone"));

                    imageUrl = documentSnapshot.getString("PhotoID");

                    assert imageUrl != null;
                    storageReference.child(imageUrl).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Glide.with(ViewUserData.this).load(uri.toString()).diskCacheStrategy(DiskCacheStrategy.ALL).into(userImage);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(getApplicationContext(),"non sono riuscito a caricare l'immagine",Toast.LENGTH_SHORT).show();
                        }
                    });


                }
                else {
                    Toast.makeText(getApplicationContext(),"empty document",Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @SuppressLint("ShowToast")
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Fail Loading Data",Toast.LENGTH_SHORT);

            }

        });



        user_progressbar.setVisibility(View.GONE);
    }
}