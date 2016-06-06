package com.lankr.dennisit.util;

import com.lankr.dennisit.entity.process.ElasticSearchHandler;

public class ElasticSearchHandlerFactory {
	
	private static ElasticSearchHandler esHandler ;
	
	public ElasticSearchHandler getInstance(){
		if (esHandler == null) {
			esHandler = ElasticSearchHandler.instance() ;
		}
		return esHandler ;
	}
}
