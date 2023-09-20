package com.example.projectcryptoapp.gragment;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.projectcryptoapp.R;
import com.example.projectcryptoapp.Retrofit.ApiInterface;
import com.example.projectcryptoapp.Retrofit.AptiUtilities;
import com.example.projectcryptoapp.RoomDatabase;
import com.example.projectcryptoapp.adapter.GainAdapter;
import com.example.projectcryptoapp.databinding.FragmentDetailsBinding;
import com.example.projectcryptoapp.model.CryptoCurrency;
import com.example.projectcryptoapp.model.MarketModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailsFragment extends Fragment {

    private CryptoCurrency cryptoCurrency;

    public DetailsFragment() {
        // Required empty public constructor
    }

    private FragmentDetailsBinding binding;
    List<RoomDatabase> watchlist;
    boolean isAdded;

    List<CryptoCurrency> list;
    ApiInterface apiInterface;
    WatchListFragment watchListFragment;
    Executor executor;



    FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        executor = Executors.newFixedThreadPool(2);

        binding = FragmentDetailsBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();
        watchListFragment =new WatchListFragment();


        Bundle bundle = getArguments();
        if (bundle != null) {
            cryptoCurrency = (CryptoCurrency) bundle.getSerializable("value");
        }




        watchlist = loadWatchlistFromSharedPreferences();

        if (watchlist != null){
            for (RoomDatabase roomCurrency : watchlist) {
                if (cryptoCurrency != null && roomCurrency != null) {
                    String currencySymbol = cryptoCurrency.getSymbol().toLowerCase().toString().trim();
                    String roomCurrencyName = roomCurrency.getName().toLowerCase().toString().trim();

                    if (currencySymbol.equals(roomCurrencyName)) {
                        binding.addWatchlistButton.setBackgroundResource(R.drawable.ic_heart_filled);
                        binding.addWatchlistButton.setChecked(true);

                        break;
                    }

                }
              //  String currencySymbol = cryptoCurrency.getSymbol().toLowerCase().toString().trim();
               // String roomCurrencyName = roomCurrency.getName().toLowerCase().toString().trim();

            }

        }

        binding.backStackButton.setOnClickListener(view -> {

            executor.execute(new Runnable() {
                @Override
                public void run() {
                    watchListFragment = (WatchListFragment) getChildFragmentManager().findFragmentById(R.id.watchListFragment);
                    if (watchListFragment != null) {
                        watchListFragment.refreshData(); // Call the refresh method in WatchListFragment
                    }
                }
            });



            getParentFragmentManager().popBackStack();

        });

        loadOtherDataA();
        loadDefaultImage();
        imageAccordingToTime();

        if (watchlist == null) {
            watchlist = new ArrayList<>(); // Initialize watchlist if it's null
        }
      //  watchlist = loadWatchlistFromSharedPreferences();
        binding.addWatchlistButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    binding.addWatchlistButton.setBackgroundResource(R.drawable.ic_heart_filled);

                    // Add the new item to the existing watchlist
                    executor.execute(new Runnable() {
                        @Override
                        public void run() {
                            watchlist.add(new RoomDatabase(cryptoCurrency.getSymbol(), 1));

                        }
                    });

                    Toast.makeText(getContext(), "Added to watchlist", Toast.LENGTH_SHORT).show();

                } else {
                    binding.addWatchlistButton.setBackgroundResource(R.drawable.ic_heart_outline);

                    // Find and remove the item from the watchlist based on its unique identifier
                    executor.execute(new Runnable() {
                        @Override
                        public void run() {
                            RoomDatabase itemToRemove = new RoomDatabase(cryptoCurrency.getSymbol(), 1);
                            removeFromWatchlist(itemToRemove);

                        }
                    });
                    Toast.makeText(getContext(), "Removed from watchlist", Toast.LENGTH_SHORT).show();
                }

                // Save the updated watchlist to SharedPreferences
                saveWatchlistToSharedPreferences(watchlist);
            }
        });


        return rootView;
    }

    private void saveWatchlistToSharedPreferences(List<RoomDatabase> watchlist) {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPrefs", getContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String watchlistJson = gson.toJson(watchlist);

        editor.putString("watchlist", watchlistJson);
        editor.apply();
    }
    private List<RoomDatabase> loadWatchlistFromSharedPreferences() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPrefs", getContext().MODE_PRIVATE);
        String watchlistJson = sharedPreferences.getString("watchlist", "");

        Gson gson = new Gson();
        Type type = new TypeToken<List<RoomDatabase>>() {}.getType();
        return gson.fromJson(watchlistJson, type);
    }


    private void removeFromWatchlist(RoomDatabase itemToRemove) {
        for (Iterator<RoomDatabase> iterator = watchlist.iterator(); iterator.hasNext(); ) {
            RoomDatabase roomCurrency = iterator.next();
            if (roomCurrency.getName().equals(itemToRemove.getName())) {
                iterator.remove();
                break;
            }
        }
    }

    private void imageAccordingToTime(){
        binding.button5.setOnClickListener(view -> {

            binding.button5.setBackgroundResource(R.drawable.active_button);
            binding.button1.setBackgroundResource(0);
            binding.button2.setBackgroundResource(0);
            binding.button3.setBackgroundResource(0);
            binding.button4.setBackgroundResource(0);
            binding.button.setBackgroundResource(0);

            String s = "15";
            binding.detaillChartWebView.loadUrl(
                    "https://s.tradingview.com/widgetembed/?frameElementId=tradingview_76d87&symbol=" +
                            cryptoCurrency.getSymbol() + "&interval=" + s + "&hidesidetoolbar=1&hidetoptoolbar=" +
                            "1&symboledit=1&saveimage=1&toolbarbg=F1F3F6&studies=[]&hideideas=" +
                            "1&theme=Dark&style=D&timezone=Etc%2FUTC&studies_overrides={}&overrides=" +
                            "{}&enabled_features=[]&disabled_features=[]&locale=en&utm_source=coinmarketcap.com&utm_medium" +
                            "=widget&utm_campaign=chart&utm_term=BTCUSDT"
            );


        });
        binding.button4.setOnClickListener(view -> {

            binding.button4.setBackgroundResource(R.drawable.active_button);
            binding.button1.setBackgroundResource(0);
            binding.button2.setBackgroundResource(0);
            binding.button3.setBackgroundResource(0);
            binding.button5.setBackgroundResource(0);
            binding.button.setBackgroundResource(0);

            String s = "1H";
            binding.detaillChartWebView.loadUrl(
                    "https://s.tradingview.com/widgetembed/?frameElementId=tradingview_76d87&symbol=" +
                            cryptoCurrency.getSymbol() + "&interval=" + s + "&hidesidetoolbar=1&hidetoptoolbar=" +
                            "1&symboledit=1&saveimage=1&toolbarbg=F1F3F6&studies=[]&hideideas=" +
                            "1&theme=Dark&style=D&timezone=Etc%2FUTC&studies_overrides={}&overrides=" +
                            "{}&enabled_features=[]&disabled_features=[]&locale=en&utm_source=coinmarketcap.com&utm_medium" +
                            "=widget&utm_campaign=chart&utm_term=BTCUSDT"
            );


        });
        binding.button3.setOnClickListener(view -> {

            binding.button3.setBackgroundResource(R.drawable.active_button);
            binding.button1.setBackgroundResource(0);
            binding.button2.setBackgroundResource(0);
            binding.button5.setBackgroundResource(0);
            binding.button4.setBackgroundResource(0);
            binding.button.setBackgroundResource(0);

            String s = "4H";
            binding.detaillChartWebView.loadUrl(
                    "https://s.tradingview.com/widgetembed/?frameElementId=tradingview_76d87&symbol=" +
                            cryptoCurrency.getSymbol() + "&interval=" + s + "&hidesidetoolbar=1&hidetoptoolbar=" +
                            "1&symboledit=1&saveimage=1&toolbarbg=F1F3F6&studies=[]&hideideas=" +
                            "1&theme=Dark&style=D&timezone=Etc%2FUTC&studies_overrides={}&overrides=" +
                            "{}&enabled_features=[]&disabled_features=[]&locale=en&utm_source=coinmarketcap.com&utm_medium" +
                            "=widget&utm_campaign=chart&utm_term=BTCUSDT"
            );


        });
        binding.button2.setOnClickListener(view -> {

            binding.button2.setBackgroundResource(R.drawable.active_button);
            binding.button1.setBackgroundResource(0);
            binding.button5.setBackgroundResource(0);
            binding.button3.setBackgroundResource(0);
            binding.button4.setBackgroundResource(0);
            binding.button.setBackgroundResource(0);

            String s = "D";
            binding.detaillChartWebView.loadUrl(
                    "https://s.tradingview.com/widgetembed/?frameElementId=tradingview_76d87&symbol=" +
                            cryptoCurrency.getSymbol() + "&interval=" + s + "&hidesidetoolbar=1&hidetoptoolbar=" +
                            "1&symboledit=1&saveimage=1&toolbarbg=F1F3F6&studies=[]&hideideas=" +
                            "1&theme=Dark&style=D&timezone=Etc%2FUTC&studies_overrides={}&overrides=" +
                            "{}&enabled_features=[]&disabled_features=[]&locale=en&utm_source=coinmarketcap.com&utm_medium" +
                            "=widget&utm_campaign=chart&utm_term=BTCUSDT"
            );


        });
        binding.button1.setOnClickListener(view -> {

            binding.button1.setBackgroundResource(R.drawable.active_button);
            binding.button5.setBackgroundResource(0);
            binding.button2.setBackgroundResource(0);
            binding.button3.setBackgroundResource(0);
            binding.button4.setBackgroundResource(0);
            binding.button.setBackgroundResource(0);

            String s = "W";
            binding.detaillChartWebView.loadUrl(
                    "https://s.tradingview.com/widgetembed/?frameElementId=tradingview_76d87&symbol=" +
                            cryptoCurrency.getSymbol() + "&interval=" + s + "&hidesidetoolbar=1&hidetoptoolbar=" +
                            "1&symboledit=1&saveimage=1&toolbarbg=F1F3F6&studies=[]&hideideas=" +
                            "1&theme=Dark&style=D&timezone=Etc%2FUTC&studies_overrides={}&overrides=" +
                            "{}&enabled_features=[]&disabled_features=[]&locale=en&utm_source=coinmarketcap.com&utm_medium" +
                            "=widget&utm_campaign=chart&utm_term=BTCUSDT"
            );


        });
        binding.button.setOnClickListener(view -> {

            binding.button.setBackgroundResource(R.drawable.active_button);
            binding.button5.setBackgroundResource(0);
            binding.button2.setBackgroundResource(0);
            binding.button3.setBackgroundResource(0);
            binding.button4.setBackgroundResource(0);
            binding.button1.setBackgroundResource(0);

            String s = "M";
            binding.detaillChartWebView.loadUrl(
                    "https://s.tradingview.com/widgetembed/?frameElementId=tradingview_76d87&symbol=" +
                            cryptoCurrency.getSymbol() + "&interval=" + s + "&hidesidetoolbar=1&hidetoptoolbar=" +
                            "1&symboledit=1&saveimage=1&toolbarbg=F1F3F6&studies=[]&hideideas=" +
                            "1&theme=Dark&style=D&timezone=Etc%2FUTC&studies_overrides={}&overrides=" +
                            "{}&enabled_features=[]&disabled_features=[]&locale=en&utm_source=coinmarketcap.com&utm_medium" +
                            "=widget&utm_campaign=chart&utm_term=BTCUSDT"
            );


        });
    }
    private void loadDefaultImage(){

        binding.detaillChartWebView.loadUrl(
                "https://s.tradingview.com/widgetembed/?frameElementId=tradingview_76d87&symbol=" +
                        cryptoCurrency.getSymbol() + "&interval=D&hidesidetoolbar=1&hidetoptoolbar=" +
                        "1&symboledit=1&saveimage=1&toolbarbg=F1F3F6&studies=[]&hideideas=" +
                        "1&theme=Dark&style=D&timezone=Etc%2FUTC&studies_overrides={}&overrides=" +
                        "{}&enabled_features=[]&disabled_features=[]&locale=en&utm_source=coinmarketcap.com&utm_medium" +
                        "=widget&utm_campaign=chart&utm_term=BTCUSDT"
        );
    }
    private void loadOtherDataA(){
        binding.detaillChartWebView.getSettings().setJavaScriptEnabled(true);
        binding.detaillChartWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);


        binding.detailSymbolTextView.setText(cryptoCurrency.getSymbol());
        Glide.with(getContext()).load("https://s2.coinmarketcap.com/static/img/coins/64x64/" + cryptoCurrency.getId() + ".png").into(binding.detailImageView);
        binding.detailPriceTextView.setText("$" + String.format("%.05f", cryptoCurrency.getQuotes().get(0).getPrice()));


        if (cryptoCurrency.getQuotes().get(0).getPercentChange24h() > 0.0) {
            binding.detailChangeTextView.setText(String.format("%.03f", cryptoCurrency.getQuotes().get(0).getPercentChange24h()) + "%");
            binding.detailChangeTextView.setTextColor(getContext().getResources().getColor(R.color.green)); // Set the green color
        } else {
            binding.detailChangeTextView.setText(String.format("%.02f", cryptoCurrency.getQuotes().get(0).getPercentChange24h()) + "%");
            binding.detailChangeTextView.setTextColor(getContext().getResources().getColor(R.color.red)); // Set the green color
        }

    }

}