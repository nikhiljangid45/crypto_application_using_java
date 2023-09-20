package com.example.projectcryptoapp.model;

import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CryptoCurrency implements Serializable {
    private List<AuditInfo> auditInfoList;
    private double circulatingSupply;
    private Double cmcRank;
    private String dateAdded;
    private int id;
    private Double isActive;
    private boolean isAudited;
    private String lastUpdated;
    private Double marketPairCount;
    private Double maxSupply;
    private String name;
    private Platform platform;
    private List<Quote> quotes;
    private double selfReportedCirculatingSupply;
    private String slug;
    private String symbol;
    private List<String> tags;
    private double totalSupply;


    public CryptoCurrency(List<AuditInfo> auditInfoList, double circulatingSupply, Double cmcRank, String dateAdded, int id, Double isActive, boolean isAudited, String lastUpdated, Double marketPairCount, Double maxSupply, String name, Platform platform, List<Quote> quotes, double selfReportedCirculatingSupply, String slug, String symbol, List<String> tags, double totalSupply) {
        this.auditInfoList = auditInfoList;
        this.circulatingSupply = circulatingSupply;
        this.cmcRank = cmcRank;
        this.dateAdded = dateAdded;
        this.id = id;
        this.isActive = isActive;
        this.isAudited = isAudited;
        this.lastUpdated = lastUpdated;
        this.marketPairCount = marketPairCount;
        this.maxSupply = maxSupply;
        this.name = name;
        this.platform = platform;
        this.quotes = quotes;
        this.selfReportedCirculatingSupply = selfReportedCirculatingSupply;
        this.slug = slug;
        this.symbol = symbol;
        this.tags = tags;
        this.totalSupply = totalSupply;
    }


    public List<AuditInfo> getAuditInfoList() {
        return auditInfoList;
    }

    public void setAuditInfoList(List<AuditInfo> auditInfoList) {
        this.auditInfoList = auditInfoList;
    }

    public double getCirculatingSupply() {
        return circulatingSupply;
    }

    public void setCirculatingSupply(double circulatingSupply) {
        this.circulatingSupply = circulatingSupply;
    }

    public Double getCmcRank() {
        return cmcRank;
    }

    public void setCmcRank(Double cmcRank) {
        this.cmcRank = cmcRank;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getIsActive() {
        return isActive;
    }

    public void setIsActive(Double isActive) {
        this.isActive = isActive;
    }

    public boolean isAudited() {
        return isAudited;
    }

    public void setAudited(boolean audited) {
        isAudited = audited;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Double getMarketPairCount() {
        return marketPairCount;
    }

    public void setMarketPairCount(Double marketPairCount) {
        this.marketPairCount = marketPairCount;
    }

    public Double getMaxSupply() {
        return maxSupply;
    }

    public void setMaxSupply(Double maxSupply) {
        this.maxSupply = maxSupply;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public List<Quote> getQuotes() {
        return quotes;
    }

    public void setQuotes(List<Quote> quotes) {
        this.quotes = quotes;
    }

    public double getSelfReportedCirculatingSupply() {
        return selfReportedCirculatingSupply;
    }

    public void setSelfReportedCirculatingSupply(double selfReportedCirculatingSupply) {
        this.selfReportedCirculatingSupply = selfReportedCirculatingSupply;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public double getTotalSupply() {
        return totalSupply;
    }

    public void setTotalSupply(double totalSupply) {
        this.totalSupply = totalSupply;
    }
}

