package com.demo.hotelbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.hotelbooking.entity.HotelBookingStatusEntity;

@Repository
public interface HotelBookingStatusRepository extends JpaRepository<HotelBookingStatusEntity,String>{

	

}
