package org.jp.sale.beans;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SaleRecords {

	@Expose
	@SerializedName("store")
	private String storeName;
	
	@Expose
	@SerializedName("items")
	private List<Record[]> records;
	
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public List<Record[]> getRecords() {
		return records;
	}
	public void setRecords(List<Record[]> records) {
		this.records = records;
	}
}
