package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class ReportItemProcessor implements ItemProcessor<Report, Report>{

	private static Logger Log=LoggerFactory.getLogger("ReportItemProcessor.class");
	
	@Override
	public Report process(Report item) throws Exception {
		final String firstname=item.getFirstname().toUpperCase();
		final String lastname=item.getLastname().toUpperCase();
		final Report transformeditem=new Report(firstname,lastname);
	
		Log.info("Converting ("+item+") into ("+transformeditem+")");
		return transformeditem;
	}

}
