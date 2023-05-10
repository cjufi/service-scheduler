package com.prime.rushhour;

import com.prime.rushhour.infrastructure.config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(RsaKeyProperties.class)
@SpringBootApplication
public class RushhourApplication {

	public static void main(String[] args) {
		SpringApplication.run(RushhourApplication.class, args);
	}

}
