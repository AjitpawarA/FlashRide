package com.alpha.FlashRide.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.alpha.FlashRide.entity.Customer;

import jakarta.transaction.Transactional;

	@Repository
	public interface CustomerRepository extends JpaRepository<Customer, Integer>{


		@Modifying
	    @Transactional
	    void deleteByMobileNo(long mobileNo);

	    Optional<Customer> findByMobileNo(long mobileNo);

	   
	    Customer findByMobileNo(String mobileNo);
	   

	}

