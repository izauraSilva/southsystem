package com.southsystem.batch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import com.southsystem.batch.listener.JobCompletionListener;
import com.southsystem.batch.model.File;
import com.southsystem.batch.writer.DadosItemWriter;

@Configuration
@EnableBatchProcessing
public class ProcessJob<T> {
	
	@Value("/input/*.dat")
	private Resource[] inputResources;
	
	@Autowired
    private JobBuilderFactory jobBuilderFactory;
     
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    
    @Autowired
	private JobCompletionListener jobCompletionListener;
    
    @Autowired
	public ItemProcessor<? super File, ? extends T> dadosItemProcessor;	
    
    @Autowired
    private DadosItemWriter<T> dadosItemWriter;
	
	@Bean
	public Job readDatFilesJob() {
	    return jobBuilderFactory
	            .get("readDatFilesJob")
	            .incrementer(new RunIdIncrementer())
	            .listener(jobCompletionListener)
	            .start(step1())
	            .build();
	}
	
	@Bean
    public Step step1() {
        return stepBuilderFactory
        		.get("step1")
        		.<File,T>chunk(1000)
                .reader(multiResourceItemReader())  
                .processor(dadosItemProcessor)
                .writer(dadosItemWriter)              
                .build();
    }
	
	@Bean
    public MultiResourceItemReader<File> multiResourceItemReader() {
        MultiResourceItemReader<File> resourceItemReader = new MultiResourceItemReader<File>();
        resourceItemReader.setResources(inputResources);
        resourceItemReader.setDelegate(reader());
        return resourceItemReader;
    }
	
	@Bean
    public FlatFileItemReader<File> reader() {      
        FlatFileItemReader<File> reader = new FlatFileItemReader<File>();       
         
        reader.setLineMapper(new DefaultLineMapper<File>() {
			{
				setLineTokenizer(new DelimitedLineTokenizer() {
					{
						setDelimiter("ç");
						setNames(new String[] { "field1",
												"field2",
												"field3",
												"field4" });
					}
				});
				setFieldSetMapper(new BeanWrapperFieldSetMapper<File>() {
					{
						setTargetType(File.class);
					}
				});
			}
		});
             
        return reader;
    }


}
