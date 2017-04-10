package com.redhat.products;


import java.util.List;


public class QueryParams {
	
	private List<String> productIds;
	private String fromPostcode;
	private String toPostcode;
	private String arrivalDate;
	private String target;


	public QueryParams(List<String> productIds, String fromPostcode, String toPostcode, String arrivalDate, String target) {
		this.productIds = productIds;
		this.fromPostcode = fromPostcode;
		this.toPostcode = toPostcode;
		this.arrivalDate = arrivalDate;
		this.target = target;
	}
	
	public QueryParams() {
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Ids: ");
		for (String id : productIds) {
			sb.append(id + ", ");
		}
		sb.append("\nfroPostcode: ").append(fromPostcode);
		sb.append("\ntoPostcode: ").append(toPostcode);
		sb.append("\narrivalDate: ").append(arrivalDate);
		sb.append("\ntarget: ").append(target);
		sb.append("\n");
		
		return sb.toString();
	}
	
	public List<String> getProductIds() {
		return productIds;
	}
	public void setProductIds(List<String> productIds) {
		this.productIds = productIds;
	}
	public String getFromPostcode() {
		return fromPostcode;
	}
	public void setFromPostcode(String fromPostcode) {
		this.fromPostcode = fromPostcode;
	}
	public String getToPostcode() {
		return toPostcode;
	}
	public void setToPostcode(String toPostcode) {
		this.toPostcode = toPostcode;
	}
	public String getArrivalDate() {
		return arrivalDate;
	}
	public void setArrivalDate(String arrivalDate) {
		this.arrivalDate = arrivalDate;
	}
	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}
	

}
