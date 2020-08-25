package com.example.progettooop.ui.dashboard.yourproducts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.progettooop.R;
import com.example.progettooop.ui.Objects.Request;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;

public class ProductRequestAdapter extends RecyclerView.Adapter<ProductRequestAdapter.RequestViewHolder> {
    private ArrayList<Request> requests;
    private Context context;

    public ProductRequestAdapter(ArrayList<Request> requests, Context context) {
        this.requests = requests;
        this.context = context;
    }

    public static class RequestViewHolder extends RecyclerView.ViewHolder{
        TextView message,when,userId;
        Button accept;

        public RequestViewHolder(@NonNull View itemView) {
            super(itemView);
        message = itemView.findViewById(R.id.txtmessage);
        when = itemView.findViewById(R.id.txtwhen);
        userId =itemView.findViewById(R.id.txtuser);
        accept =itemView.findViewById(R.id.btnaccept);
        }
    }


    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(context);
        View view =inflater.inflate(R.layout.request_card,parent,false);
        return new ProductRequestAdapter.RequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder holder, int position) {
        holder.message.setText(requests.get(position).getMessage());
        holder.when.setText(requests.get(position).getWhen());

        FirebaseFirestore db =FirebaseFirestore.getInstance();
        db.collection("utenti").document(requests.get(position).getUserId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document=task.getResult();
                holder.userId.setText(document.get("username").toString());
            }
        });//TODO adding a calculated field into product to reduce accesses to the server

        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WriteBatch batch = db.batch();

//accepts the request
                DocumentReference doc1 =db.collection("watchlist")
                        .document(requests.get(position).getWatchlistId());
                        batch.update(doc1,"state","accepted");

//metto l'annuncio come accettato in modo da non venire più mostrato nella home e nella ricerca
                DocumentReference doc2 =db.collection("annuncio")
                        .document(requests.get(position).getProductId());
                        batch.update(doc2,"state","accepted");

//chi ha accettato
                DocumentReference doc3=db.collection("annuncio")
                        .document(requests.get(position).getProductId());
                        batch.update(doc3,"UserIdAccepted", requests.get(position).getUserId());

//la watchlist che ha accettato
                DocumentReference doc4=db.collection("annuncio")
                        .document(requests.get(position).getProductId());
                        batch.update(doc4,"WatchlistIdAccepted", requests.get(position).getWatchlistId());
//eseguo la transazione
                        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(context,"dataupdated",Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }


    @Override
    public int getItemCount() {
        return requests.size();
    }
}
