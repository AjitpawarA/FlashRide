package com.alpha.FlashRide.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alpha.FlashRide.entity.Userr;

@Repository
public interface UserrRepo extends JpaRepository<Userr, Integer> {

	Optional<Userr> findByMobno(long mobileNumber);

	boolean existsByMobno(long mobno);

}
