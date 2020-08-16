package com.example.progettooop.ui.dashboard;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.progettooop.R;
import com.example.progettooop.ui.Objects.Product;
import com.example.progettooop.ui.recycleViewAdapters.MyAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/*per questa sezione è sufficiente usare la classe product di advertisement perché
richiedono esattamente gli stessi attributi del search e della home.
Bisogna solo creare la tabella che metta in relazione gli user con gli annunci.
 */
public class FavouriteAdvertisement extends Fragment {
    public static final String ARG_OBJECT = "object2";

    RecyclerView recyclerView ;
    FirebaseFirestore db;
    FirebaseAuth auth;


    // creates the view and calls the function favouriteProductsToRecycleview to load the cards
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_favourite, container, false);

        ArrayList<Product> products  = new ArrayList<Product>();
        auth =FirebaseAuth.getInstance();

       favouriteProductsToRecycleview(root,products);
        return root;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }

    private void favouriteProductsToRecycleview(View v, ArrayList<Product> prod) {
        db = FirebaseFirestore.getInstance();

        //first query that gets every favourite item of the user

        db.collection("watchlist")
                .whereEqualTo("User",auth.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()){

                                //second query that uses the favourite items to retirieve their data

                                FirebaseFirestore db2 = FirebaseFirestore.getInstance();
                                db2.collection("annuncio")
                                        .document(document.getId())
                                        .get()
                                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                prod.add(new Product(documentSnapshot.getString("name"),
                                                        documentSnapshot.getString("quantity"),
                                                        documentSnapshot.getString("expiration"),
                                                        documentSnapshot.getString("UId"),
                                                        documentSnapshot.getId()));
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(v.getContext(),"second query failed",Toast.LENGTH_SHORT).show();
                                    }
                                });


                            }
                            recyclerView = v.findViewById(R.id.result_list);
                            MyAdapter myAdapter = new MyAdapter(v.getContext(),prod);
                            recyclerView.setAdapter(myAdapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
