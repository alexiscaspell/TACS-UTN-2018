package ar.utn.tacs.service.external.impl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

import ar.utn.tacs.dao.external.ExternalDao;
import ar.utn.tacs.service.external.ExternalService;


public class ExternalServiceImpl implements ExternalService{
	
	@Autowired
	private ExternalDao externalDao;
	
	Gson gson = new Gson();
	
	private static String COIN_MARKET_CAP_URL = "https://api.coinmarketcap.com/v1/ticker/";
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> coinMarketCap() {
		
		String response = makeRequest("GET",null,COIN_MARKET_CAP_URL);
		
		Map<String, Object> mapResult = gson.fromJson(response, Map.class);
		
		return mapResult;
	}
	
	private String makeRequest(String method,Object objeto,String url ) {
		
		try {
			String urlMail = url;
			URI uri = new URI(urlMail);

			HttpHeaders headers = new HttpHeaders();
	        headers.add("Content-Type", "application/json");
	        headers.add("Accept", "application/json");
	        
	        HttpEntity<String> getEntity = new HttpEntity<String>(gson.toJson(objeto), headers);
	        
	        RestTemplate restTemplate = new RestTemplate();
	        ResponseEntity<String> response =restTemplate.exchange(uri, getHttpMethod(method),getEntity,String.class);

	        System.out.println("\nSTATUS : "+ response.getStatusCode()+" "+response.getStatusCode().getReasonPhrase());
	        System.out.println("Response :"+ response.getBody());
	        
	        return response.hasBody() ? response.getBody() : null;
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (InvalidMediaTypeException e) {
			e.printStackTrace();
		}
		return null;
	}

	private HttpMethod getHttpMethod(String method) {
		
		return HttpMethod.resolve(method);
	}

}
