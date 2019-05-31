package DAI1819.OBD.Repository;

import DAI1819.OBD.entity.ObdAverage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import org.springframework.stereotype.Component;

@RepositoryRestResource(path = "/api/obdaverage")
@Component("ObdAverageRepository")
public interface ObdAverageRepository extends JpaRepository<ObdAverage, String> {
    
    /*@Query(value = "SELECT a FROM ObdAverage a")
    List<ObdAverage> findAllOBDS();*/
      
}

