package com.southsystem.batch.listener;

import java.util.List;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Service;

@Service
public class JobCompletionListener implements JobExecutionListener {
	
	private long startTime;
	private long endTime;

	@Override
	public void beforeJob(JobExecution jobExecution) {
		startTime = System.currentTimeMillis();
		if(jobExecution.getStatus() == BatchStatus.STARTED) {
			System.out.println("job process start... " + startTime);
		}
	}	

	@Override
	public void afterJob(JobExecution jobExecution) {

		endTime = System.currentTimeMillis();

		System.out.println("job process end..." + startTime);
		System.out.println("elapsed time: " + (endTime - startTime) + "ms");
		
		if(jobExecution.getStatus() == BatchStatus.COMPLETED) {				
			System.out.println("Arquivo processado com sucesso");
		} else if(jobExecution.getStatus() == BatchStatus.FAILED){
			List<Throwable> exceptionList = jobExecution.getAllFailureExceptions();
			for(Throwable th : exceptionList){
				System.out.println("exception :" +th.getLocalizedMessage());
			}
		}
	}

}


