package com.nnk.springboot.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public class BidListForm {
    @NotBlank(message = "Account is mandatory")
    @Length(max = 30)
    private String account;

    @NotBlank(message = "Type is mandatory")
    @Length(max = 30)
    private String type;

    @NotNull(message = "Bid quantity is mandatory")
    private Double bidQuantity;

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
}
