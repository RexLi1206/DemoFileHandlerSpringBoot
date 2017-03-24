package restFile.app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import it.ozimov.springboot.mail.configuration.EnableEmailTools;
import restFile.app.config.StorageProperties;
import restFile.app.storage.service.StorageService;



@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
@EnableScheduling
@EnableEmailTools
public class DemoApplication 
{

	public static void main(String[] args) throws Exception
	{
		SpringApplication.run(DemoApplication.class, args);
	}
	
	@Bean
	CommandLineRunner init(StorageService storageService) 
	{
		return (args) -> 
		{
            storageService.deleteAll();
            storageService.init();
		};
	}
}
