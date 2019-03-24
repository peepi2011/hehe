package DAI1819.SAMIS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import DAI1819.SAMIS.entity.Utilizador;
import org.springframework.stereotype.Component;

@RepositoryRestResource(path = "/api/users")
@Component("UtilizadorRepository")
public interface UtilizadorRepository extends JpaRepository<Utilizador, String> {


}

