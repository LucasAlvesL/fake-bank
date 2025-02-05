package com.fakebank.main.modules.repositories;

import com.fakebank.main.modules.entities.TransfersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransferRepository extends JpaRepository<TransfersEntity, UUID> {
}
