package com.fakebank.main.modules.dto.Transfer;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class TransferRequestDTO {

    @NotNull
    private String receiverEmail;

    @NotNull
    private BigDecimal amount;

    public String getReceiverEmail() {
        return receiverEmail;
    }

    public void setReceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
