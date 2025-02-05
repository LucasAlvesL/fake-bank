package com.fakebank.main.modules.services;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fakebank.main.modules.dto.Transfer.TransferRequestDTO;
import com.fakebank.main.modules.dto.Transfer.TransferResponseDTO;
import com.fakebank.main.modules.entities.TransfersEntity;
import com.fakebank.main.modules.entities.UserEntity;
import com.fakebank.main.modules.repositories.TransferRepository;
import com.fakebank.main.modules.repositories.UserRepository;
import com.fakebank.main.providers.JWTUserProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TransferService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTUserProvider jwtUserProvider;

    @Autowired
    private TransferRepository transferRepository;

    @Transactional
    public TransferResponseDTO transferMoney(TransferRequestDTO transferRequest, String token) {
        DecodedJWT decodedJWT = jwtUserProvider.validateToken(token);
        if (decodedJWT == null) {
            throw new RuntimeException("Invalid or expired token");
        }

        // Extract sender's UUID from the token subject (which is the user UUID)
        String senderUuid = decodedJWT.getSubject();  // Subject is the user UUID

        // Retrieve sender from the database using UUID
        UserEntity sender = userRepository.findById(UUID.fromString(senderUuid))
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        // Retrieve receiver from the database using email (receiverEmail)
        UserEntity receiver = userRepository.findByEmail(transferRequest.getReceiverEmail())
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        // Process the transfer
        BigDecimal amount = transferRequest.getAmount();

        if (sender.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        // Update sender and receiver balances
        sender.setBalance(sender.getBalance().subtract(amount));
        receiver.setBalance(receiver.getBalance().add(amount));

        // Save updated user entities
        userRepository.save(sender);
        userRepository.save(receiver);

        TransfersEntity transfer = new TransfersEntity();
        transfer.setSender(sender);
        transfer.setReceiverEmail(String.valueOf(receiver));
        transfer.setAmount(amount);
        transfer.setMade_at(LocalDateTime.now());  // assuming you have a transfer date field

        // Save the transfer record
        transferRepository.save(transfer);

        return new TransferResponseDTO(
                "Transfer successful",
                amount,
                receiver.getEmail()
        );
    }
}
