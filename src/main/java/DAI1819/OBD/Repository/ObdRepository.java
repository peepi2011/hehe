package DAI1819.OBD.Repository;

import DAI1819.OBD.entity.Obd;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import org.springframework.stereotype.Component;

@RepositoryRestResource(path = "/api/obd")
@Component("ObdRepository")
public interface ObdRepository extends JpaRepository<Obd, String> {
    
    //Lista de velocidades de um obd
    @Query(value ="SELECT velocidade FROM daiObd.obd where idobd =?1 AND (`daiObd`.`obd`.`dataobd` >= (CAST(NOW() AS DATE) - INTERVAL 7 DAY)) order by dataobd",nativeQuery = true)
    List<Integer> findVelocidadeLast7DaysByidobd(String idObd);
    
    
   
      
}
