package DAI1819.OBD.Controller;

import DAI1819.OBD.CalculoPerfilRisco;
import DAI1819.OBD.entity.ObdAverage;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import org.springframework.stereotype.Component;
import DAI1819.OBD.Repository.ObdAverageRepository;


@RestController
@Component("ObdAverageController")
public class ObdAverageController {

    @Autowired
    EntityManager em;

    @Autowired
    ObdAverageRepository obdAverageRepository;
    
    @Autowired
    ObdController obdcontroller;

    //Get the data of all users from databse and send them to the client
    @GetMapping("/api/obdaverage")
    public List<ObdAverage> readAll() {
        return obdAverageRepository.findAll();
    }
    public List<String> readAllOBDs() {
        return obdAverageRepository.findAllOBDS();
    }
   

}
