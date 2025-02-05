package com.fakebank.main.modules.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_transfers")
@Schema(description = "Shows all the transfers made by the user")
public class TransfersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "transfer_id", nullable = false, updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", referencedColumnName = "user_id")
    private UserEntity sender;

    @Column(name = "receiver_email", nullable = false)
    private String receiverEmail;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "made_at", nullable = false, updatable = false)
    private LocalDateTime made_at = LocalDateTime.now();

    public TransfersEntity() {
    }

    public TransfersEntity(UserEntity sender, String receiverEmail, BigDecimal amount, LocalDateTime made_at) {
        this.sender = sender;
        this.receiverEmail = receiverEmail;
        this.amount = amount;
        this.made_at = made_at;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UserEntity getSender() {
        return sender;
    }

    public void setSender(UserEntity sender) {
        this.sender = sender;
    }

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

    public LocalDateTime getMade_at() {
        return made_at;
    }

    public void setMade_at(LocalDateTime made_at) {
        this.made_at = made_at;
    }
}
