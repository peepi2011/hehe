package DAI1819.OBD;

import DAI1819.OBD.Controller.ObdAverageController;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"DAI1819.OBD.Controller", "DAI1819.OBD.entity", "DAI1819.OBD.Repository"})
public class StartApplication {
    
        static List<Integer> list = new ArrayList<Integer>();
        static List<Double> lista = new ArrayList<Double>();
        @Autowired
        static ObdAverageController obdAverageController;
        

	public static void main(String[] args) {
            
            
		SpringApplication.run(StartApplication.class, args);
                list.add(10);
                list.add(21);
                list.add(10);
                list.add(12);
                list.add(18);
                list.add(28);
                list.add(10);
                
                lista.add(70.0);
                lista.add(2001.0);
                lista.add(7.0);
                lista.add(150.0);
                lista.add(5.0);
                lista.add(4.0);

                CalculoPerfilRisco aceleracao = new CalculoPerfilRisco();
                aceleracao.calcularAceleracaoTravagem(list);
                aceleracao.calcularestatico(lista);
                obdAverageController.calcular();

	}

}
