package com.alpha.FlashRide.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alpha.FlashRide.entity.Payment;


@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer>{
  
}
