package com.example.progettooop.ui.user;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.progettooop.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

public class UserFragment extends Fragment implements View.OnClickListener {

    private TextView username,numero,indirizzo,email;
    private ProgressBar user_progressbar;
    private UserViewModel userViewModel;
    private ImageView userImage;
    private TextView monday,tuesday,wednesday,thursday,friday,saturday,sunday;
    public String imageUrl;
    private RatingBar rate;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    StorageReference storageReference;


    @SuppressLint("ClickableViewAccessibility")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        View root = inflater.inflate(R.layout.fragment_user, container, false);
        root.findViewById(R.id.modify_button).setOnClickListener(this);
        root.findViewById(R.id.fabopenreview).setOnClickListener(this);

        username = root.findViewById(R.id.user_username);
        numero =root.findViewById(R.id.User_Phone_Number);
        indirizzo = root.findViewById(R.id.User_address);
        email = root.findViewById(R.id.User_email);
        userImage = root.findViewById(R.id.User_iv);
        user_progressbar =root.findViewById(R.id.progressbar_user);
        rate = (RatingBar) root.findViewById(R.id.rating_bar);

        monday = root.findViewById(R.id.monday_hrs);
        tuesday = root.findViewById(R.id.tuesday_hrs);
        thursday = root.findViewById(R.id.thursday_hrs);
        wednesday = root.findViewById(R.id.wednesday_hrs);
        friday = root.findViewById(R.id.friday_hrs);
        saturday = root.findViewById(R.id.saturday_hrs);
        sunday = root.findViewById(R.id.sunday_hrs);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        getInfo();
        fillratingbar();
        return root;
    }




    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.modify_button) {
            Intent intent = new Intent(getContext(), ModifyUserInfo.class);
            intent.putExtra("type", "user");
            startActivity(intent);
        }
        if(view.getId()==R.id.fabopenreview){
            Intent intent1 = new Intent(getContext(), reviewleftActivity.class);
            intent1.putExtra("type", "user");
            startActivity(intent1);
        }
    }



    private void getInfo(){
        user_progressbar.setVisibility(View.VISIBLE);
        DocumentReference doc = db.
                collection("utenti")
                .document(Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
        doc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    username.setText(documentSnapshot.getString("username"));
                    email.setText(documentSnapshot.getString("email"));
                    indirizzo.setText(documentSnapshot.getString("address"));
                    numero.setText(documentSnapshot.getString("phone"));
                    monday.setText(documentSnapshot.getString("monday"));
                    tuesday.setText(documentSnapshot.getString("tuesday"));
                    wednesday.setText(documentSnapshot.getString("wednesday"));
                    thursday.setText(documentSnapshot.getString("thursday"));
                    friday.setText(documentSnapshot.getString("friday"));
                    saturday.setText(documentSnapshot.getString("saturday"));
                    sunday.setText(documentSnapshot.getString("sunday"));

                    imageUrl = documentSnapshot.getString("PhotoID");
                    /*Glide.with(UserFragment.this)
                            .load(imageUrl)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(userImage);*/

                    if( imageUrl != null){
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
                    });}


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
        db.collection("recensione").whereEqualTo("UserReviewedId",FirebaseAuth.getInstance().getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot querySnapshot) {
                        float count= (float) 0.0;
                        float sum = (float) 0.0;
                        for(QueryDocumentSnapshot doc:querySnapshot){
                            count+=1;
                            sum += (float) doc.get("RatingReview",Float.class);
                        }
                        rate.setRating((float) (sum/count));
                    }
                });


        user_progressbar.setVisibility(View.GONE);
    }

    private void fillratingbar(){

    }
}

