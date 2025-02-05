package com.fakebank.main.modules.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_users")
@Schema(description = "Represents a system user with authentication and banking details")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id", updatable = false, nullable = false)
    @Schema(description = "Unique identifier for the user.", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID id;

    @Column(name = "full_name", nullable = false)
    @NotNull
    @Schema(description = "Full name of the user.", example = "John Doe")
    private String fullName;

    @Column(name = "age")
    @Min(value = 0, message = "Age must be a positive number")
    @Schema(description = "Age of the user.", example = "30")
    private int age;

    @Column(name = "email", nullable = false, unique = true)
    @Email(message = "Invalid email address")
    @NotNull
    @Schema(description = "User's email address (used as UUID).", example = "user@example.com")
    private String email;

    @Column(name = "password", nullable = false)
    @Size(min = 5, max = 100, message = "Password must be at least 5 characters long")
    @NotNull
    @Schema(description = "Hashed password for authentication.", example = "$2a$10$abcdefgh...", hidden = true)
    private String password;

    @Column(name = "cpf", nullable = false, unique = true)
    @Schema(description = "Brazilian CPF (Cadastro de Pessoas Físicas) number.", example = "123.456.789-09")
    @NotNull
    private String cpf;

    @Column(name = "balance", nullable = false)
    @NotNull
    @Schema(description = "User's account balance.", example = "1000.50")
    private BigDecimal balance = BigDecimal.ZERO;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    @Schema(description = "Timestamp when the user was created.", example = "2025-02-05T10:15:30")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @Schema(description = "Timestamp when the user was last updated.", example = "2025-02-05T10:20:00")
    private LocalDateTime updatedAt;

    public UserEntity() {
    }

    public UserEntity(String fullName, int age, String email, String password, String cpf, BigDecimal balance, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.fullName = fullName;
        this.age = age;
        this.email = email;
        this.password = password;
        this.cpf = cpf;
        this.balance = balance;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}

