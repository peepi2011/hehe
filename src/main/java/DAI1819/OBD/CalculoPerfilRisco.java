package DAI1819.OBD;

import DAI1819.OBD.Controller.ObdAverageController;
import DAI1819.OBD.Controller.ObdController;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class CalculoPerfilRisco {

    int numAceleracoes = 0;
    int numTravagens = 0;

    private GetControllers getControllers = new GetControllers();


    public void calcularPerfilRisco(){
        List<String> listaOBDs = getControllers.getObdAverageController().readAllOBDs();
        List<String> lista = new ArrayList<>();
        HashMap<String,Double> resultadoPerfilRisco = new HashMap<>();
        System.out.println(listaOBDs);

    }

    public int[] calcularAceleracaoTravagem(List<Integer> velocidades) {
        //velocidades vem de uma query que está order by data
        double valor1 = velocidades.get(0);
        double valor2 = velocidades.get(1);
        //positivo se for uma travagem; negativo se for aceleração
        double percentagem = ((valor1 - valor2) / valor1);
        if (percentagem > 0.5) {
            numTravagens++;
        }
        if (percentagem < -0.25) {
            numAceleracoes++;
        }
        velocidades.remove(0);
        if (velocidades.size() >= 2) {
            calcularAceleracaoTravagem(velocidades);
        }

        int[] results = {numAceleracoes, numTravagens};
        return results;
        
    }
    
    public void calcularestatico(List<Double> parametros, int[] results) {
        
        double v_cm = 50;
        double r_cm = 2000;
        double a_brusc = 5;
        double d_perc = 100;
        double t_brusc = 2;
        double h_cond = 2;
        double p_v = 0.25;
        double p_r = 0.15;
        double p_a = 0.15;
        double p_d = 0.15;
        double p_t = 0.15;
        double p_h = 0.15;
        double penalizacao = 0.1;
        
        double v = parametros.get(0);
        double r = parametros.get(1);
        double a = results[0];
        double d = parametros.get(2);
        double t = results[1];
        double h = parametros.get(3);
        double pr; 
        
        System.out.println(a + " - " + t);
        
        pr = p_v - ((v/v_cm) - 1) * penalizacao;
        pr += p_r - ((r/r_cm) - 1) * penalizacao;
        pr += p_a - ((a/a_brusc) - 1) * penalizacao;
        pr += p_d - ((d/d_perc) - 1) * penalizacao;
        pr += p_t - ((t/t_brusc) - 1) * penalizacao;
        pr += p_h - ((h/h_cond) - 1) * penalizacao;
        
        System.out.println(pr);
    }
}
