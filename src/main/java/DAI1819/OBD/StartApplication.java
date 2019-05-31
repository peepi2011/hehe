package DAI1819.OBD;

import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"DAI1819.OBD.Controller", "DAI1819.OBD.entity", "DAI1819.OBD.Repository"})
public class StartApplication {
    
        static List<Integer> list = new ArrayList<Integer>();
        

	public static void main(String[] args) {
		SpringApplication.run(StartApplication.class, args);
                list.add(10);
                list.add(21);
                list.add(10);
                list.add(12);
                list.add(18);
                list.add(28);
                list.add(10);

                Aceleracao aceleracao = new Aceleracao();
                aceleracao.calcularAceleracaoTravagem(list);

	}

}
