package com.deepak.beans;

public class Address {
	private int houseNo;
	private String locality;
	private String city;
	private int pinCode;
	private String state;

	public Address(int hn, String lc, String ct, int pin, String st) {
		this.houseNo = hn;
		this.locality = lc;
		this.city = ct;
		this.pinCode = pin;
		this.state = st;
	}

	public void display() {
		System.out.println("Address: " + "House No " + this.houseNo + ", Locality " + this.locality + ", City "
				+ this.city + ", State " + this.state + ", PinCode : " + this.pinCode);
	}
}
