package com.revature.model;

public class Account {
	private int id;
	private String type;
	private double balance;
	private String status;
	
	public Account() {
	}

	public Account(int id, String type, double balance) {
		this.id = id;
		this.type = type;
		this.balance = balance;
	}

	public Account(int id, String type, double balance, String status) {
		this.id = id;
		this.type = type;
		this.balance = balance;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return this.type+ " Account: "+this.id+" | Balance: $" + this.balance + " | Status: " + this.status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
}
