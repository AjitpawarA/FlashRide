package com.alpha.FlashRide.controller;

import com.alpha.FlashRide.ResponseStructure;
import com.alpha.FlashRide.DTO.LoginRequestDTO;
import com.alpha.FlashRide.DTO.RegisterCustomerDTO;
import com.alpha.FlashRide.DTO.RegisterDriverVehicleDTO;
import com.alpha.FlashRide.Repository.CustomerRepository;
import com.alpha.FlashRide.Repository.DriverRepository;
import com.alpha.FlashRide.Repository.UserrRepo;
import com.alpha.FlashRide.Repository.VehicleRepository;
import com.alpha.FlashRide.Service.CustomerService;
import com.alpha.FlashRide.entity.Customer;
import com.alpha.FlashRide.entity.Driver;
import com.alpha.FlashRide.entity.Userr;
import com.alpha.FlashRide.entity.Vehicle;
import com.alpha.FlashRide.exception.MobileAlreadyRegisteredException;
import com.alpha.FlashRide.security.JwtUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@RestController
@RequestMapping("/auth")
public class AuthController {
	

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private CustomerService custser;

    @Autowired
    private VehicleRepository vehicleRepository;

    private final UserrRepo userrRepository;
    private final CustomerRepository customerRepository;
    private final DriverRepository driverRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserrRepo userrRepository,
                          CustomerRepository customerRepository,
                          DriverRepository driverRepository,
                          PasswordEncoder passwordEncoder) {
        this.userrRepository = userrRepository;
        this.customerRepository = customerRepository;
        this.driverRepository = driverRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // =====================================================
    // CUSTOMER REGISTRATION
    // =====================================================
    @PostMapping("/register/customer")
    public ResponseEntity<ResponseStructure<String>> registerCustomer(
            @RequestBody RegisterCustomerDTO dto) {

        long mobileNo = dto.getMobileNo();

        if (userrRepository.existsByMobno(mobileNo)) {
            throw new MobileAlreadyRegisteredException();
        }

        Userr user = new Userr();
        user.setMobno(mobileNo);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole("CUSTOMER");
        userrRepository.save(user);

        Customer customer = new Customer();
        customer.setName(dto.getName());
        customer.setAge(dto.getAge());
        customer.setGender(dto.getGender());
        customer.setMobileNo(mobileNo);
        customer.setEmailId(dto.getEmailId());
        customer.setCurrentLoc(
                custser.getCityFromCoordinates(dto.getLatitude(), dto.getLongitude())
        );
        customer.setUserr(user);

        customerRepository.save(customer);

        ResponseStructure<String> rs = new ResponseStructure<>();
        rs.setStatuscode(200);
        rs.setMessage("Customer registered successfully");
        rs.setData("SUCCESS");

        return ResponseEntity.ok(rs);
    }

    // =====================================================
    // DRIVER REGISTRATION
    // =====================================================
    @PostMapping("/register/driver")
    public ResponseEntity<ResponseStructure<String>> registerDriver(
            @RequestBody RegisterDriverVehicleDTO dto) {

        long mobileNo = dto.getMobileNo();

        if (userrRepository.existsByMobno(mobileNo)) {
            throw new MobileAlreadyRegisteredException();
        }

        Userr userr = new Userr();
        userr.setMobno(mobileNo);
        userr.setPassword(passwordEncoder.encode(dto.getPassword()));
        userr.setRole("DRIVER");
        userrRepository.save(userr);

        Driver driver = new Driver();
        driver.setName(dto.getDriverName());
        driver.setAge(dto.getAge());
        driver.setGender(dto.getGender());
        driver.setMobileno(mobileNo);
        driver.setMailid(dto.getMailId());
        driver.setLicenseNo(dto.getLicenseNo());
        driver.setUpiid(dto.getUpiID());
        driver.setStatus("Available");
        driver.setUserr(userr);

        driverRepository.save(driver);

        Vehicle vehicle = new Vehicle();
        vehicle.setDriver(driver);
        vehicle.setName(dto.getVehicleName());
        vehicle.setVehicleNo(dto.getVehicleNo());
        vehicle.setType(dto.getVehicleType());
        vehicle.setModel(dto.getModel());
        vehicle.setCapacity(dto.getVehicleCapacity());
        vehicle.setCurrentCity(
                custser.getCityFromCoordinates(dto.getLatitude(), dto.getLongitude())
        );
        vehicle.setAvailableStatus("Available");
        vehicle.setPricePerKM(dto.getPricePerKM());
        vehicle.setAvgSpeed(dto.getAverageSpeed());

        vehicleRepository.save(vehicle);

        ResponseStructure<String> rs = new ResponseStructure<>();
        rs.setStatuscode(200);
        rs.setMessage("Driver and vehicle registered successfully");
        rs.setData("SUCCESS");

        return ResponseEntity.ok(rs);
    }
    
    @PostMapping("/login")
    public ResponseEntity<ResponseStructure<String>> login(
            @RequestBody LoginRequestDTO dto) {

        // Authenticate using Spring Security
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        String.valueOf(dto.getMobileNo()),
                        dto.getPassword()
                )
        );

        // Fetch user
        Userr user = userrRepository.findByMobno(dto.getMobileNo())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Generate JWT
        String token = jwtUtils.generateToken(
                String.valueOf(dto.getMobileNo()),
                user.getRole()
        );

        ResponseStructure<String> rs = new ResponseStructure<>();
        rs.setStatuscode(200);
        rs.setMessage("Login successful");
        rs.setData("Bearer " + token);

        return ResponseEntity.ok(rs);
    }

}
