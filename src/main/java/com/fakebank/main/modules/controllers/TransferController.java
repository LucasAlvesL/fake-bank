package com.fakebank.main.modules.controllers;

import com.fakebank.main.modules.dto.Transfer.TransferRequestDTO;
import com.fakebank.main.modules.dto.Transfer.TransferResponseDTO;
import com.fakebank.main.modules.entities.TransfersEntity;
import com.fakebank.main.modules.repositories.UserRepository;
import com.fakebank.main.modules.services.TransferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users/transfer")
@Tag(name = "Transfer", description = "Transfer Controller")
public class TransferController {

    @Autowired
    private TransferService transferService;

    @Autowired
    private UserRepository userRepository;

    @Operation(summary = "Transfer money", description = "Performs a money transfer between users.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Transfer successful",
                    content = @Content(schema = @Schema(implementation = TransferResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request - Invalid transfer details",
                    content = @Content(schema = @Schema(implementation = TransferResponseDTO.class)))
    })
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<TransferResponseDTO> transfer(HttpServletRequest request, @Valid @RequestBody TransferRequestDTO transferRequest) {
        try {
            String authorizationHeader = request.getHeader("Authorization");
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new TransferResponseDTO("Invalid or missing Authorization header"));
            }
            String token = authorizationHeader.replace("Bearer ", "");

            TransferResponseDTO response = transferService.transferMoney(transferRequest, token);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new TransferResponseDTO(e.getMessage()));
        }
    }
}
