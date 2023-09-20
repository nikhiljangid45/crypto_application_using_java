package com.example.projectcryptoapp.gragment;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.projectcryptoapp.R;
import com.example.projectcryptoapp.Retrofit.ApiInterface;
import com.example.projectcryptoapp.Retrofit.AptiUtilities;
import com.example.projectcryptoapp.RoomDatabase;
import com.example.projectcryptoapp.adapter.GainAdapter;
import com.example.projectcryptoapp.inteface.InteFaceItemClick;
import com.example.projectcryptoapp.model.CryptoCurrency;
import com.example.projectcryptoapp.model.MarketModel;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WatchListFragment extends Fragment {

    private List<RoomDatabase> nikhil;
    private List<CryptoCurrency> watchlist;
    List<CryptoCurrency> list;
    ApiInterface apiInterface;

    RecyclerView recyclerView;
    GainAdapter adapter;
    InteFaceItemClick inteFaceItemClick;
    SpinKitView spinKitView;
    public WatchListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_watch_list, container, false);


        watchlist = new ArrayList<>();
        nikhil = loadWatchlistFromSharedPreferences();
        list = new ArrayList<>();
        spinKitView = view.findViewById(R.id.spinKitView);
        recyclerView = view.findViewById(R.id.watchlistRecyclerView);



       apiInterface = AptiUtilities.getRetrofitInstance().create(ApiInterface.class);
        Call<MarketModel> call = apiInterface.getMarketModel();
        call.enqueue(new Callback<MarketModel>() {
            @Override
            public void onResponse(Call<MarketModel> call, Response<MarketModel> response) {
                if (response.isSuccessful()) {
                    MarketModel model = response.body();

                    // Populate 'list' with API data
                    list.addAll(model.getData().getCryptoCurrencyList());

                    // Filter and populate 'watchlist'
                    for (CryptoCurrency currency : list) {
                        for (RoomDatabase roomCurrency : nikhil) {
                            String currencySymbol = currency.getSymbol();
                            String roomCurrencyName = roomCurrency.getName();

                            if (currencySymbol.equals(roomCurrencyName)) {
                                watchlist.add(currency);
                                break;
                            }
                        }
                    }

                    // Hide progress spinner
                    spinKitView.setVisibility(View.INVISIBLE);
                    // Set the adapter with filtered data
                    adapter = new GainAdapter(getContext(), watchlist, inteFaceItemClick);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<MarketModel> call, Throwable t) {
                // Handle failure
            }
        });

        spinKitView.setVisibility(View.VISIBLE); // Show progress spinner
        inteFaceItemClick = (InteFaceItemClick) requireActivity();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    public void refreshData() {
        // Perform actions to refresh the data in the WatchListFragment
        // For example, you can notify the adapter about the data change
        if (adapter != null) {
            adapter.notifyDataSetChanged(); // Refresh the adapter's data
        }
    }
    // ... (rest of your code)

    private List<RoomDatabase> loadWatchlistFromSharedPreferences() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPrefs", getContext().MODE_PRIVATE);
        String watchlistJson = sharedPreferences.getString("watchlist", "");

        Gson gson = new Gson();
        Type type = new TypeToken<List<RoomDatabase>>() {}.getType();
        return gson.fromJson(watchlistJson, type);
    }


}