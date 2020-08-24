package com.example.progettooop.ui.advertisement;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.progettooop.R;
import com.example.progettooop.ui.Objects.Product;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static android.content.ContentValues.TAG;


public class AddAdvFragment extends Fragment implements View.OnClickListener {
    private EditText name,quantity,expiration;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private FirebaseFirestore db;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        AddAdvViewModel addadvViewModel = new ViewModelProvider(this).get(AddAdvViewModel.class);
        View root = inflater.inflate(R.layout.fragment_addadv,null);
        name = root.findViewById(R.id.addv_name);
        quantity = root.findViewById(R.id.addv_qty);
        expiration = root.findViewById(R.id.addv_expiration);
        Button saveButton = root.findViewById(R.id.addv_save);
        progressBar = root.findViewById(R.id.addv_progressbar);
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addData();
            }
        });

    return root;
    }

    private void addData () {
        progressBar.setVisibility(View.VISIBLE);
        Product product = new Product(name.getText().toString(), quantity.getText().toString(), expiration.getText().toString(), Objects.requireNonNull(auth.getCurrentUser()).getUid(), null,"posted");
        if (product.getName().isEmpty()) {
            name.requestFocus();
            name.setError("name required");
            return;
        }
        if (product.getQuantity().isEmpty()) {
            quantity.requestFocus();
            quantity.setError("quantity required");
            return;
        }

        if (product.getExpiration().isEmpty()) {
            expiration.requestFocus();
            expiration.setError("name required");
            return;
        }


        Map<String, Object> user = new HashMap<>();
        user.put("UId", product.getUserId());
        user.put("name", product.getName());
        user.put("quantity", product.getQuantity());
        user.put("expiration", product.getExpiration());
        user.put("search_name", product.getName().toLowerCase());
        user.put("state",product.getState());


        db.collection("annuncio")
                .document()
                .set(user, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "Saved!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "DocumentSnapshot");
                        name.setText("");
                        quantity.setText("");
                        expiration.setText("");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }


    @Override
    public void onClick(View view) {

    }
}
