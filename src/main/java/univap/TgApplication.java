package univap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import univap.repository.OidRepo;

@SpringBootApplication
public class TgApplication extends WebMvcConfigurerAdapter{
	
	@Autowired
	public static OidRepo oidrepo;
	
	public static void main(String[] args) {
		SpringApplication.run(TgApplication.class, args);
	}
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "TRACE", "CONNECT");
	}

}
