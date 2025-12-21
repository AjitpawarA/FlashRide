package com.alpha.FlashRide.entity;

	import java.util.ArrayList;
	import java.util.List;

	import org.hibernate.annotations.Cascade;


	import com.fasterxml.jackson.annotation.JsonIgnore;

	import jakarta.persistence.CascadeType;
	import jakarta.persistence.Entity;
	import jakarta.persistence.GeneratedValue;
	import jakarta.persistence.GenerationType;
	import jakarta.persistence.Id;
	import jakarta.persistence.JoinColumn;
	import jakarta.persistence.OneToMany;
	import jakarta.persistence.OneToOne;

	@Entity
	public class Driver {

		@Id
		@GeneratedValue (strategy = GenerationType.AUTO)
		private int id;
		private long licenseNo;
		private String upiid;
		private String name;
		private String status="Available";
		private int age;
		private Long mobileno;
		private String gender;
		private String mailid;
		
		
		@OneToOne(mappedBy = "driver", cascade = CascadeType.ALL, orphanRemoval = true)
		@JsonIgnore
		private Vehicle vehicle;
		
		 @OneToMany(cascade = CascadeType.ALL)
		 private List<Booking> bookings = new ArrayList<>();

		public long getLicenseNo() {
			return licenseNo;
		}

		public void setLicenseNo(long licenseNo) {
			this.licenseNo = licenseNo;
		}

		public String getUpiid() {
			return upiid;
		}

		public void setUpiid(String upiid) {
			this.upiid = upiid;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}

		public Long getMobileno() {
			return mobileno;
		}

		public void setMobileno(Long mobileno) {
			this.mobileno = mobileno;
		}

		public String getGender() {
			return gender;
		}

		public void setGender(String gender) {
			this.gender = gender;
		}

		public String getMailid() {
			return mailid;
		}

		public void setMailid(String mailid) {
			this.mailid = mailid;
		}

		public Vehicle getVehicle() {
			return vehicle;
		}

		public void setVehicle(Vehicle vehicle) {
			this.vehicle = vehicle;
		}

		public List<Booking> getBookings() {
			return bookings;
		}

		public void setBookings(List<Booking> bookings) {
			this.bookings = bookings;
		}

		public Driver(long licenseNo, String upiid, String name, String status, int age, long mobileno, String gender,
				String mailid, Vehicle vehicle, List<Booking> bookinglist) {
			super();
			this.licenseNo = licenseNo;
			this.upiid = upiid;
			this.name = name;
			this.status = status;
			this.age = age;
			this.mobileno = mobileno;
			this.gender = gender;
			this.mailid = mailid;
			this.vehicle = vehicle;
			this.bookings = bookinglist;
		}

		public Driver() {
			super();
		}

		@Override
		public String toString() {
			return "Driver [id=" + id + ", licenseNo=" + licenseNo + ", upiid=" + upiid + ", name=" + name + ", status="
					+ status + ", age=" + age + ", mobileno=" + mobileno + ", gender=" + gender + ", mailid=" + mailid
					+ ", vehicle=" + vehicle + ", bookinglist=" + bookings + "]";
		}

		
	}

