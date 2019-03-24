package DAI1819.SAMIS.repository;

import DAI1819.SAMIS.entity.PropostaApolice;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Component;

@RepositoryRestResource(path = "/api/propostaapolices")
@Component("PropostaApoliceRepository")
public interface PropostaApoliceRepository extends JpaRepository<PropostaApolice, Integer>  {
    
}
