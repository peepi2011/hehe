/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAI1819.OBD;

import DAI1819.OBD.Controller.ObdAverageController;
import DAI1819.OBD.entity.Obd;
import DAI1819.OBD.entity.ObdAverage;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


public class CalculoPerfilRisco {

    int numAceleracoes = 0;
    int numTravagens = 0;
      
    
    
    public void calcularPerfilRisco(List<ObdAverage> obdsAverages,List<Obd> obds){
        List<ObdAverage> listaOBDSAverages = obdsAverages;
        List<Obd> listaOBDS = obds;
        
        List<String> lista = new ArrayList<>();
        HashMap<String,Double> resultadoPerfilRisco = new HashMap<>();
        
        for(ObdAverage obd : listaOBDSAverages){
            lista.add(obd.getIdObd());
        }      
       
        System.out.println(lista);
    }

    public void calcularAceleracaoTravagem(List<Integer> velocidades) {
        //velocidades vem de uma query que está order by data
        double valor1 = velocidades.get(0);
        double valor2 = velocidades.get(1);
        //positivo se for uma travagem; negativo se for aceleração
        double percentagem = ((valor1 - valor2) / valor1);
        System.out.println(percentagem);
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
        System.out.println(Arrays.toString(results));
        
    }
}
