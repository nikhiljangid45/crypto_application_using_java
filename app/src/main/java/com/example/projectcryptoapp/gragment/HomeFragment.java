package com.example.projectcryptoapp.gragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.projectcryptoapp.R;
import com.example.projectcryptoapp.Retrofit.ApiInterface;
import com.example.projectcryptoapp.Retrofit.AptiUtilities;
import com.example.projectcryptoapp.adapter.GainAdapter;
import com.example.projectcryptoapp.adapter.MyPagerAdapter;
import com.example.projectcryptoapp.adapter.TopMarketAdapter;
import com.example.projectcryptoapp.model.CryptoCurrency;
import com.example.projectcryptoapp.model.MarketModel;
import com.google.android.material.tabs.TabLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {


    ApiInterface apiInterface;

    List<CryptoCurrency> cryptoCurrencyList;
    TopMarketAdapter topMarketAdapter;
    RecyclerView recyclerView;


    TabLayout tabLayout;
    ViewPager viewPager;
    View loss , gain;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);




        recyclerView = view.findViewById(R.id.topCurrencyRecyclerView);
        cryptoCurrencyList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        topMarketAdapter = new TopMarketAdapter(getContext(),cryptoCurrencyList);


        apiInterface = AptiUtilities.getRetrofitInstance().create(ApiInterface.class);
        Call<MarketModel> call = apiInterface.getMarketModel();
        call.enqueue(new Callback<MarketModel>() {
            @Override
            public void onResponse(Call<MarketModel> call, Response<MarketModel> response) {
                if (response.isSuccessful()){
                    MarketModel model = response.body();
                    cryptoCurrencyList = model.getData().getCryptoCurrencyList(); // Assign the data
                    topMarketAdapter = new TopMarketAdapter(getContext(), cryptoCurrencyList);
                    recyclerView.setAdapter(topMarketAdapter);
                }else {
                    Toast.makeText(getContext(), "sdfjkgsdkngfaw", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MarketModel> call, Throwable t) {
               Log.v("Nikhil",t.getMessage());

            }
        });



        // Tablayout
        tabLayout = view.findViewById(R.id.tabLayout);
         viewPager = view.findViewById(R.id.contentViewPager);


        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        loss = view.findViewById(R.id.topLoseIndicator);
        gain = view.findViewById(R.id.topGainIndicator);

        viewPager.setOffscreenPageLimit(2); // Replace NUM_PAGES with the number of pages in your ViewPager

         viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
          @Override
          public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

          }

          @Override
          public void onPageSelected(int position) {

              if (position == 0){
                  gain.setVisibility(View.VISIBLE);
                  loss.setVisibility(View.INVISIBLE);

              }else {
                  gain.setVisibility(View.INVISIBLE);
                  loss.setVisibility(View.VISIBLE);
              }
          }

          @Override
          public void onPageScrollStateChanged(int state) {

          }
      });

        tabLayout.setupWithViewPager(viewPager);


        return view;
    }


}