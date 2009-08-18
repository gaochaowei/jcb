package com.jcb.bean;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;

public abstract class BaseBean implements Serializable {

	private Date createDate;
	private Date updateDate;

	private static final long serialVersionUID = -7104957256916978546L;

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String toString() {
		try {
			return BeanUtils.describe(this).toString();
		} catch (Exception e) {
			return this.hashCode() + "@" + this.getClass().getCanonicalName();
		}
	}
}
