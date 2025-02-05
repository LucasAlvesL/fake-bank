package com.fakebank.main.modules.dto.Transfer;

import java.math.BigDecimal;

public class TransferResponseDTO {
    private String message;
    private BigDecimal amountTransferred;
    private String receiverEmail;

    public TransferResponseDTO(String message, BigDecimal amountTransferred, String receiverEmail) {
        this.message = message;
        this.amountTransferred = amountTransferred;
        this.receiverEmail = receiverEmail;
    }

    public TransferResponseDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BigDecimal getAmountTransferred() {
        return amountTransferred;
    }

    public void setAmountTransferred(BigDecimal amountTransferred) {
        this.amountTransferred = amountTransferred;
    }

    public String getReceiverEmail() {
        return receiverEmail;
    }

    public void setReceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
    }
}
