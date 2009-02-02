package com.jcb.bean;

import java.util.Date;

public class HDBResaleBean extends BaseBean {

	private static final long serialVersionUID = -3104018004895130390L;
	private String town;
	private String street;
	private String type;
	private String blk;
	private String story;
	private float floorArea;
	private Date leaseCommenceDate;
	private int resalePrice;
	private Date resaleApprovalDate;

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBlk() {
		return blk;
	}

	public void setBlk(String blk) {
		this.blk = blk;
	}

	public String getStory() {
		return story;
	}

	public void setStory(String story) {
		this.story = story;
	}

	public float getFloorArea() {
		return floorArea;
	}

	public void setFloorArea(float floorArea) {
		this.floorArea = floorArea;
	}

	public Date getLeaseCommenceDate() {
		return leaseCommenceDate;
	}

	public void setLeaseCommenceDate(Date leaseCommenceDate) {
		this.leaseCommenceDate = leaseCommenceDate;
	}

	public int getResalePrice() {
		return resalePrice;
	}

	public void setResalePrice(int resalePrice) {
		this.resalePrice = resalePrice;
	}

	public Date getResaleApprovalDate() {
		return resaleApprovalDate;
	}

	public void setResaleApprovalDate(Date resaleApprovalDate) {
		this.resaleApprovalDate = resaleApprovalDate;
	}

}
