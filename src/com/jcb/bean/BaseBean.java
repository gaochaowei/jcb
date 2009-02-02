package com.jcb.bean;

import java.io.Serializable;

import org.apache.commons.beanutils.BeanUtils;

public abstract class BaseBean implements Serializable {

	private static final long serialVersionUID = -7104957256916978546L;

	public String toString() {
		try {
			return BeanUtils.describe(this).toString();
		} catch (Exception e) {
			return this.hashCode() + "@" + this.getClass().getCanonicalName();
		}
	}
}
