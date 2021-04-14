# demoSpringBoot

@Author: Anshu Kumar
@Version: 0.1

Objective of Project:
Summary:
This API has been build for book a hotel depending upon the bonus points of the user and hotel points.

Conditions:
a. If User has 'n' bonus points and Price to book the hotel is 'n’ ,Status of room changes to "Booked".

b. If User has 'n' bonus points and Price to book the hotel is greater than 'n’ , Status of room changes to "Pending Approval".

c. Any changes to user bonus is tracked in the database.





Assumptions:
1. there will be always a valid call from UI i.e. valid HotelId should pe passed.
2. the passed hotelId will be always in non booked state i.e. only available hote booking shall be passed.
3. A user can book one or more number of booking
4. User has already registered for the user
5. hotel booking is olny poosible when the status is availale or else it will ask to contact the admin
6. Even when pending approval, the next user cannot able to book the status 		
7. No user bonus pointy alteration in case of pending approval or hotel not booked.


Table Structure for HotelBooking.
Table 1:

HotelBookingStatus

HotelId (PK)
HoteName
BookingPoints
BookingStatus
lastUpdatedDate
lastModifiedBy

Create Query:

create table demo.HotelBookingStatus 
(HotelId character varying(20) primary Key, 
HoteName character varying(100) not null, 
BookingPoints Numeric  not null,
BookingStatus character varying(20) Default 'Available'  not null,
lastUpdatedDate timestamp(0) without time zone,
lastModifiedBy character varying(20));

Insert into HotelBookingStatus Values( 'H001','Hotel One', 45, 'Available',null,null);
Insert into HotelBookingStatus Values( 'H002','Hotel Two', 15, 'Available',null,null);
Insert into HotelBookingStatus Values( 'H003','Hotel Three', 75, 'Available',null,null);
Insert into HotelBookingStatus Values( 'H004','Hotel Three', 55, 'Available',null,null);
Insert into HotelBookingStatus Values( 'H005','Hotel Four', 35, 'Available',null,null);


ALTER TABLE ONLY HotelBookingStatus
    ADD CONSTRAINT fk_userId FOREIGN KEY (lastModifiedBy) REFERENCES UserDetails(UserId);
Table 2:

UserId (Pk)
UserPoins

create table demo.UserDetails 
(UserId character varying(20) primary Key, 
UserPoins numeric not null);


Insert into UserDetails Values( 'U001',145);
Insert into UserDetails Values( 'U002',10);
Insert into UserDetails Values( 'U003',58);

API Design
Hotel
Booking API
	
URI:
http://localhost:8087/bookHotel/{HoteId}/{userId}

Request Headers:
HTTP Headers:
Accept: application/json

Request Parameters:

Path Parameter: 
1. HotelId (String)
2. UserId (String)

Response Paramter:

Success:
Response body Will be in JSON format.
200 : success when the user have sufficient userPoints To book the hotels
When the request is accepted but requires additonal approval
201 : Not acceptable request as the user points are less than booking
when the user sends the incorrect ID's:
400 : Bad request , with message Either the user Id or the Hotel Id is not valid, Contract Admin!
when hotel is not available then 
406, Not Acceptable
Failure:
500 : Bad request/internal server error when the user donot pass the correct input or format.


To call the API:
Below URL can be used:
http://localhost:8087/bookHotel/H004/U004
along with the header as Accept as 'application/json'
Request type should be put.
 
 
 
