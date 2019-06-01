package DAI1819.OBD;

import DAI1819.OBD.Controller.ObdAverageController;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ClassPathResource;

@SpringBootApplication
@ComponentScan(basePackages = {"DAI1819.OBD.Controller", "DAI1819.OBD.entity", "DAI1819.OBD.Repository"})
public class StartApplication {

        static List<Integer> list = new ArrayList<Integer>();
        
        @Autowired
        static ObdAverageController obdAverageController;
        

        public static void main(String[] args) {
            
            
		ApplicationContext appContext = SpringApplication.run(StartApplication.class, args);
		SpringContext.context = appContext;

		list.add(10);
		list.add(21);
		list.add(10);
		list.add(12);
		list.add(18);
		list.add(28);
		list.add(10);

		CalculoPerfilRisco aceleracao = new CalculoPerfilRisco();
		aceleracao.calcularAceleracaoTravagem(list);

		System.out.println();
		aceleracao.calcularPerfilRisco();
	}

}
