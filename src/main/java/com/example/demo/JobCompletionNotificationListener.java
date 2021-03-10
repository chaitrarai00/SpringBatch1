package com.example.demo;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport{
	private static final Logger log=LoggerFactory.getLogger(JobCompletionNotificationListener.class);
	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate=jdbcTemplate;
	}
	
	/*
	 * define an operation to be done after the job
	 * after data is inserted to internal memory
	 * TO INDICATE BATCH IS DONE
	 */
	public void afterJob(JobExecution jobExecution) {
		if(jobExecution.getStatus()==BatchStatus.COMPLETED) {
			log.info("!!!JOB COMPLETED PLEASE VIEW RESULTS!!!!!!!!!!!!");
			
			jdbcTemplate.query("SELECT first_name,last_name FROM report", 
					(rs,row)->new Report(
							rs.getString(1),
							rs.getString(2))
					).forEach(report->log.info("Found<"+report+"> int the database"));
		}
	}
}
