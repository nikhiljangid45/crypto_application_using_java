package com.example.projectcryptoapp.gragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.projectcryptoapp.R;
import com.example.projectcryptoapp.Retrofit.ApiInterface;
import com.example.projectcryptoapp.Retrofit.AptiUtilities;
import com.example.projectcryptoapp.adapter.GainAdapter;
import com.example.projectcryptoapp.inteface.InteFaceItemClick;
import com.example.projectcryptoapp.model.CryptoCurrency;
import com.example.projectcryptoapp.model.MarketModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TopGainFragment extends Fragment {


    RecyclerView recyclerView;
    GainAdapter adapter;
    List<CryptoCurrency> list;
    ApiInterface apiInterface;

    public TopGainFragment() {
        // Required empty public constructor
    }



    InteFaceItemClick inteFaceItemClick;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top_gain, container, false);

        inteFaceItemClick = (InteFaceItemClick) requireActivity();






        recyclerView = view.findViewById(R.id.topgainRecycleView);
        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new GainAdapter(getContext(),list,inteFaceItemClick);

        apiInterface = AptiUtilities.getRetrofitInstance().create(ApiInterface.class);
        Call<MarketModel> call = apiInterface.getMarketModel();
        call.enqueue(new Callback<MarketModel>() {
            @Override
            public void onResponse(Call<MarketModel> call, Response<MarketModel> response) {

                if (response.isSuccessful()){
                    MarketModel model = response.body();

                    for (CryptoCurrency cryptoCurrency : model.getData().getCryptoCurrencyList()) {
                        double percentChange24h = cryptoCurrency.getQuotes().get(0).getPercentChange24h();
                        if (percentChange24h > 0.0) {
                            list.add(cryptoCurrency); // Add top gainers to the list
                        }else {

                        }
                        
                    }
                    Collections.sort(list, new Comparator<CryptoCurrency>() {
                        @Override
                        public int compare(CryptoCurrency crypto1, CryptoCurrency crypto2) {
                            double percentChange1 = crypto1.getQuotes().get(0).getPercentChange24h();
                            double percentChange2 = crypto2.getQuotes().get(0).getPercentChange24h();
                            return Double.compare(percentChange2, percentChange1);
                        }
                    });


                    adapter =new GainAdapter(getContext(),list,inteFaceItemClick);
                    recyclerView.setAdapter(adapter);
                 //   adapter.notifyDataSetChanged(); // Notify adapter about data change
                } else {
                    Toast.makeText(getContext(), "Failed to fetch data", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<MarketModel> call, Throwable t) {

            }
        });




        return view;
    }
    
    
}