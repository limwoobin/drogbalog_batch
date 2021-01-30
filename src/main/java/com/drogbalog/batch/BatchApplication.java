package com.drogbalog.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableBatchProcessing
@SpringBootApplication
public class BatchApplication {
	public static void main(String[] args) {
		System.out.println(">>>>>>>>>>>>>>>>>>> Start");
		for (String arg : args) {
			System.out.println(">>>>>>>>>>>>>> arg :" + arg);
		}
		SpringApplication.run(BatchApplication.class, args);
	}

}
