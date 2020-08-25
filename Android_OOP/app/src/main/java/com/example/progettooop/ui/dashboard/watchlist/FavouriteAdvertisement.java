package com.example.progettooop.ui.dashboard.watchlist;

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
import com.example.progettooop.ui.Objects.wishedProd;
import com.example.progettooop.ui.recycleViewAdapters.WatchlistCardAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/*per questa sezione è sufficiente usare la classe product di advertisement perché
richiedono esattamente gli stessi attributi del search e della home.
Bisogna solo creare la tabella che metta in relazione gli user con gli annunci.
 */
public class FavouriteAdvertisement extends Fragment {
    public static final String ARG_OBJECT = "object2";
    public RecyclerView recyclerView;
    private ArrayList<wishedProd> products;

    private RecyclerView.Adapter adapter;
    private LinearLayoutManager linearLayoutManager;

    // creates the view and calls the function favouriteProductsToRecycleview to load the cards
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_favourite, container, false);
        recyclerView = root.findViewById(R.id.result_favourite);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        products = new ArrayList<wishedProd>();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseFirestore.getInstance()
                .collection("watchlist")
                .whereEqualTo("UserAddingId", auth.getUid())
                .orderBy("state")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (value!=null)
                        for (QueryDocumentSnapshot document : value) {
                            products.add(new wishedProd(document.getString("name"),
                                    document.getString("quantity"),
                                    document.getString("expire"),
                                    document.getString("UserPostingId"),
                                    document.getString("ProductId"),
                                    document.getString("UserAddingId"),
                                    document.getId(),
                                    document.getString("state")));
                        }
                        adapter = new WatchlistCardAdapter(getContext(), products);
                        recyclerView.setAdapter(adapter);
                    }
                });


        //favouriteProductsToRecycleview(root,products);

        //fillRecycleView(root);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}


