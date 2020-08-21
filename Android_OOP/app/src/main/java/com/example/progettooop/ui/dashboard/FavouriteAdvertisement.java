package com.example.progettooop.ui.dashboard;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.progettooop.R;
import com.example.progettooop.ui.Objects.Product;
import com.example.progettooop.ui.recycleViewAdapters.HomeAndSearchCardAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
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
    public RecyclerView recyclerView ;
    private ArrayList<Product> products;


    // creates the view and calls the function favouriteProductsToRecycleview to load the cards
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_favourite, container, false);

        return root;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        products  = new ArrayList<Product>();
        favouriteProductsToRecycleview(view);
    }

    private void favouriteProductsToRecycleview(View v) {

        FirebaseFirestore db;
        FirebaseAuth auth;
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        //first query that gets every favourite item of the user

        Task<QuerySnapshot> documenti = db.collection("watchlist")
                .whereEqualTo("User", auth.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                //second query that uses the favourite items to retirieve their data

                                FirebaseFirestore db2 = FirebaseFirestore.getInstance();
                                DocumentReference doc = db2.collection("annuncio")
                                        .document(Objects.requireNonNull(document.getString("Product")));
                                doc.get()
                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                DocumentSnapshot doc2;
                                                if (task.getResult().exists()) {
                                                    doc2 = task.getResult();
                                                } else {
                                                    return;
                                                }
                                                doc2 = task.getResult();
                                                products.add(new Product(doc2.getString("name"),
                                                        doc2.getString("quantity"),
                                                        doc2.getString("expiration"),
                                                        doc2.getString("UId"),
                                                        doc2.getId()));
                                            }
                                            //until here prod fills correctly , but when it goes to the recycle view it's empty??? TODO
                                        });
                            }

                                recyclerView = v.findViewById(R.id.result_favourite);
                                HomeAndSearchCardAdapter homeAndSearchCardAdapter = new HomeAndSearchCardAdapter(v.getContext(), products);
                                recyclerView.setAdapter(homeAndSearchCardAdapter);
                                recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
                        }
                    }
                });

    }
}
