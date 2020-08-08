package com.example.progettooop.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

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
import com.google.firebase.firestore.Query;
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
    private ArrayList < Product> products ;
    private Product product;

    public SearchFragment() {
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        products = new ArrayList<Product>();
        product =new Product (null,null,null,null);

        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);

        View root = inflater.inflate(R.layout.fragment_search, container, false);

        mSearchField = (EditText) root.findViewById(R.id.search_field);
        mSearchBtn = (ImageButton) root.findViewById(R.id.search_btn);
        db=FirebaseFirestore.getInstance();

// Create a query against the collection.
        Query query = db.collection("annuncio").whereEqualTo("name", "latte uht parzialmente scremato 1litro");
            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            product.setName(document.get("name").toString());
                            product.setExpiration(document.get("quantity").toString());
                            product.setQuantity(document.get("expiration").toString());
                            product.setUserId(document.get("UId").toString());
                            products.add(product);
                        }
                    } else {

                        return;

                    }
                }
            });


            recyclerView = root.findViewById(R.id.result_list);
            MyAdapter myAdapter = new MyAdapter(root.getContext(), products);
            recyclerView.setAdapter(myAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
            return root;
        }

        @Override
        public void onClick (View view){

        }

}
