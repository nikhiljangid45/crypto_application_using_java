package com.example.projectcryptoapp.model;


public class MarketModel {
    private Data data;
    private Status status;

    public MarketModel(Data data, Status status) {
        this.data = data;
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public Status getStatus() {
        return status;
    }
}
