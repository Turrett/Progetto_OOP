package com.example.progettooop.ui.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.progettooop.R;
import com.example.progettooop.ui.Objects.Product;
import com.example.progettooop.ui.recycleViewAdapters.*;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RecyclerView recyclerView;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private TextView txtloggeduser;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        ArrayList<Product> products  = new ArrayList<Product>();
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        txtloggeduser =(TextView) root.findViewById(R.id.lblnomeutentelog);
        searchProductsToRecycleview(root,products);
        getInfo();
        return root;

    }


    private void searchProductsToRecycleview (View v, ArrayList < Product > prod){
        db = FirebaseFirestore.getInstance();
        db.collection("annuncio")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {

                                prod.add(new Product(document.getString("name"),
                                        document.getString("quantity"),
                                        document.getString("expiration"),
                                        document.getString("UId"),
                                        document.getId()));

                            }
                            recyclerView = v.findViewById(R.id.list_home);
                            MyAdapter myAdapter = new MyAdapter(v.getContext(), prod);
                            recyclerView.setAdapter(myAdapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));

                        } else {
                            Toast.makeText(getContext(), "task not successfull", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void getInfo(){
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        DocumentReference doc = db.collection("utenti")
                .document(Objects.requireNonNull(auth.getCurrentUser()).getUid());
        doc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    txtloggeduser.setText(documentSnapshot.getString("username"));
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

