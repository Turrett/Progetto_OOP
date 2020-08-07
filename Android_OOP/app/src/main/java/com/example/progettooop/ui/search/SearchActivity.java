package com.example.progettooop.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.progettooop.R;
import com.example.progettooop.ui.advertisement.Product;

public class SearchActivity extends Fragment {
    RecyclerView recyclerView;
    Product product[];
    SearchViewModel searchViewModel;

    protected View onCreate(@NonNull LayoutInflater inflater,
                            ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        View root = inflater.inflate(R.layout.fragment_search, container, false);


        product[0]= new Product("latte","10","21/09/2012");
        product [1] = new Product ("biscotti","5","21/09/2019");
        product [2] = new Product ("pane","2","10/05/2021");
        product [3] = new Product ("uova","3","20/16/2022");
        recyclerView=root.findViewById(R.id.result_list);

        MyAdapter myAdapter =new MyAdapter(getContext(),product);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return root;
    }
}
