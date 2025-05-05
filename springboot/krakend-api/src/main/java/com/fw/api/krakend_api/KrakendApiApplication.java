package com.fw.api.krakend_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class KrakendApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(KrakendApiApplication.class, args);
	}

}
