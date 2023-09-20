package com.example.projectcryptoapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projectcryptoapp.R;
import com.example.projectcryptoapp.model.CryptoCurrency;

import java.sql.CallableStatement;
import java.util.List;

public class TopMarketAdapter extends RecyclerView.Adapter<TopMarketAdapter.TopViewHolder> {


    private Context context;
    private List<CryptoCurrency> cryptoCurrencyList;


    public TopMarketAdapter(Context context, List<CryptoCurrency> cryptoCurrencyList) {
        this.context = context;
        this.cryptoCurrencyList = cryptoCurrencyList;
    }

    @NonNull
    @Override
    public TopMarketAdapter.TopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.top_currency_layout,parent,false);
        return new TopViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull TopMarketAdapter.TopViewHolder holder, int position) {
           CryptoCurrency cryptoCurrency = cryptoCurrencyList.get(position);
         double nik  =0.0;
        holder.name.setText(cryptoCurrency.getName());
        Glide.with(context).load("https://s2.coinmarketcap.com/static/img/coins/64x64/" + cryptoCurrency.getId() + ".png").into(holder.imageView);



        if (cryptoCurrency.getQuotes().get(0).getPercentChange24h() > 0.0){
           holder.change.setText(String.format("%.02f",cryptoCurrency.getQuotes().get(0).getPercentChange24h()));
            holder.change.setTextColor(context.getResources().getColor(R.color.green)); // Set the green color
        }else{
            holder.change.setText(String.format("%.02f",cryptoCurrency.getQuotes().get(0).getPercentChange24h()));
            holder.change.setTextColor(context.getResources().getColor(R.color.red)); // Set the green color
        }


    }

    @Override
    public int getItemCount() {
        return cryptoCurrencyList.size();
    }

    public class TopViewHolder extends RecyclerView.ViewHolder {

ImageView imageView;
TextView name,change;

        public TopViewHolder(@NonNull View itemView) {
            super(itemView);
        imageView = itemView.findViewById(R.id.topCurrencyImageView);
        name = itemView.findViewById(R.id.topCurrencyNameTextView);
        change = itemView.findViewById(R.id.topCurrencyChangeTextView);



        }
    }

}
