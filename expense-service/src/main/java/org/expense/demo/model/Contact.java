package org.expense.demo.model;

public class Contact {
	private int id;
	private String username;
	private String name;
	private String email;
	private String mobile;
	private Double amountOwed;
	public int getId() {
		return id;
	}
	public Contact setId(int id) {
		this.id = id;
		return this;
	}
	public String getUsername() {
		return username;
	}
	public Contact setUsername(String username) {
		this.username = username;
		return this;
	}
	public String getName() {
		return name;
	}
	public Contact setName(String name) {
		this.name = name;
		return this;
	}
	public Double getAmountOwed() {
		return amountOwed;
	}
	public void setAmountOwed(Double amountOwed) {
		this.amountOwed = amountOwed;
	}
	public String getEmail() {
		return email;
	}
	public Contact setEmail(String email) {
		this.email = email;
		return this;
	}
	public String getMobile() {
		return mobile;
	}
	public Contact setMobile(String mobile) {
		this.mobile = mobile;
		return this;
	}
	
}
