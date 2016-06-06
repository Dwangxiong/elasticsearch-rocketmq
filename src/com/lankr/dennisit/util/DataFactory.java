package com.lankr.dennisit.util;

import java.util.ArrayList;
import java.util.List;

import com.lankr.dennisit.util.JsonUtil;
import com.lankr.model.Hospital;

public class DataFactory {

	 public static DataFactory dataFactory = new DataFactory();
	    
	    private DataFactory(){
	        
	    }
	    
	    public DataFactory getInstance(){
	        return dataFactory;
	    }
	    
	    public static String getInitJsonData(Hospital hospital){
	    	 String data  = JsonUtil.obj2JsonData(hospital);
	        return data;
	    }
	
}
