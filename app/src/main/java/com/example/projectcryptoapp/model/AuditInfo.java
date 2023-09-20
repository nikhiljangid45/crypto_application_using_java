package com.example.projectcryptoapp.model;

public class AuditInfo {
    private int auditStatus;
    private String auditTime;
    private String auditor;
    private String coinId;
    private String contractAddress;
    private String contractPlatform;
    private String reportUrl;
    private String score;

    public AuditInfo(int auditStatus, String auditTime, String auditor, String coinId,
                     String contractAddress, String contractPlatform, String reportUrl, String score) {
        this.auditStatus = auditStatus;
        this.auditTime = auditTime;
        this.auditor = auditor;
        this.coinId = coinId;
        this.contractAddress = contractAddress;
        this.contractPlatform = contractPlatform;
        this.reportUrl = reportUrl;
        this.score = score;
    }

    public int getAuditStatus() {
        return auditStatus;
    }

    public String getAuditTime() {
        return auditTime;
    }

    public String getAuditor() {
        return auditor;
    }

    public String getCoinId() {
        return coinId;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public String getContractPlatform() {
        return contractPlatform;
    }

    public String getReportUrl() {
        return reportUrl;
    }

    public String getScore() {
        return score;
    }
}
