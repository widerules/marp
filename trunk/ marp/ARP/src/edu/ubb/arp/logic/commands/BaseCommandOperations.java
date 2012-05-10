package edu.ubb.arp.logic.commands;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;


public class BaseCommandOperations {
	private static final Logger logger = Logger.getLogger(BaseCommandOperations.class);
	// Setter methods
	
	/**
	 * Creates an error JSONArray.
	 * @param errorCode -inf - 0
	 * @return Returns an error message in JSONArray if the errorCode <= 0 else returns unknown error.
	 */
	public JSONArray setError(int errorCode) {
		String methodName = "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() ";
		JSONArray result = new JSONArray();
		JSONObject errmsg = new JSONObject();
		
		if (errorCode <= 0) { // everithing is okey
			errmsg.put("error", new Integer(errorCode));
		} else { 
			logger.error(getClass().getName() + methodName + "errorCode > 0");
			errmsg.put("error", new Integer(-1));
		}
		
		result.add(errmsg);
		
		return result;
	}
	
	public JSONArray addString(String key, String element, JSONArray response) {
		String methodName = "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() ";
		JSONArray result = JSONArray.fromObject(response);
		
		if (key == null) {
			logger.error(getClass().getName() + methodName + "parameter: key is null");
			return setError(-1);
		}
		
		if (result == null) {
			result = new JSONArray();
		}
		
		JSONObject obj = new JSONObject();
		obj.put(key, element);
		result.add(obj);
		
		return result;
	}
	
	/**
	 * Appends response with the parameter element, if the response were null create a new response with the parameter element.
	 * @param key is not null.
	 * @param element
	 * @param response 
	 * @return unknown error if the parameter: key is null, else response appended with the new element.
	 */
	public JSONArray addInt(String key, int element, JSONArray response) {
		String methodName = "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() ";
		JSONArray result = JSONArray.fromObject(response);
		
		if (key == null) {
			logger.error(getClass().getName() + methodName + "parameter: key is null");
			return setError(-1);
		}
		
		if (result == null) {
			result = new JSONArray();
		}
		
		JSONObject obj = new JSONObject();
		obj.put(key, new Integer(element));
		result.add(obj);

		return result;
	}
	
	public JSONArray addBool(String key, boolean element, JSONArray response) {
		String methodName = "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() ";
		JSONArray result = JSONArray.fromObject(response);
		
		if (key == null) {
			logger.error(getClass().getName() + methodName + "parameter: key is null");
			return setError(-1);
		}
		
		if (result == null) {
			result = new JSONArray();
		}
		
		JSONObject obj = new JSONObject();
		obj.put(key, new Boolean(element));
		result.add(obj);
		
		return result;
	}
	
	// Getter methods
	
	/**
	 * 
	 * @param index
	 * @param key
	 * @param request
	 * @return
	 * @throws IllegalStateException if the request do not contains the given element.
	 */
	public String getString(int index, String key, JSONArray request) throws IllegalStateException {
		String methodName = "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() ";
		String result = null;
		
		try {
			JSONObject obj = request.getJSONObject(index);
			result = obj.getString(key);
			
			return result;
		} catch (JSONException e) {
			logger.error(getClass().getName() + methodName + e);
			throw new IllegalStateException(e);
		}	
	}
	
	public int getInt(int index, String key, JSONArray request) throws IllegalStateException {
		String methodName = "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() ";
		int result = 0;
		
		try {
			JSONObject obj = request.getJSONObject(index);
			result = obj.getInt(key);
			
			return result;
		} catch (JSONException e) {
			logger.error(getClass().getName() + methodName + e);
			throw new IllegalStateException(e);
		}	
	}
	
	public boolean getBool(int index, String key, JSONArray request) throws IllegalStateException {
		String methodName = "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() ";
		boolean result = false;
		
		try {
			JSONObject obj = request.getJSONObject(index);
			result = obj.getBoolean(key);
			
			return result;
		} catch (JSONException e) {
			logger.error(getClass().getName() + methodName + e);
			throw new IllegalStateException(e);
		}	
	}
	
	
	// Others
	
    /**
	 * 
	 * @param response
	 * @return true - if the response is an error message, false if not.
	 */
	public boolean errorCheck(JSONArray response) {
		if (response.size() == 1) {
			JSONObject obj = response.getJSONObject(0);
			try {
				obj.getInt("error");
				return true;
			} catch(JSONException e) {
				return false;
			}
		} 
		
		return false;
	}
	
}