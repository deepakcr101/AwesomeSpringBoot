package com.deepak.beans;

public class Address {
	private int houseNo;
	private String locality;
	private String city;
	private int pinCode;
	private String state;

	public int getHouseNo() {
		return houseNo;
	}

	public void setHouseNo(int houseNo) {
		this.houseNo = houseNo;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getPinCode() {
		return pinCode;
	}

	public void setPinCode(int pinCode) {
		this.pinCode = pinCode;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void display() {
		System.out.println("Address: " + "House No " + this.houseNo + ", Locality " + this.locality + ", City "
				+ this.city + ", State " + this.state + ", PinCode : " + this.pinCode);
	}
}
