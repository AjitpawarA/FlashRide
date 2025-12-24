package com.alpha.FlashRide.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alpha.FlashRide.ResponseStructure;
import com.alpha.FlashRide.DTO.AvailableVehiclesDTO;
import com.alpha.FlashRide.DTO.CustomerActiveBookingDTO;
import com.alpha.FlashRide.DTO.CustomerCancelBookingResponseDTO;
import com.alpha.FlashRide.DTO.RegisterCustomerDTO;
import com.alpha.FlashRide.DTO.VehicleDetailsDTO;
import com.alpha.FlashRide.Repository.BookingRepository;
import com.alpha.FlashRide.Repository.CustomerRepository;
import com.alpha.FlashRide.Repository.UserrRepo;
import com.alpha.FlashRide.Repository.VehicleRepository;
import com.alpha.FlashRide.entity.Booking;
import com.alpha.FlashRide.entity.Customer;
import com.alpha.FlashRide.entity.Userr;
import com.alpha.FlashRide.entity.Vehicle;
import com.alpha.FlashRide.exception.CoordinatesNotFoundException;
import com.alpha.FlashRide.exception.CustomerNotFoundException;
import com.alpha.FlashRide.exception.DistanceCalculationFailedException;
import com.alpha.FlashRide.exception.InvalidLocationException;

import jakarta.transaction.Transactional;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepo;

	@Autowired
	private VehicleRepository vehicleRepo;

	@Autowired
	private BookingRepository bookingRepo;

	@Autowired
	private LocationService ls;

	@Autowired
	private RestTemplate restTemplate;

	@Value("${locationiq.api.key}")
	private String apiKey;

	@Value("${distancematrix.api.key}")
	private String distanceMatrixApiKey;
	
	@Autowired
	private UserrRepo userrRepo;

//========separate method to get city from latitude ,longitude========

	public String getCityFromCoordinates(String lat, String lon) {
		try {
			String url = "https://us1.locationiq.com/v1/reverse?key=" + apiKey + "&lat=" + lat + "&lon=" + lon
					+ "&format=json";

			Map response = restTemplate.getForObject(url, Map.class);
			Map address = (Map) response.get("address");

			if (address.containsKey("city"))
				return address.get("city").toString();

			if (address.containsKey("town"))
				return address.get("town").toString();

			if (address.containsKey("village"))
				return address.get("village").toString();

			return "Unknown";
		} catch (Exception e) {
			return "Unknown";
		}
	}

	// ====separate method to validate the destination, if coordinates fetches then
	// it is correct destination
	private void validateDestination(String destination) {

		if (destination == null || destination.isBlank()) {
			throw new InvalidLocationException();
		}

		String url = "https://us1.locationiq.com/v1/search.php?key=" + apiKey + "&format=json&q="
				+ URLEncoder.encode(destination, StandardCharsets.UTF_8);

		Object obj = restTemplate.getForObject(url, Object.class);

		// Response should be a list
		if (!(obj instanceof List<?> list)) {
			throw new InvalidLocationException();
		}

		if (list.isEmpty()) {
			throw new InvalidLocationException();
		}

		// First element should be a map
		Object first = list.get(0);
		if (!(first instanceof Map<?, ?> map)) {
			throw new InvalidLocationException();
		}

		if (!map.containsKey("lat") || !map.containsKey("lon")) {
			throw new InvalidLocationException();
		}

		String lat = map.get("lat").toString();
		String lon = map.get("lon").toString();

		if (!isNumber(lat) || !isNumber(lon)) {
			throw new InvalidLocationException();
		}
	}

	private boolean isNumber(String s) {
		if (s == null || s.isBlank())
			return false;

		for (char c : s.toCharArray()) {
			if (!Character.isDigit(c) && c != '.' && c != '-') {
				return false;
			}
		}
		return true;
	}

	// =============================================================
	public ResponseStructure<String> saveCustomer(RegisterCustomerDTO dto) {

		String city = getCityFromCoordinates(dto.getLatitude(), dto.getLongitude());

		Customer c = new Customer();
		c.setName(dto.getName());
		c.setEmailId(dto.getEmailId());
		c.setAge(dto.getAge());
		c.setGender(dto.getGender());
		c.setMobileNo(dto.getMobileNo());
		c.setCurrentLoc(city);
		// booking list remains empty

		Userr user = new Userr();
		user.setMobno(dto.getMobileNo());
		user.setPassword(dto.getPassword());
		user.setRole("CUSTOMER");
		
		userrRepo.save(user);
		customerRepo.save(c);

		ResponseStructure<String> rs = new ResponseStructure<>();
		rs.setStatuscode(HttpStatus.OK.value());
		rs.setMessage("Customer registered successfully");
		rs.setData("Saved");
		return rs;
	}

	// ===========================================================

	@Transactional
	public ResponseStructure<String> deletecustomer(long mobileNo) {

		Customer c = customerRepo.findByMobileNo(mobileNo).orElseThrow(CustomerNotFoundException::new);

		customerRepo.delete(c);

		ResponseStructure<String> rs = new ResponseStructure<>();
		rs.setStatuscode(HttpStatus.OK.value());
		rs.setMessage("Customer deleted");
		rs.setData("Deleted");
		return rs;
	}
//=========================================================================

	public ResponseStructure<Customer> findCustomer(long mobileNo) {

		Customer c = customerRepo.findByMobileNo(mobileNo).orElseThrow(CustomerNotFoundException::new);

		ResponseStructure<Customer> rs = new ResponseStructure<>();
		rs.setStatuscode(HttpStatus.OK.value());
		rs.setMessage("Customer found");
		rs.setData(c);
		return rs;
	}

//==========================================================================
	public ResponseStructure<AvailableVehiclesDTO> getAvailableVehicles(long mobileNumber, String destinationLocation) {

		ResponseStructure<AvailableVehiclesDTO> structure = new ResponseStructure<>();

		// Fetch customer
		Customer customer = customerRepo.findByMobileNo(mobileNumber)
				.orElseThrow(() -> new CustomerNotFoundException());

		String sourceLocation = customer.getCurrentLoc();
		if (sourceLocation == null || sourceLocation.isBlank() || sourceLocation == destinationLocation
				|| destinationLocation == null || destinationLocation.isBlank()) {

			throw new InvalidLocationException();
		}

		Map<String, Double> sourceCoords = getCoordinatesSafe(sourceLocation);
		Map<String, Double> destCoords = getCoordinatesSafe(destinationLocation);

		if (destCoords == null || !destCoords.containsKey("lat") || !destCoords.containsKey("lon"))
			throw new InvalidLocationException();

		double slat = sourceCoords.get("lat");
		double slon = sourceCoords.get("lon");
		double dlat = destCoords.get("lat");
		double dlon = destCoords.get("lon");

		Map<String, Object> distMap = getDistanceSafe(slat, slon, dlat, dlon);

		double distanceMeters = (double) distMap.get("distance");
		double durationSeconds = (double) distMap.get("time");

		double distanceKm = distanceMeters / 1000.0;
		double durationMinutes = durationSeconds / 60.0;
		double avgSpeed = (distanceKm / (durationMinutes / 60.0));

		List<Vehicle> vehicles = vehicleRepo.findVehiclesByCity(sourceLocation, "Available");

		List<VehicleDetailsDTO> dtoList = new ArrayList<>();
		for (Vehicle v : vehicles) {

			int fare = (int) (distanceKm * v.getPricePerKM());
			int estTime = (int) durationMinutes;
			double actualSpeed = (distanceKm / (durationMinutes / 60.0));
			v.setAvgSpeed(actualSpeed);

			VehicleDetailsDTO dto = new VehicleDetailsDTO();
			dto.setV(v);
			dto.setFare(fare);
			dto.setEstimatedTime(estTime);
			dto.setAveragespeed(actualSpeed);
			dtoList.add(dto);
		}

		AvailableVehiclesDTO available = new AvailableVehiclesDTO();
		available.setAvailableVehicles(dtoList);
		available.setSourceLocation(sourceLocation);
		available.setDestination(destinationLocation);
		available.setDistance(distanceKm);
		available.setC(customer);

		structure.setMessage("Available vehicles fetched successfully");
		structure.setStatuscode(HttpStatus.OK.value());
		structure.setData(available);

		return structure;
	}

//===================to get coordinates from place==============
	private Map<String, Double> getCoordinatesSafe(String place) {

		List<Map<String, Object>> res = Optional
				.ofNullable(restTemplate.getForObject("https://us1.locationiq.com/v1/search.php?key=" + apiKey
						+ "&format=json&q=" + URLEncoder.encode(place, StandardCharsets.UTF_8), List.class))
				.orElseThrow(() -> new CoordinatesNotFoundException("No response from coordinates API"));

		Map<String, Object> loc = res.stream().findFirst()
				.orElseThrow(() -> new CoordinatesNotFoundException("Invalid place: " + place));

		Double lat = Optional.ofNullable(loc.get("lat")).map(Object::toString).map(Double::parseDouble)
				.orElseThrow(() -> new CoordinatesNotFoundException("Latitude missing for: " + place));

		Double lon = Optional.ofNullable(loc.get("lon")).map(Object::toString).map(Double::parseDouble)
				.orElseThrow(() -> new CoordinatesNotFoundException("Longitude missing for: " + place));

		return Map.of("lat", lat, "lon", lon);
	}

	// to get distance from source and destination
	private Map<String, Object> getDistanceSafe(double slat, double slon, double dlat, double dlon) {

		String url = "https://api-v2.distancematrix.ai/maps/api/distancematrix/json" + "?origins=" + slat + "," + slon
				+ "&destinations=" + dlat + "," + dlon + "&key=" + distanceMatrixApiKey;

		Map<String, Object> response = Optional.ofNullable(restTemplate.getForObject(url, Map.class))
				.orElseThrow(() -> new DistanceCalculationFailedException("No response from distance matrix API"));

		List<Map<String, Object>> rows = Optional.ofNullable((List<Map<String, Object>>) response.get("rows"))
				.orElseThrow(() -> new DistanceCalculationFailedException("Rows missing in distance API"));

		Map<String, Object> row0 = rows.stream().findFirst()
				.orElseThrow(() -> new DistanceCalculationFailedException("No rows available"));

		List<Map<String, Object>> elements = Optional.ofNullable((List<Map<String, Object>>) row0.get("elements"))
				.orElseThrow(() -> new DistanceCalculationFailedException("Elements missing in distance API"));

		Map<String, Object> elem0 = elements.stream().findFirst()
				.orElseThrow(() -> new DistanceCalculationFailedException("No elements available"));

		Map<String, Object> dist = Optional.ofNullable((Map<String, Object>) elem0.get("distance"))
				.orElseThrow(() -> new DistanceCalculationFailedException("Distance value missing"));

		Map<String, Object> dur = Optional.ofNullable((Map<String, Object>) elem0.get("duration"))
				.orElseThrow(() -> new DistanceCalculationFailedException("Time value missing"));

		return Map.of("distance", Double.parseDouble(dist.get("value").toString()), "time",
				Double.parseDouble(dur.get("value").toString()));
	}

//============= customer active booking=========
	public ResponseEntity<ResponseStructure<CustomerActiveBookingDTO>> CustomerSeeActiveBooking(long mobileNo) {

		Customer customer = customerRepo.findByMobileNo(mobileNo)
				.orElseThrow(() -> new RuntimeException("Customer not found"));

		customer.setBookingflag(true);
		customerRepo.save(customer);

		Booking booking = bookingRepo.findActiveBookingByCustomerId(customer.getMobileNo());

		if (booking == null) {
			customer.setBookingflag(false);
			customerRepo.save(customer);
			throw new RuntimeException("No current booking found");
		}

		CustomerActiveBookingDTO customerActiveBookingDTO = new CustomerActiveBookingDTO();
		customerActiveBookingDTO.setCustomername(customer.getName());
		customerActiveBookingDTO.setCustomerMobile(customer.getMobileNo());
		customerActiveBookingDTO.setBooking(booking);
		customerActiveBookingDTO.setCurrentLocation(booking.getVehicle().getCurrentCity());

		boolean isBooked = booking.getBookingStatus() != null
				&& booking.getBookingStatus().trim().equalsIgnoreCase("booked");
		customer.setBookingflag(isBooked);
		customerRepo.save(customer);

		// Response
		ResponseStructure<CustomerActiveBookingDTO> rs = new ResponseStructure<>();
		rs.setStatuscode(HttpStatus.OK.value());
		rs.setMessage("Active Booking fetched successfully");
		rs.setData(customerActiveBookingDTO);

		return new ResponseEntity<>(rs, HttpStatus.OK);
	}

//--------------------------------------------------------------------------------
	public CustomerCancelBookingResponseDTO cancelBookingByCustomer(int custId, int bookingId) {

		// Fetch customer
		Customer customer = customerRepo.findById(custId).orElseThrow(() -> new RuntimeException("Customer not found"));

		// Fetch booking
		Booking booking = bookingRepo.findById(bookingId).orElseThrow(() -> new RuntimeException("Booking not found"));

		int penaltyCount = customer.getPenaltyCount();
		int fare = booking.getFare();
		int penaltyAmount = 0;

		// Apply penalty based on previous cancellations
		if (penaltyCount == 1) {
			penaltyAmount = (fare * 10) / 100; // 10% penalty
		} else if (penaltyCount >= 2) {
			penaltyAmount = (fare * 20) / 100; // 20% penalty
		}

		// Update booking status
		booking.setBookingStatus("cancelledByCustomer");

		// Increment penalty count for future bookings
		customer.setPenaltyCount(penaltyCount + 1);

		// Save both entities
		bookingRepo.save(booking);
		customerRepo.save(customer);

		// Prepare message
		String message = (penaltyAmount == 0) ? "Booking cancelled successfully. No penalty applied."
				: "Booking cancelled successfully. Penalty applied: ₹" + penaltyAmount;

		return new CustomerCancelBookingResponseDTO(message, "CANCELLED", penaltyAmount);
	}
}
