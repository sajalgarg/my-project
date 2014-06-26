package com.futor.analytics.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.futor.analytics.message.service.MessageOperation;
import com.futor.analytics.service.MessageService;
import com.futor.analytics.util.MessageOperationEnum;

public class MessageServiceImpl implements MessageService{
	@Autowired
	MessageOperation conceptTimeHandler;

	@Override
	public void getMesssages(List<String> operationList,Map<String, String> params) throws Exception {
		if(operationList.contains(MessageOperationEnum.CONCEPTTIME.toString())){
			conceptTimeHandler.getMessage(params);
		}
	}

	
}
