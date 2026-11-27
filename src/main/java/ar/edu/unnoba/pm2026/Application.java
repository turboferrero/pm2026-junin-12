package ar.edu.unnoba.pm2026;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ar.edu.unnoba.pm2026.courses.util.PasswordEncoder;

@SpringBootApplication
public class Application {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new PasswordEncoder();
	}
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
