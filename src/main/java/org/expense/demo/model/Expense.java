package org.expense.demo.model;

import javax.xml.bind.annotation.XmlRootElement;
//object model of the visitor 
@XmlRootElement
public class Expense {

	private Integer expenseId;
	private String title;
	private String note;
	private String category;
	private String paymentMode;
	private Double amount;
	private String currency;
	private Integer userId;
	private Integer owedUserId;
	private Integer groupId;
	
	public Integer getExpenseId() {
		return expenseId;
	}
	public void setExpenseId(Integer expenseId) {
		this.expenseId = expenseId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getPaymentMode() {
		return paymentMode;
	}
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getOwedUserId() {
		return owedUserId;
	}
	public void setOwedUserId(Integer owedUserId) {
		this.owedUserId = owedUserId;
	}
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
}
