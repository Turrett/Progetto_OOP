package com.example.progettooop.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.progettooop.R;
import com.example.progettooop.ui.advertisement.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class SearchFragment extends Fragment implements View.OnClickListener {
    private RecyclerView recyclerView;
    private SearchViewModel searchViewModel;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private EditText mSearchField;
    private ImageButton mSearchBtn;
    ArrayList <Product> products  = new ArrayList<Product>();

    public SearchFragment() {

    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        View root = inflater.inflate(R.layout.fragment_search, container, false);

        mSearchField = (EditText) root.findViewById(R.id.search_field);
        mSearchBtn = (ImageButton) root.findViewById(R.id.search_btn);

        searchProductsToRecycleview(root,products,"Uova");

        return root;
    }


    private void searchProductsToRecycleview(View v, ArrayList<Product> prod, String search) {
        db = FirebaseFirestore.getInstance();
        db.collection("annuncio")
                .whereEqualTo("search_name", search.toLowerCase())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {

                                prod.add(new Product(document.getString("name"),
                                        document.getString("expiration"),
                                        document.getString("quantity"),
                                        document.getString("UId")));
                            }
                            recyclerView = v.findViewById(R.id.result_list);
                            MyAdapter myAdapter = new MyAdapter(v.getContext(),prod);
                            recyclerView.setAdapter(myAdapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));

                        } else {
                            Toast.makeText(getContext(), "task not successfull", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }


    @Override
    public void onClick(View view) {
    }
}
