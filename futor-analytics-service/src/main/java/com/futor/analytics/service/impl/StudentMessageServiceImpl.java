package com.futor.analytics.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.futor.analytics.message.benchmark.service.ConceptBenchmarkService;
import com.futor.analytics.message.service.StudentModelService;
import com.futor.analytics.service.RuleService;
import com.futor.analytics.service.StudentMessageService;

public class StudentMessageServiceImpl implements StudentMessageService {
	@Autowired
	StudentModelService studentModelService;
	@Autowired
	ConceptBenchmarkService conceptBenchmarkService;
	@Autowired
	RuleService ruleService;

	@Override
	public void getConceptTimeMessage(long userId, String courseNid)
			throws Exception {
		long studentCourseAverageConceptTime = studentModelService.getConceptTime(userId, courseNid);
		long courseAverageBenchmarkConceptTime = conceptBenchmarkService.getConceptBenchmarkTime(courseNid);
		boolean messageFlag = ruleService.fireRule(studentCourseAverageConceptTime, courseAverageBenchmarkConceptTime);
		
		
		
	}
	

}
