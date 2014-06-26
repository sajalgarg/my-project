package com.futor.analytics.message.benchmark.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.futor.analytics.message.benchmark.db.ConceptBenchmark;
import com.futor.analytics.message.benchmark.service.ConceptBenchmarkService;

public class ConceptBenchmarkServiceImpl implements ConceptBenchmarkService {
	@Autowired
	ConceptBenchmark conceptBenchmark;
	
	@Override
	public long getConceptBenchmarkTime(String courseNid) throws Exception {
		return conceptBenchmark.getConceptBenchmarkTime(courseNid);
	}

	
}
