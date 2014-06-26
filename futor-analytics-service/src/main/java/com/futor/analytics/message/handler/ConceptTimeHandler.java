package com.futor.analytics.message.handler;

import java.util.Map;

import com.futor.analytics.message.service.MessageOperation;
import com.futor.analytics.service.StudentMessageService;
import com.futor.analytics.util.MessageOperationParamEnum;

public class ConceptTimeHandler implements MessageOperation {

	StudentMessageService studentMessageService;
	@Override
	public void getMessage(Map<String, String> params) throws Exception{
		Long userId=Long.parseLong(params.get(MessageOperationParamEnum.USERID.toString()));
		String courseNid = params.get(MessageOperationParamEnum.COURSENID.toString());
		studentMessageService.getConceptTimeMessage(userId, courseNid);
	}

}
