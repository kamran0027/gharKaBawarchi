package com.Kamran.gharKaBawarchi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class GharKaBawarchiApplication {

	public static void main(String[] args) {
		SpringApplication.run(GharKaBawarchiApplication.class, args);
	}

}
