package com.alpha.FlashRide.DTO;


public class RegisterDriverVehicleDTO {

    private Long licenseNo;
    private String upiID;
    private String driverName;
    private Integer age;
    private long mobileNo;
    private String gender;
    private String mailId;
    
    private String vehicleName;
    private String vehicleNo;
    private String vehicleType;
    private String model;
    private Integer vehicleCapacity;
    private String latitude;
    private String longitude;
    private Integer pricePerKM;
    private int averageSpeed;


    public RegisterDriverVehicleDTO() {
        super();
    }

  
    public long getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(long licenseNo) {
        this.licenseNo = licenseNo;
    }

    public String getUpiID() {
        return upiID;
    }

    public void setUpiID(String upiID) {
        this.upiID = upiID;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public long getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(long mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMailId() {
        return mailId;
    }

    public void setMailId(String mailId) {
        this.mailId = mailId;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getVehicleCapacity() {
        return vehicleCapacity;
    }

    public void setVehicleCapacity(Integer vehicleCapacity) {
        this.vehicleCapacity = vehicleCapacity;
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

    public Integer getPricePerKM() {
        return pricePerKM;
    }

    public void setPricePerKM(Integer pricePerKM) {
        this.pricePerKM = pricePerKM;
    }

    @Override
    public String toString() {
        return "RegisterDriverVehicleDTO [licenseNo=" + licenseNo + ", upiID=" + upiID + ", driverName=" + driverName
                + ", age=" + age + ", mobileNo=" + mobileNo + ", gender=" + gender + ", mailId=" + mailId
                + ", vehicleName=" + vehicleName + ", vehicleNo=" + vehicleNo + ", vehicleType=" + vehicleType
                + ", model=" + model + ", vehicleCapacity=" + vehicleCapacity + ", latitude=" + latitude
                + ", longitude=" + longitude + ", pricePerKM=" + pricePerKM + "]";
    }

	public int getAverageSpeed() {
		return averageSpeed;
	}

	public void setAverageSpeed(int averageSpeed) {
		this.averageSpeed = averageSpeed;
	}

	public void setLicenseNo(Long licenseNo) {
		this.licenseNo = licenseNo;
	}

	public RegisterDriverVehicleDTO(Long licenseNo, String upiID, String driverName, Integer age, long mobileNo,
			String gender, String mailId, String vehicleName, String vehicleNo, String vehicleType, String model,
			Integer vehicleCapacity, String latitude, String longitude, Integer pricePerKM, int averageSpeed) {
		super();
		this.licenseNo = licenseNo;
		this.upiID = upiID;
		this.driverName = driverName;
		this.age = age;
		this.mobileNo = mobileNo;
		this.gender = gender;
		this.mailId = mailId;
		this.vehicleName = vehicleName;
		this.vehicleNo = vehicleNo;
		this.vehicleType = vehicleType;
		this.model = model;
		this.vehicleCapacity = vehicleCapacity;
		this.latitude = latitude;
		this.longitude = longitude;
		this.pricePerKM = pricePerKM;
		this.averageSpeed = averageSpeed;
	}
    
}

