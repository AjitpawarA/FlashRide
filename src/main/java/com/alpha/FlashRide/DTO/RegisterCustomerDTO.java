package com.alpha.FlashRide.DTO;

public class RegisterCustomerDTO {

	private String name;
	private int age;
	private String gender;
	private long mobileNo;
	private String emailId;
	private String latitude;
	private String longitude;
	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public RegisterCustomerDTO(String name, int age, String gender, long mobileNo, String emailId, String latitude,
			String longitude,String passowrd) {
		super();
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.mobileNo = mobileNo;
		this.emailId = emailId;
		this.latitude = latitude;
		this.longitude = longitude;
		this.password=passowrd;
	}

	public RegisterCustomerDTO() {
		super();
	}

	@Override
	public String toString() {
		return "RegisterCustomerDTO [name=" + name + ", age=" + age + ", gender=" + gender + ", mobileNo=" + mobileNo
				+ ", emailId=" + emailId + ", latitude=" + latitude + ", longitude=" + longitude + "]";
	}

}
