package com.alpha.FlashRide.entity;

	import java.util.ArrayList;
	import java.util.List;

	import com.fasterxml.jackson.annotation.JsonIgnore;

	import jakarta.persistence.Column;
	import jakarta.persistence.Entity;
	import jakarta.persistence.GeneratedValue;
	import jakarta.persistence.GenerationType;
	import jakarta.persistence.Id;

	import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

	@Entity
	public class Customer {
		
		public Userr getUserr() {
			return userr;
		}

		public void setUserr(Userr userr) {
			this.userr = userr;
		}

		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private int id;
		private String name;
		private int age;
		private String gender;
		private long mobileNo;
		private String emailId;
		private String currentLoc;
		private int penaltyCount = 0;

		@OneToOne
		private Userr userr;
		
		@Column(name="bookingflag")
		private boolean bookingflag = false;
		
		@OneToMany
		@JsonIgnore
		private List<Booking> bookinglist = new ArrayList<>();

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}

		public String getGender() {
			return gender;
		}

		public void setGender(String gender) {
			this.gender = gender;
		}

		public long getMobileNo() {
			return mobileNo;
		}

		public void setMobileNo(long mobileNo) {
			this.mobileNo = mobileNo;
		}

		public String getEmailId() {
			return emailId;
		}

		public void setEmailId(String emailId) {
			this.emailId = emailId;
		}

		public String getCurrentLoc() {
			return currentLoc;
		}

		public void setCurrentLoc(String currentLoc) {
			this.currentLoc = currentLoc;
		}

		public boolean isBookingflag() {
			return bookingflag;
		}

		public void setBookingflag(boolean bookingflag) {
			this.bookingflag = bookingflag;
		}

		public List<Booking> getBookinglist() {
			return bookinglist;
		}

		public void setBookinglist(List<Booking> bookinglist) {
			this.bookinglist = bookinglist;
		}

		public Customer(String name, int age, String gender, long mobileNo, String emailId, String currentLoc,
				boolean bookingflag, List<Booking> bookinglist, Userr userr) {
			super();
			this.name = name;
			this.age = age;
			this.gender = gender;
			this.mobileNo = mobileNo;
			this.emailId = emailId;
			this.currentLoc = currentLoc;
			this.bookingflag = bookingflag;
			this.bookinglist = bookinglist;
			this.userr=userr;
		}

		public Customer() {
			super();
		}

		@Override
		public String toString() {
			return "Customer [id=" + id + ", name=" + name + ", age=" + age + ", gender=" + gender + ", mobileNo="
					+ mobileNo + ", emailId=" + emailId + ", currentLoc=" + currentLoc + ", bookingflag=" + bookingflag
					+ ", bookinglist=" + bookinglist + "]";
		}

		public int getPenaltyCount() {
			return penaltyCount;
		}

		public void setPenaltyCount(int penaltyCount) {
			this.penaltyCount = penaltyCount;
		}
		
	}


