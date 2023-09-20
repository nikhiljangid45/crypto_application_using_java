package com.example.projectcryptoapp.model;


public class Platform {
    private int id;
    private String name;
    private String slug;
    private String symbol;
    private String token_address;

    public Platform(int id, String name, String slug, String symbol, String token_address) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.symbol = symbol;
        this.token_address = token_address;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getToken_address() {
        return token_address;
    }
}
