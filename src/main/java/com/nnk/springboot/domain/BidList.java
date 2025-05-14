package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.sql.Timestamp;

/**
 * BidList Java Bean
 * This class is a representation of the bid list entity in the database.
 * It contains fields that correspond to the columns in the bid list table.
 */
@Entity
@Table(name = "bidlist")
public class BidList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BidListId")
    private Integer bidListId;

    @NotBlank(message = "Account is mandatory")
    @Length(max = 30)
    private String account;

    @NotBlank(message = "Type is mandatory")
    @Length(max = 30)
    private String type;

    @NotNull(message = "Bid quantity is mandatory")
    private Double bidQuantity;

    @NotNull(message = "Ask quantity is mandatory")
    private Double askQuantity;

    @NotNull(message = "Date is mandatory")
    private Double bid;

    @NotNull(message = "Ask is mandatory")
    private Double ask;

    @NotBlank(message = "Benchmark is mandatory")
    @Length(max = 125)
    private String benchmark;

    @NotNull(message = "Bid list date is mandatory")
    private Timestamp bidListDate;

    @NotBlank(message = "Commentary is mandatory")
    @Length(max = 125)
    private String commentary;

    @NotBlank(message = "Security is mandatory")
    @Length(max = 125)
    private String security;

    @NotBlank(message = "Status is mandatory")
    @Length(max = 10)
    private String status;

    @NotBlank(message = "Trader is mandatory")
    @Length(max = 125)
    private String trader;

    @NotBlank(message = "Book is mandatory")
    @Length(max = 125)
    private String book;

    @NotBlank(message = "Creation name is mandatory")
    @Length(max = 125)
    private String creationName;

    @NotNull(message = "Creation date is mandatory")
    private Timestamp creationDate;

    @NotBlank(message = "Revision name is mandatory")
    @Length(max = 125)
    private String revisionName;

    @NotNull(message = "Revision date is mandatory")
    private Timestamp revisionDate;

    @NotBlank(message = "Deal name is mandatory")
    @Length(max = 125)
    private String dealName;

    @NotBlank(message = "Deal type is mandatory")
    @Length(max = 125)
    private String dealType;

    @NotBlank(message = "Source list id is mandatory")
    @Length(max = 125)
    private String sourceListId;

    @NotBlank(message = "Side is mandatory")
    @Length(max = 125)
    private String side;

    public BidList() {}

    public BidList(String account, String type, Double bidQuantity) {
        this.account = account;
        this.type = type;
        this.bidQuantity = bidQuantity;
    }

    public void setBidListId(Integer id) {
        this.bidListId = id;
    }

    public Integer getBidListId() {
        return bidListId;
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

    public Double getBidQuantity() {
        return bidQuantity;
    }

    public void setBidQuantity(Double bidQuantity) {
        this.bidQuantity = bidQuantity;
    }

    public Double getAskQuantity() {
        return askQuantity;
    }

    public void setAskQuantity(Double askQuantity) {
        this.askQuantity = askQuantity;
    }

    public Double getBid() {
        return bid;
    }

    public void setBid(Double bid) {
        this.bid = bid;
    }

    public Double getAsk() {
        return ask;
    }

    public void setAsk(Double ask) {
        this.ask = ask;
    }

    public String getBenchmark() {
        return benchmark;
    }

    public void setBenchmark(String benchmark) {
        this.benchmark = benchmark;
    }

    public Timestamp getBidListDate() {
        return bidListDate;
    }

    public void setBidListDate(Timestamp bidListDate) {
        this.bidListDate = bidListDate;
    }

    public String getCommentary() {
        return commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
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
