package com.example.projectcryptoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.projectcryptoapp.Retrofit.ApiInterface;
import com.example.projectcryptoapp.Retrofit.AptiUtilities;
import com.example.projectcryptoapp.gragment.DetailsFragment;
import com.example.projectcryptoapp.gragment.HomeFragment;
import com.example.projectcryptoapp.gragment.MarketFragment;
import com.example.projectcryptoapp.gragment.WatchListFragment;
import com.example.projectcryptoapp.inteface.InteFaceItemClick;
import com.example.projectcryptoapp.model.CryptoCurrency;
import com.example.projectcryptoapp.model.MarketModel;

import java.io.Serializable;
import java.util.List;

import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements InteFaceItemClick {

    SmoothBottomBar bottomNavigationView;
    FrameLayout navController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        String api_key ="https://api.coinmarketcap.com/data-api/v3/cryptocurrency/listing?start=1&limit=500";
        String imag =" https://s2.coinmarketcap.com/static/i...\" + imageID + \".png\n";

        navController = findViewById(R.id.frameContainerView);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);


       replaceFragement(new HomeFragment());

        bottomNavigationView.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public boolean onItemSelect(int item) {


                if (item == 0){
                   replaceFragement(new HomeFragment());
                }
                if (item == 1){
                    replaceFragement(new MarketFragment());
                }
                if (item == 2){
                    replaceFragement(new WatchListFragment());
                }
                return true;
            }
        });



    }

    private void  replaceFragement(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameContainerView,fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void clickTheItem(CryptoCurrency cryptoCurrency) {

        Bundle bundle = new Bundle();
        bundle.putSerializable("value", (Serializable) cryptoCurrency);

        Fragment mainFragement = new DetailsFragment();

        mainFragement.setArguments(bundle);
        FragmentManager ft = getSupportFragmentManager();
        FragmentTransaction fm = ft.beginTransaction();
        fm.addToBackStack(null);
        fm.add(R.id.frameContainerView, mainFragement);
        fm.commit();
    }
}