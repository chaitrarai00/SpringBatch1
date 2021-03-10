package com.example.demo;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
	@Autowired
	public JobBuilderFactory jobBuilderFactory;
	//to define the jobs
	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	//to define the steps
	@Value("${file.input}")
	private String fileinput;
	/*
	 * Defining Writer Reader and processors
	 */
	@Bean
	public FlatFileItemReader<Report> reader(){
		return new FlatFileItemReaderBuilder<Report>().
				name("reportItemReader")
				.resource(new ClassPathResource("report.csv"))
				.delimited()
				.names(new String[] {"firstname","lastname"})
				.fieldSetMapper(new BeanWrapperFieldSetMapper<Report>() {
			{
				setTargetType(Report.class);
			}
		}).build();
	}
	
	@Bean
	public ReportItemProcessor processor() {
		return new ReportItemProcessor();
	}
	
	@Bean
	public JdbcBatchItemWriter<Report> writer(DataSource datasource){
		return new JdbcBatchItemWriterBuilder<Report>()
				.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Report>())
				.sql("INSERT INTO report (first_name, last_name) VALUES (:firstname, :lastname)")
				.dataSource(datasource)
				.build();
	}
	/*
	 * Job Configuration
	 * define job and each steps
	 */
	@Bean
	public Job importuserJob(JobCompletionNotificationListener listener, Step step1) {
		return jobBuilderFactory.get("importuserJob")
				.incrementer(new RunIdIncrementer())
				.listener(listener)
				.flow(step1)
				.end()
				.build();
	}
	
	@Bean
	public Step step1(JdbcBatchItemWriter<Report> writer) {
		return stepBuilderFactory.get("step1")
				.<Report,Report>chunk(10)
				.reader(reader())
				.processor(processor())
				.writer(writer)
				.build();
	}
	
	
}
