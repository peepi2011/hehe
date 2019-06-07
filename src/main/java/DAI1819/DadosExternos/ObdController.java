package DAI1819.DadosExternos;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@RestController
@Component("ObdController")
public class ObdController {

    @Autowired
    EntityManager em;

    @Autowired
    ObdRepository obdRepository;

    //Get the data of all users from databse and send them to the client
    @GetMapping("/api/obd")
    public List<Obd> readAll() {
        return obdRepository.findAll();
    }

    public List<Integer> readVelocidadesLast7DaysByIdObd(String idObd) {
        return obdRepository.findVelocidadeLast7DaysByidobd(idObd);
    }

    //Criar registo dos dados do OBD
    @PostMapping("/api/dadosExternos/obd/{idOBD}")
    @CrossOrigin(origins = "http://localhost")
    public ResponseEntity<String> create(@PathVariable(value = "idOBD") String idOBD, @RequestBody String data) {
        Gson gsonObject = new Gson();
        ArrayList<String> response = null;
        response = gsonObject.fromJson(data, ArrayList.class);

        for (Object jsonData : response) {
            Obd obd = new Obd();
            Map<String, String> obdRowData = new HashMap<>();
            String[] jsonDataItems = jsonData.toString().replace("{", "").replace("}", "").split(",");
            
            for (String str : jsonDataItems) {
                String[] finalStr = str.split("=");
                obdRowData.put(finalStr[0], finalStr[1]);

            }

            //Split date and Time
            String[] splitDateAndTime = obdRowData.get("Device Time").split(" ");
            String month = null;
            //Split date and Time
            String[] dateItems = splitDateAndTime[0].split("-");
            switch (dateItems[1]) {
                case "january":
                case "jan":
                    month = "01";
                    break;

                case "febuary":
                case "feb":
                case "fev":
                    month = "02";
                    break;

                case "march":
                case "mar":
                    month = "03";
                    break;

                case "april":
                case "apr":
                case "abr":
                    month = "04";
                    break;

                case "may":
                case "mai":
                    month = "05";
                    break;

                case "june":
                case "jun":
                    month = "06";
                    break;

                case "july":
                case "jul":
                    month = "07";
                    break;

                case "august":
                case "aug":
                case "ago":
                    month = "08";
                    break;

                case "september":
                case "sep":
                case "sept":
                case "set":
                    month = "09";
                    break;

                case "october":
                case "oct":
                case "out":
                    month = "10";
                    break;

                case "november":
                case "nov":
                    month = "11";
                    break;

                case "december":
                case "dec":
                case "dez":
                    month = "12";
                    break;
            }

            String formatDate = dateItems[2] + "-" + month + "-" + dateItems[0];

            //Create OBD ROW
            obd.setIdobd(idOBD);
            obd.setDataobd(formatDate);
            obd.setHora(splitDateAndTime[1].substring(0, splitDateAndTime[1].indexOf(".")));
            obd.setTotalkm(Double.valueOf(obdRowData.get(" Trip distance (stored in vehicle profile)(km)")).intValue());
            obd.setRotacoes(Integer.valueOf(obdRowData.get(" Engine RPM(rpm)")));
            obd.setTempoviagem(Double.valueOf(obdRowData.get(" Trip time(whilst moving)(s)")).intValue());
            obd.setVelocidade(Integer.valueOf(obdRowData.get(" Speed (OBD)(km/h)")));
            
            obdRepository.save(obd);           
        }
        return new ResponseEntity<>("Sucesso!", HttpStatus.OK);
    }

}
