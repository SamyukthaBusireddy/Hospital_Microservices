package com.hospital.billings.reposiories;

import com.hospital.billings.entities.BillingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillingRepository extends JpaRepository<BillingEntity, Long> {
}