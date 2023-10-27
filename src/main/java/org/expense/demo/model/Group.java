package org.expense.demo.model;

import java.util.List;

public class Group {
    private String groupName;
    private int groupId;
    private List<Integer> userIdList;
    private Integer amount;
    private Integer totalGroupSpend;
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public int getGroupId() {
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
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public Integer getTotalGroupSpend() {
		return totalGroupSpend;
	}
	public void setTotalGroupSpend(Integer totalGroupSpend) {
		this.totalGroupSpend = totalGroupSpend;
	}
    
}
