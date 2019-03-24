package DAI1819.SAMIS.repository;

import DAI1819.SAMIS.entity.Apolice;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Component;

@RepositoryRestResource(path = "/api/apolices")
@Component("ApoliceRepository")
public interface ApoliceRepository extends JpaRepository<Apolice, Integer> {

}
