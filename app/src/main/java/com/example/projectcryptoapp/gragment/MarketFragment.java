package com.example.projectcryptoapp.gragment;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectcryptoapp.R;
import com.example.projectcryptoapp.Retrofit.ApiInterface;
import com.example.projectcryptoapp.Retrofit.AptiUtilities;
import com.example.projectcryptoapp.adapter.GainAdapter;
import com.example.projectcryptoapp.inteface.InteFaceItemClick;
import com.example.projectcryptoapp.model.CryptoCurrency;
import com.example.projectcryptoapp.model.MarketModel;
import com.github.ybq.android.spinkit.SpinKitView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MarketFragment extends Fragment {

    RecyclerView recyclerView;
    GainAdapter adapter;
    List<CryptoCurrency> list;
    ApiInterface apiInterface;
    InteFaceItemClick inteFaceItemClick;

    SpinKitView spinKitView;
    SearchView search;

    public MarketFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_market, container, false);


        inteFaceItemClick = (InteFaceItemClick) requireActivity();
        search = view.findViewById(R.id.searchEditTexts);
        spinKitView = view.findViewById(R.id.spinKitView3);


        recyclerView = view.findViewById(R.id.currencyRecyclerView);
        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new GainAdapter(getContext(), list, inteFaceItemClick);
        spinKitView.setVisibility(View.INVISIBLE);

        apiInterface = AptiUtilities.getRetrofitInstance().create(ApiInterface.class);
        Call<MarketModel> call = apiInterface.getMarketModel();
        call.enqueue(new Callback<MarketModel>() {
            @Override
            public void onResponse(Call<MarketModel> call, Response<MarketModel> response) {
                if (response.isSuccessful()) {
                    MarketModel model = response.body();

                    for (CryptoCurrency cryptoCurrency : model.getData().getCryptoCurrencyList()) {

                        list.add(cryptoCurrency); // Add top gainers to the list

                    }

                }
                spinKitView.setVisibility(View.INVISIBLE);
                adapter = new GainAdapter(getContext(), list, inteFaceItemClick);
                recyclerView.setAdapter(adapter);
            }


            @Override
            public void onFailure(Call<MarketModel> call, Throwable t) {

            }
        });


        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }

        });

        return view;
    }


    public void filter(String text) {

        List<CryptoCurrency> cryptoCurrencyList = new ArrayList<>();

        for (CryptoCurrency item : list) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                cryptoCurrencyList.add(item);
            }
        }

        if (cryptoCurrencyList.isEmpty()){
            Toast.makeText(getContext(), "No Data Found..", Toast.LENGTH_SHORT).show();

        }else {
            adapter.filterList(cryptoCurrencyList);

        }
    }
}