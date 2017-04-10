package com.redhat.products;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;

public class ProductFilter {

	private Map<String, Integer> map;
	
	// Set up all wanted features in a map for easy lookup
	public ProductFilter(Exchange exchange) {
		map = new HashMap<String, Integer>();
		String features = exchange.getIn().getHeader("features", String.class);
		if (features != null) {
			String[] ids = features.replaceAll("\\s", "").split(",");
			for (int i = 0; i < ids.length; i++) {
				map.put(ids[i], i);
			}
		}
	}
	
	// determine if the type is wanted by checking the map
	public boolean isWanted(String key) {
		// if map is empty, it means there is no filter. Everything goes through.
		if (map.size() == 0) return true;
		
		return map.containsKey(key);
	}
	
	// Returns a list containing filtered productInfo
	public List<ProductInfo> FilterList(List<ProductInfo> list) {
		List<ProductInfo> filteredList = new ArrayList<ProductInfo>();
		
		for (ProductInfo info : list) {
			if (isWanted(info.getType())) {
				filteredList.add(info);
			}
		}
		return filteredList;
		
	}
}
