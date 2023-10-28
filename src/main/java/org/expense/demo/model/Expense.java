package org.expense.demo.model;

import java.sql.Timestamp;
import java.util.List;

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
	private Timestamp trans_dttm;
	private Integer owedUserId;
	private List<Integer> owingUserId;
	public List<Integer> getOwingUserId() {
		return owingUserId;
	}
	public void setOwingUserId(List<Integer> owingUserId) {
		this.owingUserId = owingUserId;
	}
	private Integer groupId;
	
	public Timestamp getTrans_dttm() {
		return trans_dttm;
	}
	public void setTrans_dttm(Timestamp trans_dttm) {
		this.trans_dttm = trans_dttm;
	}
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
