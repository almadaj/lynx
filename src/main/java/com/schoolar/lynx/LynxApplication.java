package com.schoolar.lynx;
import com.schoolar.lynx.config.StorageConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(StorageConfig.class)
public class LynxApplication {

	public static void main(String[] args) {
		SpringApplication.run(LynxApplication.class, args);
	}

}
