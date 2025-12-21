package com.alpha.FlashRide.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alpha.FlashRide.exception.CoordinatesNotFoundException;
import com.alpha.FlashRide.exception.DistanceCalculationFailedException;
import com.alpha.FlashRide.exception.InvalidLocationException;
@Service
public class LocationService {
     
	   @Autowired
	    private RestTemplate restTemplate;

	    @Value("${locationiq.api.key}")
	    private String apiKey;

	    @Value("${distancematrix.api.key}")
	    private String distanceMatrixApiKey;
	    
	//========separate method to get city from latitude ,longitude========
	    
	    private String getCityFromCoordinates(String lat, String lon) {
	        try {
	            String url = "https://us1.locationiq.com/v1/reverse?key=" + apiKey +
	                         "&lat=" + lat + "&lon=" + lon + "&format=json";

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
	
	
	 //====separate method to validate the destination, if coordinates fetches then it is correct destination
    private void validateDestination(String destination) {

        if (destination == null || destination.isBlank()) {
            throw new InvalidLocationException();
        }

        String url = "https://us1.locationiq.com/v1/search.php?key=" + apiKey +
                     "&format=json&q=" + URLEncoder.encode(destination, StandardCharsets.UTF_8);

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
        if (s == null || s.isBlank()) return false;

        for (char c : s.toCharArray()) {
            if (!Character.isDigit(c) && c != '.' && c != '-') {
                return false;
            }
        }
        return true;
    }
	
	  //to get distance from source and destination
    private Map<String, Object> getDistanceSafe(double slat, double slon, double dlat, double dlon) {

        String url = "https://api-v2.distancematrix.ai/maps/api/distancematrix/json"
                + "?origins=" + slat + "," + slon
                + "&destinations=" + dlat + "," + dlon
                + "&key=" + distanceMatrixApiKey;

        Map<String, Object> response = Optional.ofNullable(
                restTemplate.getForObject(url, Map.class)
        ).orElseThrow(() ->
                new DistanceCalculationFailedException("No response from distance matrix API")
        );

        List<Map<String, Object>> rows = Optional.ofNullable(
                (List<Map<String, Object>>) response.get("rows")
        ).orElseThrow(() ->
                new DistanceCalculationFailedException("Rows missing in distance API")
        );

        Map<String, Object> row0 = rows.stream()
                .findFirst()
                .orElseThrow(() ->
                        new DistanceCalculationFailedException("No rows available")
                );

        List<Map<String, Object>> elements = Optional.ofNullable(
                (List<Map<String, Object>>) row0.get("elements")
        ).orElseThrow(() ->
                new DistanceCalculationFailedException("Elements missing in distance API")
        );

        Map<String, Object> elem0 = elements.stream()
                .findFirst()
                .orElseThrow(() ->
                        new DistanceCalculationFailedException("No elements available")
                );

        Map<String, Object> dist = Optional.ofNullable(
                (Map<String, Object>) elem0.get("distance")
        ).orElseThrow(() ->
                new DistanceCalculationFailedException("Distance value missing")
        );

        Map<String, Object> dur = Optional.ofNullable(
                (Map<String, Object>) elem0.get("duration")
        ).orElseThrow(() ->
                new DistanceCalculationFailedException("Time value missing")
        );

        return Map.of(
                "distance", Double.parseDouble(dist.get("value").toString()),
                "time", Double.parseDouble(dur.get("value").toString())
        );
    }
  //to get coordinates from place
    private Map<String, Double> getCoordinatesSafe(String place) {

        List<Map<String, Object>> res = Optional.ofNullable(
                restTemplate.getForObject(
                        "https://us1.locationiq.com/v1/search.php?key=" + apiKey +
                        "&format=json&q=" + URLEncoder.encode(place, StandardCharsets.UTF_8),
                        List.class
                )
        ).orElseThrow(() ->
                new CoordinatesNotFoundException("No response from coordinates API")
        );

        Map<String, Object> loc = res.stream()
                .findFirst()
                .orElseThrow(() ->
                        new CoordinatesNotFoundException("Invalid place: " + place)
                );

        Double lat = Optional.ofNullable(loc.get("lat"))
                .map(Object::toString)
                .map(Double::parseDouble)
                .orElseThrow(() ->
                        new CoordinatesNotFoundException("Latitude missing for: " + place)
                );

        Double lon = Optional.ofNullable(loc.get("lon"))
                .map(Object::toString)
                .map(Double::parseDouble)
                .orElseThrow(() ->
                        new CoordinatesNotFoundException("Longitude missing for: " + place)
                );

        return Map.of("lat", lat, "lon", lon);
    }

}
