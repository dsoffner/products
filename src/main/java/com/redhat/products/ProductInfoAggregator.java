package com.redhat.products;


import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

public class ProductInfoAggregator implements AggregationStrategy {

	@Override
	public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        
		ExtractQueryParams extract = new ExtractQueryParams();
    	ProductFilter filter = new ProductFilter(newExchange);
		 
        if (oldExchange == null) {
            // the first time we aggregate we only have the new exchange,
            // so we just return it after transforming the result from the web service
      	
        	List<ProductInfo> infoList = extract.getTransformedResults(newExchange);
        	
        	// filter the list
        	newExchange.getIn().setBody(filter.FilterList(infoList));
        	
            return newExchange;
        }
 
     // Merge productInfo into old exchange by adding the productInfo from new exchange
        List<ProductInfo> completeList = (List<ProductInfo>) oldExchange.getIn().getBody();
        List<ProductInfo> infoList = extract.getTransformedResults(newExchange);
 
        // merge results
        completeList.addAll(filter.FilterList(infoList));
        
        // put merged results back on old to preserve it
        oldExchange.getOut().setBody(completeList);
 
        // return old as this is the one that has all the productInfo aggregated
        return oldExchange;
	}
	

}
