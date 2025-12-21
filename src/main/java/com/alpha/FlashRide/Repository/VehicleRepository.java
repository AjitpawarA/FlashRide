package com.alpha.FlashRide.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.alpha.FlashRide.entity.Vehicle;


@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

    @Query("SELECT v FROM Vehicle v " +
            "WHERE LOWER(TRIM(v.currentCity)) LIKE LOWER(CONCAT('%', TRIM(:city), '%')) " +
            "AND v.availableStatus = :status")
    List<Vehicle> findVehiclesByCity(String city, String status);

    List<Vehicle> findByAvailableStatus(String status);
}
