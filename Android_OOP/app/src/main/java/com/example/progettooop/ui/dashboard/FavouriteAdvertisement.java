package com.example.progettooop.ui.dashboard;

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
import com.example.progettooop.ui.recycleViewAdapters.HomeAndSearchCardAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

/*per questa sezione è sufficiente usare la classe product di advertisement perché
richiedono esattamente gli stessi attributi del search e della home.
Bisogna solo creare la tabella che metta in relazione gli user con gli annunci.
 */
public class FavouriteAdvertisement extends Fragment {
    public static final String ARG_OBJECT = "object2";
    public RecyclerView recyclerView;
    private ArrayList<Product> products;
    private QuerySnapshot querySnapshot;


    // creates the view and calls the function favouriteProductsToRecycleview to load the cards
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_favourite, container, false);
        recyclerView = root.findViewById(R.id.result_favourite);
        products = new ArrayList<Product>();

        favouriteProductsToRecycleview();

        fillRecycleView(root);

        return root;
    }

    private void favouriteProductsToRecycleview() {

        FirebaseFirestore db;
        FirebaseAuth auth;
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        //first query that gets every favourite item of the user

                db.collection("watchlist")
                .whereEqualTo("User", auth.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            secondDb(Objects.requireNonNull(task.getResult()));
                        }
                    }
                });

    }

    private void secondDb(QuerySnapshot querySnapshot) {
        for (QueryDocumentSnapshot document : querySnapshot) {

            FirebaseFirestore db2 = FirebaseFirestore.getInstance();
            db2.collection("annuncio")
                    .document(Objects.requireNonNull(document.getString("Product")))
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            DocumentSnapshot doc2;
                            if (task.getResult().exists()) {
                                doc2 = task.getResult();
                            } else {
                                return;
                            }
                            products.add(new Product(document.getString("name"),
                                    document.getString("quantity"),
                                    document.getString("expiration"),
                                    document.getString("UId"),
                                    document.getId()));
                        }
                        //until here prod fills correctly , but when it goes to the recycle view it's empty??? TODO
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(),e.toString(),Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void fillRecycleView(View root){
        HomeAndSearchCardAdapter homeAndSearchCardAdapter = new HomeAndSearchCardAdapter(root.getContext(), products);
        recyclerView.setAdapter(homeAndSearchCardAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
    }
}


