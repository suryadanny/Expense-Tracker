package org.gateway.demo.model;

import java.util.List;

public class Group {
    private String groupName;
    private Integer groupId;
    private List<Integer> userIdList;
    private Double amount;
    private Double totalGroupSpend;
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public List<Integer> getUserIdList() {
		return userIdList;
	}
	public void setUserIdList(List<Integer> userIdList) {
		this.userIdList = userIdList;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Double getTotalGroupSpend() {
		return totalGroupSpend;
	}
	public void setTotalGroupSpend(Double totalGroupSpend) {
		this.totalGroupSpend = totalGroupSpend;
	}
    
}
