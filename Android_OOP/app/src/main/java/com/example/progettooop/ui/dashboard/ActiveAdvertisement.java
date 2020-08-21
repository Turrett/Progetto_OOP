package com.example.progettooop.ui.dashboard;

import android.content.Intent;
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
import com.example.progettooop.ui.Objects.DashProduct;
import com.example.progettooop.ui.advertisement.AddAdvFragment;
import com.example.progettooop.ui.recycleViewAdapters.PostedProductsCardAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ActiveAdvertisement extends Fragment implements View.OnClickListener{

    public static final String ARG_OBJECT = "object1";
    private FirebaseAuth mauth ;
    private FirebaseFirestore db;
    private RecyclerView recyclerView;
    private FloatingActionButton fab;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mauth = FirebaseAuth.getInstance();
        View root =inflater.inflate(R.layout.fragment_activeadv, container, false);

        fab = (FloatingActionButton) root.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(this);

        ArrayList<DashProduct> prodotti = new ArrayList<DashProduct>();
        searchProductsToRecycleview(root,prodotti);
        return root;
    }

    private void searchProductsToRecycleview(View v, ArrayList<DashProduct> prod) {
        this.db = FirebaseFirestore.getInstance();
        this.db.collection("annuncio")
                .whereEqualTo("UId",mauth.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {

                                prod.add(new DashProduct(document.getString("name"),
                                        document.getString("quantity"),
                                        document.getString("expiration"),
                                        document.getId()));

                            }
                            recyclerView = v.findViewById(R.id.result_annunci);
                            PostedProductsCardAdapter myAdapter = new PostedProductsCardAdapter(v.getContext(), prod);
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

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.floatingActionButton:
               addadv();
               break;

        }
    }


    public void addadv(){
        Intent intent = new Intent(getActivity(), AddAdvFragment.class);
        startActivity(intent);
    }
}
