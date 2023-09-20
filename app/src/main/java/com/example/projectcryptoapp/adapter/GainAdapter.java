package com.example.projectcryptoapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
import com.example.projectcryptoapp.gragment.DetailsFragment;
import com.example.projectcryptoapp.inteface.InteFaceItemClick;
import com.example.projectcryptoapp.model.CryptoCurrency;
import com.example.projectcryptoapp.model.MarketModel;

import java.util.List;

import retrofit2.Callback;

public class GainAdapter extends RecyclerView.Adapter<GainAdapter.GainViewHolder> {


    private Context context;
    private List<CryptoCurrency> list;

    InteFaceItemClick inteFaceItemClick ;


    public GainAdapter(Context context, List<CryptoCurrency> list, InteFaceItemClick inteFaceItemClick) {
        this.context = context;
        this.list = list;
        this.inteFaceItemClick = inteFaceItemClick;
    }

    @NonNull
    @Override
    public GainAdapter.GainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.currency_item_layout,parent,false);
        return new GainViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull GainAdapter.GainViewHolder holder, @SuppressLint("RecyclerView") int position) {



        CryptoCurrency cryptoCurrency = list.get(position);


        holder.currencyNameTextView.setText(cryptoCurrency.getName());
        holder.currencySymbolTextView.setText(cryptoCurrency.getSymbol());
        Glide.with(context).load("https://s2.coinmarketcap.com/static/img/coins/64x64/" + cryptoCurrency.getId() + ".png").into(holder.currencyImageView);
        holder.currencyPriceTextView.setText("$"+String.format("%.02f",cryptoCurrency.getQuotes().get(0).getPrice()));
        Glide.with(context).load("https://s3.coinmarketcap.com/generated/sparklines/web/7d/usd/" + cryptoCurrency.getId() + ".png").into(holder.currencyChartImageView);




        if (cryptoCurrency.getQuotes().get(0).getPercentChange24h() > 0.0){
            holder.currencyChangeTextView.setText(String.format("%.03f",cryptoCurrency.getQuotes().get(0).getPercentChange24h())+"%");
            holder.currencyChangeTextView.setTextColor(context.getResources().getColor(R.color.green)); // Set the green color
        }else{
            holder.currencyChangeTextView.setText(String.format("%.02f",cryptoCurrency.getQuotes().get(0).getPercentChange24h())+"%");
            holder.currencyChangeTextView.setTextColor(context.getResources().getColor(R.color.red)); // Set the green color
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                inteFaceItemClick.clickTheItem(cryptoCurrency);
            }
        });


    }

    @Override
    public int getItemCount() {
            return list.size();


    }

    public void filterList(List<CryptoCurrency> cryptoCurrencyList) {



        list = cryptoCurrencyList;
        notifyDataSetChanged();
    }

    public class GainViewHolder extends RecyclerView.ViewHolder {


   ImageView currencyImageView ,currencyChartImageView,currencyChangeImageView;
   TextView currencyNameTextView ,currencySymbolTextView ,currencyPriceTextView,currencyChangeTextView;

        public GainViewHolder(@NonNull View itemView) {
            super(itemView);

            currencyImageView = itemView.findViewById(R.id.currencyImageView);
            currencyChartImageView = itemView.findViewById(R.id.currencyChartImageView);
            currencyChangeImageView = itemView.findViewById(R.id.currencyChangeImageView);



            currencyNameTextView = itemView.findViewById(R.id.currencyNameTextView);
            currencySymbolTextView = itemView.findViewById(R.id.currencySymbolTextView);
            currencyPriceTextView = itemView.findViewById(R.id.currencyPriceTextView);
            currencyChangeTextView = itemView.findViewById(R.id.currencyChangeTextView);




        }
    }



}
