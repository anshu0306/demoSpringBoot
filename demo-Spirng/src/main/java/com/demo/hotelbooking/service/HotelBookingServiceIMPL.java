package com.demo.hotelbooking.service;

import java.util.Date;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.demo.hoteBookingException.HotelBookingException;
import com.demo.hotelbooking.entity.HotelBookingStatusEntity;
import com.demo.hotelbooking.entity.UserDetailsEntity;
import com.demo.hotelbooking.repository.HotelBookingStatusRepository;
import com.demo.hotelbooking.repository.UserDeatailsRepository;

@Service
public class HotelBookingServiceIMPL implements HotelBookingService{
	private static final Logger log = LoggerFactory.getLogger(HotelBookingServiceIMPL.class);
	@Autowired
	private UserDeatailsRepository userDeatailsRepository;

	@Autowired
	private HotelBookingStatusRepository hotelBookingStatusRepository;

	@Override
	public String doBooking(String userId, String hotelId) throws HotelBookingException{
		UserDetailsEntity ud = 	null;
		HotelBookingStatusEntity hbs = null;
		String returnval = "unknown";
		int updatedPoints ;
		try {
			Optional<HotelBookingStatusEntity> hotelEntity = 
					hotelBookingStatusRepository.findById(hotelId);
			Optional<UserDetailsEntity> userEntity = userDeatailsRepository.findById(userId);
			if(hotelEntity.isPresent() && userEntity.isPresent()) {
				ud = userEntity.get();
				hbs = hotelEntity.get();
				if(ud.getUserPoints() >= hbs.getBookingPoints() && hbs.getBookingStatus().equals("Available")) {
					returnval = "Booked";
					updatedPoints = ud.getUserPoints()-hbs.getBookingPoints();

				}else if(ud.getUserPoints() < hbs.getBookingPoints() && hbs.getBookingStatus().equals("Available")){
					
					returnval = "Pending Approval";
					updatedPoints = ud.getUserPoints();
				}else {
					throw new HotelBookingException("Either hotel is not available or User has less Bonus points",HttpStatus.NOT_ACCEPTABLE);
					
				}
				hbs.setBookingStatus(returnval);
				hbs.setLastUpdatedDate(new Date());
				hbs.setLastModifiedBy(userId);
				ud.setUserPoints(updatedPoints);
				
				hotelBookingStatusRepository.saveAndFlush(hbs);
				userDeatailsRepository.saveAndFlush(ud);

			}else {
				throw new HotelBookingException("Either the user Id or the Hotel Id is not valid, Contract Admin!",HttpStatus.BAD_REQUEST);
			}

		}catch(Exception e) {
			throw  new HotelBookingException(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return returnval;
	}
}
