package DAI1819.OBD;

import DAI1819.OBD.Controller.ObdAverageController;
import DAI1819.OBD.Controller.ObdController;
import DAI1819.OBD.HttpRequest.HttpRequest;
import DAI1819.OBD.entity.ObdAverage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CalculoPerfilRisco {

    public CalculoPerfilRisco() {
    }

    //Perfil Base
    private final Map<String, String> perfilRiscoOtimo = this.getPerfilBase();

    int numAceleracoes = 0;
    int numTravagens = 0;

    private GetControllers getControllers = new GetControllers();

    public void calcularPerfisRisco() {

        //lista de todos os OBDS
        List<String> listaOBDs = getControllers.getObdAverageController().readAllOBDs();

        for (String obd : listaOBDs) {
            Map<String, String> perfilCalculado = new HashMap<>();
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

            System.out.println(idOBD + " " + velocidadeMedia + " " + rotacoesMedia + " " + kilometros + " " + tempoMedioViagem + " " + numeroAceleracoesBruscas + " " + numeroTravagensBruscas);

            overall = calcularOverall(velocidadeMedia, rotacoesMedia, kilometros, tempoMedioViagem, numeroAceleracoesBruscas, numeroTravagensBruscas);

            perfilCalculado.put("id_obd", idOBD);
            perfilCalculado.put("velocidade_media", String.valueOf(velocidadeMedia));
            perfilCalculado.put("rotacao_media", String.valueOf(rotacoesMedia));
            perfilCalculado.put("distancia_percorrida", String.valueOf(kilometros));
            perfilCalculado.put("horas_conducao", String.valueOf(tempoMedioViagem));
            perfilCalculado.put("aceleracoes_bruscas", String.valueOf(numeroAceleracoesBruscas));
            perfilCalculado.put("travagens_bruscas", String.valueOf(numeroTravagensBruscas));
            perfilCalculado.put("overall", String.valueOf(overall));

            submeterPerfilRisco(perfilCalculado);

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

    private double calcularOverall(double velocidadeMedia, double rotacoesMedia, double kilometros, double tempoMedioViagem, int numeroAceleracoesBruscas, int numeroTravagensBruscas) {
        System.out.println();
        double velocidadeOtima = Double.parseDouble(perfilRiscoOtimo.get("velocidade"));
        double rotacaoOtima = Double.parseDouble(perfilRiscoOtimo.get("rotacao"));
        double aceleracoesBruscasOtima = Double.parseDouble(perfilRiscoOtimo.get("aceleracao"));
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
        overall += (rotacoesMedia / rotacaoOtima) * penalizacaoRotacao;
        overall += (kilometros / distanciaPercorridaOtima) * penalizacaoDistanciaPercorrida;
        overall += (tempoMedioViagem / horasConducaoOtima) * penalizacaoHorasConducao;
        overall += (numeroAceleracoesBruscas / aceleracoesBruscasOtima) * penalizacaoAceleracoesBruscas;
        overall += (numeroTravagensBruscas / travagensBruscasOtima) * penalizacaoTravagensBruscas;

        return overall;

    }

    //Retorna o valor otimo de cada avriavel do perfil de risco
    public static Map<String, String> getPerfilBase() {
        Map<String, String> response = new HashMap<>();
        HttpRequest httpRequest = new HttpRequest();
        Gson gsonObject = new Gson();

        try {
            response = gsonObject.fromJson(httpRequest.getPerfilBase(), new TypeToken<HashMap<String, Object>>() {
            }.getType());

        } catch (IOException ex) {
            Logger.getLogger(CalculoPerfilRisco.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }

    //Submete o perfil de risco calculado
    public void submeterPerfilRisco(Map<String, String> perfilRiscoCalculado) {
        HttpRequest httpRequest = new HttpRequest();
        Gson gsonObject = new Gson();
        String mensagem = gsonObject.toJson(perfilRiscoCalculado);
        
        System.out.println(mensagem);

        try {
            httpRequest.submeterPerfilRisco(mensagem);
        } catch (IOException ex) {
            Logger.getLogger(CalculoPerfilRisco.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
