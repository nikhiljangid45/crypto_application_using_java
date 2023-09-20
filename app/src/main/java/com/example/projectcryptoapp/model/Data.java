package com.example.projectcryptoapp.model;

import java.util.List;

public class Data {
    private List<CryptoCurrency> cryptoCurrencyList;
    private String totalCount;

    public Data(List<CryptoCurrency> cryptoCurrencyList, String totalCount) {
        this.cryptoCurrencyList = cryptoCurrencyList;
        this.totalCount = totalCount;
    }

    public List<CryptoCurrency> getCryptoCurrencyList() {
        return cryptoCurrencyList;
    }

    public String getTotalCount() {
        return totalCount;
    }
}
