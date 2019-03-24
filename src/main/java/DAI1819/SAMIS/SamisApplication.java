package DAI1819.SAMIS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"DAI1819.SAMIS.controller","DAI1819.SAMIS.entity","DAI1819.SAMIS.repository", "DAI1819.SAMIS.exception", "DAI1819.SAMIS.payload", "DAI1819.SAMIS.service"})
public class SamisApplication {

	public static void main(String[] args) {
		SpringApplication.run(SamisApplication.class, args);
	}

}
