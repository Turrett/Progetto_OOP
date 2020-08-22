package com.example.progettooop.ui.dashboard;

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
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
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
    public RecyclerView recyclerView;
    private ArrayList<Product> products;
    private QuerySnapshot querySnapshot;
    private CollectionReference db2;
    private FirebaseRecyclerAdapter firebaseRecyclerAdapter;
    private LinearLayoutManager linearLayoutManager;
    Product product;


    // creates the view and calls the function favouriteProductsToRecycleview to load the cards
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_favourite, container, false);
        recyclerView = root.findViewById(R.id.result_favourite);
        /*linearLayoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);*/

        products = new ArrayList<Product>();


        favouriteProductsToRecycleview(root,products);

        fillRecycleView(root);

        return root;
    }

    private void favouriteProductsToRecycleview(View v, ArrayList<Product> prod) {

        FirebaseFirestore db;
        FirebaseAuth auth;
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        //first query that gets every favourite item of the user

        db.collection("watchlist")
                .whereEqualTo("UserAddingId", auth.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()&& !task.getResult().isEmpty()) {
                            for (QueryDocumentSnapshot document : task.getResult())
                            {
                                products.add( new Product(document.getString("name"),
                                        document.getString("quantity"),
                                        document.getString("expire"),
                                        document.getString("UserPostingId"),
                                        document.getString("Product")));
                            }
                            HomeAndSearchCardAdapter homeAndSearchCardAdapter = new HomeAndSearchCardAdapter(v.getContext(), prod);
                            recyclerView.setAdapter(homeAndSearchCardAdapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
                        }
                    }
                });

    }



    private void fillRecycleView(View root) {
        HomeAndSearchCardAdapter homeAndSearchCardAdapter = new HomeAndSearchCardAdapter(root.getContext(), products);
        recyclerView.setAdapter(homeAndSearchCardAdapter);
    }

    /*public class ViewHolderCard extends RecyclerView.ViewHolder {
        TextView name, quantity, expire, userid;
        Button goToUser;
        FloatingActionButton addToFavorite;
        String productId;

        public ViewHolderCard(@NonNull View itemView) {
            super(itemView);
            expire = itemView.findViewById(R.id.productexpire);
            name = itemView.findViewById(R.id.productname);
            quantity = itemView.findViewById(R.id.productquantity);
            userid = itemView.findViewById(R.id.productauthor);

            addToFavorite = itemView.findViewById(R.id.add_to_fav_btn);

            goToUser = itemView.findViewById(R.id.btngotouser);

        }
    }*/

    /*private void fetch() {
        FirebaseAuth auth;
        auth = FirebaseAuth.getInstance();

        Query query = FirebaseFirestore.getInstance().collection("watchlist")
                .whereEqualTo("User", auth.getUid());


        FirestoreRecyclerOptions<Product> options =
                new FirestoreRecyclerOptions.Builder<Product>().setQuery(query, new SnapshotParser<Product>() {
                    @NonNull
                    @Override
                    public Product parseSnapshot(@NonNull DataSnapshot snapshot) {
                        return new Product(snapshot.child("name").getValue().toString()
                                , snapshot.child("quantity").getValue().toString()
                                , snapshot.child("expire").getValue().toString()
                                , snapshot.child("User").getValue().toString()
                                , snapshot.child("Product").getValue().toString());
                    }
                })
                        .build();


        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Product, ViewHolderCard>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolderCard holder, int position, @NonNull Product model) {
                holder.name.setText(model.getName());
                holder.quantity.setText(model.getQuantity());
                holder.expire.setText(model.getExpiration());
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("utenti").document(model.getUserId())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                DocumentSnapshot document = task.getResult();
                                holder.userid.setText(document.get("username").toString());
                            }
                        });//TODO adding a calculated field into product to reduce accesses to the server

                holder.addToFavorite.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        FirebaseAuth auth = FirebaseAuth.getInstance();
                        Date date = new Date();
                        SimpleDateFormat ft = new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss a zzz");

                        Map<String, Object> prod = new HashMap<>();
                        prod.put("User", auth.getUid());
                        prod.put("Product", products.get(position).getProductId());
                        prod.put("date", ft.format(date));
                        prod.put("quantity", products.get(position).getQuantity());
                        prod.put("expire", products.get(position).getExpiration());
                        prod.put("name", products.get(position).getExpiration());

                        db.collection("watchlist")
                                .document()
                                .set(prod, SetOptions.merge())
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(view.getContext(), "added to favourites", Toast.LENGTH_SHORT).show();

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(view.getContext(), "cannot add to favourites", Toast.LENGTH_SHORT).show();
                                    }
                                });

                    }
                });
                holder.productId = products.get(position).getProductId();
                holder.goToUser.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(view.getContext(), ViewUserData.class);
                        i.putExtra("UserId", products.get(position).getUserId());
                        getContext().startActivity(i);
                    }
                });

            }

            @Override
            public ViewHolderCard onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.home_card, parent, false);

                return new ViewHolderCard(view);
            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }*/

}


