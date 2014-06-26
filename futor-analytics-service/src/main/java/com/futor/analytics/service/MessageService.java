package com.futor.analytics.service;

import java.util.List;
import java.util.Map;

public interface MessageService {
	void getMesssages(List<String> operationList, Map<String,String> params) throws Exception;
}
