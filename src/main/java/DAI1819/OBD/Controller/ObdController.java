package DAI1819.OBD.Controller;

import DAI1819.OBD.entity.Obd;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import org.springframework.stereotype.Component;
import DAI1819.OBD.Repository.ObdRepository;


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

}
