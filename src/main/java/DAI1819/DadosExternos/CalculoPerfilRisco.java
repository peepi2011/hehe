package DAI1819.DadosExternos;

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

            //Velocidades dos ultimos 7 dias
            List<Integer> velocidades = obdController.readVelocidadesLast7DaysByIdObd(idOBD);

            //Resultado das acelerações e das travagens
            int[] resultados = calcularAceleracaoTravagem(velocidades);

            //O primeiro elemento da lista de resultados é a aceleração
            numeroAceleracoesBruscas = resultados[0];
            //O segundo elemento da lista de resultados é a travagem
            numeroTravagensBruscas = resultados[1];

            //Calcula a percentagem que irá ser enquadrada nos perfis de risco
            overall = calcularOverall(velocidadeMedia, rotacoesMedia, kilometros, tempoMedioViagem, numeroAceleracoesBruscas, numeroTravagensBruscas);

            //Coloca num HashMap as variaveis calculadas
            perfilCalculado.put("id_obd", idOBD);
            perfilCalculado.put("velocidade_media", String.valueOf(velocidadeMedia));
            perfilCalculado.put("rotacao_media", String.valueOf(rotacoesMedia));
            perfilCalculado.put("distancia_percorrida", String.valueOf(kilometros));
            perfilCalculado.put("horas_conducao", String.valueOf(tempoMedioViagem));
            perfilCalculado.put("aceleracoes_bruscas", String.valueOf(numeroAceleracoesBruscas));
            perfilCalculado.put("travagens_bruscas", String.valueOf(numeroTravagensBruscas));
            perfilCalculado.put("overall", String.valueOf(overall));


            //Submete o perfil de risco calculado no servidor
            submeterPerfilRisco(perfilCalculado);

        }

    }

    //Calculo do numero de aceleracoes bruscas e travagens
    public int[] calcularAceleracaoTravagem(List<Integer> velocidades) {
        int[] results = new int[2];
        for(int i = 0; velocidades.size() >= 2; i++) {
            //velocidades vem de uma query que está order by data
            double valor1 = velocidades.get(0);
            double valor2 = velocidades.get(1);
            //positivo se for uma travagem; negativo se for aceleração
            double percentagem = ((valor1 - valor2) / valor1);
            if (percentagem > 0.5) {
                numTravagens++;
                results[0] = numTravagens;
            }
            if (percentagem < -0.25) {
                numAceleracoes++;
                results[1] = numAceleracoes;
            }
            velocidades.remove(0);
        }
        return results;
    }

    //Calculo da percentagem overall do perfil de risco
    private double calcularOverall(double velocidadeMedia, double rotacoesMedia, double kilometros, double tempoMedioViagem, int numeroAceleracoesBruscas, int numeroTravagensBruscas) {
        //Valores do perfilOtimo vindos da main app
        double velocidadeOtima = Double.parseDouble(perfilRiscoOtimo.get("velocidade"));
        double rotacaoOtima = Double.parseDouble(perfilRiscoOtimo.get("rotacao"));
        double aceleracoesBruscasOtima = Double.parseDouble(perfilRiscoOtimo.get("aceleracao"));
        double travagensBruscasOtima = Double.parseDouble(perfilRiscoOtimo.get("travagens"));
        double distanciaPercorridaOtima = Double.parseDouble(perfilRiscoOtimo.get("distancia"));
        double horasConducaoOtima = Double.parseDouble(perfilRiscoOtimo.get("horas"));

        //Percentagem que cada campo penaliza
        double penalizacaoVelocidade = 0.25;
        double penalizacaoRotacao = 0.15;
        double penalizacaoAceleracoesBruscas = 0.15;
        double penalizacaoTravagensBruscas = 0.15;
        double penalizacaoDistanciaPercorrida = 0.15;
        double penalizacaoHorasConducao = 0.15;

        //Sumatorio das percentagens que irá retornar um valor de 0 a 1 (percentagem)
        double overall = (velocidadeMedia / velocidadeOtima) * penalizacaoVelocidade;
        overall += (rotacoesMedia / rotacaoOtima) * penalizacaoRotacao;
        overall += (kilometros / distanciaPercorridaOtima) * penalizacaoDistanciaPercorrida;
        overall += (tempoMedioViagem / horasConducaoOtima) * penalizacaoHorasConducao;
        overall += (numeroAceleracoesBruscas / aceleracoesBruscasOtima) * penalizacaoAceleracoesBruscas;
        overall += (numeroTravagensBruscas / travagensBruscasOtima) * penalizacaoTravagensBruscas;

        return overall;

    }

    //Retorna o valor otimo de cada variavel do perfil de risco
    public static Map<String, String> getPerfilBase() {
        //Inicializar instancia para fazer o pedido HTTP e instancias para a resposta obtida e transforma la em um JSON
        Map<String, String> response = new HashMap<>();
        HttpRequest httpRequest = new HttpRequest();
        Gson gsonObject = new Gson();

        //Tentar obter o perfil base usando o pedido http, que vem em json e transformar em um Map<String, String>
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
        //Inicializar instancia para fazer o pedido HTTP e instancias para a resposta obtida e transforma la em um JSON
        HttpRequest httpRequest = new HttpRequest();
        Gson gsonObject = new Gson();
        String mensagem = gsonObject.toJson(perfilRiscoCalculado);

        try {
            httpRequest.submeterPerfilRisco(mensagem);
        } catch (IOException ex) {
            Logger.getLogger(CalculoPerfilRisco.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
