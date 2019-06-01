package DAI1819.OBD;


import DAI1819.OBD.Controller.ObdAverageController;
import DAI1819.OBD.Controller.ObdController;
import DAI1819.OBD.entity.ObdAverage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CalculoPerfilRisco {
    
    //Perfil Base
    private static Map<String, String> perfilRiscoOtimo = getPerfilBase();

    int numAceleracoes = 0;
    int numTravagens = 0;

    private GetControllers getControllers = new GetControllers();


    public void calcularPerfisRisco(){
        
        
        
        //lista de todos os OBDS
        List<String> listaOBDs = getControllers.getObdAverageController().readAllOBDs();
        
        for(String obd : listaOBDs){
            //controllers
            ObdAverageController obdAverageController = getControllers.getObdAverageController();
            ObdController obdController = getControllers.getObdController();
            
            //OBD Average object
            ObdAverage obdObject = obdAverageController.readByIdOBD(obd);
            
            //Variaveis do perfil de risco
            String idOBD = obd; 
            double velocidadeMedia = obdObject.getVelocidade();
            double rotacoesMedia = obdObject.getRotacoes();
            double kilometros = obdObject.getKm();
            double tempoMedioViagem = obdObject.getTempo();
            int numeroAceleracoesBruscas;
            int numeroTravagensBruscas;
            double overall;
            
            List<Integer> velocidades = obdController.readVelocidadesLast7DaysByIdObd(idOBD);
            
            int[] resultados = calcularAceleracaoTravagem(velocidades);
           
            numeroAceleracoesBruscas = resultados[0];
            numeroTravagensBruscas = resultados[1];
            
            
            System.out.println(idOBD +" " + velocidadeMedia + " " +rotacoesMedia + " " +kilometros +" " +tempoMedioViagem + " " +numeroAceleracoesBruscas + " " +numeroTravagensBruscas);
        
            overall = calcularOverall(velocidadeMedia,rotacoesMedia,kilometros,tempoMedioViagem,numeroAceleracoesBruscas,numeroTravagensBruscas);
            
            
        
        
        }     

    }

    
    
    //Calculo do numero de aceleracoes bruscas e travagens
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
   
    /*
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
    
    

*/
    //Retorna o valor otimo de cada avriavel do perfil de risco
    public static Map<String,String> getPerfilBase(){ 
        return new HashMap<>();         
    }

    
    
    private double calcularOverall(double velocidadeMedia, double rotacoesMedia, double kilometros, double tempoMedioViagem, int numeroAceleracoesBruscas, int numeroTravagensBruscas) {
        double velocidadeOtima = Double.parseDouble(perfilRiscoOtimo.get("velocidade"));
        double rotacaoOtima = Double.parseDouble(perfilRiscoOtimo.get("rotacoes"));
        double aceleracoesBruscasOtima = Double.parseDouble(perfilRiscoOtimo.get("aceleracoes"));
        double travagensBruscasOtima = Double.parseDouble(perfilRiscoOtimo.get("travagens"));
        double distanciaPercorridaOtima = Double.parseDouble(perfilRiscoOtimo.get("distancia"));
        double horasConducaoOtima = Double.parseDouble(perfilRiscoOtimo.get("horas"));                       
        
        
        double penalizacaoVelocidade = 0.25;
        double penalizacaoRotacao = 0.15;
        double penalizacaoAceleracoesBruscas = 0.15;
        double penalizacaoTravagensBruscas = 0.15;
        double penalizacaoDistanciaPercorrida = 0.15;
        double penalizacaoHorasConducao = 0.15;
        
        double overall = (velocidadeMedia / velocidadeOtima) * penalizacaoVelocidade;
        overall += (rotacoesMedia / rotacaoOtima ) * penalizacaoRotacao;
        overall += (kilometros / distanciaPercorridaOtima) * penalizacaoDistanciaPercorrida;
        overall += (tempoMedioViagem / horasConducaoOtima) * penalizacaoHorasConducao;
        overall += (numeroAceleracoesBruscas / aceleracoesBruscasOtima) * penalizacaoAceleracoesBruscas;
        overall += (numeroTravagensBruscas / travagensBruscasOtima) * penalizacaoTravagensBruscas;
        
        return overall;

    }
}
