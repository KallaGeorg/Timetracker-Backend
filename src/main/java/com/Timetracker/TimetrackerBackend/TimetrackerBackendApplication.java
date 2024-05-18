package com.Timetracker.TimetrackerBackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.Timetracker.Config.CorsConfig;

@SpringBootApplication @Import(CorsConfig.class)
public class TimetrackerBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(TimetrackerBackendApplication.class, args);
	}

}
