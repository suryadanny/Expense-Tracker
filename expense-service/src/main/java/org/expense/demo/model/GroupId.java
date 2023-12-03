package org.expense.demo.model;

import java.io.Serializable;
import java.util.List;

public class GroupId implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5833525579213299644L;
	List<Integer> groupIds;

	public List<Integer> getGroupIds() {
		return groupIds;
	}

	public void setGroupIds(List<Integer> groupIds) {
		this.groupIds = groupIds;
	}

}
