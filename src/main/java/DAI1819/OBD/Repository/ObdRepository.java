package DAI1819.OBD.Repository;

import DAI1819.OBD.entity.Obd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import org.springframework.stereotype.Component;

@RepositoryRestResource(path = "/api/obd")
@Component("ObdRepository")
public interface ObdRepository extends JpaRepository<Obd, String> {
      
}
