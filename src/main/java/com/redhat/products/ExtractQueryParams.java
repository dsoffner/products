package com.redhat.products;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import org.apache.camel.Exchange;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.cxf.message.MessageContentsList;

import com.redhat.products.ProductInfo;

import sample.ws.service.ProductInfoArray;
import sample.ws.service.ProductQuery;
import startrack.ws.service.StProductInfo;
import startrack.ws.service.StProductInfoArray;
import startrack.ws.service.StProductQuery;


public class ExtractQueryParams {

	private static final int SAP = 1;
	private static final int STARTRACK = 2;
	
	private static final Map<String, Integer> TARGET_MAP;
	static
	{
	    Map<String, Integer>productMap = new HashMap<String, Integer>();
	    productMap.put("STR-ON", STARTRACK);
	    productMap.put("STR-ARL", STARTRACK);
	    productMap.put("EPARCEL-REG", SAP);
	    productMap.put("EPARCEL-EXP", SAP);
	    TARGET_MAP = Collections.unmodifiableMap(productMap);
	}
	
	private static final Map<String, Integer> FEATURE_MAP;
	static
	{
	    Map<String, Integer>featureMap = new HashMap<String, Integer>();
	    featureMap.put("IDOD", 1);
	    featureMap.put("ATL", 1);
	    featureMap.put("TIMESLOTS", 1);
	    featureMap.put("WKEND", 1);
	    featureMap.put("COLLECT", 1);
	    FEATURE_MAP = Collections.unmodifiableMap(featureMap);
	}
	
	static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	// for single WS target test
    public QueryParams extract(Exchange exchange) throws Exception {
    	QueryParams params = new QueryParams();
    	params.setArrivalDate(exchange.getIn().getHeader("arrival_date", String.class));
    	params.setFromPostcode(exchange.getIn().getHeader("from_postcode", String.class));
    	params.setToPostcode(exchange.getIn().getHeader("to_postcode", String.class));
    	
        String[] ids = exchange.getIn().getHeader("product_ids", String.class).replaceAll("\\s", "").split(",");
        params.setProductIds(Arrays.asList(ids));
        
        System.out.println("QueryParams:\n" + params);
        
        //exchange.getOut().setBody(params);
        return params;

    } 
    
    // check if all required query parameters and their values are OK
    public boolean isQuerryParameterErrorDetected(Exchange exchange) throws Exception {
    	boolean error = false;
    	StringBuilder sb = new StringBuilder();
    	if (exchange.getIn().getHeader("arrival_date", String.class) == null) {
    		sb.append("- arrival_date not specified\n");
    	}
    	if (exchange.getIn().getHeader("from_postcode", String.class) == null) {
    		sb.append("- from_postcode not specified\n");
    	}
    	if (exchange.getIn().getHeader("to_postcode", String.class) == null) {
    		sb.append("- to_postcode not specified\n");
    	}
    	String productIds = exchange.getIn().getHeader("product_ids", String.class);
    	if (productIds == null) {
    		sb.append("- productIds not specified\n");
    	}
    	else {
    		String[] ids = productIds.replaceAll("\\s", "").split(",");
    		for (int i = 0; i < ids.length; i++) {
    			if (! TARGET_MAP.containsKey(ids[i])) {
    				sb.append("- invalid productId: " + ids[i] +  "\n");
    			}
    		}
    	}

    	String features = exchange.getIn().getHeader("features", String.class);
    	if (features != null) {
    		String[] ids = features.replaceAll("\\s", "").split(",");
    		for (int i = 0; i < ids.length; i++) {
    			if (! FEATURE_MAP.containsKey(ids[i])) {
    				sb.append("- invalid feature: " + ids[i] +  "\n");
    			}
    		}
    	}
    	if (sb.length() > 0) {
    		error = true;
    		exchange.getIn().setHeader("errorMsg", sb.toString());
    	}

    	
    	return error;
    }
    
    // error handling assuming error message already set in exchange previously
    public void returnError(Exchange exchange) {
    	System.out.println("returnError:\n" + exchange.getIn().getHeader("errorMsg"));
        exchange.getIn().setHeader(Exchange.CONTENT_TYPE, "text/plain");
        exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, 400);
        exchange.getIn().setBody(exchange.getIn().getHeader("errorMsg"));
    }
    
    // transform params to that required by SapWS
    public ProductQuery setSapParams(QueryParams params)  {
//    	QueryParams query = extract(exchange);
    	ProductQuery pQuery = new ProductQuery();
/*    	GregorianCalendar cal = new GregorianCalendar();
    	cal.setTime(query.getArrivalDate());
    	XMLGregorianCalendar xmlDate = DatatypeFactory.newInstance().newXMLGregorianCalendarDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DAY_OF_MONTH), 0);
    	pQuery.setArrivalDate(xmlDate);
    	System.out.println("XMLGregorianCalendar:\n" + xmlDate);*/
    	pQuery.setArrivalDate(params.getArrivalDate());
    	pQuery.setFromPostcode(params.getFromPostcode());
    	pQuery.setToPostcode(params.getToPostcode());
    	for (int i = 0; i < params.getProductIds().size(); i++) {
    		pQuery.getProductIds().add(params.getProductIds().get(i));
    	}
    	
    	//exchange.getIn().setBody(pQuery);
    	return pQuery;
    }
    
    // transform params to that required by StartrackWS
    public StProductQuery setStParams(QueryParams params)  {
//    	QueryParams query = extract(exchange);
    	StProductQuery pQuery = new StProductQuery();
/*    	GregorianCalendar cal = new GregorianCalendar();
    	cal.setTime(query.getArrivalDate());
    	XMLGregorianCalendar xmlDate = DatatypeFactory.newInstance().newXMLGregorianCalendarDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DAY_OF_MONTH), 0);
    	pQuery.setArrivalDate(xmlDate);
    	System.out.println("XMLGregorianCalendar:\n" + xmlDate);*/
    	pQuery.setArrivalDate(params.getArrivalDate());
    	pQuery.setFromPostcode(params.getFromPostcode());
    	pQuery.setToPostcode(params.getToPostcode());
    	for (int i = 0; i < params.getProductIds().size(); i++) {
    		pQuery.getProductIds().add(params.getProductIds().get(i));
    	}
    	
    	//exchange.getIn().setBody(pQuery);
    	return pQuery;
    }

    
    // transform SapWS results to that required by REST service
    public List<ProductInfo> getTransformedSapResults(Exchange exchange) throws Exception {
    	System.out.println("getTransformedSapResults called:\n");
    	MessageContentsList mList = (MessageContentsList) exchange.getIn().getBody();
    	ProductInfoArray list = (ProductInfoArray) mList.get(0);
    	//sample.ws.service.ProductInfo[] array = (sample.ws.service.ProductInfo[]) exchange.getOut().getBody();
		List<ProductInfo> productInfoList = new ArrayList<ProductInfo>();
    	for (int i = 0; i < list.getItem().size(); i++) {
    		ProductInfo pInfo = new ProductInfo();
    		sample.ws.service.ProductInfo info = (sample.ws.service.ProductInfo) list.getItem().get(i);
/*    		BeanUtils.copyProperties(pInfo, info);
    		productInfoList.add(pInfo);*/
    		
			pInfo.setProductId(info.getProductId());
			pInfo.setArrivalDate(DATE_FORMAT.format(info.getArrivalDate().toGregorianCalendar().getTime()));
			pInfo.setDayId(info.getDayId());
			pInfo.setDayOfWeek(info.getDayOfWeek());
			pInfo.setDescription(info.getDescription());
			pInfo.setDispatchDate(DATE_FORMAT.format(info.getDispatchDate().toGregorianCalendar().getTime()));
			pInfo.setEndTime(info.getEndTime());
			pInfo.setStartTime(info.getStartTime());
			pInfo.setTsId(info.getTsId());
			pInfo.setType(info.getType());
			
			productInfoList.add(pInfo);
			
			System.out.println("productInfo #" + i + ":\n" + pInfo);
    	}
    	return productInfoList;
    	//exchange.getOut().setBody(productInfo);
    }
    
    // transform StartrackWS results to that required by REST service
    public List<ProductInfo> getTransformedStResults(Exchange exchange) throws Exception {
    	System.out.println("getTransformedStResults called:\n");
    	MessageContentsList mList = (MessageContentsList) exchange.getIn().getBody();
    	StProductInfoArray list = (StProductInfoArray) mList.get(0);
    	//sample.ws.service.ProductInfo[] array = (sample.ws.service.ProductInfo[]) exchange.getOut().getBody();
		List<ProductInfo> productInfoList = new ArrayList<ProductInfo>();
    	for (int i = 0; i < list.getItem().size(); i++) {
    		ProductInfo pInfo = new ProductInfo();
    		StProductInfo info = (StProductInfo) list.getItem().get(i);
/*    		BeanUtils.copyProperties(pInfo, info);
    		productInfoList.add(pInfo);*/
    		
    		pInfo.setProductId(info.getProductId());
			pInfo.setArrivalDate(DATE_FORMAT.format(info.getArrivalDate().toGregorianCalendar().getTime()));
			pInfo.setDayId(info.getDayId());
			pInfo.setDayOfWeek(info.getDayOfWeek());
			pInfo.setDescription(info.getDescription());
			pInfo.setDispatchDate(DATE_FORMAT.format(info.getDispatchDate().toGregorianCalendar().getTime()));
			pInfo.setEndTime(info.getEndTime());
			pInfo.setStartTime(info.getStartTime());
			pInfo.setTsId(info.getTsId());
			pInfo.setType(info.getType());
    		
    		productInfoList.add(pInfo);
			
			System.out.println("productInfo #" + i + ":\n" + pInfo);
    	}
    	return productInfoList;
    	//exchange.getOut().setBody(productInfo);
    }
    
    // split the request into Sap and Startrack requests
    public List<QueryParams> splitRequest(Exchange exchange) throws Exception {
    	List<QueryParams> list = new ArrayList<QueryParams>();
    	QueryParams query = extract(exchange);
    	List<String> sapList = new ArrayList<String>();
    	List<String> stList = new ArrayList<String>();
    	
    	for (int i = 0; i < query.getProductIds().size(); i++) {
    		Integer target = TARGET_MAP.get(query.getProductIds().get(i));
    		if (target != null) {
    			if (target.intValue() == SAP) {
    				sapList.add(query.getProductIds().get(i));
    			}
    			else {
    				stList.add(query.getProductIds().get(i));
    			}
    		}
    	}
    	
    	if (sapList.size() != 0) {
    		QueryParams sapParams = new QueryParams();
    		BeanUtils.copyProperties(sapParams, query);
    		sapParams.setTarget("sap");
    		sapParams.setProductIds(sapList);
    		list.add(sapParams);
    	}
    	if (stList.size() != 0) {
    		QueryParams stParams = new QueryParams();
    		BeanUtils.copyProperties(stParams, query);
    		stParams.setTarget("startrack");
    		stParams.setProductIds(stList);
    		list.add(stParams);
    	}
    	
    	for (QueryParams param : list) {
    		System.out.println("After split:\n" + param);
    	}
    	
    	return list;
    }
    
    // check elsewhere to make sure there is only one kind of request and call splitRequest
    public List<QueryParams> setupRequest(Exchange exchange) throws Exception {
    	return splitRequest(exchange);
    }
    
    // check if the target field in QueryParams is set to 'sap"
    public boolean isSap(QueryParams params) {
    	if (params.getTarget().equals("sap")) {
    		return true;
    	}
    	return false;
    }
    
    // get the appropriate result from SapWS or StartrackWS
	public List<ProductInfo> getTransformedResults(Exchange exchange) {
		
		MessageContentsList mList = (MessageContentsList) exchange.getIn().getBody();
		List<ProductInfo> infoList = null;
		
		try {
			if (mList.get(0) instanceof ProductInfoArray) {
				infoList = getTransformedSapResults(exchange);
			}
			else {
				infoList = getTransformedStResults(exchange);
			}
			
		} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		return infoList;
	}
	
	// Set the final result for display
	public void setTransformedResults(Exchange exchange) {
		List<ProductInfo> list = getTransformedResults(exchange);
		ProductFilter filter = new ProductFilter(exchange);
		exchange.getOut().setBody(filter.FilterList(list));
	}
	
	// check if we need to use a splitter in Composed Message Procesor EIP
	public boolean isSplitterRequired(Exchange exchange) throws Exception {
		QueryParams query = extract(exchange);
		boolean bSap = false;
		boolean bStartrack = false;
    	for (int i = 0; i < query.getProductIds().size(); i++) {
    		Integer target = TARGET_MAP.get(query.getProductIds().get(i));
    		if (target != null) {
    			if (target.intValue() == SAP) {
    				bSap = true;
    			}
    			else {
    				bStartrack = true;
    			}
    		}
    	}
    	
    	return bSap && bStartrack;
	}
	
	// check if there is only SapWS call in the request
	public boolean isSapOnly(Exchange exchange) throws Exception {
		QueryParams query = extract(exchange);
		boolean bSap = false;
		boolean bStartrack = false;
    	for (int i = 0; i < query.getProductIds().size(); i++) {
    		Integer target = TARGET_MAP.get(query.getProductIds().get(i));
    		if (target != null) {
    			if (target.intValue() == SAP) {
    				bSap = true;
    			}
    			else {
    				bStartrack = true;
    			}
    		}
    	}
    	
    	return bSap && !bStartrack;
	}


}
