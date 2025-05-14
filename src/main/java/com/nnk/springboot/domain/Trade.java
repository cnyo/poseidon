package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.sql.Timestamp;

@Entity
@Table(name = "trade")
public class Trade {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "TradeId")
    private Integer tradeId;

    @NotBlank(message = "Account is mandatory")
    @Length(max = 125)
    private String account;

    @NotBlank(message = "Type is mandatory")
    @Length(max = 125)
    private String type;

    @NotNull(message = "Buy quantity is mandatory")
    private Double buyQuantity;

    @NotNull(message = "Sell quantity is mandatory")
    private Double sellQuantity;

    @NotNull(message = "Buy price is mandatory")
    private Double buyPrice;

    @NotNull(message = "Sell price is mandatory")
    private Double sellPrice;

    @NotBlank(message = "Benchmark is mandatory")
    @Length(max = 125)
    private String benchmark;

    @NotNull(message = "Trade date is mandatory")
    private Timestamp tradeDate;

    @NotBlank(message = "Settle date is mandatory")
    @Length(max = 125)
    private String security;

    @NotBlank(message = "Settle date is mandatory")
    @Length(max = 125)
    private String status;

    @NotBlank(message = "Settle date is mandatory")
    @Length(max = 125)
    private String trader;

    @NotBlank(message = "Settle date is mandatory")
    @Length(max = 125)
    private String book;

    @NotBlank(message = "Settle date is mandatory")
    @Length(max = 125)
    private String creationName;

    @NotNull(message = "Settle date is mandatory")
    private Timestamp creationDate;

    @NotBlank(message = "Settle date is mandatory")
    @Length(max = 125)
    private String revisionName;

    @NotNull(message = "Settle date is mandatory")
    private Timestamp revisionDate;

    @NotBlank(message = "Settle date is mandatory")
    @Length(max = 125)
    private String dealName;

    @NotBlank(message = "Settle date is mandatory")
    @Length(max = 125)
    private String dealType;

    @NotBlank(message = "Settle date is mandatory")
    @Length(max = 125)
    private String sourceListId;

    @NotBlank(message = "Settle date is mandatory")
    @Length(max = 125)
    private String side;

    public Trade() {}

    public Trade(String account, String type) {
        setAccount(account);
        setType(type);
    }

    public void setTradeId(Integer id) {
        this.tradeId = id;
    }

    public Integer getTradeId() {
        return tradeId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getBuyQuantity() {
        return buyQuantity;
    }

    public void setBuyQuantity(Double buyQuantity) {
        this.buyQuantity = buyQuantity;
    }

    public Double getSellQuantity() {
        return sellQuantity;
    }

    public void setSellQuantity(Double sellQuantity) {
        this.sellQuantity = sellQuantity;
    }

    public Double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(Double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public Double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getBenchmark() {
        return benchmark;
    }

    public void setBenchmark(String benchmark) {
        this.benchmark = benchmark;
    }

    public Timestamp getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(Timestamp tradeDate) {
        this.tradeDate = tradeDate;
    }

    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        this.security = security;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTrader() {
        return trader;
    }

    public void setTrader(String trader) {
        this.trader = trader;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public String getCreationName() {
        return creationName;
    }

    public void setCreationName(String creationName) {
        this.creationName = creationName;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public String getRevisionName() {
        return revisionName;
    }

    public void setRevisionName(String revisionName) {
        this.revisionName = revisionName;
    }

    public Timestamp getRevisionDate() {
        return revisionDate;
    }

    public void setRevisionDate(Timestamp revisionDate) {
        this.revisionDate = revisionDate;
    }

    public String getDealName() {
        return dealName;
    }

    public void setDealName(String dealName) {
        this.dealName = dealName;
    }

    public String getDealType() {
        return dealType;
    }

    public void setDealType(String dealType) {
        this.dealType = dealType;
    }

    public String getSourceListId() {
        return sourceListId;
    }

    public void setSourceListId(String sourceListId) {
        this.sourceListId = sourceListId;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }
}
