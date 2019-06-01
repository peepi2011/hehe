package DAI1819.OBD;

import DAI1819.OBD.Controller.ObdAverageController;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"DAI1819.OBD.Controller", "DAI1819.OBD.entity", "DAI1819.OBD.Repository"})

public class StartApplication {
        public static void main(String[] args) {
            
            
		ApplicationContext appContext = SpringApplication.run(StartApplication.class, args);
		SpringContext.context = appContext;

		CalculoPerfilRisco calculoPerfilRisco = new CalculoPerfilRisco();
		calculoPerfilRisco.calcularPerfisRisco();
	}

}
